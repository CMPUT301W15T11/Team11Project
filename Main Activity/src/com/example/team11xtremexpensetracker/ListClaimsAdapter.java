package com.example.team11xtremexpensetracker;

/*
 * An Adapter for Expense Claims List
 */

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListClaimsAdapter extends ArrayAdapter<ExpenseClaim>{

	private Context context;
	private ArrayList<ExpenseClaim> claimList;
	
	public ListClaimsAdapter(Context context,ArrayList<ExpenseClaim> claimList){
		super(context,0,claimList);
		this.context = context ;
		this.claimList = claimList;
		
	}
	public View getView(int position,View convertView,ViewGroup parent){
		ExpenseClaim tempClaim = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
			}
		TextView item = (TextView)convertView.findViewById(R.id.itemNameView);
		item.setText(tempClaim.getName());
		return convertView;
	}
	
	

}
