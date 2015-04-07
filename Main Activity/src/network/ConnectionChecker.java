package network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The instance of this class is to use for checking that network is connected or not
 * @author Mingtuo
 *
 */
public class ConnectionChecker {

	
	public ConnectionChecker(){}

	/**
	 * This function decide to only save file locally or also update to online database
	 * @param activity
	 * @return boolean
	 */
	public boolean netConnected(Activity activity) {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileNetwork = connectivityManager.getActiveNetworkInfo();
		if(mobileNetwork==null){
			return false;
		}
		if(mobileNetwork.isConnectedOrConnecting()){
			return true;
		}else{
			return false;
		}
	}
}
