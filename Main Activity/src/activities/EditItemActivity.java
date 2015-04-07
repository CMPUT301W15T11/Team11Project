package activities;

/*
 * Lets user edit a pre-existing item.
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
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EditItemActivity extends Activity {

	private ClaimsList datafile;
	private EditText itemname;
	private EditText itemdescription;
	private EditText itemamount;

	private String itemnamestr;
	private String itemunitstr;
	private String itemdescriptionstr;
	private String itemamountstr;
	private String itemcategorystr;
	private Calendar itemdate;

	private Item currentitem;
	private Spinner categoryspinner;
	private ArrayAdapter<String> categoryAdapter;
	private Spinner unitspinner;
	private ArrayAdapter<String> unitAdapter;
	private Button edititemdate;
	private Calendar editdate;
	private int claimID;
	private int itemID;
	private Item list;

	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";
	private ClaimListController clc;

	private Client client;

	private Bitmap photoBitmap;
	private boolean hasPhoto = false;
	private byte[] pressedPhoto = new byte[65536];
	private Button photoButton;
	private String locationStr;
	private Button mapButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);

		dataList = this.loadFromFile();
		clc = new ClaimListController();
		clc.setClaimsList(dataList);

		Intent intent = getIntent();
		itemID = intent.getIntExtra("itemID", 0);
		claimID = intent.getIntExtra("claimID", 0);
		locationStr = intent.getStringExtra("locationStr");
		list = ClaimListController.getClaimsList().getClaimById(claimID).getItemById(itemID);

		client = new Client();

		itemnamestr = list.getItem().toString();
		itemname = (EditText) findViewById(R.id.edititemname);
		itemname.setText(itemnamestr);

		itemdescriptionstr = list.getDescription().toString();
		itemdescription = (EditText) findViewById(R.id.editdescription);
		itemdescription.setText(itemdescriptionstr);

		itemamountstr = list.getAmount().toString();
		itemamount = (EditText) findViewById(R.id.editamountspent);
		itemamount.setText(itemamountstr);

		// ==========================================================================================================
		// ============================create spinner

		categoryspinner = (Spinner) findViewById(R.id.editcategory);
		ArrayList<String> categorylist = new ArrayList<String>();
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

		categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorylist);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categoryspinner.setAdapter(categoryAdapter);

		unitspinner = (Spinner) findViewById(R.id.editunit);
		ArrayList<String> unitlist = new ArrayList<String>();
		unitlist.add("CAD");
		unitlist.add("USD");
		unitlist.add("EUR");
		unitlist.add("GBP");
		unitlist.add("CHF");
		unitlist.add("JPY");
		unitlist.add("CNY");
		unitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitlist);
		unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unitspinner.setAdapter(unitAdapter);

		// =======datepicker
		editdate = Calendar.getInstance();
		edititemdate = (Button) findViewById(R.id.edititemdate);
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
						editdate.set(startYear, startMonthOfYear + 1, startDayOfMonth);

					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();

			}
		});

		photoButton = (Button) findViewById(R.id.selectPicBut);
		photoButton.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (list.getHasPhoto() == true) {
					list.setHasPhoto(false);
					list.setPhoto(null);
					Context context = getApplicationContext();
					CharSequence text = "Photo Removed!";
					int duration = Toast.LENGTH_LONG;
					// Toast toast = Toast.makeText(context, text, duration);
					// toast.show();
				}
				return true;

			}
		});
		// ==========================================================================================================
		// ============================confirm the edit

		Button editsave = (Button) findViewById(R.id.editconfirm);
		editsave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemnamestr = itemname.getText().toString();
				itemunitstr = unitspinner.getSelectedItem().toString();
				itemcategorystr = categoryspinner.getSelectedItem().toString();
				itemdescriptionstr = itemdescription.getText().toString();
				itemamountstr = itemamount.getText().toString();

				if (itemnamestr.equals("") || itemamountstr.equals("")) {
					Toast.makeText(EditItemActivity.this, "Information is not completed", Toast.LENGTH_SHORT).show();
					return;
				}

				list.setAmount(itemamountstr);
				list.setItem(itemnamestr);
				list.setCategory(itemcategorystr);
				list.setDescription(itemdescriptionstr);
				list.setUnit(itemunitstr);
				list.setDate(editdate);
				list.setHasPhoto(hasPhoto);
				if (hasPhoto == true) {
					list.setPhoto(pressedPhoto);
				}

				saveInFile();

				ExpenseClaim currentClaim = ClaimListController.getClaimsList().getClaimById(claimID);

				if (new ConnectionChecker().netConnected(EditItemActivity.this) == true) {
					client.addClaim(currentClaim);
				} else {
					Toast.makeText(EditItemActivity.this, "No network connected, applying change locally",
							Toast.LENGTH_SHORT).show();
				}

				Intent backIntent = new Intent();
				backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				backIntent.setClass(EditItemActivity.this, ViewClaimActivity.class);
				backIntent.putExtra("claimID", claimID);
				startActivity(backIntent);
				finish();

			}
		});

		mapButton = (Button) findViewById(R.id.editgeolocation);
		if (locationStr != null) {
			mapButton.setText("Change Location");
		}

		mapButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(EditItemActivity.this, MapActivity.class);
				intent.putExtra("claimID", claimID);
				intent.putExtra("itemID", itemID);
				intent.putExtra("mapMode", 4);

				if (locationStr != null) {
					intent.putExtra("locationStr", locationStr);
				}

				startActivity(intent);

			}
		});

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
	 * 
	 * @param v
	 *            the Select Photo button
	 */
	public void choosePhoto(View v) {
		if (list.getHasPhoto() == true) {
			list.setHasPhoto(false);
			list.setPhoto(null);
		}
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 1);

	}

	/**
	 * When the user returns from the gallery application they are directed
	 * towards this method where the photo bitmap is converted to a JPEG,
	 * compressed and stored in a byte array where it is uploaded with the
	 * expense item that has been edited.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == RESULT_OK) {
			Uri photoUri = intent.getData();

			if (photoUri != null) {
				try {
					photoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
					int byteCount = photoBitmap.getByteCount();

					if (byteCount > 0) {
						Context context = getApplicationContext();
						CharSequence text = "Photo Added!";
						int duration = Toast.LENGTH_LONG;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						hasPhoto = true;
						Log.i("Image Upload", "" + byteCount);
					}
					ByteArrayOutputStream blob = new ByteArrayOutputStream();
					photoBitmap.compress(CompressFormat.JPEG, 20, blob);
					pressedPhoto = blob.toByteArray();
					Log.i("size of byte array", "" + (int) pressedPhoto.length);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
