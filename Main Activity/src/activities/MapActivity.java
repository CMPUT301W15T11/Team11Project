package activities;

import model.GeoLocation;
import model.MapViewController;

import org.osmdroid.api.IMapController;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import com.example.team11xtremexpensetracker.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

public class MapActivity extends Activity {

	private IMapController mapController;
	private Marker current;
	private MapView map;
	private Button doneButton;
	private GeoLocation currentLocation;
	private GeoPoint currentGeoPoint;
	private GeoPoint currentPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		
		
		
		
		map = (MapView) findViewById(R.id.mapview);
		doneButton = (Button) findViewById(R.id.MapDone);

		map.setTileSource(TileSourceFactory.MAPNIK);

		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);

		mapController = map.getController();
		mapController.setZoom(30);
		
		MapViewController.initializeLocationManager(this);
		currentLocation = MapViewController.getLocation();
		
		currentPoint = new GeoPoint(currentLocation.getLatitude(),currentLocation.getLongitude());

		current = new Marker(map);
		current.setIcon(getResources().getDrawable((R.drawable.marker)));
		setMarker(current, currentPoint);

		mapController.setCenter(currentPoint);

		
		
		
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				currentPoint = current.getPosition();
				currentLocation.setLatLng(currentPoint.getLatitude(), currentPoint.getLongitude());
				GeoLocation test = new GeoLocation(53.526546, -113.528155);
				Toast.makeText(getApplicationContext(), String.valueOf(GeoLocation.distanceBetween(test,currentLocation)), Toast.LENGTH_LONG).show();
				/*Intent backIntent = new Intent(MapActivity.this, AddItemActivity.class);
				startActivity(backIntent);
				finish();*/
			}

		});
	}

	// if the user clicked on a already selected geolocation to get here

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	private void setMarker(Marker mark, GeoPoint gp) {
		mark.setPosition(gp);
		mark.setDraggable(true);
		mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
		mapController.setCenter(gp);
		map.getOverlays().add(mark);
	}

}
