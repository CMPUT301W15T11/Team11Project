package model;

import android.location.Location;
import android.util.FloatMath;

public class GeoLocation {
	private double latitude;
	private double longitude;

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;

	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double lng) {
		this.longitude = lng;

	}

	public void setLatitude(double lat) {
		this.latitude = lat;
	}

	public void setLatLng(double lat, double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}

	public static String toString(GeoLocation location) {
		return new String(location.latitude + "," + location.longitude);
	}

	public static GeoLocation toGeoLocation(String geolocation) {
		String[] parts = geolocation.split(",");
		GeoLocation gl = new GeoLocation(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
		return gl;
	}

	public static float distanceBetween(GeoLocation location1, GeoLocation location2) {

		Location locationA = new Location("point A");

		locationA.setLatitude(location1.getLatitude());
		locationA.setLongitude(location1.getLongitude());

		Location locationB = new Location("point B");

		locationB.setLatitude(location2.getLatitude());
		locationB.setLongitude(location2.getLongitude());

		float distance = locationA.distanceTo(locationB);
		return distance;
	}
}
