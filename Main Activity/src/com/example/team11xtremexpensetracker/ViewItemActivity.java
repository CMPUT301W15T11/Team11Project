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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
	private Item list;
	private boolean indicator;
	private CheckBox completeness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_item);
		completeness = (CheckBox)findViewById(R.id.completed);	
		
		addListenerOnChkIos();
		//==========================================================================================================
		//============================load and display
		
		
		
		Intent intent = getIntent();
		final int itemID = intent.getIntExtra("itemID",0);
		final int claimID = intent.getIntExtra("claimID", 0);
		list= ClaimListController.getClaimsList().getClaimById(claimID).getItemById(itemID);
		indicator = list.getIndecator();
		if (indicator == false){
			//Toast.makeText(ViewItemActivity.this, "first not Checked", Toast.LENGTH_SHORT).show();
			
		}else{
			completeness.setChecked(true);
			//Toast.makeText(ViewItemActivity.this, "first Checked", Toast.LENGTH_SHORT).show();
		}
		
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
		
		itemamountstr = list.getAmount().toString();
		itemamount = (TextView)findViewById(R.id.amountspentView);
		itemamount.setText(itemamountstr);
		
		

		
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
	
	public void addListenerOnChkIos() {
		
		completeness.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v)
			{

				// TODO Auto-generated method stub
				
			
			if (((CheckBox)v).isChecked()){
				Toast.makeText(ViewItemActivity.this, "Checked", Toast.LENGTH_SHORT).show();
				list.setIndecator(true);
				
			}
			else{
				list.setIndecator(false) ;
				Toast.makeText(ViewItemActivity.this, "Not Checked", Toast.LENGTH_SHORT).show();
			}
		}
		});
	}
	


	

}
