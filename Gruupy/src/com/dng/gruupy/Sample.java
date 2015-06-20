package com.dng.gruupy;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class Sample extends BaseAdapter {
	LayoutInflater Lift;
	Context c;
	Bitmap bmmp;
	int layoutResourceId;
	Typeface face;
	ArrayList<HashMap<String, String>> group_list;

	public Sample(Context c, ArrayList<HashMap<String, String>> group_list,
			Typeface face) {
		this.c = c;
		this.face = face;
		this.Lift = LayoutInflater.from(c);
		this.group_list = group_list;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return group_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return group_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		Viewholder holder = null;
		DisplayImageOptions options = null;
		ImageLoader imageLoader = ImageLoader.getInstance();
		if (v == null) {

			v = (RelativeLayout) Lift.inflate(R.layout.sample, null);
			holder = new Viewholder();
			holder.group_name = (TextView) v.findViewById(R.id.text_name1);
			holder.creator = (TextView) v.findViewById(R.id.creator1);
			holder.chaptr = (TextView) v.findViewById(R.id.text_status);
			holder.channel_num1 = (TextView) v.findViewById(R.id.num1);
			holder.channel2 = (TextView) v.findViewById(R.id.num2);
			holder.channel3 = (TextView) v.findViewById(R.id.num3);
			holder.group_name.setTypeface(face);
			holder.creator.setTypeface(face);
			holder.channel_num1.setTypeface(face);
			holder.chaptr.setTypeface(face);
			holder.profile = (ImageView) v.findViewById(R.id.img_group);
			v.setTag(holder);
		} else {
			holder = (Viewholder) v.getTag();
		}
		String grouptitle = group_list.get(arg0).get("name");
		holder.group_name.setText("" + grouptitle.replace("-", " "));
		String creator1 = "";
		if (group_list.get(arg0).get("creator_list").toString()
				.equalsIgnoreCase("")) {
			holder.creator.setText("Not Available");
		} else {
			creator1 = group_list.get(arg0).get("creator_list").substring(0, 1);
			Log.i("Creator",
					"Creator" + group_list.get(arg0).get("creator_list"));
//			holder.creator.setText(creator1.toUpperCase()
//					+ group_list.get(arg0).get("creator_list").substring(1)+" "+group_list.get(arg0).get("channel_name"));
			holder.creator.setText(" "+group_list.get(arg0).get("channel_name"));
		
		}
		
		if (group_list.get(arg0).get("number_alloc").equals(0)) {
			holder.channel_num1.setText(group_list.get(arg0).get("NA"));
		} else {
			holder.channel_num1.setText(group_list.get(arg0)
					.get("number_alloc").substring(0,1));
			holder.channel2.setText(group_list.get(arg0)
					.get("number_alloc").substring(1,2));
			holder.channel3.setText(group_list.get(arg0)
					.get("number_alloc").substring(2,3));
		}
		if (group_list.get(arg0).get("chapter_taught").equals(null)) {
			holder.chaptr.setText(group_list.get(arg0).get("Not available"));
		} else {
			holder.chaptr.setText("Now - "
					+ group_list.get(arg0).get("chapter_taught"));
		}
		Transformation transformation = new Transformation() {

			@Override
			public Bitmap transform(Bitmap source) {
				int targetWidth;
				int w = source.getWidth();
				int h = source.getHeight();
				if (w > h) {
					targetWidth = w;
					Log.i("w>", "h" + targetWidth);
				} else {
					targetWidth = h;
					Log.i("h>", "w" + targetWidth);
				}

				double aspectRatio = (double) source.getHeight()
						/ (double) source.getWidth();
				int targetHeight = (int) (targetWidth * aspectRatio);
				Bitmap result = Bitmap.createScaledBitmap(source, targetWidth,
						targetHeight, false);
				if (result != source) {
					// Same bitmap is returned if sizes are the same
					source.recycle();
				}
				return result;
			}

			@Override
			public String key() {
				return "transformation" + " desiredWidth";
			}
		};

		/*
		 * Picasso.with(c).load(group_list.get(arg0).get("imagepath").toString())
		 * .error(android.R.drawable.stat_notify_error)
		 * .transform(transformation) .into(holder.profile);
		 */
		//
		String path = group_list.get(arg0).get("imagepath").toString();
		Picasso.with(c).load(path.replace(" ", "%20")).resize(90, 95)
				.into(holder.profile);
		return v;
	}

	public class Viewholder {
		TextView group_name, creator, chaptr, channel_num1, channel2, channel3;
		ImageView profile;
	}

}
