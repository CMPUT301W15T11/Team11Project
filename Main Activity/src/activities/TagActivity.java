package activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import model.ClaimListController;
import model.ClaimsList;
import model.ExpenseClaim;
import model.FlowLayout;
import model.SubmittedClaimController;
import model.Tag;
import model.UserController;
import network.Client;
import network.ConnectionChecker;

import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.color;
import com.example.team11xtremexpensetracker.R.drawable;
import com.example.team11xtremexpensetracker.R.id;
import com.example.team11xtremexpensetracker.R.layout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Add Tag Activity: used to add tags and manage claims
 * 
 * @author Mingtuo
 * 
 */

public class TagActivity extends Activity {
	private EditText tagEdit;
	private Button tagAdd;
	private String tagContent;

	private int claimID;
	private ExpenseClaim currentClaim;

	private FlowLayout tagLayout;

	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";
	private ClaimListController clc;

	private Client client;

	/**
	 * Initialize every view and tag function
	 */
	public void init() {
		// we should get data from save file first, and show them on the screens
		/**
		 * set each view
		 */
		tagEdit = (EditText) findViewById(R.id.add_tag_editText);
		tagAdd = (Button) findViewById(R.id.add_tag_button);
		tagLayout = (FlowLayout) findViewById(R.id.tag_layout);

		Intent intent = getIntent();
		claimID = intent.getIntExtra("claimID", 0);
		if (claimID >= 0) {

			if (UserController.getUserType().equals("Claimant")) {
				currentClaim = ClaimListController.getClaimsList().getClaimById(claimID);
			} else if (UserController.getUserType().equals("Approver")) {
				currentClaim = SubmittedClaimController.getSubmittedClaimById(claimID);
			}
		}
		
		if(currentClaim.getStatus().equals("Submitted")||currentClaim.getStatus().equals("Approved")){
			tagEdit.setFocusable(false);
			tagEdit.setFocusableInTouchMode(false);
			tagEdit.setHint(R.string.status_lock);
		}

		client = new Client();
		/**
		 * check tagList() is empty or not, if not empty pop every tag onto
		 * screen
		 */
		if (currentClaim.getTagList().size() > 0) {
			for (int i = 0; i < currentClaim.getTagList().size(); i++) {
				showTag(currentClaim.getTagList().get(i).getTagName());
			}
		}

		/**
		 * set listener to add button
		 */
		tagAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				tagContent = tagEdit.getText().toString();
				if(tagContent.equals("")){
					Toast.makeText(TagActivity.this,"Empty input",Toast.LENGTH_SHORT).show();
					return;
				}
				createTag(tagContent);
			}
		});

	}

	/**
	 * function used to create new tag and add onto screen
	 * 
	 * @param tagContent
	 */
	private void createTag(String tagContent) {

		final TextView newTag = new TextView(this);
		MarginLayoutParams mp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mp.setMargins(10, 10, 10, 10);
		newTag.setLayoutParams(mp);
		newTag.setText(tagContent);
		newTag.setGravity(Gravity.CENTER);
		newTag.setBackgroundResource(R.drawable.flag_02);
		newTag.setTextColor(getResources().getColor(R.color.skyblue));
		newTag.setFocusable(true);
		newTag.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(TagActivity.this);
				adb.setMessage("Delete selected tag?");
				adb.setCancelable(true);
				adb.setPositiveButton("Confirm", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						for (int index = 0; index < currentClaim.getTagList().size(); index++) {
							if (currentClaim.getTagList().get(index).equals(newTag.getText().toString())) {
								currentClaim.getTagList().remove(index);
							}
						}
						saveInFile();
						if (new ConnectionChecker().netConnected(TagActivity.this) == true) {
							client.addClaim(currentClaim);
						} else {
							Toast.makeText(TagActivity.this, "No network connected, applying change locally", Toast.LENGTH_SHORT).show();
						}
						// tagLayout.notify();
					}
				});
				return false;
			}
		});

		tagEdit.setText("");
		tagLayout.addView(newTag);

		Tag aNewTag = new Tag(tagContent);
		currentClaim.getTagList().add(aNewTag);
		saveInFile();
		if (new ConnectionChecker().netConnected(TagActivity.this) == true) {
			client.addClaim(currentClaim);
		} else {
			Toast.makeText(TagActivity.this, "No network connected, applying change locally", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * function used to pop pre-exist tag out
	 * 
	 * @param tagContent
	 */
	private void showTag(String tagContent) {
		final TextView newTag = new TextView(this);
		MarginLayoutParams mp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mp.setMargins(10, 10, 10, 10);
		newTag.setLayoutParams(mp);
		newTag.setText(tagContent);
		newTag.setGravity(Gravity.CENTER);
		newTag.setBackgroundResource(R.drawable.flag_02);
		newTag.setTextColor(getResources().getColor(R.color.skyblue));
		newTag.setFocusable(true);
		newTag.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(TagActivity.this);
				adb.setMessage("Delete selected tag?");
				adb.setCancelable(true);
				adb.setPositiveButton("Confirm", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						for (int index = 0; index < currentClaim.getTagList().size(); index++) {
							if (currentClaim.getTagList().get(index).equals(newTag.getText().toString())) {
								currentClaim.getTagList().remove(index);
							}
						}
						saveInFile();
						if (new ConnectionChecker().netConnected(TagActivity.this) == true) {
							client.addClaim(currentClaim);
						} else {
							Toast.makeText(TagActivity.this, "No network connected, applying change locally", Toast.LENGTH_SHORT).show();
						}
						// tagLayout.notify();
					}
				});
				return false;
			}
		});

		tagLayout.addView(newTag);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tag_window);
		init();
	}

	/**
	 * load file function
	 * 
	 * @return dataList
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

	/**
	 * save file function
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

}
