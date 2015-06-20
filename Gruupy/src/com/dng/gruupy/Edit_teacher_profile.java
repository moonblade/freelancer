package com.dng.gruupy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import com.dng.gruupy.Teacher_registration.Getwants;
import com.squareup.picasso.Picasso;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Edit_teacher_profile extends Activity {

	ImageView profile_image;
	public static String get_teach_acc_url = Global_Constants.BASE_URL + "teacher_profile.php";
	String edit_teacher_profile = Global_Constants.BASE_URL + "edit_teacher_profile.php";
	RadioGroup gender_group, qual_group, exp_group, teaching_lvl_group;
	RadioButton radioSexButton, radioExpButton, fresh, middle, expert;
	CheckBox degree, certificate, others, junio, senio, uni, other_lev;
	String pwd;
	CheckBox radioQualButton, teachinglvlButton;
	public static String gender_str = "", fresh_str = "", junio_str = "",
			senio_str = "", uni_str = "", other_str = "", middle_str = "",
			expert_str = "", qual_str = "", cert_str = "", others_str = "",
			tech_lev_str = "", exp_str = "", teaching_lvl_str = "";

	EditText username, email, grade, other_qual, nick_name, other_llvl,
			teaching_status, curr_inst, password, c_password, subjects_list;
	TextView next;
	Button back;
	Uri selectedImageUri;
	String id = "", user_type = "";
	ByteArrayBody pro_image = null;
	Bitmap img_bitmap;
	private int chkBitmap = 0;
	private Bitmap thumb;
	private Bitmap bitmap;
	String Hon, pp = "http://www.p3-group.com/img/dummy_avatar.png?v1.3.12";
	File path, file;
	String selectedPath, user_name, m_Status, name, Email, Gender, insti, Expe,
			image, qualification, subject, Active, status;
	LinearLayout others_levl, others_qual;
	SharedPreferences pref;
	ProgressDialog pdialog1;

	Global global;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		setContentView(R.layout.act_edit_tutor);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
						| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		global = (Global) getApplicationContext();
		sp = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		profile_image = (ImageView) findViewById(R.id.uimageView1);

		degree = (CheckBox) findViewById(R.id.degree);
		certificate = (CheckBox) findViewById(R.id.certificate);
		others = (CheckBox) findViewById(R.id.others);
		others_levl = (LinearLayout) findViewById(R.id.others_levl);
		other_qual = (EditText) findViewById(R.id.other_qual);
		other_llvl = (EditText) findViewById(R.id.other_qual1);
		others_qual = (LinearLayout) findViewById(R.id.others_qual);
		pref = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		global = (Global) getApplicationContext();
		id = pref.getString("id", "");
		Log.e("UId", "UId" + id);
		user_type = pref.getString("user_type", "");
		Log.e("Utype", "UType" + user_type);
		junio = (CheckBox) findViewById(R.id.junio);
		senio = (CheckBox) findViewById(R.id.senio);
		uni = (CheckBox) findViewById(R.id.uni);
		other_lev = (CheckBox) findViewById(R.id.other_lev);

		fresh = (RadioButton) findViewById(R.id.fresh);
		middle = (RadioButton) findViewById(R.id.middle);
		expert = (RadioButton) findViewById(R.id.expert);
		get_account_details g_details = new get_account_details();
		g_details.execute();
		if (fresh.isChecked() == true) {
			exp_str = "0-5 years";
		}

		gender_group = (RadioGroup) findViewById(R.id.gender_group);
		int selectedId = gender_group.getCheckedRadioButtonId();
		radioSexButton = (RadioButton) findViewById(selectedId);
		gender_str = radioSexButton.getText().toString();
		Log.e("Genderuncheckd", "String" + gender_str);

		exp_group = (RadioGroup) findViewById(R.id.exp_group);
		int sselectedId = exp_group.getCheckedRadioButtonId();
		radioExpButton = (RadioButton) findViewById(selectedId);
		exp_str = radioExpButton.getText().toString();
		Log.e("Expchecked", "String" + exp_str);

		// ----------------RadioGroup for gender,qualifications-------//
		gender_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				int selectedId = gender_group.getCheckedRadioButtonId();
				radioSexButton = (RadioButton) findViewById(selectedId);
				gender_str = radioSexButton.getText().toString();
				Log.e("Gendercheckd", "String" + gender_str);
			}
		});

		exp_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				int selectedId = exp_group.getCheckedRadioButtonId();
				radioExpButton = (RadioButton) findViewById(selectedId);
				exp_str = radioExpButton.getText().toString();
				Log.e("Expchecked", "String" + exp_str);
			}
		});

		qual_group = (RadioGroup) findViewById(R.id.qual_group);
		if (degree.isChecked() == true) {
			qual_str = "1";
			Log.e("qual_str0", "String" + qual_str);
		}
		degree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (degree.isChecked()) {
					qual_str = "1";
					Log.e("qual_str1", "String" + qual_str);
				} else {
					qual_str = "0";
					Log.e("qual_str1", "String" + qual_str);
				}
			}
		});
		certificate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Deg", "ree" + degree.isChecked());
				if (certificate.isChecked()) {
					cert_str = "1";
				} else {
					cert_str = "0";
				}
			}
		});
		others.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (others.isChecked()) {
					others_qual.setVisibility(0);
					others_str = "1";
					Log.e("qual_str4", "String" + others_str);
				} else {
					others_qual.setVisibility(9);
					others_str = "0";
				}
			}
		});

		teaching_lvl_group = (RadioGroup) findViewById(R.id.teaching_lvl_group);
		if (junio.isChecked() == true) {
			junio_str = "1";
			Log.i("J", "UN" + junio_str);
		}

		junio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (junio.isChecked()) {
					junio_str = "1";
					Log.i("J", "UN" + junio_str);
				} else {
					junio_str = "0";
				}
			}
		});
		senio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (senio.isChecked()) {
					senio_str = "1";
				} else {
					senio_str = "0";
				}
			}
		});
		uni.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (uni.isChecked()) {
					uni_str = "1";
				} else {
					uni_str = "0";
				}
			}
		});
		other_lev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (other_lev.isChecked()) {
					others_levl.setVisibility(0);
					other_str = "1";

				} else {
					others_levl.setVisibility(9);
					other_str = "0";
				}
			}
		});

		// --------------name,Email,password,username---------------------//
		username = (EditText) findViewById(R.id.user_name1);
		email = (EditText) findViewById(R.id.email1);
		back = (Button) findViewById(R.id.back_btn);
		password = (EditText) findViewById(R.id.password);
		c_password = (EditText) findViewById(R.id.password2);
		nick_name = (EditText) findViewById(R.id.nick);

		subjects_list = (EditText) findViewById(R.id.subject_spl);

		// ----------------------Qualifications--------------------------//

		grade = (EditText) findViewById(R.id.grade_spl);
		next = (TextView) findViewById(R.id.next_btn);
		back = (Button) findViewById(R.id.back_btn);
		curr_inst = (EditText) findViewById(R.id.institute);

		// ----------------------network thread
		// exception------------------------//
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		String default_url = Global_Constants.BASE_URL + "uploads/female140.jpeg";
		DownloadFromUrl(default_url, "female140.png");
		selectedPath = file.getPath();
		pro_image = executeMultipartPost(selectedPath);

		profile_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.intent.action.PICK",
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(
						Intent.createChooser(intent, "Select file to upload "),
						10);

			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) { // TODO Auto-generated method stub
				if (validate()) {
					next.setTextColor(Color.parseColor("#FF7F00"));
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							next.setTextColor(Color.parseColor("#000000"));
							edit_teacher_profile dl = new edit_teacher_profile();
							dl.execute();
						}
					}, 1000);

				} else {
					Toast.makeText(Edit_teacher_profile.this,
							"All fields are required", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub //
				startActivity(new Intent(Edit_teacher_profile.this,
						MainActivity.class));
				Edit_teacher_profile.this.finish();
			}
		});

	}

	protected boolean validate() {
		// TODO Auto-generated method stub
		if (nick_name.getText().toString().equalsIgnoreCase("")) {
			nick_name.setError("Username missing");
			return false;
		} else if (email.getText().toString().equalsIgnoreCase("")
				&& !isValidEmail(email.getText().toString())) {
			email.setError("EmailId missing");
			return false;
		} /*
		 * else if (password.getText().toString().equalsIgnoreCase("")) {
		 * password.setError("Password missing"); return false; } else if
		 * (!password.getText().toString()
		 * .equals(c_password.getText().toString())) {
		 * Toast.makeText(Edit_teacher_profile.this, "Password Mismatch", 6)
		 * .show(); return false;
		 * 
		 * }
		 */else if (username.getText().toString()
				.equalsIgnoreCase(nick_name.getText().toString())) {
			nick_name.setError("Name and username should not be same");
			return false;
		} else {
			return true;
		}

	}

	public class edit_teacher_profile extends AsyncTask<Void, Void, String> {
		ProgressDialog pd1;
		
		 @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd1=ProgressDialog.show(Edit_teacher_profile.this, "", "Updating...");
		}
		
		@Override
		protected String doInBackground(Void... params) {

			edit_teacher_profile();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd1.dismiss();
			if (status.equalsIgnoreCase("true")) {
				Editor edittr = pref.edit();
				edittr.putString("regd", "true");
				edittr.commit();
				startActivity(new Intent(Edit_teacher_profile.this,
						ChatMain.class));

			} else {
				Toast.makeText(Edit_teacher_profile.this,
						"Some error occurred..please try again!!", 5).show();
			}

		}

		private void edit_teacher_profile() {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			String resp_str = "";
			ResponseHandler<String> repos = new BasicResponseHandler();
			HttpPost httppost = new HttpPost(edit_teacher_profile);
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			try {

				System.out.println("helloooo--" + global.getID());
				System.out.println("name--" + user_name);
				reqEntity.addPart("id", new StringBody(global.getID()));
				// reqEntity.addPart("id", new StringBody("238"));// teacher_id
				reqEntity.addPart("fname", new StringBody(nick_name.getText()
						.toString()));
				reqEntity.addPart("honourific", new StringBody(username
						.getText().toString()));
				// reqEntity.addPart("subject", new StringBody("Physics"));
				// reqEntity.addPart("password", new
				// StringBody(password.getText().toString()));
				reqEntity.addPart("email", new StringBody(email.getText()
						.toString()));

				reqEntity.addPart("image", pro_image);
				System.out.println("Image--" + pro_image);
				if (qual_str.equalsIgnoreCase("1")) {

					if (cert_str.equalsIgnoreCase("1")) {
						reqEntity.addPart("qualification", new StringBody(
								"Degree Certification,Private Certification"));
						Log.i("Thrddee1", "quals"
								+ "Degree Certification,Private Certification");

						if (others_str.equalsIgnoreCase("1")) {
							reqEntity.addPart("qualification", new StringBody(
									"Degree Certification,Private Certification,other_"
											+ other_qual.getText().toString()));
							Log.i("Thrdllldee1",
									"qua2ls"
											+ "Degree Certification,Private Certification,other_"
											+ other_qual.getText().toString());
						} else {

						}
					}
					if (others_str.equalsIgnoreCase("1")) {

						Log.i("Three1",
								"quals"
										+ "Degree Certification,Private Certification,other_"
										+ other_qual.getText().toString());

						reqEntity.addPart("qualification", new StringBody(
								"Degree Certification,Private Certification,other_"
										+ other_qual.getText().toString()));
					} else {
						reqEntity.addPart("qualification", new StringBody(
								"Degree Certfication"));
					}

				} else if (cert_str.equalsIgnoreCase("1")) {

					if (qual_str.equalsIgnoreCase("1")) {
						reqEntity.addPart("qualification", new StringBody(
								"Degree Certification,Private Certification"));
						if (others_str.equalsIgnoreCase("1")) {
							reqEntity.addPart("qualification", new StringBody(
									"Degree Certification,Private Certification,other_"
											+ other_qual.getText().toString()));
						}
					}
					if (others_str.equalsIgnoreCase("1")) {
						Log.i("Three2", "quals");
						reqEntity.addPart("qualification", new StringBody(
								"Degree Certification,Private Certification,other_"
										+ other_qual.getText().toString()));
					} else {
						reqEntity.addPart("qualification", new StringBody(
								"Private Certfication"));
					}

				} else if (others_str.equalsIgnoreCase("1")) {
					if (qual_str.equalsIgnoreCase("1")) {
						reqEntity.addPart("qualification", new StringBody(
								"Degree Certification,other_"
										+ other_qual.getText().toString()));
						if (cert_str.equalsIgnoreCase("1")) {
							reqEntity.addPart("qualification", new StringBody(
									"Degree Certification & Private Certification,other_"
											+ other_qual.getText().toString()));
						}
					}
					if (cert_str.equalsIgnoreCase("1")) {
						Log.i("Three3", "quals");
						reqEntity.addPart("qualification", new StringBody(
								"Private Certification,other_"
										+ other_qual.getText().toString()));
					} else {
						reqEntity.addPart("qualification", new StringBody(
								"other_" + other_qual.getText().toString()));
					}

				} else {

				}
				System.out.println("TCHNG LVL");
				// -------------------------------------------------Teaching
				// level------------------------------//

				Log.e("Exdpd1", "11dd" + junio_str);
				if (junio_str.equalsIgnoreCase("1")) {

					if (senio_str.equalsIgnoreCase("1")) {
						Log.e("Exdpccd1", "1cc1dd"
								+ "Junior High and Senior High");
						reqEntity.addPart("teaching_level", new StringBody(
								"Junior High and Senior High"));

						if (uni_str.equalsIgnoreCase("1")) {
							Log.e("Exdp1",
									"11d"
											+ "Junior High,Senior High,University,other_"
											+ other_llvl.getText().toString());
							if (other_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High,University,other_"
														+ other_llvl.getText()
																.toString()));
								Log.e("Exp1",
										"11"
												+ "Junior High,Senior High,University,other_"
												+ other_llvl.getText()
														.toString());
							} else {

								Log.e("Exp1d",
										"11s"
												+ "Junior High,Senior High,University,other_"
												+ other_llvl.getText()
														.toString());

								reqEntity
										.addPart(
												"teaching_level",
												new StringBody(
														"Junior High,Senior High and University"));

							}
						} else {
							if (other_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High,other_"
														+ other_llvl.getText()
																.toString()));
							} else {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High"));

							}
						}
					} else {
						reqEntity.addPart("teaching_level", new StringBody(
								"University"));
					}
				} else if (senio_str.equalsIgnoreCase("1")) {
					if (junio_str.equalsIgnoreCase("1")) {

						reqEntity.addPart("teaching_level", new StringBody(
								"Junior High and Senior High"));

						if (uni_str.equalsIgnoreCase("1")) {

							if (other_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High,University,other_"
														+ other_llvl.getText()
																.toString()));
							} else {

								reqEntity
										.addPart(
												"teaching_level",
												new StringBody(
														"Junior High,Senior High and University"));

							}
						} else {
							if (other_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High,other_"
														+ other_llvl.getText()
																.toString()));
							} else {

								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High"));

							}
						}
					} else {
						reqEntity.addPart("teaching_level", new StringBody(
								"Senior High"));
					}
				} else if (uni_str.equalsIgnoreCase("1")) {
					if (junio_str.equalsIgnoreCase("1")) {

						reqEntity.addPart("teaching_level", new StringBody(
								"Junior High and University"));

						if (senio_str.equalsIgnoreCase("1")) {

							if (other_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High,University,other_"
														+ other_llvl.getText()
																.toString()));
							} else {

								reqEntity
										.addPart(
												"teaching_level",
												new StringBody(
														"Junior High,Senior High and University"));

							}
						} else {
							if (other_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,University,other_"
														+ other_llvl.getText()
																.toString()));
							} else {

								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,University "));

							}
						}
					} else {
						reqEntity.addPart("teaching_level", new StringBody(
								"University"));
					}
				} else if (other_str.equalsIgnoreCase("1")) {

					if (senio_str.equalsIgnoreCase("1")) {

						reqEntity.addPart("teaching_level", new StringBody(
								"Senior High,other_"
										+ other_llvl.getText().toString()));

						if (uni_str.equalsIgnoreCase("1")) {

							if (junio_str.equalsIgnoreCase("1")) {
								reqEntity
										.addPart(
												"teaching_level",
												new StringBody(
														"Junior High,Senior High,University "));
							} else {

								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High & Senior High"));

							}
						} else {
							if (junio_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High,other_ "
														+ other_llvl.getText()
																.toString()));
							} else {

								reqEntity.addPart("teaching_level",
										new StringBody("Senior High,other_"
												+ other_llvl.getText()
														.toString()));

							}
						}
					} else {
						reqEntity.addPart("teaching_level", new StringBody(
								"other_" + other_llvl.getText().toString()));
					}
				}
				reqEntity.addPart("tgrade", new StringBody("9"));
				reqEntity.addPart("experience", new StringBody(exp_str));
				System.out.println("experience" + exp_str);
				httppost.setEntity(reqEntity);
				Log.i("Entity", "" + reqEntity.toString());

				resp_str = httpclient.execute(httppost, repos);
				System.out.println("-------Edit profileeeeeee teacher---------"
						+ resp_str.toString());
				JSONObject js = new JSONObject(resp_str);
				JSONObject job = js.getJSONObject("response");
				status = job.getString("status");

			} catch (ClientProtocolException e) {
				System.out.println("ClientProtocolException--" + e);
			} catch (IOException e) {
				System.out.println("IOException--" + e);
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}

	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(target)
						.matches();
	}

	public class get_account_details extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			get_account_details();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (m_Status.equalsIgnoreCase("true")) {
				Log.e("Insti", "Insti" + insti);
				username.setText(Hon);
				// Username.setText(user_name);
				email.setText(Email);
				password.setText(pwd);
				c_password.setText(pwd);
				nick_name.setText(user_name);
				// ---------------------------Teaching levels--------------//

				if (insti.contains("other_")) {
					Log.e("gh", "Insti" + insti);
					other_lev.setChecked(true);
					int u = insti.indexOf("other_");
					others_levl.setVisibility(0);
					other_llvl
							.setText(insti.substring(u).replace("other_", ""));
				}
				Log.e("fwd", "fwd" + insti);
				if (insti.contains("Junior High")) {
					Log.e("Insti1", "Insti" + insti);
					junio.setChecked(true);
				}
				if (insti.contains("Senior High")) {
					Log.e("Insti2", "Insti" + insti);
					senio.setChecked(true);
					junio.setChecked(false);
				}
				if (insti.contains("Junior High,Senior High")) {
					Log.e("Insti3", "Insti" + insti);
					junio.setChecked(true);
					senio.setChecked(true);
				}
				if (insti.contains("University")) {
					Log.e("Insti4", "Insti" + insti);
					uni.setChecked(true);
				}
				if (insti.contains("Junior High,Senior High,University")) {
					Log.e("Insti5", "Insti" + insti);
					junio.setChecked(true);
					senio.setChecked(true);
					uni.setChecked(true);
				}
				if (insti.contains("Junior High,University")) {
					Log.e("Insti6", "Insti" + insti);
					junio.setChecked(true);
					uni.setChecked(true);
				}
				if (insti.contains("Senior High,University")) {
					Log.e("Insti7", "Insti" + insti);
					senio.setChecked(true);
					uni.setChecked(true);
				} else {

				}
				// ---------------------------Qualifications--------------//
				if (qualification.contains("other_")) {
					Log.e("Deggh", "qualification" + qualification);
					others.setChecked(true);
					int u = qualification.indexOf("other_");
					others_qual.setVisibility(0);
					other_qual.setText(qualification.substring(u).replace(
							"other_", ""));
				}
				Log.e("fwd", "fwd" + qualification);
				if (qualification.contains("Degree Certification")) {
					Log.e("Deggh", "qualification" + qualification);
					degree.setChecked(true);
				}
				if (qualification.contains("Private Certification")) {
					Log.e("Deggh", "qualification" + qualification);
					certificate.setChecked(true);
				}
				if (qualification
						.contains("Degree Certification,Private Certification")) {
					Log.e("Deggh", "qualification" + qualification);
					degree.setChecked(true);
					certificate.setChecked(true);
				} else {

				}
				// ------------------Experience--------------//
				if (Expe.equalsIgnoreCase("0-5 years")) {
					fresh.setChecked(true);
				} else if (Expe.equalsIgnoreCase(">5 years")) {
					middle.setChecked(true);
				} else if (Expe.equalsIgnoreCase(">10 years")) {
					expert.setChecked(true);
				}
				Picasso.with(Edit_teacher_profile.this).load(image)
						.resize(150, 150).into(profile_image);
			} else {
				Toast.makeText(Edit_teacher_profile.this,
						"Some error occurred..Please try again!!", 5).show();
			}

		}

	}

	public void get_account_details() {
		DefaultHttpClient dhtp = new DefaultHttpClient();
		String output = "";
		HttpPost post = new HttpPost(get_teach_acc_url);
		ResponseHandler<String> response_handler = new BasicResponseHandler();
		List<NameValuePair> input_list = new ArrayList<NameValuePair>();
		input_list.add(new BasicNameValuePair("teacher_id", id));
		Log.i("Input params", "Input params" + input_list);
		try {
			post.setEntity(new UrlEncodedFormEntity(input_list));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			output = dhtp.execute(post, response_handler);
			Log.i("Output", "output string" + output);
			try {
				JSONObject job1 = new JSONObject(output);
				JSONObject job2 = job1.getJSONObject("response");
				m_Status = job2.getString("status");
				if (m_Status.equalsIgnoreCase("true")) {
					user_name = job2.getString("username");
					Log.i("USERNAME", "UNAME" + user_name);
					name = job2.getString("name");
					Email = job2.getString("Email");
					Gender = job2.getString("Gender");
					pwd = job2.getString("password");
					insti = job2.getString("teaching_level");
					Expe = job2.getString("experience");
					Hon = job2.getString("honourific");
					image = job2.getString("image");
					qualification = job2.getString("qualification");
					subject = job2.getString("subject");
					Active = job2.getString("tgrade");
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			if (data.getData() != null) {
				selectedImageUri = data.getData();
				Log.e("in ok", "selected");
			}
			if (requestCode == 10)

			{

				selectedPath = getRealPathFromURI(Edit_teacher_profile.this,
						selectedImageUri);
				Log.i("SP2", "selected_path" + selectedPath);
				pro_image = executeMultipartPost(selectedPath);
				profile_image.setImageURI(selectedImageUri);
			}
		}
	}

	public String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri, proj, null,
					null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public void DownloadFromUrl(String imageURL, String fileName) { // this is
																	// the
																	// downloader
																	// method
		try {
			URL url = new URL(imageURL); // you
											// can
			path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			file = new File(path, "/" + "female140.png");

			long startTime = System.currentTimeMillis();
			Log.d("ImageManager", "download begining");
			Log.d("ImageManager", "download url:" + url);
			Log.d("ImageManager", "downloaded file name:" + fileName);
			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			Log.d("Filepath", "File path" + file.getPath());
			Log.d("ImageManager",
					"download ready in"
							+ ((System.currentTimeMillis() - startTime) / 1000)
							+ " sec");
			fos.close();

		} catch (IOException e) {
			Log.d("ImageManager", "Error: " + e);
		}

	}

	public ByteArrayBody executeMultipartPost(String Path) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateandTime = sdf.format(new Date());
		String name = currentDateandTime + "_"
				+ Path.substring(Path.lastIndexOf("/") + 1);
		System.out.println("==========mm=============" + name);
		Bitmap bitmapOrg = BitmapFactory.decodeFile(Path);

		if (chkBitmap == 1) {
			thumb = Bitmap.createScaledBitmap(bitmap, 300, 400, false);

		} else {
			thumb = Bitmap.createScaledBitmap(bitmapOrg, 300, 400, false);

		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		thumb.compress(CompressFormat.JPEG, 90, bos);
		byte[] data = bos.toByteArray();
		ByteArrayBody bab = new ByteArrayBody(data, name);
		return bab;
	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
	}
}
