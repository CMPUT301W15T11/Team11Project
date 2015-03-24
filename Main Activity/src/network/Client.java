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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.example.team11xtremexpensetracker.ExpenseClaim;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Client implements DataManager {

	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t11/_search";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t11/";
	private static final String TAG = "ClaimSearch";

	private Gson gson;

	public Client() {
		gson = new Gson();
	}

	
	public ArrayList<ExpenseClaim> searchClaim(String searchString, String field) {
		// TODO Auto-generated method stub
		ArrayList<ExpenseClaim> result = new ArrayList<ExpenseClaim>();
		HttpClient httpClint = new DefaultHttpClient();
		try {
			HttpPost httpPost = createSearchRequest(searchString, field);

			HttpResponse response;
			response = httpClint.execute(httpPost);
			SearchResponse<ExpenseClaim> sr = parseSearchResponse(response);
			Hits<ExpenseClaim> hits = sr.getHits();
			result.add(hits.getHits().get(1).getSource());
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;

	}

	@Override
	public ExpenseClaim getClaim(int id) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + id);

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

	@Override
	public void addClaim(ExpenseClaim claim) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + claim.getName());

			StringEntity stringEntity = new StringEntity(gson.toJson(claim));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteClaim(int id) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpDelete deleteRequest = new HttpDelete(RESOURCE_URL + id);
			deleteRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(deleteRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private HttpPost createSearchRequest(String searchString, String field) throws UnsupportedEncodingException {

		HttpPost searchRequest = new HttpPost(SEARCH_URL);

		String[] fields = null;
		if (field != null) {
			fields = new String[1];
			fields[0] = field;
		}

		SimpleSearchCommand command = new SimpleSearchCommand(searchString, fields);

		String query = command.getJsonCommand();
		Log.i(TAG, "Json command: " + query);

		StringEntity stringEntity;
		stringEntity = new StringEntity(query);

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);

		return searchRequest;
	}
	
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
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


	/*
	 * code on eClass is bullshit :3
	 * 
	 * 
	 * 
	 * private HttpClient httpclient = new DefaultHttpClient();
	 * 
	 * private Gson gson = new Gson();
	 * 
	 * public void insertClaim(ExpenseClaim claim) throws IllegalStateException,
	 * IOException {
	 * 
	 * HttpPost httpPost = new
	 * HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w15t11/" +
	 * claim.getName()); StringEntity stringentity = null;
	 * 
	 * try { stringentity = new StringEntity(gson.toJson(claim)); } catch
	 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * httpPost.setHeader("Accept", "application/json");
	 * 
	 * httpPost.setEntity(stringentity); HttpResponse response = null; try {
	 * response = httpclient.execute(httpPost); } catch (ClientProtocolException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * String status = response.getStatusLine().toString();
	 * System.out.println(status); HttpEntity entity = response.getEntity();
	 * BufferedReader br = new BufferedReader(new
	 * InputStreamReader(entity.getContent())); String output;
	 * System.err.println("Output from Server -> "); while ((output =
	 * br.readLine()) != null) { System.err.println(output); }
	 * 
	 * // bug may exist if (entity != null) {
	 * 
	 * httpPost.abort(); }
	 * 
	 * // HttpRequestBase.releaseConnection(); the sample code is wrong }
	 * 
	 * public void getnClaim(){ try{ HttpGet getRequest = new
	 * HttpGet("http://cmput301.softwareprocess.es:8080/cmput301w15t11/"
	 * );//S4bRPFsuSwKUDSJImbCE2g?pretty=1
	 * 
	 * getRequest.addHeader("Accept","application/json");
	 * 
	 * HttpResponse response = httpclient.execute(getRequest);
	 * 
	 * String status = response.getStatusLine().toString();
	 * System.out.println(status);
	 * 
	 * String json = response.getEntity().toString(); //***
	 * 
	 * java.lang.reflect.Type elasticSearchResponseType = new
	 * TypeToken<ElasticSearchResponse<ExpenseClaim>>(){}.getType();
	 * 
	 * ElasticSearchResponse<ExpenseClaim> esResponse = gson.fromJson(json,
	 * elasticSearchResponseType);
	 * 
	 * ExpenseClaim claim = esResponse.getSource();
	 * System.out.println(claim.toString()); // getRequest.releaseConnection();
	 * same issue here
	 * 
	 * } catch (ClientProtocolException e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * } catch (IOException e) {
	 * 
	 * e.printStackTrace(); } }
	 * 
	 * public void searchClaim(String str) throws ClientProtocolException,
	 * IOException { HttpGet searchRequest = new HttpGet(
	 * "http://cmput301.softwareprocess.es:8080/testing/lab02/_search?pretty=1&q="
	 * + java.net.URLEncoder.encode(str,"UTF-8"));
	 * searchRequest.setHeader("Accept","application/json"); HttpResponse
	 * response = httpclient.execute(searchRequest); String status =
	 * response.getStatusLine().toString(); System.out.println(status);
	 * 
	 * String json = response.getStatusLine().toString();
	 * 
	 * java.lang.reflect.Type elasticSearchSearchResponseType = new
	 * TypeToken<ElasticSearchSearchResponse<ExpenseClaim>>(){}.getType();
	 * ElasticSearchSearchResponse<ExpenseClaim> esResponse =
	 * gson.fromJson(json, elasticSearchSearchResponseType);
	 * System.err.println(esResponse); for (ElasticSearchResponse<ExpenseClaim>
	 * r : esResponse.getHits()) { ExpenseClaim claim = r.getSource();
	 * System.err.println(claim); } //searchRequest.releaseConnection(); }
	 */
}
