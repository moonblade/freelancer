package utilities;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dng.gruupy.R;

public class copy_adapter extends BaseAdapter {
	LayoutInflater Lift;
	Context c;
	SharedPreferences sprefs;
	int layoutResourceId;
	String UNAME,UCLR;
	Typeface face;
	String uname;
	ArrayList<String> chat_list, temp_list;

	public copy_adapter(Context c,
			ArrayList<String> chat_list,String unames) {
		this.c = c;
		this.Lift = LayoutInflater.from(c);
		this.chat_list = chat_list;
		this.temp_list = chat_list;
		this.uname=unames;
		face = Typeface.createFromAsset(c.getAssets(), "Aaargh.ttf");
		sprefs = c.getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		UNAME = sprefs.getString("Uname", "");
		UCLR = sprefs.getString("color_uname", "");
		Log.i("UNAME", "" + UNAME);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chat_list.size();
	}

	public void clear() {
		temp_list = chat_list;
		chat_list.clear();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return chat_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		Viewholder holder = null;
		if (v == null) {

			v = (LinearLayout) Lift.inflate(R.layout.new_chat, null);
			holder = new Viewholder();
			holder.sender_llay = (LinearLayout) v
					.findViewById(R.id.chat_sender);
			holder.rec_llay = (LinearLayout) v
					.findViewById(R.id.LinearLayout01);
			holder.sgroup_name = (TextView) v.findViewById(R.id.sname);
			holder.schat_msg = (TextView) v.findViewById(R.id.TextView001);
			holder.rgroup_name = (TextView) v.findViewById(R.id.rname);
			holder.rchat_msg = (TextView) v.findViewById(R.id.chat_msga);
			holder.stymchck = (TextView) v.findViewById(R.id.stime);
			holder.rtymchck = (TextView) v.findViewById(R.id.rtime);
			v.setTag(holder);
		} else {
			holder = (Viewholder) v.getTag();
		}
		Random rand = new Random();

			// --------------My record-------------------//

			holder.sgroup_name.setTextColor(Integer.parseInt(UCLR));
			holder.sgroup_name.setText(UNAME);
			holder.sgroup_name.setTypeface(face);

			holder.stymchck.setText("11:00");
			holder.stymchck.setTypeface(face);
			holder.rchat_msg.setText(chat_list.get(arg0));
			holder.rchat_msg.setTypeface(face);
			holder.rec_llay.setVisibility(View.GONE);
		return v;
	}

	public class Viewholder {
		TextView sgroup_name, schat_msg, schck, stymchck;
		TextView rgroup_name, rchat_msg, rchck, rtymchck;
		LinearLayout sender_llay, rec_llay;
		RelativeLayout reciever_lay, sender_lay;
	}

	public void add(Object object) {
		// TODO Auto-generated method stub
		chat_list = temp_list;
	}

}
