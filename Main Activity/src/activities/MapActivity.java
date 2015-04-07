package activities;

import model.GeoLocation;
import model.MapViewController;
import model.UserController;

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
	private int mapMode;
	private int claimID;
	private int itemID;
	private int destID;

	/*
	 * Map Picker:
	 * mode 1:pick home location
	 * mode 2:pick expense item location
	 * mode 3:pick destination location
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		
		Intent intent = getIntent();
		mapMode = intent.getIntExtra("mapMode", 0);
		
		if (mapMode == 2){
			claimID = intent.getIntExtra("claimID", -1);
			destID = intent.getIntExtra("destID", -1);
		}
		
		
		map = (MapView) findViewById(R.id.mapview);
		doneButton = (Button) findViewById(R.id.MapDone);

		map.setTileSource(TileSourceFactory.MAPNIK);

		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);

		mapController = map.getController();
		mapController.setZoom(30);
		
		MapViewController.initializeLocationManager(this);
		
		if (mapMode == 1){
			if (UserController.getHomeLocation()!=null){
				currentLocation = GeoLocation.toGeoLocation(UserController.getHomeLocation());
				//Toast.makeText(getApplicationContext(), UserController.getHomeLocation(), Toast.LENGTH_LONG).show();
			}
			else{
				currentLocation = MapViewController.getLocation();
			}
		}
		else if (mapMode == 2){
			currentLocation = MapViewController.getLocation();
		}
		else if (mapMode == 3){
			
		}
		else{
			currentLocation = MapViewController.getLocation();
		}
		
		
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
				
				switch (mapMode){
				
				case 1:
					Intent intent1 = new Intent(MapActivity.this, ListClaimsActivity.class);
					UserController.setHomeLocation(GeoLocation.toString(currentLocation));
					startActivity(intent1);
					
				case 2:
					Intent intent2 = new Intent();
					intent2.setClass(MapActivity.this, AddDestinationActivity.class);
					intent2.putExtra("claimID", claimID);
					intent2.putExtra("locationStr", GeoLocation.toString(currentLocation));
					MapActivity.this.startActivity(intent2);
				case 3:
				
				case 0:
					finish();
				}
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
