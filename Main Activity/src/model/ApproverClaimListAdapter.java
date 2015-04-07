package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.team11xtremexpensetracker.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Adapter for listview which shows all submitted claims for approver
 * @author Mingtuo
 * @author Stin
 *
 */
public class ApproverClaimListAdapter extends ArrayAdapter<ExpenseClaim>{
	
	public ApproverClaimListAdapter(Context context,ArrayList<ExpenseClaim> claimList){
		super(context,0,claimList);
		
	}
	
	@Override	
	public View getView(int position,View convertView,ViewGroup parent){
		ExpenseClaim tempClaim = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.approver_listitem,parent,false);
			//convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
			}
		TextView itemName = (TextView)convertView.findViewById(R.id.item_text);
		itemName.setText(tempClaim.getName());
		
		TextView itemStatus=(TextView)convertView.findViewById(R.id.item_text1);
		itemStatus.setText(tempClaim.getStatus());
		
		TextView itemDate=(TextView)convertView.findViewById(R.id.item_text3);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = df.format(tempClaim.getStartDate().getTime());
		itemDate.setText(dateStr);
		
		return convertView;
	}
}
