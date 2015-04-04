package activities;

import model.UserController;

import com.example.team11xtremexpensetracker.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AccountActivity extends Activity {

	private Button backButton;
	private Button confirmButton;
	private EditText usernameEdit;

	private void init() {

		
		backButton = (Button) findViewById(R.id.account_back);
		confirmButton = (Button) findViewById(R.id.account_confirm);
		usernameEdit=(EditText)findViewById(R.id.username_editText);
		

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
				String userName=usernameEdit.getText().toString();
				UserController.setUserName(userName);
				UserController.setFILENAME(userName);
				Intent intent=new Intent(AccountActivity.this,ListClaimsActivity.class);
				
				startActivity(intent);
				finish();
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
}
