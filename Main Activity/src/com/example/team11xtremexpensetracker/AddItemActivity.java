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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends Activity{
	private static final String FILENAME = "save.sav";
	private Itemlist datafile;
	private EditText itemname;
	private String itemnamestr;
	private String itemunitstr;
	private String itemcategorystr;
	private String itemamountstr;
	private EditText itemamount;
	private String itemdescriptionstr;
	private EditText itemdescription;
	private Spinner categoryspinner;
	private ArrayAdapter<String> categoryAdapter;
	private Spinner unitspinner;
	private ArrayAdapter<String> unitAdapter;
	private Button additemdate;
	private Calendar adddate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		

		//==========================================================================================================
		//============================spinner created for category and unit		
		categoryspinner = (Spinner)findViewById(R.id.additemcategory);
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

		//==========================================================================================================
		//============================spinner created for category and unit
		categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categorylist);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categoryspinner.setAdapter(categoryAdapter);
		
		unitspinner = (Spinner)findViewById(R.id.additemunit);
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
		//============================get input text to string and save into Gson,		
		
		datafile = this.loadFromFile();
		itemname = (EditText)findViewById(R.id.additemname);
		unitspinner = (Spinner)findViewById(R.id.additemunit);
		categoryspinner = (Spinner)findViewById(R.id.additemcategory);
		
		//button add date
		adddate=Calendar.getInstance();
		additemdate = (Button)findViewById(R.id.additemdate);
		additemdate.setOnClickListener(new View.OnClickListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new SingleDatePickerDialog(AddItemActivity.this, 0, new SingleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth) {
						String textString_start = String.format("%d-%d-%d", startYear, startMonthOfYear + 1,
								startDayOfMonth);
						additemdate.setText(textString_start);
						adddate.set(startYear, startMonthOfYear+1, startDayOfMonth);
						
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
				
			}
		});
		
		//Button Add
		Button itemadd = (Button)findViewById(R.id.confirmadd);
		
		
		itemadd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemnamestr = itemname.getText().toString();
				itemunitstr = unitspinner.getSelectedItem().toString();
				itemcategorystr = categoryspinner.getSelectedItem().toString();
				
				
				Item newItem = new Item();
				newItem.setItem(itemnamestr);
				newItem.setUnit(itemunitstr);
				newItem.setCategory(itemcategorystr);
				
				
				datafile.additem(newItem);
				saveInFile();
				
				Intent backIntent = new Intent();
				backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				backIntent.setClass(AddItemActivity.this, ViewClaimActivity.class);
				startActivity(backIntent);
				
			}
		});
		

		

	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//==========================================================================================================
		//============================Gson
	}
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
