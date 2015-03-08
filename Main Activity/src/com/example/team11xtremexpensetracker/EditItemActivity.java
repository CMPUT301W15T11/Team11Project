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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class EditItemActivity extends Activity{
	private static final String FILENAME = "save.sav";
	private Itemlist datafile;
	private EditText itemname;
	private String itemnamestr;
	private Item currentitem;
	private Spinner categoryspinner;
	private ArrayAdapter<String> categoryAdapter;
	private Spinner unitspinner;
	private ArrayAdapter<String> unitAdapter;
	private Button edititemdate;
	private Calendar editdate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		//==========================================================================================================
		//============================create spinner
		
		categoryspinner = (Spinner)findViewById(R.id.editcategory);
		ArrayList<String> categorylist	= new ArrayList<String>();
		categorylist.add("Air Fare");
		categorylist.add("Ground Transport");
		categorylist.add("Vehicle Rental");
		categorylist.add("Private Automobile");
		categorylist.add("Fuel");
		categorylist.add("Parking");
		categorylist.add("Registration");
		categorylist.add("Accommodation");
		categorylist.add("Meal");
		categorylist.add("Supplies");
		
		categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categorylist);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categoryspinner.setAdapter(categoryAdapter);
		
		unitspinner = (Spinner)findViewById(R.id.editunit);
		ArrayList<String> unitlist	= new ArrayList<String>();
		unitlist.add("CAD");
		unitlist.add("USD");
		unitlist.add("EUR");
		unitlist.add("GBP");
		unitlist.add("CHF");
		unitlist.add("JPY");
		unitlist.add("CNY");
		unitAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,unitlist);
		unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unitspinner.setAdapter(unitAdapter);
		
		//==========================================================================================================
		//============================load from file, put the information on EditText
		//==========================================================================================================
		//============================A claimID should be intented
		
		
		Intent intent = getIntent();
		final int itemID = intent.getIntExtra("itemID", 0);
		datafile = loadFromFile();
		currentitem = datafile.getItemlist().get(itemID);
		itemnamestr = datafile.getItemlist().get(itemID).getItem();
		itemname = (EditText)findViewById(R.id.edititemname);
		itemname.setText(itemnamestr);
		//=======datepicker
		editdate=Calendar.getInstance();
		edititemdate = (Button)findViewById(R.id.edititemdate);
		edititemdate.setOnClickListener(new View.OnClickListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new SingleDatePickerDialog(EditItemActivity.this, 0, new SingleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth) {
						String textString_start = String.format("%d-%d-%d", startYear, startMonthOfYear + 1,
								startDayOfMonth);
						edititemdate.setText(textString_start);
						editdate.set(startYear, startMonthOfYear+1, startDayOfMonth);
						
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
				
			}
		});
		
		//==========================================================================================================
		//============================confirm the edit
		
		Button editsave = (Button)findViewById(R.id.editconfirm);
		editsave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemnamestr = itemname.getText().toString();
				
				
				
				currentitem.setItem(itemnamestr);
				
				saveInFile();
				
				Intent backIntent = new Intent();
				backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				backIntent.setClass(EditItemActivity.this,ViewClaimActivity.class);
				startActivity(backIntent);
				
			}
		});
		
		
		
		
		
		
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
