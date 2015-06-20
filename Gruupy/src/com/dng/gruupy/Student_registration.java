package com.dng.gruupy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Student_registration extends Activity {
	ImageView profile_image;

	Uri selectedImageUri;
	ByteArrayBody pro_image;
	Bitmap img_bitmap;
	private int chkBitmap = 0;
	private Bitmap thumb;
	private Bitmap bitmap;
	char[] name;
	String firstName,lastName;

	SharedPreferences sp;
	String pp = "http://www.p3-group.com/img/dummy_avatar.png?v1.3.12";
	String selectedPath, response_location, Location_fetched, status;

	Global global;
	File path, file;

	EditText first_name,c_password, grade, school, location, username, pwd, email;

	Button next, cancel;

	ProgressDialog pdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();

		global = (Global) getApplicationContext();
		sp = getSharedPreferences("mprefs", Context.MODE_PRIVATE);

		setContentView(R.layout.act_student_registr);
		location = (EditText) findViewById(R.id.locator);
		profile_image = (ImageView) findViewById(R.id.uimageView1);
		first_name = (EditText) findViewById(R.id.user_name1);
		email = (EditText) findViewById(R.id.email1);
		grade = (EditText) findViewById(R.id.grade);
		school = (EditText) findViewById(R.id.institute);
		next = (Button) findViewById(R.id.next_btn);
//		cancel = (Button) findViewById(R.id.back_bttn);
		c_password=(EditText)findViewById(R.id.c_password);
		username = (EditText) findViewById(R.id.s_username);
		pwd = (EditText) findViewById(R.id.s_password);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		
		Get_location get_loc=new Get_location();
		get_loc.execute();
		
		String default_url = "http://andriodiosdevelopers.com/groupy/uploads/female140.png";
		DownloadFromUrl(default_url, "female140.png");
		selectedPath = file.getPath();
		pro_image = executeMultipartPost(selectedPath);
		/*cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(Student_registration.this,
				// MainActivity.class));
				Student_registration.this.finish();
			}
		});*/

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (validate()) {
					Getwants gws = new Getwants();
					gws.execute();
				} else {
					Toast.makeText(Student_registration.this,
							"All fields required", 5).show();
				}
			}
		});

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
	}

	protected boolean validate() {
		// TODO Auto-generated method stub
		if (first_name.getText().toString().equalsIgnoreCase("")) {
			first_name.setError("Name missing");
			return false;
		} else if (email.getText().toString().equalsIgnoreCase("")
				&& !isValidEmail(email.getText().toString())) {
			email.setError("EmailId missing");
			return false;
		} else if (grade.getText().toString().equalsIgnoreCase("")) {
			grade.setError("EmailId missing");
			return false;
		} else if (school.getText().toString().equalsIgnoreCase("")) {
			school.setError("Institute missing");
			return false;
		} else if (username.getText().toString().equalsIgnoreCase("")) {
			username.setError("USername missing");
			return false;
		} else if (pwd.getText().toString().equalsIgnoreCase("")) {
			pwd.setError("Password missing");
			return false;
		} else if (!pwd.getText().toString().equals(c_password.getText().toString())) {
			Toast.makeText(Student_registration.this, "Password Mismatch", 6).show();
			return false;
		}else if (location.getText().toString().equalsIgnoreCase("")
				|| location.getText().toString()
						.equalsIgnoreCase("loading location...")) {
			location.setError("Location missing");
			return false;
		} else {
			return true;
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
				selectedPath = getRealPathFromURI(Student_registration.this,
						selectedImageUri);
				pro_image = executeMultipartPost(selectedPath);
				profile_image.setImageURI(selectedImageUri);
			}
		}
	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(target)
						.matches();
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

	// --------------GetLoaction--------//

	public class Get_location extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {

			DefaultHttpClient dhcp = new DefaultHttpClient();
			ResponseHandler<String> respo_string = new BasicResponseHandler();
			HttpPost post = new HttpPost(
					"http://andriodiosdevelopers.com/groupy/find_location.php?lat_long="
							+ global.getLat_long());

			try {
				response_location = dhcp.execute(post, respo_string);
				try {
					JSONObject response_object = new JSONObject(
							response_location);
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

	public class Getwants extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = ProgressDialog.show(Student_registration.this, "",
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
			pdialog.dismiss();
			if (status.equals("false")) {
				Toast.makeText(getApplicationContext(),
						"Failed to upload Profile!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Profile uploaded successfully!", Toast.LENGTH_LONG)
						.show();
				global.setUname(first_name.getText().toString());
				Random rand = new Random();
				Editor edittr = sp.edit();
				edittr.putString("Uname", first_name.getText().toString());
				edittr.putString("regd", "true");
				edittr.putString(
						"color_uname",
						String.valueOf(Color.argb(255, rand.nextInt(256),
								rand.nextInt(256), rand.nextInt(256))));
				edittr.commit();
				System.out.println("in else");
				startActivity(new Intent(Student_registration.this,
						ChatMain.class));
			}
		}

		protected void getwants() {

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "student.php");
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			try {

				System.out.println("helloooo--" + global.getID());
				System.out.println("name--" + first_name.getText().toString());
				reqEntity.addPart("id", new StringBody(global.getID()));
//				reqEntity.addPart("id", new StringBody("231"));
				reqEntity.addPart("user_type", new StringBody("student"));
				if (first_name.getText().toString().contains(" ")) {
					String name=first_name.getText().toString();
					int firstSpace = name.indexOf(" "); // detect the first space character
					firstName = name.substring(0, firstSpace);  // get everything upto the first space character
					lastName = name.substring(firstSpace).trim(); // get ever
				}else{
					firstName=first_name.getText().toString();
					lastName=first_name.getText().toString();
				}
				Log.e("First name", "firstname" + firstName);
				reqEntity.addPart("fname", new StringBody(firstName));
				reqEntity.addPart("lname", new StringBody(lastName));
				reqEntity.addPart("email", new StringBody(email.getText()
						.toString()));
				System.out.println("Name--" + first_name.getText().toString());
				reqEntity.addPart("username", new StringBody(username.getText()
						.toString()));
				System.out
						.println("USerName--" + username.getText().toString());
				if(pro_image.equals(null)){
					String default_url = "http://andriodiosdevelopers.com/groupy/uploads/female140.png";
					DownloadFromUrl(default_url, "female140.png");
					selectedPath = file.getPath();
					pro_image = executeMultipartPost(selectedPath);
					reqEntity.addPart("image", pro_image);
				}else{
					reqEntity.addPart("image", pro_image);
				}
				System.out.println("Image--" + pro_image);
				reqEntity.addPart("phone_no",
						new StringBody(global.getRegd_no()));
				reqEntity.addPart("password", new StringBody(pwd.getText()
						.toString()));
				reqEntity.addPart("grade", new StringBody(grade.getText()
						.toString()));
				System.out.println("Password" + pwd.getText().toString());
				reqEntity.addPart("school", new StringBody(school.getText()
						.toString()));
				System.out.println("School" + school.getText().toString());
				reqEntity.addPart("location", new StringBody(location.getText()
						.toString()));
				System.out.println("Location" + location.getText().toString());
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

}
