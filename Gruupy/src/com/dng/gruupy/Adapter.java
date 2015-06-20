package com.dng.gruupy;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Adapter extends BaseExpandableListAdapter {
	private List<Object_class> objects;
	private Activity activity;
	private LayoutInflater inflater;
	static String chapter_name_positionwise;

	public Adapter(Activity activity, List<Object_class> objects) {
		this.objects = objects;
		this.activity = activity;
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object_class getChild(int groupPosition, int childPosition) {
		return objects.get(groupPosition).getObjects().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		Object_class object = (Object_class) getChild(groupPosition,
				childPosition);
		CustomExpandableListView subObjects = (CustomExpandableListView) convertView;
		if (convertView == null) {
			subObjects = new CustomExpandableListView(activity);
		}
		Log.i("Chapter","position passed"+chapter_name_positionwise);
		Adapter2 adapter = new Adapter2(activity, object,chapter_name_positionwise);
		subObjects.setAdapter(adapter);

		return subObjects;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return objects.get(groupPosition).getObjects().size();
	}

	@Override
	public Object_class getGroup(int groupPosition) {
		return objects.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return objects.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		Object_class object = (Object_class) getGroup(groupPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_element, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(object.getName());
		chapter_name_positionwise=object.getName();
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}

class Adapter2 extends BaseExpandableListAdapter {
	private Object_class object;
	private LayoutInflater inflater;
	Dialog topic_dial, topic_dialog;
	Global global;
	String chptr_name;
	EditText sub_topic;
	Button resume, playback;
	String rresponse,chapter_name, topic,session,SstatuS,topic_recd,c_id,user_id;
	
	private Activity activity;

	public Adapter2(Activity activity, Object_class object,String chapter_name_positionwise) {
		this.activity = activity;
		this.object = object;
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.chptr_name=chapter_name_positionwise;
		global = (Global) activity.getApplicationContext();
		SharedPreferences preferences = activity.getSharedPreferences("mprefs",
				Context.MODE_PRIVATE);
		user_id = preferences.getString("id", "");
	}

	@Override
	public Object_class getChild(int groupPosition, int childPosition) {
		return object.getObjects().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Object_class object = (Object_class) getChild(0, childPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_element, null);

			Resources r = activity.getResources();
			float px40 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					40, r.getDisplayMetrics());
			convertView.setPadding(convertView.getPaddingLeft() + (int) px40,
					convertView.getPaddingTop(), convertView.getPaddingRight(),
					convertView.getPaddingBottom());
		}
		
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(object.getName());
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return object.getObjects().size();
	}

	@Override
	public Object_class getGroup(int groupPosition) {
		return object;
	}

	@Override
	public int getGroupCount() {
		return 1;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_element, null);
			Resources r = activity.getResources();
			float px20 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					20, r.getDisplayMetrics());
			convertView.setPadding(convertView.getPaddingLeft() + (int) px20,
					convertView.getPaddingTop(), convertView.getPaddingRight(),
					convertView.getPaddingBottom());
		}
		RelativeLayout rel = (RelativeLayout) convertView
				.findViewById(R.id.rel_child);
		final TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(object.getName());
		if(global.getFold().equalsIgnoreCase("1")){
			rel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					global.setTopic(name.getText().toString());
					Log.e("Group ","Possion"+chptr_name);
					String cc_name=chptr_name;
					global.setChap_name(cc_name);
					activity.startActivity(new Intent(activity,
							CaptureSignature.class).putExtra("start", "0"));
				}
			});
		}
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
}