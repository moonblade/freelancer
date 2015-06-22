package com.dng.gruupy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.splunk.mint.Mint;

public class MainActivity extends ActionBarActivity implements
		ConnectionCallbacks, OnConnectionFailedListener {
	String status, regd = "";
	Button agree, cancel;
	ImageView groups, channels, active_chats;
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	static final String login_url = Global_Constants.BASE_URL + "login.php";
	SharedPreferences pref;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

	private Location mLastLocation;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	// boolean flag to toggle periodic location updates
	private boolean mRequestingLocationUpdates = false;

	private LocationRequest mLocationRequest;

	// Location updates intervals in sec
	private static int UPDATE_INTERVAL = 10000; // 10 sec
	private static int FATEST_INTERVAL = 5000; // 5 sec
	private static int DISPLACEMENT = 10;
	RelativeLayout logo;
	double mLatitudeText, mLongitudeText;
	String mLat, mLong;
	LocationManager Locmanager;
	boolean status_returned = false;
	EditText usname, upwd;
	int b;
	Typeface face;
//	private static final String OUR_BUGSENSE_API_KEY = "ce69f2ee";
	private static final String OUR_BUGSENSE_API_KEY = "53daf3d7"; //saleam
	
	Dialog login_dialog;
	Boolean isInternetPresent = false;
	ProgressDialog pd;
	ConnectionDetector cd;
	CheckBox cb1;
	Global global;
	String lat_long, user_id, status_login, user_type, login_response,
			pin = "i9i5e5ikale2o7u7u4ik";// Secret Key
	String phn, stat_log;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		global = (Global) getApplicationContext();
		Mint.initAndStartSession(this, OUR_BUGSENSE_API_KEY);
		Locmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		face = Typeface.createFromAsset(getAssets(), "Aaargh.ttf");
		// Building the GoogleApi client
		buildGoogleApiClient();
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
		} else {

		}

		// LocationListener ll = new mylocationlistener();
		// Locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
		// ll);
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
		} else {
			// Internet connection is not present
			// Ask user to connect to Internet
			showAlertDialog(MainActivity.this, "No Internet Connection",
					"You don't have internet connection.", false);
		}
		pref = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		regd = pref.getString("regd", "");
		System.out.println("9999rId is====" + regd);
		setupView();
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


	private void setupView() {
		// TODO Auto-generated method stub
		agree = (Button) findViewById(R.id.cont_bttn);
		cancel = (Button) findViewById(R.id.canc_bttn);
		groups = (ImageView) findViewById(R.id.act_chats);
		channels = (ImageView) findViewById(R.id.my_channels);
		active_chats = (ImageView) findViewById(R.id.my_groups);
		agree.setTypeface(face);
		if (!regd.equalsIgnoreCase("true") || regd.equalsIgnoreCase("")) {
			agree.setVisibility(View.VISIBLE);
			cancel.setVisibility(0);
			groups.setVisibility(9);
			channels.setVisibility(9);
			active_chats.setVisibility(9);

			agree.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(MainActivity.this,
							RegistrationActivity.class));
					MainActivity.this.finish();
				}
			});
			cancel.setOnClickListener(cancelClicked);

		} else {
			agree.setVisibility(9);
			cancel.setVisibility(9);
			groups.setVisibility(0);
			channels.setVisibility(0);
			active_chats.setVisibility(0);

			active_chats.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					login_dialog = new Dialog(MainActivity.this,
							android.R.style.Theme_Translucent_NoTitleBar);
					login_dialog.setContentView(R.layout.dialog_login);
					/*
					 * WindowManager.LayoutParams wmlp =
					 * login_dialog.getWindow() .getAttributes(); wmlp.gravity =
					 * Gravity.TOP | Gravity.LEFT; wmlp.x = 100; // The new
					 * position of the X coordinates wmlp.y = 100; // The new
					 * position of the Y coordinates wmlp.width =
					 * wmlp.MATCH_PARENT; // Width wmlp.height = 500; // Height
					 * login_dialog.getWindow().setAttributes(wmlp);
					 */
					usname = (EditText) login_dialog.findViewById(R.id.uname);
					upwd = (EditText) login_dialog.findViewById(R.id.upwd);
					Button login = (Button) login_dialog
							.findViewById(R.id.login_bttn);
					cb1 = (CheckBox) login_dialog.findViewById(R.id.checkBox1r);
					if (!pref.getString("r_username", "").equals("")) {
						usname.setText(pref.getString("r_username", ""));
						upwd.setText(pref.getString("r_password", ""));
					}
					login.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							b = 0;
							if (!(usname.getText().toString()
									.equalsIgnoreCase(""))
									|| !(upwd.getText().toString()
											.equalsIgnoreCase(""))) {
								Log.e("Remem", "Remmebered" + cb1.isChecked());
								if (cb1.isChecked() == true) {
									Editor sp_edit = pref.edit();
									sp_edit.putString("r_username", usname
											.getText().toString());
									sp_edit.putString("r_password", upwd
											.getText().toString());
									sp_edit.commit();
								}
								login login_thread = new login();
								login_thread.execute();
							} else {
								Toast.makeText(MainActivity.this,
										"Please enter complete details", 5)
										.show();
							}
						}
					});
					login_dialog.show();
				}
			});
			groups.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					login_dialog = new Dialog(MainActivity.this,
							android.R.style.Theme_Translucent_NoTitleBar);
					login_dialog.setContentView(R.layout.dialog_login);
					/*
					 * WindowManager.LayoutParams wmlp =
					 * login_dialog.getWindow() .getAttributes(); wmlp.gravity =
					 * Gravity.TOP | Gravity.LEFT; wmlp.x = 100; // The new
					 * position of the X coordinates wmlp.y = 100; // The new
					 * position of the Y coordinates wmlp.width =
					 * wmlp.MATCH_PARENT; // Width wmlp.height = 500; // Height
					 * login_dialog.getWindow().setAttributes(wmlp);
					 */
					usname = (EditText) login_dialog.findViewById(R.id.uname);
					upwd = (EditText) login_dialog.findViewById(R.id.upwd);
					cb1 = (CheckBox) login_dialog.findViewById(R.id.checkBox1r);
					Button login = (Button) login_dialog
							.findViewById(R.id.login_bttn);
					if (!pref.getString("r_username", "").equals("")) {
						usname.setText(pref.getString("r_username", ""));
						upwd.setText(pref.getString("r_password", ""));
					}
					login.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							b = 1;
							if (!(usname.getText().toString()
									.equalsIgnoreCase(""))
									|| !(upwd.getText().toString()
											.equalsIgnoreCase(""))) {
								Log.e("Remem", "Remmebered" + cb1.isSelected());
								if (cb1.isChecked() == true) {
									Editor sp_edit = pref.edit();
									sp_edit.putString("r_username", usname
											.getText().toString());
									sp_edit.putString("r_password", upwd
											.getText().toString());
									sp_edit.commit();
								}
								login login_thread = new login();
								login_thread.execute();
							} else {
								Toast.makeText(MainActivity.this,
										"Please enter complete details", 5)
										.show();
							}
						}
					});
					login_dialog.show();
				}
			});
			channels.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					login_dialog = new Dialog(MainActivity.this,
							android.R.style.Theme_Translucent_NoTitleBar);
					login_dialog.setContentView(R.layout.dialog_login);
					/*
					 * WindowManager.LayoutParams wmlp =
					 * login_dialog.getWindow() .getAttributes(); wmlp.gravity =
					 * Gravity.TOP | Gravity.LEFT; wmlp.x = 100; // The new
					 * position of the X coordinates wmlp.y = 100; // The new
					 * position of the Y coordinates wmlp.width =
					 * wmlp.MATCH_PARENT; // Width wmlp.height = 500; // Height
					 * login_dialog.getWindow().setAttributes(wmlp);
					 */
					usname = (EditText) login_dialog.findViewById(R.id.uname);
					cb1 = (CheckBox) login_dialog.findViewById(R.id.checkBox1r);
					upwd = (EditText) login_dialog.findViewById(R.id.upwd);
					Button login = (Button) login_dialog
							.findViewById(R.id.login_bttn);
					if (!pref.getString("r_username", "").equals("")) {
						usname.setText(pref.getString("r_username", ""));
						upwd.setText(pref.getString("r_password", ""));
					}
					login.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							b = 2;
							if (!(usname.getText().toString()
									.equalsIgnoreCase(""))
									|| !(upwd.getText().toString()
											.equalsIgnoreCase(""))) {
								if (cb1.isChecked() == true) {
									Editor sp_edit = pref.edit();
									sp_edit.putString("r_username", usname
											.getText().toString());
									sp_edit.putString("r_password", upwd
											.getText().toString());
									sp_edit.commit();
								}
								login login_thread = new login();
								login_thread.execute();
							} else {
								Toast.makeText(MainActivity.this,
										"Please enter complete details", 5)
										.show();
							}
						}
					});
					login_dialog.show();

				}
			});
		}

	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
				.addConnectionCallbacks(MainActivity.this)
				.addOnConnectionFailedListener(MainActivity.this)
				.addApi(LocationServices.API).build();
	}

	private void displayLocation() {

		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mGoogleApiClient);

		if (mLastLocation != null) {
			double latitude = mLastLocation.getLatitude();
			double longitude = mLastLocation.getLongitude();
			global.setLat_long(latitude+","+longitude);
		} else {

			Toast.makeText(
					MainActivity.this,
					"(Couldn't get the location. Make sure location is enabled on the device)",
					5).show();
		}
	}

	public class login extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = ProgressDialog.show(MainActivity.this, "", "Logging in");
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			stat_log = login();
			Log.i("stat", "frfhkjhkj" + stat_log);
			return stat_log;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			Log.i("stat", "stat_log" + status_login);
			if (status_login.equals(null)) {
				Toast.makeText(MainActivity.this,
						"Login failed!! please try again later", 5).show();
			} else {
				if (status_login.equalsIgnoreCase("true")) {
					if (b == 0) {
						startActivity(new Intent(MainActivity.this,
								ChatMain.class));
						MainActivity.this.finish();
					} else if (b == 1) {
						startActivity(new Intent(MainActivity.this,
								ShowAllGroup.class));
						MainActivity.this.finish();
					} else {
						startActivity(new Intent(MainActivity.this,
								My_channels.class));
						MainActivity.this.finish();
					}
					Editor edi = pref.edit();
					edi.putString("Sub_type", user_type);
					// edi.putString("id", user_id);
					edi.commit();
				} else {
					Toast.makeText(MainActivity.this,
							"Login failed!! please try again later", 5).show();
				}
			}

		}

		private String login() {
			// TODO Auto-generated method stub
			DefaultHttpClient dhcp = new DefaultHttpClient();
			ResponseHandler<String> response = new BasicResponseHandler();
			HttpPost post_mthd = new HttpPost(login_url);
			List<NameValuePair> login_params = new ArrayList<NameValuePair>();
			login_params.add(new BasicNameValuePair("username", usname
					.getText().toString()));
			login_params.add(new BasicNameValuePair("password", upwd.getText()
					.toString()));

			try {
				post_mthd.setEntity(new UrlEncodedFormEntity(login_params));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				login_response = dhcp.execute(post_mthd, response);
				Log.i("LRespo", "Login_Repo" + login_response);
				JSONObject job1;
				try {
					job1 = new JSONObject(login_response);
					JSONObject job2 = job1.getJSONObject("response");
					String stat = job2.getString("status");
					Log.i("stat", "stat" + stat);

					// String id = job2.getString("id");
					if (stat.equalsIgnoreCase(" true")) {
						status_login = "true";
						String type = job2.getString("user_type");
						user_type = type;
						// user_id = id;

					} else if (stat.equalsIgnoreCase(" false")) {
						status_login = "false";
					} else {
						status_login = "false";
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
			return status_login;
		}

	}

	public OnClickListener cancelClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MainActivity.this.finish();
		}
	};

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		displayLocation();

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		}
	}
}
