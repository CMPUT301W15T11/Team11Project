package activities;

import java.util.Calendar;

import model.ExpenseClaim;
import model.SubmittedClaimController;
import model.UserController;
import network.Client;

import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.id;
import com.example.team11xtremexpensetracker.R.layout;
import com.example.team11xtremexpensetracker.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity for approver to add comments to submitted claim and approve or return it
 * @author Mingtuo
 *
 */
public class AddCommentsActivity extends Activity {

	private Button approveButton;
	private Button returnButton;
	private EditText commentsEdit;
	private int claimID;
	private Client client;
	private ExpenseClaim currentClaim;
	private String comments;

	private void init() {
		Intent intent = getIntent();
		claimID = intent.getIntExtra("claimID", 0);
		currentClaim = SubmittedClaimController.getSubmittedClaimById(claimID);

		approveButton = (Button) findViewById(R.id.approveClaimButton);
		returnButton = (Button) findViewById(R.id.returnClaimButton);
		commentsEdit = (EditText) findViewById(R.id.add_comments_edit);
		client=new Client();

		/**
		 * Change claim's status and sync to online database
		 * 
		 */
		approveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				comments = commentsEdit.getText().toString();
				if (comments.equals("")) {
					Toast.makeText(AddCommentsActivity.this, "Empty comments", Toast.LENGTH_SHORT).show();
				} else {
					currentClaim.setComments(comments);
					currentClaim.setStatus("Approved");
					currentClaim.setApproverName(UserController.getUserName());
					Calendar dd=Calendar.getInstance();
					currentClaim.setCommentsDate(dd);
					client.addClaim(currentClaim);
					//SubmittedClaimController.setSubmittedList(client.getApproverClaimList());
					Thread updateThread=new Thread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							SubmittedClaimController.setSubmittedList(client.getApproverClaimList());
						}
						
					});
					updateThread.start();
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent backIntent=new Intent();
					backIntent.putExtra("claimID", claimID);
					backIntent.setClass(AddCommentsActivity.this,ListClaimsActivity.class);
					startActivity(backIntent);
					finish();
				}
			}

		});
		
		
		/**
		 * Change claim's status and sync to online database
		 * 
		 */
		returnButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				comments = commentsEdit.getText().toString();
				if (comments.equals("")) {
					Toast.makeText(AddCommentsActivity.this, "Empty comments", Toast.LENGTH_SHORT).show();
				} else {
					currentClaim.setComments(comments);
					currentClaim.setStatus("Returned");
					currentClaim.setApproverName(UserController.getUserName());
					Calendar dd=Calendar.getInstance();
					currentClaim.setCommentsDate(dd);
					client.addClaim(currentClaim);
					
					Thread updateThread=new Thread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							SubmittedClaimController.setSubmittedList(client.getApproverClaimList());
						}
						
					});
					updateThread.start();
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent backIntent=new Intent();
					backIntent.putExtra("claimID", claimID);
					backIntent.setClass(AddCommentsActivity.this,ListClaimsActivity.class);
					startActivity(backIntent);
					finish();
				}
			}

		});
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_comments);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_comments, menu);
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
}
