package com.example.team11xtremexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewItemActivity extends Activity{
	private static final String FILENAME = "save.sav";
	private ClaimsList datafile;
	private TextView itemname;
	private String itemnamestr;
	private String itemunitstr;
	private TextView itemunit;
	private TextView itemcategory;
	private String itemcategorystr;
	private String itemamountstr;
	private TextView itemamount;
	private String itemdescriptionstr;
	private TextView itemdescription;
	private Calendar itemdatestr;
	private TextView itemdate;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_item);
		//==========================================================================================================
		//============================load and display
		//datafile = loadFromFile();
		Intent intent = getIntent();
		final int itemID = intent.getIntExtra("itemID",0);
		final int claimID = intent.getIntExtra("claimID", 0);
		Item list= ClaimListController.getClaimsList().getClaimById(claimID).getItemById(itemID);
		
		itemnamestr = list.getItem().toString();
		itemname = (TextView)findViewById(R.id.itemnameView);
		itemname.setText(itemnamestr);
		
		itemunitstr = list.getUnit().toString();
		itemunit = (TextView)findViewById(R.id.unitView);
		itemunit.setText(itemunitstr);
		
		itemcategorystr = list.getCategory().toString();
		itemcategory = (TextView)findViewById(R.id.categoryView);
		itemcategory.setText(itemcategorystr);
		
		itemdescriptionstr = list.getDescription().toString();
		itemdescription = (TextView)findViewById(R.id.descriptionView);
		itemdescription.setText(itemdescriptionstr);
		
		itemdatestr = list.getDate();
		String textString_start = String.format("%d/%d/%d", itemdatestr.get(Calendar.MONTH) + 1,itemdatestr.get(Calendar.DATE), itemdatestr.get(Calendar.YEAR));
		itemdate = (TextView)findViewById(R.id.itemdateview);
		itemdate.setText(textString_start);
		
		
		//==========================================================================================================
		//============================connect to edit view
	
	
	Button edititem = (Button) findViewById(R.id.edit);
	edititem.setOnClickListener(new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ViewItemActivity.this,EditItemActivity.class);
			intent.putExtra("itemID", itemID);
			startActivity(intent);
			
		}
		
		
		
		
	});
	
	//==========================================================================================================
	//============================connect to view list
	Button backtoitemlist = (Button)findViewById(R.id.backtoitemlist);
	backtoitemlist.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent backIntent = new Intent(ViewItemActivity.this,ViewClaimActivity.class);
			startActivity(backIntent);
			
		}
	});
	}
	
	//==========================================================================================================
	//============================Gson	
	/*
	private ClaimsList loadFromFile(){
		Gson gson = new Gson();
		datafile = new ClaimsList();
		try{
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<ClaimsList>(){}.getType();
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
	*/

	

}
