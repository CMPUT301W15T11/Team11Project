package server;

import save_and_load.GsonCreator;

import com.google.gson.Gson;

public class NetworkIOStreamHandler {
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t11/";
	public static final String LOG_TAG = "Elastic Search";

	private Gson gson = (new GsonCreator()).getGson();
	
	public NetworkIOStreamHandler(){};
	
}
