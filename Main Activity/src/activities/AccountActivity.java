package activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import network.Client;
import network.ConnectionChecker;
import model.ClaimListController;
import model.ClaimsList;
import model.UserController;

import com.example.team11xtremexpensetracker.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountActivity extends Activity {

	private Button backButton;
	private Button confirmButton;
	private Button cleanButton;
	private EditText usernameEdit;
	final private static String USERFILE = "userInfo.sav";
	private static final String FILENAME = "datafile.sav";
	private String usernameLoad;
	private Client client;
	private ClaimsList dataList;

	private void init() {

		backButton = (Button) findViewById(R.id.account_back);
		confirmButton = (Button) findViewById(R.id.account_confirm);
		cleanButton = (Button) findViewById(R.id.account_resetButton);
		usernameEdit = (EditText) findViewById(R.id.username_editText);

		client = new Client();

		if (UserController.getUserType().equals("Claimant")) {
			usernameLoad = this.loadUserFromFile();
			if (usernameLoad != null && !usernameLoad.equals("")) {
				usernameEdit.setText(usernameLoad);
				usernameEdit.setFocusable(false);
			}
		}

		if (UserController.getUserType().equals("Approver")) {
			cleanButton.setEnabled(false);
		}

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		confirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userName = usernameEdit.getText().toString();
				UserController.setUserName(userName);
				UserController.setFILENAME(userName);

				if (userName.equals("")) {
					Toast.makeText(AccountActivity.this, "Empty input", Toast.LENGTH_SHORT).show();
					return;
				} else if(UserController.getUserType().equals("Claimant")){
					AccountActivity.this.saveUserInFile(userName);
				}
				Intent intent = new Intent(AccountActivity.this, ListClaimsActivity.class);

				startActivity(intent);
				finish();
			}
		});

		cleanButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(AccountActivity.this);
				adb.setMessage("Warning:All local and online information of current user will be deleted.");
				adb.setCancelable(true);
				adb.setPositiveButton("Continue", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (new ConnectionChecker().netConnected(AccountActivity.this) == true) {
							dataList = AccountActivity.this.loadFromFile();
							for (int i = 0; i < dataList.getClaimsAL().size(); i++) {
								client.deleteClaim(dataList.getClaimsAL().get(i).getName());
							}
							
							//---------------------clear shit----------------------------//
							//client.deleteClaim("sa");
							//client.deleteClaim("zhz");
							
							
						
							
						}else{
							Toast.makeText(AccountActivity.this, "No network connected, action denied", Toast.LENGTH_SHORT).show();
							return;
						}
						AccountActivity.this.saveUserInFile("");
						cleanLocalClaimFile();
						usernameEdit.setText("");
						usernameEdit.setFocusable(true);
						usernameEdit.setFocusableInTouchMode(true);
						usernameEdit.requestFocus();
					}});
				adb.setNegativeButton("Cancel", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}

				});
				adb.show();
				
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
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

	private String loadUserFromFile() {
		Gson gson = new Gson();

		try {
			FileInputStream fis = openFileInput(USERFILE);
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<String>() {
			}.getType();
			usernameLoad = gson.fromJson(in, typeOfT);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usernameLoad;
	}

	// Saves claimsList to file
	private void saveUserInFile(String userName) {
		Gson gson = new Gson();

		try {
			FileOutputStream fos = openFileOutput(USERFILE, MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(userName, osw);
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

	private void cleanLocalClaimFile() {
		Gson gson = new Gson();
		ClaimsList clean = new ClaimsList();
		ClaimListController.setClaimsList(clean);
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
}
