package model;

import android.location.Location;
import android.util.FloatMath;
/**
 * geolocation structure, used to store geolocation
 * @author Stin
 *
 */
public class GeoLocation {
	private double latitude;
	private double longitude;

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;

	}
	/**
	 * get latitude
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * get longitude
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * set longitude
	 * @param lng
	 */
	public void setLongitude(double lng) {
		this.longitude = lng;

	}
	/**
	 * set latitude
	 * @param lat
	 */
	public void setLatitude(double lat) {
		this.latitude = lat;
	}
	/**
	 * set both longitude and latitude
	 * @param lat
	 * @param lng
	 */
	public void setLatLng(double lat, double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}
	/**
	 * convert data to string
	 * @param location
	 * @return String(location.latitude + "," + location.longitude)
	 */
	public static String toString(GeoLocation location) {
		return new String(location.latitude + "," + location.longitude);
	}
	/**
	 * set string to geolocation
	 * @param geolocation
	 * @return gl
	 */
	public static GeoLocation toGeoLocation(String geolocation) {
		String[] parts = geolocation.split(",");
		GeoLocation gl = new GeoLocation(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
		return gl;
	}
	/**
	 * get distance between to locations
	 * @param location1
	 * @param location2
	 * @return distance
	 */
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
