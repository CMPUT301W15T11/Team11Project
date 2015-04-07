package activities;

/*
 * Displays a list of existing expense claims to the user
 * Allows user to filter by tags, create a new claim, delete claims, or view/edit claims
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import model.ApproverClaimListAdapter;
import model.ClaimListController;
import model.ClaimsList;
import model.ExpenseClaim;
import model.GeoLocation;
import model.ListClaimsAdapter;
import model.Listener;
import model.SubmittedClaimController;
import model.UserController;
import network.Client;
import network.ConnectionChecker;

import com.example.team11xtremexpensetracker.R;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ListClaimsActivity extends Activity {
	// private ListClaimsAdapter listClaimAdapter;
	private ListView claimsListView;
	private ImageView searchImage;
	private ClaimListController clc;
	private EditText tagSearchEdit;
	private Button addClaimButton;
	private int onLongClickPos;
	private Collection<ExpenseClaim> claims;
	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";

	private ArrayList<Integer> indexCorrector;
	private boolean filterFlag;

	private Client client;
	private ArrayList<ExpenseClaim> transferList;
	private ArrayList<ExpenseClaim> screenList;
	private ApproverClaimListAdapter claimAdapter2;
	private ArrayList<ExpenseClaim> localList;
	private Button setHomeButton;

	private void claimantRefreshData() {
		dataList = this.loadFromFile();
		localList = new ArrayList<ExpenseClaim>();
		localList = dataList.getClaimsAL();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				transferList = client.syncLocalFileList();
			}
		}).start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < localList.size(); i++) {
			for (int j = 0; j < transferList.size(); j++) {
				if (!localList.get(i).getStatus().equals("In Progress")
						&& localList.get(i).getName().equals(transferList.get(j).getName())
						&& localList.get(i).getClaimantName().equals(transferList.get(j).getClaimantName())) {
					localList.set(i, transferList.get(j));
				}
			}
		}
		dataList.setClaimsAL(localList);
		ClaimListController.setClaimsList(dataList);
		saveInFile();

		return;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_claims);
		claimsListView = (ListView) findViewById(R.id.claimsListView);
		searchImage = (ImageView) findViewById(R.id.tag_search);
		tagSearchEdit = (EditText) findViewById(R.id.tag_filter);
		addClaimButton = (Button) findViewById(R.id.addClaimButton);
		setHomeButton = (Button) findViewById(R.id.setHomeButton);
		// Load in claims from disk, give them to the claimsListController

		client = new Client();

		if (new ConnectionChecker().netConnected(ListClaimsActivity.this) == true) {
			if (UserController.getUserType().equals("Claimant")) {
				claimantRefreshData();
				//Toast.makeText(ListClaimsActivity.this, "Data synchronized from online database", Toast.LENGTH_SHORT).show();
			}
		}

		dataList = this.loadFromFile();
		// dataList.sort();
		clc = new ClaimListController();
		clc.setClaimsList(dataList);
		claims = clc.getClaimsList().getClaims();

		if (UserController.getUserType().equals("Claimant")) {
			claimant_init();

		} else if (UserController.getUserType().equals("Approver")) {
			approver_init();

		}
		
		
		setHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("mapMode", 1);
				intent.setClass(ListClaimsActivity.this, MapActivity.class);
				startActivity(intent);
			}});

	}

	public void approver_init() {
		addClaimButton.setEnabled(false);
		transferList = new ArrayList<ExpenseClaim>();
		Thread loadThread = new Thread(new Runnable() {
			@Override
			public void run() {
				transferList.addAll(client.getApproverClaimList());
			}
		});
		loadThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(transferList, new Comparator<ExpenseClaim>() {

			@Override
			public int compare(ExpenseClaim claim1, ExpenseClaim claim2) {
				// TODO Auto-generated method stub
				return claim1.getStartDate().compareTo(claim2.getStartDate());
			}

		});

		SubmittedClaimController.setSubmittedList(transferList);

		screenList = new ArrayList<ExpenseClaim>();
		screenList.addAll(SubmittedClaimController.getSubmittedList());

		claimAdapter2 = new ApproverClaimListAdapter(this, screenList);
		claimsListView.setAdapter(claimAdapter2);

		indexCorrector = new ArrayList<Integer>();
		filterFlag = false;

		searchImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String searchingTag = tagSearchEdit.getText().toString();
				searchingTag.toLowerCase();
				if (searchingTag.equals("")) {
					filterFlag = false;
					screenList.clear();
					screenList.addAll(SubmittedClaimController.getSubmittedList());
					claimAdapter2.notifyDataSetChanged();
					return;
				}
				filterFlag = true;
				indexCorrector.clear();
				ArrayList<ExpenseClaim> claims = SubmittedClaimController.getSubmittedList();
				screenList.clear();
				for (int i = 0; i < claims.size(); i++) {
					for (int j = 0; j < claims.get(i).getTagList().size(); j++) {
						if (searchingTag.equals(claims.get(i).getTagList().get(j).getTagName().toLowerCase())) {
							screenList.add(claims.get(i));
							indexCorrector.add(i);
						}
					}
				}
				claimAdapter2.notifyDataSetChanged();
			}

		});

		claimsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				if (!filterFlag) {
					Intent intent = new Intent(ListClaimsActivity.this, ViewClaimActivity.class);
					intent.putExtra("claimID", position);
					startActivity(intent);
				} else {
					int correctIndex = indexCorrector.get(position);
					Intent intent = new Intent(ListClaimsActivity.this, ViewClaimActivity.class);
					intent.putExtra("claimID", correctIndex);
					startActivity(intent);
				}
				finish();

			}
		});

	}

	public void claimant_init() {
		// Setup adapter for list so claims can be displayed
		final ArrayList<ExpenseClaim> list = new ArrayList<ExpenseClaim>(claims);
		final ArrayAdapter<ExpenseClaim> claimAdapter = new ListClaimsAdapter(this, list);
		

		
		claimsListView.setAdapter(claimAdapter);

		indexCorrector = new ArrayList<Integer>();
		filterFlag = false;

		// Add listeners
		clc.getClaimsList().addListener(new Listener() {
			@Override
			public void update() {
				list.clear();
				Collection<ExpenseClaim> claims = (clc.getClaimsList().getClaims());
				if (claims == null) {
					return;
				}
				list.addAll(claims);
				claimAdapter.notifyDataSetChanged();
			}
		});

		/**
		 * set Listener to search button, which controls the function of claim
		 * filter
		 */
		searchImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String searchingTag = tagSearchEdit.getText().toString();
				searchingTag.toLowerCase();
				if (searchingTag.equals("")) {
					filterFlag = false;
					list.clear();
					Collection<ExpenseClaim> claims = (clc.getClaimsList().getClaims());
					list.addAll(claims);
					claimAdapter.notifyDataSetChanged();
					return;
				}
				filterFlag = true;
				indexCorrector.clear();
				ArrayList<ExpenseClaim> claims = (clc.getClaimsList().getClaimsAL());
				list.clear();
				for (int i = 0; i < claims.size(); i++) {
					for (int j = 0; j < claims.get(i).getTagList().size(); j++) {
						if (searchingTag.equals(claims.get(i).getTagList().get(j).getTagName().toLowerCase())) {
							list.add(claims.get(i));
							indexCorrector.add(i);
						}
					}
				}
				claimAdapter.notifyDataSetChanged();
			}

		});

		/**
		 * Called when a claim within the list is selected Calls viewClaim while
		 * passing it the appropriate claim ID
		 */
		claimsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				if (!filterFlag) {
					Intent intent = new Intent(ListClaimsActivity.this, ViewClaimActivity.class);
					intent.putExtra("claimID", position);
					startActivity(intent);
				} else {
					int correctIndex = indexCorrector.get(position);
					Intent intent = new Intent(ListClaimsActivity.this, ViewClaimActivity.class);
					intent.putExtra("claimID", correctIndex);
					startActivity(intent);
				}

			}
		});

		/**
		 * Called when an item in the claims list is long clicked Displays the
		 * "Would you like to delete" dialog, and if prompted, deletes the
		 * appropriate claim
		 */
		claimsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				onLongClickPos = position;
				AlertDialog.Builder adb = new AlertDialog.Builder(ListClaimsActivity.this);
				adb.setMessage("Delete?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (!filterFlag) {
							String nameForHttp = ClaimListController.getClaimsList().getClaimsAL().get(onLongClickPos)
									.getName();
							ClaimListController.removeClaim(onLongClickPos);
							claimAdapter.clear();
							for (int i = 0; i < ClaimListController.getClaimsList().getLength(); i++) {
								claimAdapter.add(ClaimListController.getClaimsList().getClaimById(i));
							}
							saveInFile();

							if (new ConnectionChecker().netConnected(ListClaimsActivity.this) == true) {
								client.deleteClaim(nameForHttp);
							} else {
								Toast.makeText(ListClaimsActivity.this, "No network connected, delete file locally",
										Toast.LENGTH_SHORT).show();
							}

							dataList = loadFromFile();
							// dataList.sort();
							ClaimListController.setClaimsList(dataList);
							claimAdapter.notifyDataSetChanged();
						} else {
							int correctIndex = indexCorrector.get(onLongClickPos);
							indexCorrector.remove(onLongClickPos);
							list.remove(onLongClickPos);
							String nameForHttp = ClaimListController.getClaimsList().getClaimsAL().get(correctIndex)
									.getName();
							ClaimListController.removeClaim(correctIndex);
							saveInFile();

							if (new ConnectionChecker().netConnected(ListClaimsActivity.this) == true) {
								client.deleteClaim(nameForHttp);
							} else {
								Toast.makeText(ListClaimsActivity.this, "No network connected, delete file locally",
										Toast.LENGTH_SHORT).show();
							}

							dataList = loadFromFile();
							// dataList.sort();
							ClaimListController.setClaimsList(dataList);
							claimAdapter.notifyDataSetChanged();
						}

					}

				});
				adb.setNegativeButton("Cancel", new OnClickListener() {

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

	/**
	 * invoked when "add claim" button is pressed Brings users to the
	 * AddClaimActivity.
	 */
	public void addClaim(View v) {
		// Toast.makeText(this, "Adding Claim", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(ListClaimsActivity.this, AddClaimActivity.class);
		intent.putExtra("claimID", -1);
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed() {
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
