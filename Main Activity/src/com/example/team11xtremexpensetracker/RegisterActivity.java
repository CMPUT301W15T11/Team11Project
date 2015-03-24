package com.example.team11xtremexpensetracker;

import com.example.team11xtremexpensetracker.activity.AccountActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	
	private EditText emailView;
	private EditText nameView;
	private EditText passwordView;
	private Button createButton;
	
	private void init(){
		
		emailView=(EditText)findViewById(R.id.new_account_email);
		nameView=(EditText)findViewById(R.id.new_account_name);
		passwordView=(EditText)findViewById(R.id.new_account_password);
		createButton=(Button)findViewById(R.id.new_account_create);
		
		createButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String email=emailView.getText().toString();
				String name=nameView.getText().toString();
				String password=passwordView.getText().toString();
				// TODO lack of regular expression
				if(!email.equals("")&&!name.equals("")&&!password.equals("")){
					User newUser=new User();
					newUser.setUserId(email);
					newUser.setUserName(name);
					newUser.setUserPassword(password);
					// TODO save new user into database
					
					//
					Intent intent=new Intent(RegisterActivity.this,AccountActivity.class);
					startActivity(intent);
				}
			}
		});
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
