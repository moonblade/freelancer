package com.dng.gruupy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {
	String Country_Name, country_code;
	ArrayList<String> ctryCode = new ArrayList<String>();
	ArrayList<String> ctryName = new ArrayList<String>();
	ArrayList<HashMap<String, String>> old_user = new ArrayList<HashMap<String, String>>();
	static String incomingNumber = "";
	String token = "bfe61295557d0ba46e46340720a0228e5f15a5a7";
	String appId = "b73772907bf84ccc9523827";
	String token1 = "a6ced1815c7b0206a75c763d60114959ae4e3f7d";
	String check_result, result = "";
	String appId1 = "93d18c9846064ea29478279";
	String api_key = "0004-70c42784-54858db5-b867-dfff2422";
	String private_key = "0009-70c42784-54858db5-b86c-6da1f675";
	String MOBILE;
	Spinner countryName;
	Dialog sdialog;
	EditText phoneNo, countryCode;
	Button done;
	ProgressDialog pd, pd1;
	String Result, sub_type = "";
	String OTP = "";
	String cName, outp, cCode, phn;
	Global global;
	String sess_ID = "";
	String Status, user_type, status, L_status, id, resulted;
	Dialog dbox;
	String otpStart, keymatch;
	JSONArray user_id, name, user_image;
	int Scrwidth;
	static EditText otp;
	Typeface face;
	TextView text, phon, title_app;
	String incoming_number;
	SharedPreferences sprefs;
	RelativeLayout.LayoutParams layoutParams;
	String phoneM, statusFirst, statusSecond;
	// flag for Internet connection status
	Boolean isInternetPresent = false;
	BroadcastReceiver Service_reciever;
	// Connection detector class
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.registration);
		// creating connection detector class instance
		global = (Global) getApplicationContext();
		cd = new ConnectionDetector(getApplicationContext());
		// get Internet status
		sprefs = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
//		Editor edit=sprefs.edit();
//		edit.putString("incoming", "");
//		edit.commit();
		face = Typeface.createFromAsset(getAssets(), "Aaargh.ttf");
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
		} else {
			// Internet connection is not present
			// Ask user to connect to Internet
			showAlertDialog(RegistrationActivity.this,
					"No Internet Connection",
					"You don't have internet connection.", false);
		}
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.dng.gruupy", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("Your Tag",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
		setupView();
		GetDataCountry dll = new GetDataCountry();
		dll.execute();
	}

	private void setupView() {
		countryName = (Spinner) findViewById(R.id.spinner_countryname);
		done = (Button) findViewById(R.id.done);
		done.setOnClickListener(doneClicked);
		phoneNo = (EditText) findViewById(R.id.edit_number);
		text = (TextView) findViewById(R.id.text);
		phon = (TextView) findViewById(R.id.phn);
		title_app = (TextView) findViewById(R.id.title_app);
		phon.setTypeface(face);
		phoneNo.setTypeface(face);
		countryCode = (EditText) findViewById(R.id.edit_countrycode);
		countryCode.setTypeface(face);

	}

	public OnClickListener doneClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// cName = countryName.getSelectedItem().toString();
			// cCode = countryCode.getSelectedItem().toString();
			// cCode=countryCode.getText().toString();
			// done.setEnabled(false);
			phn = phoneNo.getText().toString();
			// System.out.println("countryName--"+cName+cCode+phn);
			if (!phoneNo.getText().toString().isEmpty()
					&& !countryName.getSelectedItem().toString().isEmpty()
					&& !countryCode.getText().toString().isEmpty()) {

				// if(){
				Number_check num_check = new Number_check();
				num_check.execute();
				// }else{
				// GetRegister register = new GetRegister();
				// register.execute();
				// }

				// GetData dl=new GetData();
				// dl.execute();
			} else {
				Toast.makeText(RegistrationActivity.this,
						"All fields are required", Toast.LENGTH_LONG).show();
			}
		}
	};

	public class Number_check extends AsyncTask<Void, Void, String> {
		String result = "";
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();
			pd = ProgressDialog.show(RegistrationActivity.this, "", "wait..");
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			number_check();
			Log.e("Result", "Result" + outp);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			global.setRegd_no(phoneNo.getText().toString());
			if (outp.equalsIgnoreCase("true")) {
				if (user_type.equalsIgnoreCase("teacher")) {
					startActivity(new Intent(RegistrationActivity.this,
							Edit_teacher_profile.class));
				} else {

					startActivity(new Intent(RegistrationActivity.this,
							Student_account.class));
				}
			} else {
				GetRegister register = new GetRegister();
				register.execute();
			}

		}

	}


	public void number_check() {
		// TODO Auto-generated method stub
		String response = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
				+ "check_phone.php");
		ResponseHandler<String> res_hndl = new BasicResponseHandler();
		List<NameValuePair> linputs = new ArrayList<NameValuePair>();
		linputs.add(new BasicNameValuePair("phone_no", phoneNo.getText()
				.toString()));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(linputs));
			try {
				check_result = httpclient.execute(httppost, res_hndl);
				try {
					JSONObject job1 = new JSONObject(check_result);
					Log.e("JOb1", "Job1" + job1.toString());
					JSONObject job2 = job1.getJSONObject("response");
					Log.e("JOb2", "Job2" + job2.toString());
					JSONArray out_status_arr = job2.getJSONArray("status");

					outp = out_status_arr.getString(0);
					Log.i("Response", "Response" + check_result);
					if (outp.equalsIgnoreCase("true")) {
						user_id = new JSONArray();
						name = new JSONArray();
						JSONArray User_type = new JSONArray();
						user_image = new JSONArray();
						user_id = job2.getJSONArray("user_id");
						name = job2.getJSONArray("user_name");
						user_image = job2.getJSONArray("image");
						User_type = job2.getJSONArray("user_type");
						user_type = User_type.getString(0);
						HashMap<String, String> Old_user = new HashMap<String, String>();
						Old_user.put("user_image", user_image.getString(0));
						Old_user.put("name", name.getString(0));
						Old_user.put("user_id", user_id.getString(0));
						old_user.add(Old_user);
						global.setOld_user(old_user);
						global.setID(user_id.getString(0));
						Log.i("user type ID stored0", "" + user_type);
						SharedPreferences preferences = getSharedPreferences(
								"mprefs", Context.MODE_PRIVATE);
						Editor editor = preferences.edit();
						editor.putString("id", user_id.getString(0));
						editor.putString("user_type", user_type);
						editor.putString("phn", phn);
						editor.commit();

					} else if (check_result.equals(null)) {
						outp = "false";
					} else {
						outp = "false";
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
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class GetDataCountry extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			getDataCountry();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("In pp2");
			if (Result.equalsIgnoreCase("true")) {
				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						RegistrationActivity.this,
						android.R.layout.simple_list_item_1, ctryName);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				countryName.setAdapter(adp1);
				// Spinner_Adapter spin_adap=new
				// Spinner_Adapter(RegistrationActivity.this,android.R.layout.simple_spinner_dropdown_item,ctryName);
				// countryName.setAdapter(spin_adap);

				if (isInternetPresent) {
					Toast.makeText(RegistrationActivity.this,
							"please select country", Toast.LENGTH_LONG).show();
				}

				countryName
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								countryCode.setText("+"
										+ ctryCode.get(arg2).toString());
								((TextView) arg0.getChildAt(0))
										.setTextColor(Color.WHITE);
								countryCode.setSelection(countryCode.getText()
										.length());
								// TODO Auto-generated method stub
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
							}
						});
			} else {
				Toast.makeText(RegistrationActivity.this,
						"Data not available..Please try again!!", 5).show();
			}
		}

		protected void getDataCountry() {
			String output = "";
			HttpClient httpclient = new DefaultHttpClient();
			ResponseHandler<String> respo_handler = new BasicResponseHandler();
			// country_name,country_code,phone_no
			HttpPost httppost = new HttpPost(
					Global_Constants.BASE_URL + "country-mobile-code.php");
			try {
				// Execute HTTP Post Request
				output = httpclient.execute(httppost, respo_handler);

				System.out.println("--countryyyyyyyyyy-------" + output);
				JSONObject js = new JSONObject(output);

				if (output.length() == 0) {
					Result = "false";
				} else {
					JSONObject job = js.getJSONObject("response");
					JSONArray contry_name = job.getJSONArray("contry_name");
					if (contry_name != null) {
						int len = contry_name.length();
						ctryName = new ArrayList<String>();
						// ctryName.add("please select country");
						for (int i = 0; i < len; i++) {
							ctryName.add(contry_name.get(i).toString());
						}
					}
					JSONArray callingCodes = job.getJSONArray("callingCodes");
					if (callingCodes != null) {
						int len = callingCodes.length();
						ctryCode = new ArrayList<String>();
						for (int i = 0; i < len; i++) {
							ctryCode.add(callingCodes.get(i).toString());
						}
					}

					Result = "true";
				}
			} catch (ClientProtocolException e) {
				System.out.println("ClientProtocolException--" + e);
			} catch (IOException e) {
				System.out.println("IOException--" + e);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	//

	public class GetData extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			getData();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("In posttttttttttttttttttttttt last");
			final Editor editt = sprefs.edit();
			if (status.equals("Success")||true) {

//				if (resulted.equalsIgnoreCase(OTP)) { //motp  disabled
				if(true){
					sdialog = new Dialog(RegistrationActivity.this,
							android.R.style.Theme_Translucent_NoTitleBar);
					sdialog.setContentView(R.layout.school_dialog);
					TextView ttle = (TextView) sdialog
							.findViewById(R.id.category_school);
					Button teacher = (Button) sdialog
							.findViewById(R.id.teacher);
					Button student = (Button) sdialog
							.findViewById(R.id.student);
					teacher.setTypeface(face);
					student.setTypeface(face);
					ttle.setTypeface(face);
					sdialog.setCancelable(false);
					teacher.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							sub_type = "teacher";
							global.setSub_type(sub_type);
							editt.putString("Sub_type", "teacher");
							editt.commit();
							sdialog.dismiss();
							Loc_reg l_reg = new Loc_reg();
							l_reg.execute();
							/**/

						}
					});
					student.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							sub_type = "student";
							global.setSub_type(sub_type);
							editt.putString("Sub_type", "student");
							editt.commit();
							sdialog.dismiss();
							Loc_reg l_reg = new Loc_reg();
							l_reg.execute();
							/**/
						}
					});
					sdialog.show();

				} else {
				}
			}

		}

		protected void getData() {
//
//			HttpClient httpclient = new DefaultHttpClient();
//			ResponseHandler<String> res = new BasicResponseHandler();
//			List<NameValuePair> pvt = new ArrayList<NameValuePair>();
//			pvt.add(new BasicNameValuePair("private", private_key));
//			Log.i("Namepairs", "" + pvt.toString());
//			Log.i("Session ID", "" + sess_ID);
//			HttpPost httppost = new HttpPost("http://api.mOTP.in/v1/OTP/"
//					+ api_key + "/" + sess_ID);
//			try {
//				// Execute HTTP Post Request
//				httppost.setEntity(new UrlEncodedFormEntity(pvt));
//				String response = httpclient.execute(httppost, res);
//
//				System.out.println("--firstttttttt-------"
//						+ response.toString());
//				JSONObject js = new JSONObject(response);
//				status = js.getString("Status");
//				resulted = js.getString("Result");
//				// id=job.getString("user_id");
//				System.out.println("status======" + status);
//				// SharedPreferences preferences =
//				// getSharedPreferences("mprefs",
//				// Context.MODE_PRIVATE);
//				// Editor editor = preferences.edit();
//				// editor.putString("id", id);
//				// editor.commit();
//				// System.out.println("--id in shr-------"+id);
//			} catch (ClientProtocolException e) {
//				System.out.println("ClientProtocolException--" + e);
//			} catch (IOException e) {
//				System.out.println("IOException--" + e);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
			

			status = "success";
			resulted = "1234";
			Log.d("here","undeeeee");
//motp disabled
		}
			
	}

	//
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

	public class GetRegister extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			getRegister();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("In posttttttttttttttttttttttt second"
					+ L_status);
			if (L_status.equalsIgnoreCase("true")) {
				 Toast.makeText(RegistrationActivity.this,
				 "Register Successfully!", Toast.LENGTH_LONG).show();
								pd1 = ProgressDialog.show(RegistrationActivity.this, "",
						"Please wait...");
				Handler h1 = new Handler();
				h1.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
							pd1.dismiss();
							 String value=sprefs.getString("incoming","");
							 Log.e("value","value"+value);
							 String val=value.replace("+", "");
							dbox = new Dialog(RegistrationActivity.this);
							dbox.setContentView(R.layout.otp_dialog);
							dbox.setCancelable(false);
							otp = (EditText) dbox.findViewById(R.id.ed1);
							Button done = (Button) dbox
									.findViewById(R.id.doneotp);
							Log.i("Incoming numberr", "Incoming numberr"
									+ val);
							if(value.equalsIgnoreCase("")){
								
							}else{
								otp.setTextColor(Color.parseColor("#000000"));
								otp.setText(val);
								
							}
							
							done.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									OTP = otp.getText().toString();
									Log.i("OTP recieved", "" + OTP);
									if (OTP.equalsIgnoreCase("")) {
										Toast.makeText(
												RegistrationActivity.this,
												"Please enter OTP", 6).show();

									} else {
										GetData dl = new GetData();
										dl.execute();
										// Loc_reg l_reg=new Loc_reg();
										// l_reg.execute();
										dbox.dismiss();
									}
								}
							});
							//dbox.show(); //no dialog
							GetData dl = new GetData();
							dl.execute();
							//executes here
						}
				}, 1000);

			} else {
				Toast.makeText(RegistrationActivity.this,
						"Failed to Register!", Toast.LENGTH_LONG).show();
			}
		}

	}

	// --------------Register on mOTP-------//
	protected void getRegister() {
//		// Toast.makeText(RegistrationActivity.this, "Please wait...",
//		// Toast.LENGTH_LONG).show();
//		DefaultHttpClient dehtt = new DefaultHttpClient();
//		ResponseHandler<String> res = new BasicResponseHandler();
//		String phonewithisd = "" + countryCode.getText().toString() + ""
//				+ phoneNo.getText().toString();
//		Log.e("countrycode", "" + countryCode.getText().toString());
//		Log.e("phonewithisd", "" + phonewithisd);
//		HttpPost postMethod = new HttpPost("http://api.mOTP.in/v1/" + api_key
//				+ "/" + phonewithisd);
//
//		try {
//			String respage = "";
//			respage = dehtt.execute(postMethod, res);
//			Log.e("Login response from the server-", "" + respage);
//			JSONObject job = new JSONObject(respage);
//			String status = job.getString("Status");
//			// String result="";
//
//			if (status.equalsIgnoreCase("Success")) {
//				L_status = "true";
//				sess_ID = job.getString("Result");
//			} else if (respage.equals(null)) {
//				L_status = "false";
//			} else {
//				L_status = "false";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Log.i("Result", "" + L_status);
		
		//motp disabled
		
		sess_ID="12345";
		L_status="true";
	}

	public class Loc_reg extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			global.setOnce(0);
			Log.e("Once", "once register" + global.getOnce());
			pd = ProgressDialog.show(RegistrationActivity.this, "",
					"Please wait..");
		}

		@Override
		protected String doInBackground(Void... params) {
			Loc_reg();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			System.out.println("In post");
			if (Status.equalsIgnoreCase("1")) {
				Toast.makeText(RegistrationActivity.this,
						"Registered successfully***********",
						Toast.LENGTH_SHORT).show();
				if (global.getSub_type().equalsIgnoreCase("teacher")) {
					Intent in = new Intent(RegistrationActivity.this,
							Teacher_registration.class);
					in.putExtra("new", "1");
					startActivity(in);
					RegistrationActivity.this.finish();
				} else {
					Intent in = new Intent(RegistrationActivity.this,
							Student_registration.class);
					in.putExtra("new", "1");
					startActivity(in);
					RegistrationActivity.this.finish();
				}

			} else {
				Toast.makeText(RegistrationActivity.this,
						"Failed to register..please try again!!",
						Toast.LENGTH_SHORT).show();

			}
		}

		protected void Loc_reg() {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "register.php?&phone_no=" + phn + "&user_type="
					+ global.getSub_type());
			try {
				// Execute HTTP Post Request

				HttpResponse response = httpclient.execute(httppost);

				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				System.out.println("--firstttttttt-------" + result.toString());
				JSONObject js = new JSONObject(result.toString());
				JSONObject job = js.getJSONObject("response");
				JSONArray status = job.getJSONArray("status");
				Status = status.getString(0);
				JSONArray id = job.getJSONArray("user_id");
				System.out.println("status======" + Status + id);

				if (Status.equalsIgnoreCase("0")) {

				} else {
					user_id = new JSONArray();
					user_id = job.getJSONArray("user_id");
					Log.i("ID stored1", "" + global.getID());
					global.setID(user_id.getString(0));
					SharedPreferences preferences = getSharedPreferences(
							"mprefs", Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putString("id", user_id.getString(0));
					editor.putString("phn", phn);
					editor.commit();
				}

				System.out.println("--id in shr-------" + id + phn);
			} catch (ClientProtocolException e) {
				System.out.println("ClientProtocolException--" + e);
			} catch (IOException e) {
				System.out.println("IOException--" + e);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
}