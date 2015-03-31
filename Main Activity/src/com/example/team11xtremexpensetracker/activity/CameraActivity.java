package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class CameraActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		Context context = this;
		if (checkCameraHardware(context)==true){
			Log.i("camera","This device has a camera.");
		}
		else{
			Log.i("camera", "This device does not have a camera.");
		}
	}

		/** Check if this device has a camera */
	private boolean checkCameraHardware(Context c){
		if (c.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
			}
		}
}
