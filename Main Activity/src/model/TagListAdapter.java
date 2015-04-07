package model;

import java.util.ArrayList;
import java.util.List;

import com.example.team11xtremexpensetracker.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter for all available tags listview
 * 
 * @author Mingtuo
 *
 */
public class TagListAdapter extends ArrayAdapter<String> {

	public TagListAdapter(Context context, ArrayList<String> taglist) {
		super(context, 0, taglist);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String tempitem = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
			// convertView =
			// LayoutInflater.from(getContext()).inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
		}
		TextView itemName = (TextView) convertView.findViewById(R.id.itemNameView);
		itemName.setTextColor(-1);
		itemName.setText(tempitem);

		return convertView;
	}

}
