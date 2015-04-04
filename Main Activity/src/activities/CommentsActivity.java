package activities;

import model.ClaimListController;
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
import android.widget.TextView;

public class CommentsActivity extends Activity {
	
	private TextView statusView;
	private TextView approverView;
	private TextView dateView;
	private TextView commentsView;
	private int claimID;
	private Client client;
	private ExpenseClaim currentClaim;
	

	private void init(){
		
		client=new Client();
		Intent intent = getIntent();
		claimID = intent.getIntExtra("claimID", 0);

		if (claimID >= 0) {

			if (UserController.getUserType().equals("Claimant")) {
				currentClaim = ClaimListController.getClaimsList().getClaimById(claimID);
			} else if (UserController.getUserType().equals("Approver")) {
				currentClaim = SubmittedClaimController.getSubmittedClaimById(claimID);
			}
		}
		
		statusView=(TextView)findViewById(R.id.comments_status);
		approverView=(TextView)findViewById(R.id.comments_approverName);
		dateView=(TextView)findViewById(R.id.comments_date);
		commentsView=(TextView)findViewById(R.id.claim_comments_view);
		
		statusView.setText(currentClaim.getStatus());
		approverView.setText(currentClaim.getApproverName());
		commentsView.setText(currentClaim.getComments());
		
	}
	
	@Override
	public void onBackPressed(){
		
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comments, menu);
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
