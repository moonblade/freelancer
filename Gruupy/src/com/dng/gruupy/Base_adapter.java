package com.dng.gruupy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Base_adapter extends BaseAdapter {
	Integer[] icons = { R.drawable.audio, R.drawable.attach, R.drawable.video,R.drawable.image };
	Context ct;

	public Base_adapter(Context ctx) {
		// TODO Auto-generated constructor stub
		this.ct = ctx;
		this.icons=icons;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return icons.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return icons[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {

		LayoutInflater lift = LayoutInflater.from(ct);
		Viewholder holder;
		if (v == null) {

			v = (RelativeLayout) lift.inflate(R.layout.options_file, null);
			holder = new Viewholder();
			holder.Iview = (ImageView) v.findViewById(R.id.options_item);
			v.setTag(holder);
		} else {
			holder = (Viewholder) v.getTag();
		}
		holder.Iview.setImageResource(icons[arg0]);
		return v;
	}

	public class Viewholder {
		ImageView Iview;
	}

}
