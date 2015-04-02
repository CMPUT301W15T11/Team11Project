package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.example.team11xtremexpensetracker.ExpenseClaim;
import com.example.team11xtremexpensetracker.UserController;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class Client {

	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t11/Claims/_search";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t11/";
	private static final String LOG_TAG = "ClaimSearch";

	private Gson gson;

	public Client() {
		gson = new Gson();
	}

	public ExpenseClaim getClaim(String claimName) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + "Claims/" + claimName + "/");
		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			SearchHit<ExpenseClaim> sr = parseClaimHit(response);
			return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public void addClaim(final ExpenseClaim claim) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Gson gson = new Gson();
				HttpClient httpClient = new DefaultHttpClient();
				try {

					HttpPost addRequest = new HttpPost(RESOURCE_URL + "Claims/" + claim.getName() + "/");
					StringEntity stringEntity = new StringEntity(gson.toJson(claim));
					addRequest.setEntity(stringEntity);
					addRequest.setHeader("Accept", "application/json");

					HttpResponse response = httpClient.execute(addRequest);

					String status = response.getStatusLine().toString();
					Log.i(LOG_TAG, status);

				} catch (JsonIOException e) {
					throw new RuntimeException(e);

				} catch (JsonSyntaxException e) {
					throw new RuntimeException(e);

				} catch (IllegalStateException e) {
					throw new RuntimeException(e);

				} catch (IOException e) {
					throw new RuntimeException(e);
				}

			}
		}).start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteClaim(final String claimName) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				HttpClient httpClient = new DefaultHttpClient();
				try {
					HttpDelete deleteRequest = new HttpDelete(RESOURCE_URL + "Claims/" + claimName + "/");
					deleteRequest.setHeader("Accept", "application/json");

					HttpResponse response = httpClient.execute(deleteRequest);

					String status = response.getStatusLine().toString();
					Log.i(LOG_TAG, status);

				} catch (JsonIOException e) {
					throw new RuntimeException(e);

				} catch (JsonSyntaxException e) {
					throw new RuntimeException(e);

				} catch (IllegalStateException e) {
					throw new RuntimeException(e);

				} catch (IOException e) {
					throw new RuntimeException(e);
				}

			}
		}).start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<ExpenseClaim> getApproverClaimList() {
		String searchString = "Submitted";
		ArrayList<ExpenseClaim> claims = new ArrayList<ExpenseClaim>();
		if (searchString == null || "".equals(searchString)) {
			searchString = "*";
		}
		try {
			HttpPost searchRequest = createSearchRequest(searchString);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(searchRequest);

			String status = response.getStatusLine().toString();

			SearchResponse<ExpenseClaim> esResponse = parseSearchResponse(response);
			Hits<ExpenseClaim> hits = esResponse.getHits();

			if (hits != null) {
				if (hits.getHits() != null) {
					for (SearchHit<ExpenseClaim> sesr : hits.getHits()) {
						claims.add((sesr.getSource()));
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < claims.size(); i++) {
			if (claims.get(i).getClaimantName().equals(UserController.getUserName())) {
				claims.remove(i);
			}
		}

		return claims;
	}

	private SearchHit<ExpenseClaim> parseClaimHit(HttpResponse response) {
		try {
			String json = getEntityContent(response);
			Type searchHitType = new TypeToken<SearchHit<ExpenseClaim>>() {
			}.getType();

			SearchHit<ExpenseClaim> sr = gson.fromJson(json, searchHitType);
			return sr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private HttpPost createSearchRequest(String searchString) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		HttpPost searchRequest = new HttpPost(SEARCH_URL);
		SimpleSearchCommand command = new SimpleSearchCommand(searchString);

		String query = command.getJsonCommand();
		Log.i(LOG_TAG, "Json command: " + query);

		StringEntity stringEntity;
		stringEntity = new StringEntity(query);

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);

		return searchRequest;
	}

	public static String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}

	private SearchResponse<ExpenseClaim> parseSearchResponse(HttpResponse response) throws IOException {
		String json;
		json = getEntityContent(response);

		Type searchResponseType = new TypeToken<SearchResponse<ExpenseClaim>>() {
		}.getType();

		SearchResponse<ExpenseClaim> esResponse = gson.fromJson(json, searchResponseType);

		return esResponse;
	}

}