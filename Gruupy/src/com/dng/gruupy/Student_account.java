package com.dng.gruupy;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Student_account extends Activity {

	EditText uname, grade,username, school, Username, email;
	String get_stu_acc_url = "http://andriodiosdevelopers.com/groupy/student_profile.php";
	String edit_stu_Acc = "http://andriodiosdevelopers.com/groupy/edit_student_profile.php";

	SharedPreferences pref;
	Global global;

	String id, user_type = "teacher";
	ImageView profile_pic;
	Button next, back;

	String user_name, name, lname, Email, Grade, insti, image;

	Uri selectedImageUri;
	ByteArrayBody pro_image = null;
	Bitmap img_bitmap;
	private int chkBitmap = 0;
	private Bitmap thumb;
	private Bitmap bitmap;
	File path, file;
	String selectedPath, firstname, lastname, status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_student_profile);

		global = (Global) getApplicationContext();
		pref = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		id = pref.getString("id", "");
		Log.e("UId", "UId" + id);
		user_type = pref.getString("user_type", "");
		Log.e("Utype", "UType" + user_type);
		uname = (EditText) findViewById(R.id.user_name1);
		Username = (EditText) findViewById(R.id.nick);
		grade = (EditText) findViewById(R.id.grade);
		school = (EditText) findViewById(R.id.institute);
		email = (EditText) findViewById(R.id.email1);
		username=(EditText)findViewById(R.id.s_username);
		profile_pic = (ImageView) findViewById(R.id.uimageView1);
		next = (Button) findViewById(R.id.next_btn);
		get_account_details g_Details = new get_account_details();
		g_Details.execute();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		String default_url = "http://andriodiosdevelopers.com/groupy/uploads/female140.jpeg";
		DownloadFromUrl(default_url, "female140.jpeg");
		selectedPath = file.getPath();
		pro_image = executeMultipartPost(selectedPath);
		profile_pic.setOnClickListener(new OnClickListener() {

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

		/*back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(Student_registration.this,
				// MainActivity.class));
				Student_account.this.finish();
			}
		});*/
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				edit_student_acc edit = new edit_student_acc();
				edit.execute();

			}
		});
	}

	public class edit_student_acc extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			edit_student_acc();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (status.equalsIgnoreCase("true")) {
				Editor edittr = pref.edit();
				edittr.putString("regd", "true");
				edittr.commit();
				startActivity(new Intent(Student_account.this, ChatMain.class));
			} else {
				Toast.makeText(Student_account.this,
						"Some error occurred!! please try again", 5).show();
			}
		}
	}

	public void edit_student_acc() {
		HttpClient httpclient = new DefaultHttpClient();
		String resp_str = "";
		ResponseHandler<String> repos = new BasicResponseHandler();
		HttpPost httppost = new HttpPost(edit_stu_Acc);
		MultipartEntity reqEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {

			System.out.println("helloooo--" + global.getID());
			System.out.println("name--" + uname.getText().toString());
			reqEntity.addPart("id", new StringBody("231"));
//			reqEntity.addPart("student_id", new StringBody(global.getID())); // student_id
			/*if (uname.getText().toString().contains(" ")) { // user_name
				String name = uname.getText().toString();
				int firstSpace = name.indexOf(" "); // detect the first space
													// character
				firstname = name.substring(0, firstSpace); // get everything
															// upto the first
															// space character
				lastname = name.substring(firstSpace).trim(); // get ever
			} else {
				firstname = uname.getText().toString();
				lastname = uname.getText().toString();
			}*/
			Log.e("First name", "firstname" + firstname);
			reqEntity.addPart("fname", new StringBody(username.getText().toString()));
			reqEntity.addPart("user_name", new StringBody(username.getText().toString()));
			reqEntity.addPart("email", new StringBody(email.getText()
					.toString()));
			System.out.println("Name--" + uname.getText().toString());

			reqEntity.addPart("image", pro_image);
			System.out.println("Image--" + pro_image);
			reqEntity.addPart("grade", new StringBody(grade.getText()
					.toString()));
			reqEntity.addPart("school", new StringBody(school.getText()
					.toString()));
			System.out.println("School" + school.getText().toString());
			httppost.setEntity(reqEntity);
			Log.i("Entity", "" + reqEntity.toString());

			resp_str = httpclient.execute(httppost, repos);
			System.out.println("-------Edit profileeeeeee---------"
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
			uname.setText(name );
			username.setText(user_name);
			school.setText(insti);
			email.setText(Email);
			grade.setText(Grade);
			Picasso.with(Student_account.this).load(image).resize(200, 200).into(profile_pic);
		}

	}

	public void get_account_details() {

		DefaultHttpClient dhtp = new DefaultHttpClient();
		String output = "";
		HttpPost post = new HttpPost(get_stu_acc_url);
		ResponseHandler<String> response_handler = new BasicResponseHandler();
		List<NameValuePair> input_list = new ArrayList<NameValuePair>();
		input_list.add(new BasicNameValuePair("student_id","231"));
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
				String status = job2.getString("status");
				if (status.equalsIgnoreCase("true")) {
					user_name = job2.getString("username");
					name = job2.getString("fname");
					lname = job2.getString("lname");
					Email = job2.getString("email");
					Log.i("EmailID", "" + Email);
					Grade = job2.getString("grade");
					Log.i("grade", "" + Grade);
					insti = job2.getString("school");
					Log.i("School", "" + insti);
					image = job2.getString("image");
					Log.i("Image", "" + image);
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

				selectedPath = getRealPathFromURI(Student_account.this,
						selectedImageUri);
				Log.i("SP2", "selected_path" + selectedPath);
				pro_image = executeMultipartPost(selectedPath);
				profile_pic.setImageURI(selectedImageUri);
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
}
