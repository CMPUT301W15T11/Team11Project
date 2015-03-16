package com.example.team11xtremexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
	
	public void init(){
		//we should get data from save file first, and show them on the screens
		
		tagEdit=(EditText)findViewById(R.id.add_tag_editText);
		tagAdd=(Button)findViewById(R.id.add_tag_button);
		tagLayout=(FlowLayout)findViewById(R.id.tag_layout);
		
		Intent intent = getIntent();
		claimID = intent.getIntExtra("claimID", 0);
		if (claimID >= 0){
			new ClaimListController();
			currentClaim = ClaimListController.getClaimsList().getClaimById(claimID);
		}
		
		if(currentClaim.getTagList().size()>0){
			for(int i=0;i<currentClaim.getTagList().size();i++){
				showTag(currentClaim.getTagList().get(i).getTagName());
			}
		}
		
		tagAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				tagContent=tagEdit.getText().toString();
				createTag(tagContent);
			}
		});
		
	}
	
	private void createTag(String tagContent){
		
		TextView newTag=new TextView(this);
		MarginLayoutParams mp=new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		mp.setMargins(10,10,10,10);
		newTag.setLayoutParams(mp);
		newTag.setText(tagContent);
		newTag.setGravity(Gravity.CENTER);
		newTag.setBackgroundResource(R.drawable.flag_02);
		newTag.setTextColor(getResources().getColor(R.color.skyblue));	
		tagEdit.setText("");
		tagLayout.addView(newTag);
		
		Tag aNewTag=new Tag(tagContent);
		currentClaim.getTagList().add(aNewTag);
		saveInFile();
		
	}
	
	private void showTag(String tagContent){
		TextView newTag=new TextView(this);
		MarginLayoutParams mp=new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		mp.setMargins(10,10,10,10);
		newTag.setLayoutParams(mp);
		newTag.setText(tagContent);
		newTag.setGravity(Gravity.CENTER);
		newTag.setBackgroundResource(R.drawable.flag_02);
		newTag.setTextColor(getResources().getColor(R.color.skyblue));	
		tagLayout.addView(newTag);
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tag_window);
		init();
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

}
