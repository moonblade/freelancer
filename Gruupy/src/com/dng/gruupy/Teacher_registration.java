package com.dng.gruupy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Teacher_registration extends Activity {

	ImageView profile_image;
	RadioGroup gender_group, qual_group, exp_group, teaching_lvl_group;
	RadioButton radioSexButton ,radioExpButton, fresh,
	middle, expert;
	CheckBox degree, certificate, others, junio, senio, uni, other_lev;
	CheckBox radioQualButton,teachinglvlButton,splst;
	public static String gender_str="", fresh_str="", junio_str="", senio_str="", uni_str="", other_str="",
			middle_str="", expert_str="", qual_str="", cert_str="", others_str="",
			tech_lev_str="", exp_str="", teaching_lvl_str="";

	EditText user_name, email, grade, other_qual, nick_name, other_llvl,honour,
			teaching_status, curr_inst, password, c_password, subjects_list;
	TextView next;
	Uri selectedImageUri;
	ByteArrayBody pro_image = null;
	Bitmap img_bitmap;
	private int chkBitmap = 0;
	private Bitmap thumb;
	private Bitmap bitmap;
	String pp = "http://www.p3-group.com/img/dummy_avatar.png?v1.3.12";
	File path, file;
	String selectedPath, status;
	LinearLayout others_levl, others_qual;

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
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.new_teacher_reg);
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
		honour=(EditText)findViewById(R.id.honour);
		other_llvl=(EditText)findViewById(R.id.other_qual1);
		splst=(CheckBox)findViewById(R.id.selec);
		others_qual = (LinearLayout) findViewById(R.id.others_qual);

		junio = (CheckBox) findViewById(R.id.junio);
		senio = (CheckBox) findViewById(R.id.senio);
		uni = (CheckBox) findViewById(R.id.uni);
		other_lev = (CheckBox) findViewById(R.id.other_lev);

		fresh = (RadioButton) findViewById(R.id.fresh);
		middle = (RadioButton) findViewById(R.id.middle);
		expert = (RadioButton) findViewById(R.id.expert);

		if(fresh.isChecked()==true){
			exp_str="0-5 years";
		}
		
		splst.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(splst.isChecked()==true){
					grade.setVisibility(0);
					subjects_list.setVisibility(0);
				}else{
					grade.setVisibility(9);
					subjects_list.setVisibility(9);
				}
			}
		});
		
		
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
		/*if (degree.isChecked() == true) {
			qual_str = "1";
			Log.e("qual_str0", "String" + qual_str);
		}*/
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
		/*if(junio.isChecked()==true){
			junio_str = "1";
			Log.i("J","UN"+junio_str);
		}*/
		
		junio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (junio.isChecked()) {
					junio_str = "1";
					Log.i("J","UN"+junio_str);
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
		user_name = (EditText) findViewById(R.id.user_name1);
		email = (EditText) findViewById(R.id.email1);
		password = (EditText) findViewById(R.id.password);
		c_password = (EditText) findViewById(R.id.password2);
		nick_name = (EditText) findViewById(R.id.nick);

		subjects_list = (EditText) findViewById(R.id.subject_spl);

		// ----------------------Qualifications--------------------------//

		grade = (EditText) findViewById(R.id.grade_spl);
		next = (TextView) findViewById(R.id.next_btn);
		curr_inst = (EditText) findViewById(R.id.institute);

		// ----------------------network thread
		// exception------------------------//
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		String default_url = "http://andriodiosdevelopers.com/groupy/uploads/female140.jpeg";
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
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							next.setTextColor(Color.parseColor("#FF7F00"));
							Getwants dl = new Getwants();
							dl.execute();
						}
					}, 2500);
					
					
				} else {
					Toast.makeText(Teacher_registration.this,
							"All fields are required", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
		/*back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub //
				startActivity(new Intent(Teacher_registration.this,
						MainActivity.class));
				Teacher_registration.this.finish();
			}
		});*/

	}

	protected boolean validate() {
		// TODO Auto-generated method stub
		if (user_name.getText().toString().equalsIgnoreCase("")) {
			user_name.setError("Name missing");
			return false;
		} else if (nick_name.getText().toString().equalsIgnoreCase("")) {
			nick_name.setError("Username missing");
			return false;
		} else if (email.getText().toString().equalsIgnoreCase("")
				&& !isValidEmail(email.getText().toString())) {
			email.setError("EmailId missing");
			return false;
		}  else if (!degree.isChecked() && !certificate.isChecked() && !others.isChecked()) {
			Toast.makeText(Teacher_registration.this, "Select Qualification", 6)
			.show();
			return false;
		}else if (!junio.isChecked() && !senio.isChecked() && !uni.isChecked() && !other_lev.isChecked()) {
			Toast.makeText(Teacher_registration.this, "Select Teaching level", 6)
			.show();
			return false;
		}else if (password.getText().toString().equalsIgnoreCase("")) {
			password.setError("Password missing");
			return false;
		} else if (!password.getText().toString()
				.equals(c_password.getText().toString())) {
			Toast.makeText(Teacher_registration.this, "Password Mismatch", 6)
					.show();
			return false;

		} else if (user_name.getText().toString()
				.equalsIgnoreCase(nick_name.getText().toString())) {
			nick_name.setError("Name and username should not be same");
			return false;
		} else {
			return true;
		}

	}

	public class Getwants extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog1 = ProgressDialog.show(Teacher_registration.this, "",
					"Please wait..");

		}

		@Override
		protected String doInBackground(Void... params) {
			getwants();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdialog1.dismiss();
			if (status.equals("false")) {
				Toast.makeText(getApplicationContext(),
						"Failed to upload Profile!", Toast.LENGTH_LONG).show();
				Editor edittr = sp.edit();
				edittr.putString("regd", "false");
				edittr.commit();
			} else {
				Toast.makeText(getApplicationContext(),
						"Profile uploaded successfully!", Toast.LENGTH_LONG)
						.show();
				global.setUname(user_name.getText().toString());
				Random rand = new Random();
				Editor edittr = sp.edit();
				edittr.putString("regd", "true");
				edittr.putString("Uname", user_name.getText().toString());
				edittr.putString(
						"color_uname",
						String.valueOf(Color.argb(255, rand.nextInt(256),
								rand.nextInt(256), rand.nextInt(256))));
				edittr.commit();
				System.out.println("in else");
				startActivity(new Intent(Teacher_registration.this,
						ChatMain.class));
				Teacher_registration.this.finish();
			}
		}

		protected void getwants() {

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "teacher.php");
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			try {

				System.out.println("helloooo--" + global.getID());
				System.out.println("name--" + user_name.getText().toString());
				reqEntity.addPart("id", new StringBody(global.getID()));
//				reqEntity.addPart("id", new StringBody("240"));
				reqEntity.addPart("user_type", new StringBody("teacher"));
				reqEntity.addPart("name", new StringBody(user_name.getText()
						.toString()));
				reqEntity.addPart("email", new StringBody(email.getText()
						.toString()));
				System.out.println("Name--" + user_name.getText().toString());
				reqEntity.addPart("username", new StringBody(nick_name
						.getText().toString()));
				reqEntity.addPart("phone_no",
						new StringBody(global.getRegd_no()));
				Log.i("phone", "Phone no" + global.getRegd_no());
				reqEntity.addPart("image", pro_image);
				System.out.println("Image--" + pro_image);
				reqEntity.addPart("gender", new StringBody(gender_str));
				System.out.println("Gender" + gender_str);
				// ---------------------Lat long---------------------//
				reqEntity.addPart("password", new StringBody(password.getText()
						.toString()));
				System.out.println("Password" + password.getText().toString());
				reqEntity.addPart("subject", new StringBody(subjects_list
						.getText().toString()));
				reqEntity.addPart("honourific", new StringBody(honour.getText().toString()));
				System.out.println("Subject"
						+ subjects_list.getText().toString());
				//---------------Qualification----------------------------//
				if (qual_str.equalsIgnoreCase("1")) {
					
					if (cert_str.equalsIgnoreCase("1")) {
						reqEntity
								.addPart(
										"qualification",
										new StringBody(
												"Degree Certification,Private Certification"));
						Log.i("Thrddee1",
								"quals"
										+"Degree Certification,Private Certification");

						if (others_str.equalsIgnoreCase("1")) {
							reqEntity.addPart("qualification", new StringBody(
									"Degree Certification,Private Certification,other_"
											+ other_qual.getText().toString()));
							Log.i("Thrdllldee1",
									"qua2ls"
											+"Degree Certification,Private Certification,other_"
											+ other_qual.getText().toString());
						}else{
							
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
						reqEntity
								.addPart(
										"qualification",
										new StringBody(
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
						reqEntity.addPart("qualification", new StringBody("other_"+
								other_qual.getText().toString()));
					}

				}else{
					
				}
				System.out.println("TCHNG LVL");
				
			//-------------------------------------------------Teaching level------------------------------//	
				
				Log.e("Exdpd1", "11dd"+junio_str);
				if (junio_str.equalsIgnoreCase("1")) {
					
					if (senio_str.equalsIgnoreCase("1")) {
						Log.e("Exdpccd1", "1cc1dd"+"Junior High and Senior High");
						reqEntity.addPart("teaching_level", new StringBody(
								"Junior High and Senior High"));
						
						if (uni_str.equalsIgnoreCase("1")) {
							Log.e("Exdp1", "11d"+"Junior High,Senior High,University,other_"
									+ other_llvl.getText()
									.toString());
							if (other_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High,University,other_"
														+ other_llvl.getText()
																.toString()));
								Log.e("Exp1", "11"+"Junior High,Senior High,University,other_"
										+ other_llvl.getText()
										.toString());
							} else {
								
								Log.e("Exp1d", "11s"+"Junior High,Senior High,University,other_"
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
				}else if (other_str.equalsIgnoreCase("1")) {

					if (senio_str.equalsIgnoreCase("1")) {

						reqEntity.addPart("teaching_level", new StringBody(
								"Senior High,other_"+other_llvl.getText().toString()));

						if (uni_str.equalsIgnoreCase("1")) {

							if (junio_str.equalsIgnoreCase("1")) {
								reqEntity.addPart("teaching_level",
										new StringBody(
												"Junior High,Senior High,University "));
							} else {

								reqEntity
										.addPart(
												"teaching_level",
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
										new StringBody("Senior High,other_"+
												other_llvl.getText().toString()));

							}
						}
					} else {
						reqEntity.addPart("teaching_level", new StringBody("other_"+
								other_llvl.getText().toString()));
					}
				}
				
				//-------------------------------------------------Experience------------------------------//	
				reqEntity.addPart("experience", new StringBody(exp_str));
				System.out.println("experience" + exp_str);
				
				
				
				//-------------------Exp---------------------------//
				reqEntity.addPart("tgrade", new StringBody(grade.getText()
						.toString()));
				System.out.println("tgrade" + grade.getText().toString());
				httppost.setEntity(reqEntity);
				Log.i("Entity", "" + reqEntity.toString());
				HttpResponse response = httpclient.execute(httppost);
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				System.out.println("-------profileeeeeee---------"
						+ result.toString());
				JSONObject js = new JSONObject(result.toString());
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

				selectedPath = getRealPathFromURI(Teacher_registration.this,
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
