package com.dng.gruupy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
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

import utilities.Bitmap_blur;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;

public class ShowAllGroup extends Activity {
	ArrayList<String> Dist, groupId = new ArrayList<String>();
	ArrayList<String> Group_name = new ArrayList<String>();
	ArrayList<String> join_groupId = new ArrayList<String>();
	ArrayList<String> number_alloc,channel_names,chapter_taught,channel_no,creator_list,creator_id,Image, Group_type, groupimg = new ArrayList<String>();
	ArrayList<String> groupMNAme = new ArrayList<String>();
	ArrayList<ContactSec> gridArrayy = new ArrayList<ContactSec>();
	String st="";
	String URL = Global_Constants.BASE_URL + "subscribe.php";
	String All_groups_URL = Global_Constants.BASE_URL + "group_info.php";
	ArrayList<HashMap<String, String>> group_sub, group_Sub,
			demo = new ArrayList<HashMap<String, String>>();
	ArrayList<String> grp_subscribed_created = new ArrayList<String>();
	CustomGridFilter cm;
	String status1;
	Global global;
	Button done;
	TextView dist_lbl;
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;
	RelativeLayout mxlmt, hidden;
	static int height_s;
	RelativeLayout action_bar;
	LinearLayout list_layout;
	SharedPreferences sprefs;
	String sp, respo_str, output_str, id, U_id, output;
	String total_groups, G_id;
	TextView search,close;
	int min = 1, max = 100;
	TextView Dmin, Dmax;
	Sample sample;
	String key = "groups";
	int Cmax, Cmin;
	LinearLayout seacrh_layout;
	static LinearLayout parent_layout;
	List<String> dist_limit = new ArrayList<String>();
	EditText Keyword, max_range;
	ImageView options, icon;
	RadioButton rb1, rb2, rb3, rb4, rb5, rb6;
	int rb1_check = 0, rb2_check = 0, rb3_check = 0, rb4_check = 0,
			rb5_check = 0;
	Bitmap bmp, blurred_bmp;
	ListView groupList;
	Dialog subbox;
	DisplayMetrics dmtrx;
	String Once = "";
	int s_width;
	String[] list_opt = { "Create a group", "Search for a group" };
	Bitmap_blur blur;
	TextView min_range, group_title, fin, find_tv, from;
	TextView no_group_found;
	Typeface face;
	SeekBar dist_seek;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		s_width = displaymetrics.widthPixels;
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		Log.i("Swidth", "S WIDTH" + s_width);
		// ----------------------network thread
				// exception------------------------//
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
		// Log.e("Once","Once val"+global.getOnce());
		if (s_width > 500) {
			Log.i("540", "540" + s_width);
			setContentView(R.layout.w_sample_newshowall);
		} else {
			Log.i(">540", ">540" + s_width);
			setContentView(R.layout.w_sample_newshowall);
		}
		// setupView();
		face = Typeface.createFromAsset(getAssets(), "Aaargh.ttf");
		parent_layout = (LinearLayout) findViewById(R.id.parent_layout1);
//		dist_seek = (SeekBar) findViewById(R.id.search_seek);
		sprefs = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		U_id = sprefs.getString("id", "");
		Log.i("ID recieved", "" + U_id);
		global = (Global) getApplicationContext();
		Keyword = (EditText) findViewById(R.id.find);
		action_bar = (RelativeLayout) findViewById(R.id.act_bar);
		Keyword.setTypeface(face);
		no_group_found = (TextView) findViewById(R.id.no_groupfound);
		no_group_found.setTypeface(face);
		options = (ImageView) findViewById(R.id.btn_options);
		cd = new ConnectionDetector(getApplicationContext());
		icon = (ImageView) findViewById(R.id.logo_icon);
		seacrh_layout = (LinearLayout) findViewById(R.id.rll1);
		group_title = (TextView) findViewById(R.id.group_title);
		group_title.setTypeface(face);
		// min_range = (TextView) findViewById(R.id.min_range);
		// min_range.setTypeface(face);
//		max_range = (EditText) findViewById(R.id.max_range);
//		max_range.setTypeface(face);
//		Dmin = (TextView) findViewById(R.id.min_val);
//		Dmin.setTypeface(face);
//		Dmax = (TextView) findViewById(R.id.max_val);
//		Dmax.setTypeface(face);
//		fin = (TextView) findViewById(R.id.fin);
//		fin.setTypeface(face);
//		find_tv = (TextView) findViewById(R.id.find_tv);
//		find_tv.setTypeface(face);
//		from = (TextView) findViewById(R.id.from);
//		from.setTypeface(face);
		groupList = (ListView) findViewById(R.id.list_group);
		search = (TextView) findViewById(R.id.srch_bttn);
		search.setTypeface(face);
		close = (TextView) findViewById(R.id.close_bttn);
		close.setTypeface(face);

		list_layout = (LinearLayout) findViewById(R.id.rl2);
		// list_layout.setLayoutParams(new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.MATCH_PARENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT, 10.5f));

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 seacrh_layout.setVisibility(9);
				 icon.setVisibility(0);
				 group_title.setText("All Channels");
//				startActivity(new Intent(ShowAllGroup.this, AddGroup.class));
			}
		});
		if (global.getSub_type().equalsIgnoreCase("teacher")) {

		} else {
			// add_group.setVisibility(9);
		}
		// ----------------------Get listing----------------------//
		isInternetPresent = cd.isConnectingToInternet();
		/*if (isInternetPresent) {
			GetData dl = new GetData();
			dl.execute();
		} else {
			// Internet connection is not present
			// Ask user to connect to Internet
			showAlertDialog(ShowAllGroup.this, "No Internet Connection",
					"You don't have internet connection.", false);
		}*/
		/*max_range.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (max_range.getText().toString().equalsIgnoreCase("")) {
					dist_seek.setProgress(1);
					Dmax.setText(String.valueOf(100));
				} else {
					max = Integer.parseInt(max_range.getText().toString());
					Dmax.setText(String.valueOf(max));
				}
			}
		});*/

		options.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				height_s = 0;
				if (seacrh_layout.getVisibility() == 0) {
					seacrh_layout.setVisibility(9);
					icon.setVisibility(0);
					// list_layout.setLayoutParams(new
					// LinearLayout.LayoutParams(
					// LinearLayout.LayoutParams.MATCH_PARENT,
					// LinearLayout.LayoutParams.WRAP_CONTENT, 10.5f));
					group_title.setText("Tuition24");
					LinearLayout list_layout = (LinearLayout) findViewById(R.id.rl2);
					height_s = 0;
					LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) list_layout
							.getLayoutParams();
					lp.setMargins(0, 0, 0, 0);
					list_layout.setLayoutParams(lp);

				} else {

					group_title.setText("Search");
					icon.setVisibility(9);
					seacrh_layout.setVisibility(0);

					seacrh_layout
							.getViewTreeObserver()
							.addOnGlobalLayoutListener(
									new ViewTreeObserver.OnGlobalLayoutListener() {

										@Override
										public void onGlobalLayout() {
											// TODO Auto-generated method stub
											int width = seacrh_layout
													.getWidth();
											height_s = seacrh_layout
													.getHeight();
											Log.i("Height",
													"height of search layout"
															+ height_s);
											// boolean visible = heightDiff >
											// screenHeight / 3;
										}
									});
					LinearLayout list_layout = (LinearLayout) findViewById(R.id.rl2);
					LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) list_layout
							.getLayoutParams();
					Log.i("On click", "on Click" + height_s);
					lp.setMargins(0, height_s + 5, 0, 0);
					list_layout.setLayoutParams(lp);
				}
			}
		});

		/*dist_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (!(min == 1)) {
					progress += min;
				}
				if (max < min) {
					Toast.makeText(ShowAllGroup.this,
							"Maximum limit can't be less than minimum", 5)
							.show();
					Dmax.setText("");
				}
				if (!(max == 100)) {
					seekBar.setMax(max - 20);
					if (progress == max - 20) {
						Dmax.setText(String.valueOf(progress - 20));
					} else {
						Dmax.setText(String.valueOf(progress));
					}
				} else {
					Dmax.setText(String.valueOf(progress));
				}
			}
		});
*/
		groupList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				global.setIndex(position);
				G_id = demo.get(position).get("group_id");

				global.setGroup_id(G_id);
				Log.i("group_id fetched", "" + G_id);
				global.setGroup_name(demo.get(position).get("name"));
				Log.i("group_name fetched", "golabari" + global.getGroup_name());
				global.setGroup_image(demo.get(position).get("imagepath"));
				Log.i("group_image fetched", "" + global.getGroup_image());
				if (global.getGroups_subscribed_created().contains(
						demo.get(position).get("group_id"))) {
					startActivity(new Intent(ShowAllGroup.this,
							CaptureSignature.class).putExtra("name",
							demo.get(position).get("name")).putExtra("creator_id", creator_id.get(position)).putExtra("channel_no", channel_no.get(position)));
					// startActivity(new Intent(ShowAllGroup.this, Chat.class)
					// .putExtra("chat_main", "0"));
				} else {
//					if (demo.equals(null)) {
//						subbox.show();
//					} else {
//						if (demo.get(position).get("group_type")
//								.equalsIgnoreCase("Channel")) {
							subbox = new Dialog(
									ShowAllGroup.this,
									android.R.style.Theme_Translucent_NoTitleBar);
							subbox.setContentView(R.layout.subscribe_dialog);
							bmp = takeScreenShot(ShowAllGroup.this);
							blur = new Bitmap_blur();
							blurred_bmp = blur.fastblur(bmp, 10);
							subbox.setCancelable(false);
							final Drawable draw = new BitmapDrawable(
									getResources(), blurred_bmp);
							subbox.getWindow().setBackgroundDrawable(draw);
							WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
							lp.copyFrom(subbox.getWindow().getAttributes());
							lp.width = WindowManager.LayoutParams.MATCH_PARENT;
							lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
							// lp.gravity = Gravity.CENTER_VERTICAL;
							// Window window = subbox.getWindow();
							// window.setGravity(Gravity.CENTER);
							TextView textView1 = (TextView) subbox
									.findViewById(R.id.textView1t);
							Button yes = (Button) subbox.findViewById(R.id.yes);
							Button cncl = (Button) subbox
									.findViewById(R.id.cncl);
							textView1.setTypeface(face);
							yes.setTypeface(face);
							cncl.setTypeface(face);
							subbox.setCancelable(false);
							yes.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Add_my_group add_group = new Add_my_group();
									add_group.execute();
									subbox.dismiss();
								}
							});
							cncl.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									subbox.dismiss();
								}
							});
							subbox.show();

						}
//					}

//				}
			}
		});

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (validate()
						&& !Keyword.getText().toString()
								.equalsIgnoreCase("all")) {
					Filter_groups filter = new Filter_groups();
					filter.execute();
				} else {
					Keyword.setText("");
					GetData dl = new GetData();
					dl.execute();
				}
			}
		});

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

	private static Bitmap takeScreenShot(Activity activity) {
		View v1 = parent_layout.getRootView();
		v1.setDrawingCacheEnabled(true);
		Bitmap bm = v1.getDrawingCache();
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
		return bm;
	}

	public class Filter_groups extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			filter_groups();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Keyword.clearFocus();
			Keyword.setText("");
			dist_seek.setProgress(0);
			// groupList.setAdapter(new Sample_adapter(ShowAllGroup.this,
			// group_Sub));
			Sample_adapter sample = new Sample_adapter(ShowAllGroup.this,
					group_Sub);
			groupList.setAdapter(sample);
			if (group_Sub != null) {
				sample.notifyDataSetChanged();
			} else {
				no_group_found.setVisibility(View.VISIBLE);
			}

		}

		protected void filter_groups() {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "search_group.php");
			List<NameValuePair> listinputs = new ArrayList<NameValuePair>();
			listinputs.add(new BasicNameValuePair("group_name", Keyword
					.getText().toString()));
			listinputs.add(new BasicNameValuePair("key", key));
			listinputs.add(new BasicNameValuePair("distance", Dmax.getText()
					.toString()));
			listinputs.add(new BasicNameValuePair("lat_long", global
					.getLat_long()));

			Log.i("Linputs", "" + listinputs);
			try {
				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(listinputs));
				HttpResponse response = httpclient.execute(httppost);

				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				String status_fetched = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				HashMap<String, String> s_grp_details = new HashMap<String, String>();
				group_Sub = new ArrayList<HashMap<String, String>>();
				System.out.println("--reppppppppppps-----" + result.toString());
				JSONObject js = new JSONObject(result.toString());
				JSONObject job = js.getJSONObject("response");
				JSONArray ttle = job.getJSONArray("status");
				if (ttle != null) {
					status_fetched = ttle.getString(0);
				}
				System.out.println("rSTATUS======" + status_fetched);
				sp = status_fetched;
				System.out.println("f is---" + sp);
				if (sp.equalsIgnoreCase("true")) {

					JSONArray group_name = job.getJSONArray("group_name");
					if (group_name != null) {
						Group_name = new ArrayList<String>();
						for (int i = 0; i < group_name.length(); i++) {
							Group_name.add(group_name.get(i).toString());
							s_grp_details.put("group_name", group_name.get(i)
									.toString());
						}
						System.out.println("group_id -----" + Group_name);
					}

					JSONArray dist = job.getJSONArray("distance");
					if (dist != null) {
						Dist = new ArrayList<String>();
						for (int i = 0; i < dist.length(); i++) {
							Dist.add(dist.get(i).toString());
							s_grp_details.put("distance", Dist.get(i));
						}
					}

					JSONArray image = job.getJSONArray("image");
					if (dist != null) {
						Image = new ArrayList<String>();
						for (int i = 0; i < image.length(); i++) {
							Image.add(image.get(i).toString());
							s_grp_details.put("Image", Image.get(i));
						}
					}

					//
					JSONArray count = job.getJSONArray("no_of_groups");
					if (count != null) {
						for (int i = 0; i < count.length(); i++) {
							total_groups = count.getString(0);
							s_grp_details.put("total", total_groups);
						}
					}

					group_Sub.add(s_grp_details);
				} else {
					sp = "false";
				}
			} catch (ClientProtocolException e) {
				System.out.println("ClientProtocolException--" + e);
			} catch (IOException e) {
				System.out.println("IOException--" + e);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Log.i("Group_found", "" + group_Sub);

		}

	}

	protected boolean validate() {
		// TODO Auto-generated method stub
		if (Keyword.getText().toString().equalsIgnoreCase("")) {
			Toast.makeText(ShowAllGroup.this, "Field can't be empty", 5).show();
			return false;
		} else {
			return true;
		}

	}

	public class Add_my_group extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {

			add_my_group();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (output.equalsIgnoreCase("true")) {
				Toast.makeText(ShowAllGroup.this,
						"Group subscribed successfully", 10).show();
				startActivity(new Intent(ShowAllGroup.this, Chat.class)
						.putExtra("chat_main", "0"));

			} else {
				Toast.makeText(ShowAllGroup.this,
						"Some error occurred..Please try again!!", 10).show();
			}

		}

	}

	protected void add_my_group() {
		DefaultHttpClient dhcp = new DefaultHttpClient();
		ResponseHandler<String> response = new BasicResponseHandler();
		HttpPost postmethod = new HttpPost(URL);
		List<NameValuePair> inputs = new ArrayList<NameValuePair>();
		inputs.add(new BasicNameValuePair("user_id", U_id));
		inputs.add(new BasicNameValuePair("group_id", G_id));
		Log.d("Inputs", "" + inputs);

		try {
			postmethod.setEntity(new UrlEncodedFormEntity(inputs));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String Rsponse = dhcp.execute(postmethod, response);
			Log.d("rsponse", "" + Rsponse);
			try {
				JSONObject job1 = new JSONObject(Rsponse);
				Log.i("Job1", "" + job1);
				JSONObject j1 = job1.getJSONObject("response");
				output = j1.getString("status");
				Log.d("Output of add group", "" + output);
				if (output.equalsIgnoreCase("true")) {
					Log.d("Output of in add group", "" + output);

				} else {
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class Get_subscribed_groups extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			get_subscribed_groups();
			return null;
		}

		private void get_subscribed_groups() {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "subs_group.php");
			ResponseHandler<String> res_hndlr = new BasicResponseHandler();
			List<NameValuePair> lpair = new ArrayList<NameValuePair>();
			lpair.add(new BasicNameValuePair("user_id", U_id));

			try {
				httppost.setEntity(new UrlEncodedFormEntity(lpair));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				output_str = httpclient.execute(httppost, res_hndlr);
				try {
					JSONObject main_json = new JSONObject(output_str);
					JSONObject res_json = main_json.getJSONObject("response");
					Log.e("ResJSON", "ResJSON" + res_json.toString());
					JSONArray groups_status=res_json.getJSONArray("status");
					status1=groups_status.getString(0);
					if(status1.equalsIgnoreCase("true")){
						JSONArray jarr = res_json.getJSONArray("id");
						if (!jarr.equals(null)) {
							for (int i = 0; i < jarr.length(); i++) {
								String c_s_group_id = jarr.getString(i);
								grp_subscribed_created.add(c_s_group_id);
							}
						}
						global.setGroups_subscribed_created(grp_subscribed_created);
						Log.e("Groups subscribed from ws",
								"Listing from web service"
										+ global.getGroups_subscribed_created());
					}else{
						
					}
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public class GetData extends AsyncTask<Void, Void, String> {
		ProgressDialog pdial;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			demo.clear();
			pdial = ProgressDialog.show(ShowAllGroup.this, "", "Loading...");

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
			pdial.dismiss();
			System.out.println("In posttttttttttttttttttttttt");
			if (st.equals("false")) {
				Toast.makeText(getApplicationContext(), "No group found",
						Toast.LENGTH_LONG).show();
				System.out.println("in if");
				// list.setVisibility(View.GONE);
				// noChatFound.setVisibility(View.VISIBLE);
			} else {
				System.out.println("in else");
				Log.i("group size", "" + groupimg.size());
				for (int i = 0; i < groupimg.size(); i++) {
					System.out.println("name ========" + groupMNAme.get(i));
					System.out.println("type ========" + number_alloc.get(i));
					gridArrayy.add(new ContactSec(groupMNAme.get(i), Group_type
							.get(i), groupimg.get(i),creator_list.get(i),channel_names.get(i),chapter_taught.get(i),number_alloc.get(i)));
					HashMap<String, String> new_details = new HashMap<String, String>();
					new_details.put("name", groupMNAme.get(i));
					new_details.put("imagepath", groupimg.get(i));
					new_details.put("group_id", groupId.get(i));
					new_details.put("chapter_taught", chapter_taught.get(i));
					new_details.put("number_alloc", number_alloc.get(i));
					new_details.put("creator_list", creator_list.get(i));
					new_details.put("channel_name", channel_names.get(i));
					demo.add(new_details);
				}
				groupList.setAdapter(new Sample(ShowAllGroup.this, demo, face));
			}
			Get_subscribed_groups get_sub_groups = new Get_subscribed_groups();
			get_sub_groups.execute();

			// cm = new CustomGridFilter(ShowAllGroup.this,
			// R.layout.custom_group,
			// gridArrayy);
			// // System.out.println("size ========" + gridArrayy);
			// groupList.setAdapter(cm);
		}

		protected void getData() {
			demo.clear();
			HttpClient httpclient = new DefaultHttpClient();
			ResponseHandler<String> respo = new BasicResponseHandler();
			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "allGroups.php");
			try {
				// Execute HTTP Post Request
				List<NameValuePair> lpair = new ArrayList<NameValuePair>();
				lpair.add(new BasicNameValuePair("user_id", U_id));

				try {
					httppost.setEntity(new UrlEncodedFormEntity(lpair));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					respo_str = httpclient.execute(httppost, respo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("--shw group-----" + respo);
				JSONObject job = new JSONObject();
				JSONArray ttle;
				try {
					JSONObject js = new JSONObject(respo_str);
					job = js.getJSONObject("response");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ttle = job.getJSONArray("status");
				if (ttle != null) {
						st=ttle.get(0).toString();
				} else {
				}
				System.out.println("rSTATUS======" + st);
				if (st.equalsIgnoreCase("true")) {
					JSONArray creator=job.getJSONArray("group_creater");
					JSONArray name = job.getJSONArray("name");
					JSONArray number=job.getJSONArray("channel_no");
					JSONArray group_type = job.getJSONArray("group_type");
					JSONArray channel_name=job.getJSONArray("channel_name");
					JSONArray creator_id_list=job.getJSONArray("group_creater_id");
					JSONArray chptr=job.getJSONArray("chapter");
					if (name != null) {
						int len = name.length();
						groupMNAme = new ArrayList<String>();
						Group_type = new ArrayList<String>();
						creator_list=new ArrayList<String>(); 
						channel_names=new ArrayList<String>();
						number_alloc=new ArrayList<String>();
						creator_id=new ArrayList<String>();
						channel_no=new ArrayList<String>();
						chapter_taught=new ArrayList<String>();
						for (int i = 0; i < len; i++) {
							groupMNAme.add(name.get(i).toString());
							Group_type.add(group_type.get(i).toString());
							creator_list.add(creator.get(i).toString());
							number_alloc.add(number.get(i).toString());
							chapter_taught.add(chptr.get(i).toString());
							channel_names.add(channel_name.get(i).toString());
							channel_no.add(number.get(i).toString());
							creator_id.add(creator_id_list.get(i).toString());
						}
					}
					JSONArray im = job.getJSONArray("image");
					if (im != null) {
						int len = im.length();
						groupimg = new ArrayList<String>();
						for (int i = 0; i < len; i++) {
							groupimg.add(im.get(i).toString());
						}
					}
					JSONArray gid = job.getJSONArray("id");
					if (gid != null) {
						int len = gid.length();
						groupId = new ArrayList<String>();
						for (int i = 0; i < len; i++) {
							groupId.add(gid.get(i).toString());
							HashMap<String, String> grp_status = new HashMap<String, String>();
						}

					}

					JSONArray join_grp = job.getJSONArray("join_grp_id");

					if (join_grp != null) {
						for (int i = 0; i < join_grp.length(); i++) {
							join_groupId.add(join_grp.getString(i));
						}
					}

					SharedPreferences preferences = getSharedPreferences(
							"mypref", Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putString("gid", id);
					editor.commit();
					System.out.println("--id in shr-------" + channel_names.toString());
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public class Sample_adapter extends BaseAdapter {
		LayoutInflater Lift;
		Context c;
		int layoutResourceId;
		ArrayList<HashMap<String, String>> group_list;

		public Sample_adapter(Context c,
				ArrayList<HashMap<String, String>> group_list) {
			this.c = c;
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
			Random rand = new Random();
			ImageLoader imageLoader = ImageLoader.getInstance();
			if (v == null) {

				v = (RelativeLayout) Lift.inflate(R.layout.sample, null);
				holder = new Viewholder();

				holder.group_name = (TextView) v.findViewById(R.id.text_name);
				holder.group_name.setTypeface(face);
				holder.profile = (ImageView) v.findViewById(R.id.img_group);
				holder.group_name
						.setTextColor(Color.argb(255, rand.nextInt(256),
								rand.nextInt(256), rand.nextInt(256)));
				v.setTag(holder);
			} else {
				holder = (Viewholder) v.getTag();
			}
//			String Grouppp=group_list.get(arg0).get("group_name");
//			Log.i("Groups","groop"+Grouppp);
			holder.group_name.setText(group_list.get(arg0).get("group_name"));

			// Uri uri = Uri.parse(cn.getPic());
			// imageLoader.displayImage(group_list.get(arg0).get("Image")
			// .toString(), holder.profile, options);
			Picasso.with(c).load(group_list.get(arg0).get("Image").toString()).resize(200, 200)
					.into(holder.profile);
			return v;
		}

		public class Viewholder {
			TextView group_name;
			ImageView profile;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.new_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.new_menu_grp) {
			Intent in = new Intent(ShowAllGroup.this, AddGroup.class);
			startActivity(in);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isInternetPresent) {
			GetData dl = new GetData();
			dl.execute();
		} else {
			// Internet connection is not present
			// Ask user to connect to Internet
			showAlertDialog(ShowAllGroup.this, "No Internet Connection",
					"You don't have internet connection.", false);
		}

	}
}