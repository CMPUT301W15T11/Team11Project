package com.example.team11xtremexpensetracker;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemlistAdapter extends ArrayAdapter<Item>{


	
	public ItemlistAdapter(Context context,ArrayList<Item> itemlist){
		super(context,0,itemlist);
		
	}
	 @Override	
	public View getView(int position,View convertView,ViewGroup parent){
		Item tempitem = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
			}
		TextView item = (TextView)convertView.findViewById(R.id.placeView);
		item.setText(tempitem.getItem());
		return convertView;
	}
	
	

}
