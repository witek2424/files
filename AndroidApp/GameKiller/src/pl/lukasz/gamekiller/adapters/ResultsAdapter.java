package pl.lukasz.gamekiller.adapters;

import java.util.ArrayList;

import pl.lukasz.gamekiller.Item;
import pl.lukasz.gamekiller.R;
import pl.lukasz.gamekiller.R.id;
import pl.lukasz.gamekiller.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ResultsAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Item> items;
	
	private LayoutInflater inflater;
	
	public ResultsAdapter(Context context, ArrayList<Item> items) {
		this.context = context;
		this.items = items;
		
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Item getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Item item = getItem(position);
		convertView = inflater.inflate(R.layout.listview_result_item, null);
		
		TextView noTV = (TextView) convertView.findViewById(R.id.noTV);
		TextView valueTV = (TextView) convertView.findViewById(R.id.valueTV);
		Button acceptBT = (Button) convertView.findViewById(R.id.acceptBT);
		Button removeBT = (Button) convertView.findViewById(R.id.removeBT);
		
		noTV.setText((position + 1) + "");
		valueTV.setText(item.getRandomValue() + ":" + item.getValue());
		
		if (!item.isModified()) {
			acceptBT.setVisibility(View.GONE);
		}
		
		acceptBT.setTag(position);
		removeBT.setTag(position);
		
		return convertView;
	}

}
