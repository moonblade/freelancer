package utilities;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Spinner_adapter extends BaseAdapter {

	Context ctx;
	ArrayList<String> lesson_index;

	public Spinner_adapter(Context ctx,
			ArrayList<String> lesson_index) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		this.lesson_index = lesson_index;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lesson_index.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lesson_index.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView label = new TextView(ctx);
		label.setTextColor(Color.BLACK);
		label.setTextSize(20f);
		label.setGravity(Gravity.CENTER);
		label.setText(lesson_index.get(position));

		return label;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView label = new TextView(ctx);
		label.setTextColor(Color.BLACK);
		label.setTextSize(20f);
		label.setPadding(5, 5, 5, 5);
		label.setGravity(Gravity.CENTER);
		label.setText(lesson_index.get(position));

		return label;
	}

}
