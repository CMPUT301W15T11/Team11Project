package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import model.ExpenseClaim;
import model.UserController;

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

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * This class is for connecting with database
 * @author Mingtuo
 *
 */

public class Client {

	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t11/Claims/_search";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t11/";
	private static final String LOG_TAG = "ClaimSearch";

	private Gson gson;
	private HttpClient httpClient;

	/**
	 * 
	 *  Constructor
	 */
	public Client() {
		gson = new Gson();
		httpClient = new DefaultHttpClient();
	}

	/**
	 * 
	 * search claim by its name
	 * @param claimName
	 * @return ExpenseClaim
	 */
	public ExpenseClaim getClaim(String claimName) {
		// TODO Auto-generated method stub
		HttpGet getRequest = new HttpGet(RESOURCE_URL + "Claims/" + claimName + "/");
		HttpResponse getResponse;
		try {
			getResponse = httpClient.execute(getRequest);
			String json = getEntityContent(getResponse);
			Type searchHitType = new TypeToken<SearchHit<ExpenseClaim>>() {
			}.getType();
			SearchHit<ExpenseClaim> esResponse = gson.fromJson(json, searchHitType);
			return esResponse.getSource();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * add or UPDATE claim by passing the ExpenseClaim object
	 * @param claim
	 * @throws IllegalStateException
	 */
	public void addClaim(final ExpenseClaim claim) throws IllegalStateException {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Gson gson = new Gson();
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

				} catch (ClientProtocolException e) {
					throw new RuntimeException(e);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		thread.start();
		try {
			Thread.sleep(500);
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Delete the ExpenseClaim by its name
	 * @param claimName
	 */
	public void deleteClaim(final String claimName) {

		new Thread(new Runnable() {

			@Override
			public void run() {

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
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get all submitted for user who log in as approver
	 * @return ArrayList<ExpenseClaim>
	 */
	public ArrayList<ExpenseClaim> getApproverClaimList() {
		String searchString = "Submitted";
		ArrayList<ExpenseClaim> claims = new ArrayList<ExpenseClaim>();
		if (searchString == null || "".equals(searchString)) {
			searchString = "*";
		}
		try {
			HttpPost searchRequest = createSearchRequest(searchString);
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

	/**
	 * This one is for download data from online database when every time enter ListClaimsActivity
	 * @return ArrayList<ExpenseClaim>
	 */
	public ArrayList<ExpenseClaim> syncLocalFileList() {
		String searchString = "*";
		ArrayList<ExpenseClaim> claims = new ArrayList<ExpenseClaim>();

		try {
			HttpPost searchRequest = createSearchRequest(searchString);
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

		return claims;
	}

	/**
	 * Copied from lab code
	 * @param response
	 * @return SearchHit<ExpenseClaim>
	 */
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

	/**
	 * copied from lab code
	 * @param searchString
	 * @return HttpPost
	 * @throws UnsupportedEncodingException
	 */
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