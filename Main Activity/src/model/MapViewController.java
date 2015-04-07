package model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
/**
 * Controller for maps view
 * @author Stin
 *
 */
public class MapViewController {
	private static Location currentLocation = null;
	private static LocationManager lm = null;
	public static final int GET_GEOLOCATION_CODE = 10;

	public static void initializeLocationManager(Context context) {
		if (lm == null) {
			lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {

				@Override
				public void onLocationChanged(Location location) {
					if (location != null) {
						if (currentLocation == null) {
							currentLocation = new Location(location);
						} else {
							currentLocation.set(location);
						}
					}
				}

				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub

				}
			});
		}
	}
	/**
	 * get geolocation
	 * @return GeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude())
	 */
	public static GeoLocation getLocation() {
		if (lm == null) {
			throw new RuntimeException("Location manager was not initialized");
		}
		if (currentLocation == null) {
			// referenced https://github.com/joshua2ua/MockLocationTester on
			// March 26th 2015
			currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (currentLocation == null) {
				return new GeoLocation(53.526546, -113.528155);
			}
		}
		return new GeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
	}

}
