package com.example.team11xtremexpensetracker;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ApproverClaimListAdapter extends ArrayAdapter<ExpenseClaim>{
	
	public ApproverClaimListAdapter(Context context,ArrayList<ExpenseClaim> claimList){
		super(context,0,claimList);
		
	}
	
	@Override	
	public View getView(int position,View convertView,ViewGroup parent){
		ExpenseClaim tempClaim = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
			//convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
			}
		TextView itemName = (TextView)convertView.findViewById(R.id.itemNameView);
		itemName.setText(tempClaim.getName());
		

		
		
		return convertView;
	}
}
