package utilities;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dng.gruupy.R;

public class Comments_adapter extends BaseAdapter {
	LayoutInflater Lift;
	Context c;
	SharedPreferences sprefs;
	int layoutResourceId;
	String UNAME, UCLR;
	Typeface face;
	String slide_index;
	ArrayList<String> chat_list=new ArrayList<String>();
	ArrayList<String> Ids_list=new ArrayList<String>();
	ArrayList<String> names_list=new ArrayList<String>();
	String UID = "";

	public Comments_adapter(Context c, ArrayList<String> chat_list,
			ArrayList<String> Ids_list, ArrayList<String> names_list,
			String index) {
		this.c = c;
		this.Lift = LayoutInflater.from(c);
		this.chat_list = chat_list;
		this.slide_index = index;
		this.Ids_list = Ids_list;
		this.names_list = names_list;
		face = Typeface.createFromAsset(c.getAssets(), "Aaargh.ttf");
		sprefs = c.getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		UID = sprefs.getString("id", "");

		Log.i("Sizes", "Sizes" + chat_list.size() + Ids_list.size()
				+ names_list.size());

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names_list.size();
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
		Log.i("Sizes  notify", "notify Sizes" + chat_list.size() + Ids_list.size()
				+ names_list.size());

	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return names_list.get(arg0);
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

			v = (RelativeLayout) Lift.inflate(R.layout.listing_item, null);
			holder = new Viewholder();
			holder.rl1 = (RelativeLayout) v.findViewById(R.id.relativeLayout1);
			holder.rl2 = (RelativeLayout) v.findViewById(R.id.user2);
			holder.comments = (TextView) v.findViewById(R.id.video_thumb);
			holder.slide_number = (TextView) v.findViewById(R.id.slide_num);
			holder.sender_name = (TextView) v.findViewById(R.id.user_sending);
			holder.comments1 = (TextView) v.findViewById(R.id.TextView03);
			holder.slide_number1 = (TextView) v.findViewById(R.id.TextView01);
			holder.sender_name1 = (TextView) v.findViewById(R.id.TextView02);
			// --------------My record-------------------//
			v.setTag(holder);
		} else {
			holder = (Viewholder) v.getTag();
		}
		Log.i("IDs", "IDS" + Ids_list.get(arg0).toString()+chat_list.get(arg0).toString());
		if (Ids_list.get(arg0).toString().equalsIgnoreCase(UID)) {
			Log.i("ifIDs", "IDS" + Ids_list.get(arg0).toString());
			holder.rl1.setVisibility(0);
			holder.rl2.setVisibility(9);
			holder.comments.setText(chat_list.get(arg0));
//			holder.sender_name.setText("Me:");
			holder.sender_name.setText(names_list.get(arg0));
			if (slide_index.equalsIgnoreCase("")) {
				holder.slide_number.setText("");
			} else {
				holder.slide_number.setText("" + slide_index);
			}

		} else {
			Log.i("elseIDs", "IDS" + Ids_list.get(arg0).toString());
			holder.rl2.setVisibility(0);
			holder.rl1.setVisibility(9);
			holder.comments1.setText(chat_list.get(arg0));
			if (names_list.get(arg0).equals(null)) {
				holder.sender_name1.setText("Anonymous");
			} else {
				holder.sender_name1.setText(names_list.get(arg0));
			}

			if (slide_index.equalsIgnoreCase("")) {
				holder.slide_number1.setText("");
			} else {
				holder.slide_number1.setText("" + slide_index);
			}
		}
		return v;
	}

	public class Viewholder {
		RelativeLayout rl1, rl2;
		TextView comments, slide_number, sender_name;
		TextView comments1, slide_number1, sender_name1;
	}

}