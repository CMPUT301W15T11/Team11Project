//==========================================================================================================
//This is the main activity of my part (Expense Item.). A claim Position should be intended to me
//so that I can load its correspond expense item. So that I did not write all the load and save function since i need to change them later
//after i get the claim position.
//I have wrote getItem (Item name),getCategory and getUnit.
//I have four activity: AdditemActivity, EditItemActivity, ViewItemActivity and this page:ItemlistActivity
//I have two three other classes: item, itemlist and itemlistAdapter
//==========================================================================================================
package com.example.team11xtremexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class ItemlistActivity extends Activity {
	private static final String FILENAME = "save.sav";
	private Itemlist datafile;
	private ItemlistAdapter itemlistAdapter;
	private ListView itemlistview;
	//==========================================================================================================
	//============================I set an adapter(itemlistAdaptet) to the itemlist
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		datafile = this.loadFromFile();
		
		itemlistAdapter = new ItemlistAdapter(this,datafile.getItemlist());
		itemlistview.setAdapter(itemlistAdapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itemlist);
		//datafile = this.loadFromFile();
		itemlistview = (ListView)findViewById(R.id.itemlistView);
		//==========================================================================================================
		//============================click to add item
		Button additem = (Button)findViewById(R.id.additem);
		additem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =  new Intent(ItemlistActivity.this,AddItemActivity.class);
				startActivity(intent);
				
			}
		});
		//==========================================================================================================
		//============================intent back to claim,line 74 should be change to 
		//============================"Intent backIntent = new Intent(ItemlistActivity.this,<NAME OF CLAIM CLASS>.class);"
		Button backtoclaim = (Button)findViewById(R.id.backtoclaim);
		backtoclaim.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent backIntent = new Intent(ItemlistActivity.this,ItemlistActivity.class);
				startActivity(backIntent);
				
			}
		});
		//==========================================================================================================
		//============================click item to view this item
		
		itemlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int itemposition, long id) {
				// TODO Auto-generated method stub
				final int itemID = itemposition;
				
				Intent intent = new Intent(ItemlistActivity.this,ViewItemActivity.class);
				intent.putExtra("itemID",itemID);
				startActivity(intent);
				
			}
		});
		
		//==========================================================================================================
		//============================long click to delete	
		itemlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int itemID = position;
				AlertDialog.Builder adb = new AlertDialog.Builder(ItemlistActivity.this);
				adb.setMessage("Delete?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Item item = datafile.getItemlist().get(itemID);
						datafile.getItemlist().remove(item);
						saveInFile();
						itemlistAdapter.clear();
						datafile = loadFromFile();
						itemlistAdapter.addAll(datafile.getItemlist());
						itemlistAdapter.notifyDataSetChanged();
						
						
						
					}
					
				});
				adb.setNegativeButton("Cancel", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
					
				});

				
				adb.show();
				return false;
			}
		});
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//==========================================================================================================
	//============================Gson
	private Itemlist loadFromFile(){
		Gson gson = new Gson();
		datafile = new Itemlist();
		try{
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<Itemlist>(){}.getType();
			datafile = gson.fromJson(in, typeOfT);
			fis.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch (IOException e){
			e.printStackTrace();
		}
		return datafile;
	}
	private void saveInFile(){
		Gson gson = new Gson();
		try{
			FileOutputStream fos = openFileOutput(FILENAME,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(datafile,osw);
			osw.flush();
			fos.close();
			
			
		} catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
		
		
	}
}