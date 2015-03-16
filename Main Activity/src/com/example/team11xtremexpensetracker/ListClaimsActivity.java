package com.example.team11xtremexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ListClaimsActivity extends Activity {
	private ClaimsList datafile;
	//private ListClaimsAdapter listClaimAdapter;
	private ListView claimsListView;
	private ImageView searchImage;
	private ClaimListController clc;
	private EditText tagSearchEdit;
	private int onLongClickPos;
	private Collection<ExpenseClaim> claims; 
	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_claims);
		claimsListView = (ListView) findViewById(R.id.claimsListView); 
		searchImage=(ImageView)findViewById(R.id.tag_search);
		tagSearchEdit=(EditText)findViewById(R.id.tag_filter);
		
		dataList=this.loadFromFile();
		clc = new ClaimListController(); 
		clc.setClaimsList(dataList);
		claims = clc.getClaimsList().getClaims(); 
		final ArrayList<ExpenseClaim> list = new ArrayList<ExpenseClaim>(claims);
		final ArrayAdapter<ExpenseClaim> claimAdapter = new ArrayAdapter<ExpenseClaim>(this, android.R.layout.simple_expandable_list_item_1, list);
		claimsListView.setAdapter(claimAdapter);
		
		clc.getClaimsList().addListener(new Listener () {
			@Override
			public void update(){
				list.clear();
				Collection<ExpenseClaim> claims = (clc.getClaimsList().getClaims());
				if(claims==null){
					return;
				}
				list.addAll(claims);
				claimAdapter.notifyDataSetChanged();
			}
		});
		
		searchImage.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String searchingTag=tagSearchEdit.getText().toString();
				searchingTag.toLowerCase();
				if(searchingTag.equals("")){
					list.clear();
					Collection<ExpenseClaim> claims = (clc.getClaimsList().getClaims());
					list.addAll(claims);
					claimAdapter.notifyDataSetChanged();
					return;
				}
				ArrayList<ExpenseClaim> claims = (clc.getClaimsList().getClaimsAL());
				list.clear();
				for(int i=0;i<claims.size();i++){
					for(int j=0;j<claims.get(i).getTagList().size();j++){
						if(searchingTag.equals(claims.get(i).getTagList().get(j).getTagName().toLowerCase())){
							list.add(claims.get(i));
						}
					}
				}
				claimAdapter.notifyDataSetChanged();
			}
			
		});
		
		
		claimsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				String temp = list.get(position).toString();
				Toast.makeText(ListClaimsActivity.this, "You Clicked " + temp, Toast.LENGTH_SHORT).show();
				//Now make the same intent push as in the addClaimActivity.
				saveInFile();
				Intent intent = new Intent(ListClaimsActivity.this, ViewClaimActivity.class);
				intent.putExtra("claimID", position);
				startActivity(intent);
			}
		});
		
		
		
		
		claimsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				onLongClickPos = position;
				AlertDialog.Builder adb = new AlertDialog.Builder(ListClaimsActivity.this);
				adb.setMessage("Delete?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						ClaimListController.removeClaim(onLongClickPos);
						
						claimAdapter.clear();
						for (int i=0;i<ClaimListController.getClaimsList().getLength();i++){
							claimAdapter.add(ClaimListController.getClaimsList().getClaimById(i));
						}
						
						saveInFile();
						dataList=loadFromFile();
						ClaimListController.setClaimsList(dataList);
						claimAdapter.notifyDataSetChanged();
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
		getMenuInflater().inflate(R.menu.list_claims, menu);
		return true;
	}
	
	public void addClaim(View v){
		//Toast.makeText(this, "Adding Claim", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(ListClaimsActivity.this, AddClaimActivity.class);
		intent.putExtra("claimID", -1);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed(){
		Intent intentBackPressed = new Intent();
		intentBackPressed.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentBackPressed.setClass(ListClaimsActivity.this, MainActivity.class);
		startActivity(intentBackPressed);
	}
	
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
