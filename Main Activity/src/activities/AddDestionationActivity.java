package activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import model.ClaimListController;
import model.ClaimsList;
import model.DestListAdapter;
import model.Destination;
import model.ExpenseClaim;
import model.Item;
import model.ItemlistAdapter;

import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.layout;
import com.example.team11xtremexpensetracker.R.menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class AddDestionationActivity extends Activity {

	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";
	private ClaimListController clc;
	private int claimID;
	private ExpenseClaim currentClaim;
	private ListView existingDestsListView;
	private EditText newReasonEditText;
	private EditText newDestEditText;
	private Button addDestButton;
	private ArrayList<Destination> list;
	private DestListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_destionation);
		
		dataList=this.loadFromFile();
		ClaimListController.setClaimsList(dataList);
		Intent intent = getIntent();
		claimID = intent.getIntExtra("claimID", -1);
		//Toast.makeText(this, "Claim: " + (new Integer(claimID).toString()), Toast.LENGTH_SHORT).show();
		if (claimID >= 0){
			//new ClaimListController();
			currentClaim = ClaimListController.getClaimsList().getClaimById(claimID);
		}
		
		// get ids
		addDestButton = (Button) findViewById(R.id.addDest);
		newDestEditText = (EditText) findViewById(R.id.newDestEditText);
		newReasonEditText = (EditText) findViewById(R.id.newReasonEditText);
		existingDestsListView = (ListView) findViewById(R.id.existingDestsListView);
		
		list= currentClaim.getDestinations();
		listAdapter = new DestListAdapter(this, list);
		existingDestsListView.setAdapter(listAdapter);
		
		// create listeners
		addDestButton.setOnClickListener(new View.OnClickListener() {	
	
			@Override
			public void onClick(View v) {
				
				String newName = newDestEditText.getText().toString();
				String newReason = newReasonEditText.getText().toString();
				Destination newDest = new Destination(newName);
				newDest.setReason(newReason);
				
				list.add(newDest);
				
				saveInFile();
				dataList=loadFromFile();
				ClaimListController.setClaimsList(dataList);
				newDestEditText.setText("");
				newReasonEditText.setText("");
				
				listAdapter.notifyDataSetChanged();
				
			}
			
		});
		
		
		
		existingDestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			private int onLongClickPos;

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				onLongClickPos = position;
				AlertDialog.Builder adb = new AlertDialog.Builder(AddDestionationActivity.this);
				adb.setMessage("Delete?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						list.remove(onLongClickPos);
						
						saveInFile();
						dataList=loadFromFile();
						ClaimListController.setClaimsList(dataList);
					
						listAdapter.notifyDataSetChanged();
						
					}
					
				});
				adb.setNegativeButton("Cancel", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
					
				});
				adb.show();
				return true;
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_destionation, menu);
		return true;
	}
	
	
	@Override
	public void onBackPressed(){
		Intent intentBackPressed = new Intent();
		intentBackPressed.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentBackPressed.setClass(AddDestionationActivity.this, ViewClaimActivity.class);
		intentBackPressed.putExtra("claimID", claimID);
		AddDestionationActivity.this.startActivity(intentBackPressed);
	}
	
	//Populate the listView with the destinations in the claim
	//If an item in the list gets hold-clicked it should be deleted
	//if an item gets clicked should bring to a destination detail view
	//Also need somewhere to add "Reason for Travel"
	
	private ClaimsList loadFromFile() {
		Gson gson = new Gson();
		dataList = new ClaimsList();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<ClaimsList>() {
			}.getType();
			dataList = gson.fromJson(in, typeOfT);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataList;
	}

	private void saveInFile() {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(ClaimListController.getClaimsList(), osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
