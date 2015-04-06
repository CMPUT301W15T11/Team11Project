package model;

/*
 * An Adapter for expense item lists
 */

import java.util.ArrayList;

import com.example.team11xtremexpensetracker.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DestListAdapter extends ArrayAdapter<Destination>{


	
	public DestListAdapter(Context context,ArrayList<Destination> itemlist){
		super(context,0,itemlist);
		
	}
	 @Override	
	public View getView(int position,View convertView,ViewGroup parent){
		 Destination tempDest = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.dest_list_item,parent,false);
			}
		TextView destName = (TextView)convertView.findViewById(R.id.destName);
		destName.setText(tempDest.getDest());
		
		TextView destReason = (TextView)convertView.findViewById(R.id.destReason);
		destReason.setText(tempDest.getReason());
		
		
		return convertView;
	}
	

}