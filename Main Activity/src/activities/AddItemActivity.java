package activities;

/*
 * Activity thats presents UI for user to add a new expense claim
 * Checks to make sure expense claim has a name
 */

import java.io.ByteArrayOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import model.ClaimListController;
import model.ClaimsList;
import model.ExpenseClaim;
import model.Item;
import model.SingleDatePickerDialog;
import model.SingleDatePickerDialog.OnDateSetListener;
import network.Client;
import network.ConnectionChecker;

import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.id;
import com.example.team11xtremexpensetracker.R.layout;
import com.google.gson.Gson; 
import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
/**
 * Activity thats presents UI for user to add a new expense claim
 * @author Stin
 *
 */
public class AddItemActivity extends Activity{
	
	private ClaimsList datafile;
	private EditText itemname;
	private String itemnamestr;
	private String itemunitstr;
	private String itemcategorystr;
	private Integer itemamountstr;
	private EditText itemamount;
	private String itemdescriptionstr;
	private EditText itemdescription;
	private Spinner categoryspinner;
	private ArrayAdapter<String> categoryAdapter;
	private Spinner unitspinner;
	private ArrayAdapter<String> unitAdapter;
	private Button additemdate;
	private Calendar adddate;
	private int claimID;
	private String textString_start;
	private ExpenseClaim currentClaim;
	
	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";
	private ClaimListController clc;
	
	private Client client;

	private Bitmap photoBitmap;
	private boolean hasPhoto = false;
	private byte[] pressedPhoto;
	private String photo = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		Intent intent= getIntent();
		claimID = intent.getIntExtra("claimID", 0);
		if (claimID >= 0){
			//new ClaimListController();
			currentClaim = ClaimListController.getClaimsList().getClaimById(claimID);
		}
		
		client=new Client();
		

		
		//spinner created for category and unit	
		/**
		 * spinner created for category
		 */
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

		
		//spinner created for category and unit
		
		categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categorylist);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categoryspinner.setAdapter(categoryAdapter);
		/**
		 * spinner created for unit	
		 */
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
		
		
		//get input text to string and save into Gson,		
		
		//datafile = this.loadFromFile();
		itemname = (EditText)findViewById(R.id.additemname);
		unitspinner = (Spinner)findViewById(R.id.additemunit);
		categoryspinner = (Spinner)findViewById(R.id.additemcategory);
		itemamount = (EditText)findViewById(R.id.addamountspent);
		itemdescription = (EditText)findViewById(R.id.adddescription);
		
		
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
						textString_start = String.format("%d-%d-%d", startYear, startMonthOfYear + 1,
								startDayOfMonth);
						additemdate.setText(textString_start);
						adddate.set(startYear, startMonthOfYear+1, startDayOfMonth);
						
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
				
			}
		});

		//Button Add
		Button itemadd = (Button)findViewById(R.id.confirmadd);
		
		
		// If expense item has a name,
		// A new expense item is generated and added to the list
		// User is brought back to the viewClaim activity
		/**
		 * listener: click to view the expense item
		 */
		itemadd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemnamestr = itemname.getText().toString();
				itemunitstr = unitspinner.getSelectedItem().toString();
				itemcategorystr = categoryspinner.getSelectedItem().toString();
				itemdescriptionstr = itemdescription.getText().toString();
				itemamountstr = Integer.parseInt(itemamount.getText().toString());
				
				
				if (itemnamestr.equals("")||itemamountstr.equals("")){
					Toast.makeText(AddItemActivity.this, "Information is not completed", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Item newItem = new Item();
				newItem.setItem(itemnamestr);
				newItem.setUnit(itemunitstr);
				newItem.setCategory(itemcategorystr);
				newItem.setAmount(itemamountstr);
				newItem.setDescription(itemdescriptionstr);
				newItem.setDate(adddate);
				newItem.setHasPhoto(hasPhoto);
				newItem.setPhoto(photo);
				
				currentClaim.addItem(newItem);				
				saveInFile();
				
				if (new ConnectionChecker().netConnected(AddItemActivity.this) == true) {
					client.addClaim(currentClaim);
				} else {
					Toast.makeText(AddItemActivity.this, "No network connected, apply change locally", Toast.LENGTH_SHORT).show();
				}
				
				Intent backIntent = new Intent();
				//backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				backIntent.setClass(AddItemActivity.this, ViewClaimActivity.class);
				backIntent.putExtra("claimID", claimID);
				startActivity(backIntent);
				finish();
				
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

	}

	// Load claims from file
	/**
	 * Gson load from file
	 * @return datalist
	 */
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

	// Save claims to file
	/**
	 * save in file
	 */
	private void saveInFile() {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(clc.getClaimsList(), osw);
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
	/**
	 * sends an intent to the gallery application to get a picture in return
	 * @param v the Add Photo button
	 */
	public void choosePhoto(View v){
		Intent  photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 1);
	
	}
	/**
	 * When the user returns from the gallery application they are directed towards this method where the 
	 * photo bitmap is converted to a JPEG, compressed and stored in a byte array where it is 
	 * uploaded with the expense item that has been created. 
	 */
	protected void onActivityResult(int requestCode, int resultCode,
	        Intent intent) {
	    super.onActivityResult(requestCode, resultCode, intent);

	    if (resultCode == RESULT_OK) {
	        Uri photoUri = intent.getData();

	        if (photoUri != null) {
	            try {
	                photoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
	                ByteArrayOutputStream blob = new ByteArrayOutputStream();
	                photoBitmap.compress(CompressFormat.JPEG, 20, blob);
	                pressedPhoto= blob.toByteArray();
	                if(pressedPhoto.length > 65536){
	                	Context context = getApplicationContext();
	                	CharSequence text = "Image is too big!";
	                	int duration = Toast.LENGTH_LONG;
	                	Toast toast = Toast.makeText(context, text, duration);
	                	toast.show();
	                	hasPhoto = false;
	                }
	                else{
	                	Context context = getApplicationContext();
	                	CharSequence text = "Photo Added!";
	                	int duration = Toast.LENGTH_LONG;
	                	Toast toast = Toast.makeText(context, text, duration);
	                	toast.show();
	                	hasPhoto = true;	                	
	                	photo = Base64.encodeToString(pressedPhoto, Base64.DEFAULT);
	                }

	            }catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
}
