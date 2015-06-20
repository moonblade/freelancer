package com.dng.gruupy;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
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
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ProfilePicActivity extends Activity {
	ImageView pro_img;
	Button Proceed, skip;
	EditText userName;
	Uri selectedImageUri;
	Bitmap img_bitmap;
	String pp = "http://www.p3-group.com/img/dummy_avatar.png?v1.3.12";
	String selectedPath;
	private int chkBitmap = 0;
	RadioButton rbttn,ml,fml;
	String Location_fetched, Response_location = "";
	private Bitmap thumb;
	RadioButton rad_button;
	Typeface face;
	private Bitmap bitmap;
	ProgressDialog pdialog;
	RadioGroup gender;
	String public_visibility = "";
	SharedPreferences sp;
	ByteArrayBody pro_image;
	String sub_type="";
	String user_status = "";
	String id, status, profile_status;
	String phn;
	EditText status_update, location,age;
	JSONArray user_id, name, user_image;
	Dialog dialog;
	Global global;
	TextView agetv,ppl_view,statustv;
	String justforCorrectingFlow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.profiledata);
		global = (Global) getApplicationContext();
		face = Typeface.createFromAsset(getAssets(),
	            "Aaargh.ttf");
		sp = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		Intent i = getIntent();
		user_status = i.getStringExtra("new");
		gender = (RadioGroup) findViewById(R.id.grouped_buttons);
		rad_button = (RadioButton) findViewById(R.id.rad_button);
		agetv=(TextView)findViewById(R.id.age_tv);
		age=(EditText)findViewById(R.id.age);
		statustv=(TextView)findViewById(R.id.status);
		ppl_view=(TextView)findViewById(R.id.ppl_view);
		ppl_view.setTypeface(face);
		agetv.setTypeface(face);
		age.setTypeface(face); 
		statustv.setTypeface(face);
		ml=(RadioButton)findViewById(R.id.gender1);
		fml=(RadioButton)findViewById(R.id.gender2);
		ml.setTypeface(face);
		fml.setTypeface(face);
		location = (EditText) findViewById(R.id.location);
		location.setTypeface(face);
		rad_button.setTypeface(face);
		// user_status = "1";
		Log.i("Old User contents", "" + global.getOld_user());
		status_update = (EditText) findViewById(R.id.status_update);
		status_update.setTypeface(face);
		setupView();
		Get_location get_loc = new Get_location();
		get_loc.execute();
		rad_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rad_button.isChecked()) {
					rad_button.setChecked(false);
					public_visibility = "0";
				} else if (!rad_button.isChecked()) {
					rad_button.setChecked(true);
					public_visibility = "1";
				} else {
					rad_button.setChecked(true);
					public_visibility = "1";
				}
			}
		});
		if (user_status.equalsIgnoreCase("1")
				|| global.getOld_user().size() == 0) {

		} else if (user_status.equalsIgnoreCase("2")) {
			Log.i("Else IF", "Else IF");
			ImageLoader iloader = ImageLoader.getInstance();
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					ProfilePicActivity.this.getApplicationContext())
					.threadPoolSize(3)
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.memoryCacheSize(1500000)
					// 1.5 Mb
					.denyCacheImageMultipleSizesInMemory()
					.discCacheFileNameGenerator(new Md5FileNameGenerator())
					.build();
			ImageLoader.getInstance().init(config);
			if (global.getOld_user().get(0).get("user_image")
					.equalsIgnoreCase("")
					|| global.getOld_user().get(0).get("user_image")
							.equals(null)) {

			} else {
				try {
					iloader.displayImage(
							global.getOld_user().get(0).get("user_image"),
							pro_img);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// pp = global.getOld_user().get(0).get("user_image");
				// userName.setText("Hi");
				userName.setText(global.getOld_user().get(0).get("name"));
			}
		} else {

		}
		SharedPreferences pref = getSharedPreferences("mprefs",
				Context.MODE_PRIVATE);
		id = pref.getString("id", "");
		// global.setID(id);
		phn = pref.getString("phn", "");
		System.out.println("9999====" + id + phn);
		GetDataA gdd = new GetDataA();
		gdd.execute();

		SharedPreferences PP = getSharedPreferences("mm", Context.MODE_PRIVATE);
		justforCorrectingFlow = PP.getString("justforCorrectingFlow", null);
		// justforCorrectingFlow = pp.getString("justfor", null);
		System.out.println("justforCorrectingFlow====" + justforCorrectingFlow);
		/*
		 * if (justforCorrectingFlow != null) { Intent it = new
		 * Intent(ProfilePicActivity.this, ChatMain.class); startActivity(it); }
		 */

		// GetData gd=new GetData();
		// gd.execute();
	}

	private void setupView() {
		// TODO Auto-generated method stub
		userName = (EditText) findViewById(R.id.edit_name);
		userName.setTypeface(face);
		pro_img = (ImageView) findViewById(R.id.profile_pic);
		pro_img.setOnClickListener(profilePicClicked);
		Proceed = (Button) findViewById(R.id.btn_proceed);
		Proceed.setTypeface(face);
		Proceed.setOnClickListener(ProceedClicked);
	}

	public OnClickListener ProceedClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!userName.getText().toString().isEmpty()) {
				Getwants dl = new Getwants();
				dl.execute();
			} else {
				Toast.makeText(ProfilePicActivity.this,
						"All fields are required", Toast.LENGTH_LONG).show();
			}
		}
	};
	public OnClickListener profilePicClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent("android.intent.action.PICK",
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			startActivityForResult(
					Intent.createChooser(intent, "Select file to upload "), 10);

		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			if (data.getData() != null) {
				selectedImageUri = data.getData();
				Log.e("in ok", "selected");
			}
			if (requestCode == 10)

			{
				selectedPath = getRealPathFromURI(ProfilePicActivity.this,
						selectedImageUri);
				pro_image = executeMultipartPost(selectedPath);
				pro_img.setImageURI(selectedImageUri);
			}
		}
	}

	String getRealPathFromURI(Context context, Uri contentUri) {
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

	public class Getwants extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			int selected = gender.getCheckedRadioButtonId();
			rbttn = (RadioButton) findViewById(selected);
			pdialog=ProgressDialog.show(ProfilePicActivity.this, "", "Please wait..");
			if (status_update.getText().toString().equalsIgnoreCase("")) {
				profile_status = "Welcome to DoubleTick";
			} else {
				profile_status = status_update.getText().toString();
			}

			if (location.getText().toString().equalsIgnoreCase("")) {
				location.setText(Location_fetched);
			} else {
			}

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
			pdialog.dismiss();
			if (status.equals("false")) {
				Toast.makeText(getApplicationContext(),
						"Failed to upload Profile!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Profile uploaded successfully!", Toast.LENGTH_LONG)
						.show();
				global.setUname(userName.getText().toString());
				Random rand = new Random();
				Editor edittr = sp.edit();
				edittr.putString("Uname", userName.getText().toString());
				edittr.putString(
						"color_uname",
						String.valueOf(Color.argb(255, rand.nextInt(256),
								rand.nextInt(256), rand.nextInt(256))));
				edittr.commit();
				System.out.println("in else");
				dialog = new Dialog(ProfilePicActivity.this,
						android.R.style.Theme_Translucent_NoTitleBar);
				dialog.setContentView(R.layout.school_dialog);
				TextView ttle=(TextView)dialog.findViewById(R.id.category_school);
				Button teacher = (Button) dialog.findViewById(R.id.teacher);
				Button student = (Button) dialog.findViewById(R.id.student);
				teacher.setTypeface(face);
				student.setTypeface(face);
				ttle.setTypeface(face);
				dialog.setCancelable(false);
				teacher.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						sub_type = "teacher";
						global.setSub_type(sub_type);
						dialog.dismiss();
						startActivity(new Intent(ProfilePicActivity.this,
								ShowAllGroup.class));
						ProfilePicActivity.this.finish();

					}
				});
				student.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						sub_type = "student";
						global.setSub_type(sub_type);
						dialog.dismiss();
						startActivity(new Intent(ProfilePicActivity.this,
								ShowAllGroup.class));
						ProfilePicActivity.this.finish();
					}
				});
				dialog.show();
//				Intent in = new Intent(ProfilePicActivity.this, Basegroup.class);
//				startActivity(in);
				

			}
		}

		protected void getwants() {

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "update_profile.php");
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			try {

				System.out.println("helloooo--" + global.getID());
				System.out.println("name--" + userName.getText().toString());
				reqEntity.addPart("user_id", new StringBody(global.getID()));
				reqEntity.addPart("name", new StringBody(userName.getText()
						.toString()));
				reqEntity.addPart("image", pro_image);
				reqEntity.addPart("gender", new StringBody(rbttn.getText()
						.toString()));
				// ---------------------Lat long---------------------//
				reqEntity.addPart("location",
						new StringBody(global.getLat_long()));
				reqEntity.addPart("public_profile", new StringBody(
						public_visibility));
				reqEntity.addPart("profile_status", new StringBody(
						profile_status));
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

	public static Bitmap getBitmapFromURL(String src) {
		try {
			Log.e("src", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			Log.e("Bitmap", "returned");
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Exception", e.getMessage());
			return null;
		}
	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
	}

	public class GetDataA extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			Log.i("pic path", "" + pp);
			if (pp.equalsIgnoreCase("http://www.p3-group.com/img/dummy_avatar.png?v1.3.12")
					&& user_status.equalsIgnoreCase("2")
					&& (!pp.equalsIgnoreCase(""))) {
				pp = global.getOld_user().get(0).get("user_image");
			} else {
				pp = "http://www.brilliancecollege.com/online_coaching/company_corporation/images/dummy.jpg";
			}
			img_bitmap = getBitmapFromURL(pp);
			selectedImageUri = getImageUri(ProfilePicActivity.this, img_bitmap);
			selectedPath = getRealPathFromURI(ProfilePicActivity.this,
					selectedImageUri);
			pro_image = executeMultipartPost(selectedPath);
			System.out.println("pro_image path******========" + pro_image);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}
	}

	public class Get_location extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {

			DefaultHttpClient dhcp = new DefaultHttpClient();
			ResponseHandler<String> respo_string = new BasicResponseHandler();
			HttpPost post = new HttpPost(
					"http://andriodiosdevelopers.com/groupy/find_location.php?lat_long="
							+ global.getLat_long());

			try {
				Response_location = dhcp.execute(post, respo_string);
				try {
					JSONObject response_object = new JSONObject(
							Response_location);
					JSONObject response = response_object
							.getJSONObject("response");
					JSONArray status = response.getJSONArray("status");
					String Status = status.getString(0);
					if (Status.equalsIgnoreCase("1")) {
						JSONArray loc = response.getJSONArray("location");
						Location_fetched = loc.getString(0);
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

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			location.setText(Location_fetched);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}

}
