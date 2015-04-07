package model;

/*
 * An Adapter for Expense Claims List
 */

import java.util.ArrayList;

import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.color;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListClaimsAdapter extends ArrayAdapter<ExpenseClaim> {

	private Context context;
	private ArrayList<ExpenseClaim> claimList;
	private GeoLocation homelocation;
	private Destination tempdest;

	public ListClaimsAdapter(Context context, ArrayList<ExpenseClaim> claimList) {
		super(context, 0, claimList);
		this.context = context;
		this.claimList = claimList;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ExpenseClaim tempClaim = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.claim_list_item, parent, false);
		}
		TextView ClaimNameView = (TextView) convertView.findViewById(R.id.ClaimNameView);
		TextView ClaimDistanceView = (TextView) convertView.findViewById(R.id.ClaimDistanceView);
		ClaimNameView.setText(tempClaim.getName());

		String home = UserController.getHomeLocation();
		ArrayList<Destination> dests = tempClaim.getDestinations();
		
		if (home != null&& dests.size()!=0){
			homelocation = GeoLocation.toGeoLocation(home);
			tempdest = dests.get(0);
		}
		else{
			homelocation = null;
		}
		
		if (tempdest != null && homelocation != null) {
			GeoLocation currentlocation = GeoLocation.toGeoLocation(tempdest.getLocationStr());

			double distance = GeoLocation.distanceBetween(homelocation, currentlocation);
			ClaimDistanceView.setText("Distance from home: "+String.valueOf(distance));
			
			if (distance>0 && distance<500){
				ClaimDistanceView.setTextColor(Color.parseColor("#D3D3D3"));
			}
			else if (distance>500 && distance<1000){
				ClaimDistanceView.setTextColor(Color.parseColor("#FFFFFF"));
			}
			else if (distance>1000 && distance<50000){
				ClaimDistanceView.setTextColor(Color.parseColor("#90EE90"));
			}
			else if (distance>50000 && distance<100000){
				ClaimDistanceView.setTextColor(Color.parseColor("#0000FF"));
			}
			else if (distance>1000000 && distance<2500000){
				ClaimDistanceView.setTextColor(Color.parseColor("#800080"));
			}
			else if (distance>2500000){
				ClaimDistanceView.setTextColor(Color.parseColor("#FFA500"));
			}
			
			
		} else {
			
			ClaimDistanceView.setText("location not exist ");
		}

		return convertView;
	}

}
