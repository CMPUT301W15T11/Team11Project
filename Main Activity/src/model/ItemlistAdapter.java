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
/**
 * An Adapter for expense item lists
 * @author Stin
 *
 */
public class ItemlistAdapter extends ArrayAdapter<Item>{


	
	public ItemlistAdapter(Context context,ArrayList<Item> itemlist){
		super(context,0,itemlist);
		
	}
	 @Override	
	public View getView(int position,View convertView,ViewGroup parent){
		Item tempitem = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
			//convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
			}
		TextView itemName = (TextView)convertView.findViewById(R.id.itemNameView);
		itemName.setAlpha(125);
		itemName.setTextColor(-1);
		itemName.setText(tempitem.getItem());
		
		TextView itemAmt = (TextView)convertView.findViewById(R.id.itemAmountView);
		itemAmt.setAlpha(125);
		itemAmt.setTextColor(-1);
		itemAmt.setText(tempitem.getUnit()+" : "+tempitem.getAmount());
		
		
		return convertView;
	}
	

}
