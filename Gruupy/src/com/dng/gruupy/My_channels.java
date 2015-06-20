package com.dng.gruupy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.dng.gruupy.ChatMain.Chat_adapter;
import com.dng.gruupy.ChatMain.GetData;
import com.dng.gruupy.ChatMain.mylocationlistener;
import com.dng.gruupy.ChatMain.Chat_adapter.Viewholder;
import com.squareup.picasso.Picasso;

public class My_channels extends Activity{
	ArrayList<String> Group_name = new ArrayList<String>();
	ArrayList<String> Group_id = new ArrayList<String>();
	ArrayList<String> Group_image = new ArrayList<String>();
	ArrayList<String> img = new ArrayList<String>();
	ArrayList<String> last_msg = new ArrayList<String>();
	ListView list;
	ArrayList<HashMap<String, String>> group_sub = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> group_ssub = new ArrayList<HashMap<String, String>>();
	ArrayList<String> st = new ArrayList<String>();
	String id, sp, mNumber, UID;
	TextView noChatFound;
	Global global;
	int z = 1;
	ImageView wheel;
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;
	TextView text;
	Button serach,cancel;
	LinearLayout rellay;
	ProgressDialog pdialog;
	double mLatitudeText, mLongitudeText;
	LocationManager Locmanager;
	AutoCompleteTextView search_text;
	TextView name_grp, ttle;
	EditText channel_number;
	ImageView addGroup;
	String status_fetched, response_fetch = "";
	static String group_id_clckd = "";
	String lat_long, justforCorrectingFlow, idGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.my_channels);
		global = (Global) getApplicationContext();
		SharedPreferences pref = getSharedPreferences("mprefs",
				Context.MODE_PRIVATE);
		id = pref.getString("id", null);
		cd = new ConnectionDetector(getApplicationContext());
		System.out.println("9999====" + id);
		wheel = (ImageView) findViewById(R.id.ttle_icon);
		name_grp = (TextView) findViewById(R.id.name_grp);
		ttle = (TextView) findViewById(R.id.ttle);
		serach=(Button)findViewById(R.id.btn_serach);
		cancel=(Button)findViewById(R.id.btn_cancel);
		channel_number = (EditText) findViewById(R.id.chnnl_no);
		String htmlText = name_grp.getText().toString()
				.replace("y", "<font color='#EA46EA'>y</font>");
		String htmlTText = ttle.getText().toString()
				.replace("A", "<font color='#FF0000'>A</font>");
		name_grp.setText(Html.fromHtml(htmlText));
		// ttle.setText(Html.fromHtml(htmlTText));
		try {
			isInternetPresent = cd.isConnectingToInternet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * if (isInternetPresent) { GetData get_data = new GetData();
		 * get_data.execute(); } else { // Internet connection is not present //
		 * Ask user to connect to Internet showAlertDialog(ChatMain.this,
		 * "No Internet Connection", "You don't have internet connection.",
		 * false); }
		 */
		wheel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rellay.getVisibility() == 0) {
					rellay.setVisibility(9);
				} else {
					rellay.setVisibility(0);
				}
			}
		});

		Locmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!Locmanager.isProviderEnabled(Locmanager.GPS_PROVIDER)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Location Manager");
			builder.setMessage("Would you like to enable GPS?");
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Launch settings, allowing user to make a change
							Intent i = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(i);
						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// No location service, no Activity
							finish();
						}
					});
			builder.create().show();
		}

		LocationListener ll = new mylocationlistener();
		Locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				ll);
		list = (ListView) findViewById(R.id.listContact);
		text = (TextView) findViewById(R.id.top_txxt);
		rellay = (LinearLayout) findViewById(R.id.rl1);
		search_text = (AutoCompleteTextView) findViewById(R.id.top_txxxt);

		// ----------Check for kitkat version-------------------//

		Log.i("My version", "" + android.os.Build.VERSION.RELEASE);
		if (android.os.Build.VERSION.RELEASE.equalsIgnoreCase("4.4.4")) {

			Log.e("Matched", "");

			text.setVisibility(View.VISIBLE);
		} else {
			text.setVisibility(View.GONE);
		}
		setupViews();
		// GetData g_Data = new GetData();
		// g_Data.execute();
		text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(My_channels.this, ShowAllGroup.class));
			}
		});
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				group_id_clckd = group_sub.get(position).get("group_id");
				Log.i("ID clcked", "" + group_id_clckd);
				global.setGroup_name(group_sub.get(position).get("group_name"));
				global.setGroup_id_clicked(group_id_clckd);
				Log.i("ID clcked", "" + global.getGroup_id_clicked());
				startActivity(new Intent(My_channels.this, Chat.class).putExtra(
						"chat_main", "1"));
			}
		});

		// ------------------------Location listener--------------------------//

		search_text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (search_text.getText().toString().equalsIgnoreCase("")) {
					list.setAdapter(new Chat_adapter(My_channels.this, group_sub));
				} else {
					for (int i = 0; i < group_sub.size(); i++) {
						if (group_sub
								.get(i)
								.get("group_name")
								.equalsIgnoreCase(
										search_text.getText().toString())) {
							group_ssub.add(group_sub.get(i));
						} else if (!group_sub
								.get(i)
								.get("group_name")
								.equalsIgnoreCase(
										search_text.getText().toString())) {

							// Toast.makeText(ChatMain.this,
							// "No matching items found", 5).show();

						}
					}
					list.setAdapter(new Chat_adapter(My_channels.this, group_ssub));
				}
			}
		});
		/*
		 * search_text.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { // TODO Auto-generated method stub
		 * 
		 * 
		 * for(int i=0;i<group_sub.size();i++){
		 * if(group_sub.get(i).get("group_name"
		 * ).equalsIgnoreCase(search_text.getText().toString())){
		 * group_ssub.add(group_sub.get(i)); }else
		 * if(search_text.getText().toString().equalsIgnoreCase("")){
		 * group_ssub=group_sub; } } list.setAdapter(new
		 * Chat_adapter(ChatMain.this, group_ssub)); } });
		 */

	}

	class mylocationlistener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			Log.i("lat and long", "API INVOKED");
			if (location != null) {
				Log.d("LOCATION CHANGED", location.getLatitude() + "");
				Log.d("LOCATION CHANGED", location.getLongitude() + "");
				mLatitudeText = location.getLatitude();
				mLongitudeText = location.getLongitude();
				Log.i("lat and long", "" + mLatitudeText + "," + mLongitudeText);
				lat_long = mLatitudeText + "," + mLongitudeText;
				global.setLat_long(lat_long);
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	private void setupViews() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.listContact);
		noChatFound = (TextView) findViewById(R.id.no_chatfound);
		Typeface face = Typeface.createFromAsset(getAssets(), "Aaargh.ttf");
		search_text.setTypeface(face);
		noChatFound.setTypeface(face);
		text.setTypeface(face);
		ttle.setTypeface(face);
		name_grp.setTypeface(face);
		// addGroup = (ImageView) findViewById(R.id.btn_addgroup);
		// addGroup.setOnClickListener(addGroupClicked);
		// list.setVisibility(View.INVISIBLE);
		// noChatFound.setVisibility(View.VISIBLE);
		justforCorrectingFlow = "smile";
		SharedPreferences sprefs = getSharedPreferences("mprefs",
				Context.MODE_PRIVATE);
		SharedPreferences preferences = getSharedPreferences("mm",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("justforCorrectingFlow", justforCorrectingFlow);
		editor.commit();
		UID = sprefs.getString("id", "");
		Log.i("UID", "" + UID);
		System.out.println("--justforCorrectingFlow in shr-------"
				+ justforCorrectingFlow);

		serach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!(channel_number.getText().toString().equalsIgnoreCase("") && search_text
						.getText().toString().equalsIgnoreCase(""))) {
					for (int i = 0; i < group_sub.size(); i++) {
						if (!search_text.getText().toString()
								.equalsIgnoreCase("")) {
							if (group_sub
									.get(i)
									.get("group_name")
									.equalsIgnoreCase(
											search_text.getText().toString())) {
								group_ssub.add(group_sub.get(i));
							} else if (!group_sub
									.get(i)
									.get("group_name")
									.equalsIgnoreCase(
											search_text.getText().toString())) {

								// Toast.makeText(ChatMain.this,
								// "No matching items found", 5).show();

							}
							Log.i("Group1", "Ssub1" + group_ssub.toString());
							list.setAdapter(new Chat_adapter(My_channels.this,
									group_ssub));
						} else {
							if (group_sub
									.get(i)
									.get("last_msg")
									.equalsIgnoreCase(
											channel_number.getText().toString())) {
								group_ssub.add(group_sub.get(i));
							} else if (!group_sub
									.get(i)
									.get("last_msg")
									.equalsIgnoreCase(
											search_text.getText().toString())) {

								// Toast.makeText(ChatMain.this,
								// "No matching items found", 5).show();

							}
							Log.i("Group", "Ssub" + group_ssub.toString());
							list.setAdapter(new Chat_adapter(My_channels.this,
									group_ssub));
						}

					}

				}
			}
		});

	}

	public OnClickListener addGroupClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			System.out.println("group clicked");
			Intent in = new Intent(My_channels.this, AddGroup.class);
			startActivity(in);
		}
	};

	public class Chat_adapter extends BaseAdapter {
		LayoutInflater Lift;
		Context c;
		int layoutResourceId;
		ArrayList<HashMap<String, String>> chat_list;

		public Chat_adapter(Context c,
				ArrayList<HashMap<String, String>> chat_list) {
			this.c = c;
			this.Lift = LayoutInflater.from(c);
			this.chat_list = chat_list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return chat_list.size();
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
			Random rand = new Random();
			if (v == null) {

				v = (LinearLayout) Lift.inflate(R.layout.chat_items, null);
				holder = new Viewholder();
				holder.group_name = (TextView) v.findViewById(R.id.user_name);
				holder.chat_msg = (TextView) v.findViewById(R.id.chat_msg);
				holder.channel_num1 = (TextView) v.findViewById(R.id.num1);
				holder.channel2 = (TextView) v.findViewById(R.id.num2);
				holder.channel3 = (TextView) v.findViewById(R.id.num3);
				holder.group_image=(ImageView)v.findViewById(R.id.img_group);
				Typeface face = Typeface.createFromAsset(getAssets(),
						"Aaargh.ttf");
				holder.group_name.setTypeface(face);
				// holder.chat_msg.setTypeface(face);
				v.setTag(holder);
			} else {
				holder = (Viewholder) v.getTag();
			}
			holder.group_name.setText(chat_list.get(arg0).get("group_name"));
			holder.group_name.setTextColor(Color.argb(255, rand.nextInt(256),
					rand.nextInt(256), rand.nextInt(256)));
			if (group_sub.get(arg0).get("last_msg").equals(0)) {
				holder.channel_num1.setText(group_sub.get(arg0).get("NA"));
			} else {
				holder.channel_num1.setText(group_sub.get(arg0).get("last_msg")
						.substring(0, 1));
				holder.channel2.setText(group_sub.get(arg0).get("last_msg")
						.substring(1, 2));
				holder.channel3.setText(group_sub.get(arg0).get("last_msg")
						.substring(2, 3));
			}
			try {
				Picasso.with(My_channels.this)
						.load(group_sub.get(arg0).get("group_image")
								.replace(" ", "%20")).resize(65, 65).into(holder.group_image);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// holder.chat_msg.setText(chat_list.get(arg0).get("last_msg"));
			return v;
		}

		public class Viewholder {
			TextView group_name, chat_msg, channel_num1, channel2, channel3;
			ImageView group_image;
		}

	}

	public class GetData extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
			if (z == 1) {
				pdialog = ProgressDialog.show(My_channels.this, "", "Loading..");
				/*
				 * new Handler().postDelayed(new Runnable() {
				 * 
				 * @Override public void run() { // TODO Auto-generated method
				 * stub
				 * 
				 * } }, 3000);
				 */
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			getData();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (z == 1) {
				pdialog.dismiss();
			}
			System.out.println("In posttttttttttttttttttttttt"
					+ group_sub.size());
			ArrayAdapter<String> arr_adap = new ArrayAdapter<String>(
					My_channels.this, android.R.layout.simple_list_item_1,
					Group_name);
			search_text.setAdapter(arr_adap);

			if (group_sub.size() != 0) {
				// Toast.makeText(getApplicationContext(), "No chat found",
				// Toast.LENGTH_LONG).show();
				// noChatFound.setVisibility(View.INVISIBLE);
				list.setAdapter(new Chat_adapter(My_channels.this, group_sub));

			} else {
				System.out.println("in if");
				if (group_sub.size() == 0) {
					noChatFound.setVisibility(View.VISIBLE);
				}
			}
		}

		protected void getData() {
			HttpClient httpclient = new DefaultHttpClient();
			// HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
			// + "grp_by_chat.php");
			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "subs_group.php");
			ResponseHandler<String> resp_hndl = new BasicResponseHandler();
			List<NameValuePair> linputs = new ArrayList<NameValuePair>();
			linputs.add(new BasicNameValuePair("user_id", UID));
			// UID
			Log.i("Linputs", "" + linputs);
			try {
				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(linputs));
				response_fetch = httpclient.execute(httppost, resp_hndl);
				System.out.println("--reppppppppppp-----"
						+ response_fetch.toString());
				JSONObject js = new JSONObject(response_fetch);
				JSONObject job = js.getJSONObject("response");
				JSONArray ttle = job.getJSONArray("status");
				if (ttle != null) {
					status_fetched = ttle.getString(0);
				}
				System.out.println("rSTATUS======" + status_fetched);
				sp = status_fetched;
				System.out.println("f is---" + sp);
				group_sub.clear();
				JSONArray group_id = job.getJSONArray("id");
				if (group_id != null) {
					int len = group_id.length();
					Group_id = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						Group_id.add(group_id.get(i).toString());
					}
					System.out.println("group_id -----" + group_id.get(0));
				}

				JSONArray group_name = job.getJSONArray("name");
				if (group_name != null) {
					int len = group_name.length();
					Group_name = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						Group_name.add(group_name.get(i).toString());
					}
					System.out.println("Group_name -----" + Group_name.get(0));
				}
				JSONArray group_image = job.getJSONArray("image");
				if (group_name != null) {
					int len = group_image.length();
					Group_image = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						Group_image.add(group_name.get(i).toString());
					}
					System.out
							.println("Group_image -----" + Group_image.get(0));
				}
				/*
				 * JSONArray im = job.getJSONArray("image"); if (im != null) {
				 * int len = im.length(); img = new ArrayList<String>(); for
				 * (int i = 0; i < len; i++) { img.add(im.get(i).toString()); }
				 * }
				 */
				//
				JSONArray channel_no = job.getJSONArray("channel_no");
				if (channel_no != null) {
					int len = channel_no.length();
					last_msg = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						last_msg.add(channel_no.get(i).toString());
					}
				}

				for (int i = 0; i < group_name.length(); i++) {
					HashMap<String, String> grp_details = new HashMap<String, String>();
					grp_details.put("group_id", group_id.getString(i)
							.toString());
					grp_details.put("group_name", group_name.get(i).toString());
					grp_details.put("group_image", group_image.get(i)
							.toString());
					grp_details.put("last_msg", channel_no.get(i).toString());

					group_sub.add(grp_details);
					global.setGroup_sub(group_sub);

				}

			} catch (ClientProtocolException e) {
				System.out.println("ClientProtocolException--" + e);
			} catch (IOException e) {
				System.out.println("IOException--" + e);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Log.i("Group_subscription", "" + group_sub);

		}

	}

	//
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data != null) {
			// selectedImageUri = data.getData();
			if (requestCode == 1) {
				Uri uri = data.getData();
				if (uri != null) {
					Cursor c = null;

					try {
						c = getContentResolver()
								.query(uri,
										new String[] {
												ContactsContract.CommonDataKinds.Phone.NUMBER,
												ContactsContract.CommonDataKinds.Phone.TYPE,
												ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME },
										null, null, null);

						if (c != null && c.moveToFirst()) {
							mNumber = c.getString(0);
							int type = c.getInt(1);
							System.out
									.println("=--contactdata------" + mNumber);
						}
					} finally {
						if (c != null) {
							c.close();
						}
					}
				}
			}

		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		GetData get_data = new GetData();
		get_data.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		// alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		z = 2;
		try {
			isInternetPresent = cd.isConnectingToInternet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isInternetPresent) {
			GetData get_data = new GetData();
			get_data.execute();
		} else {
			showAlertDialog(My_channels.this, "",
					"No Internet connection available", false);
		}
	}

}
