/*package com.dng.gruupy;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.api.Status;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Upload_Content extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	ImageButton video_capture, pdf_upload;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_VIDEO = 2;
	String user_id, group_id, filePath = "";
	ProgressBar pbar;
	Global global;
	VideoView vview;
	String stts = "";
	String status_op="";
	static String Topic = "";
	RelativeLayout view_videos;
	String Repos = "";
	String fetch_comments_url = "http://andriodiosdevelopers.com/groupy/get_comments.php";
			
	ArrayList<String> comments_list = new ArrayList<String>();
	TextView txtPercentage;
	ListView questions_list;
	EditText txtcomments1;
	Dialog dlog;
	EditText enter_topic;
	String faqs_url = Global_Constants.BASE_URL + "post_comments.php";
	Context c;
	long totalSize = 0;
	Button upload_bttn;
	private Uri fileUri; // file url to store image/video

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_upload);
		video_capture = (ImageButton) findViewById(R.id.camera_launch);
		vview = (VideoView) findViewById(R.id.vview);
		vview.setVisibility(9);
		upload_bttn = (Button) findViewById(R.id.upload_bttn);
		questions_list = (ListView) findViewById(R.id.questionaire);
		global = (Global) getApplicationContext();
		txtcomments1 = (EditText) findViewById(R.id.comments1);
		view_videos = (RelativeLayout) findViewById(R.id.view_videos);
		SharedPreferences preferences = getSharedPreferences("mprefs",
				Context.MODE_PRIVATE);
		user_id = preferences.getString("id", "");
		group_id = preferences.getString("group_id", "");
		enter_topic = (EditText) findViewById(R.id.enter_topic);
		Log.i("GID from mprefs", "" + group_id);
		pdf_upload = (ImageButton) findViewById(R.id.pdf_launch);

		view_videos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Upload_Content.this,
						Uploaded_listing.class));
				Upload_Content.this.finish();
			}
		});
		
		 * questions_list.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub
		 * 
		 * } });
		 

		txtcomments1.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (keyCode) {
					case KeyEvent.KEYCODE_ENTER:
						if (txtcomments1.toString().equalsIgnoreCase("")) {
							Toast.makeText(Upload_Content.this,
									"Please enter something to post", 5).show();
						} else if (Topic.equalsIgnoreCase("")) {

							Toast.makeText(Upload_Content.this,
									"Post a video or pdf first", 5).show();

						} else {
							Questions quest = new Questions();
							quest.execute();
						}
						return true;
					default:
						break;
					}
				}

				return false;
			}
		});

		video_capture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vview.setVisibility(View.VISIBLE);
				upload_bttn.setVisibility(0);
				pdf_upload.setVisibility(9);
				video_capture.setVisibility(View.GONE);
				recordVideo();
			}
		});
		pdf_upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Upload_Content.this, pdf_listing.class));
			}
		});

		upload_bttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (enter_topic.getText().toString().equalsIgnoreCase("")) {
					enter_topic.setText("Miscellaneous");
				}
				new UploadFileToServer().execute();
			}
		});
	}

	// ----------------Async task to post FAQs ---------------//

	public class Questions extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			questions();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (stts.equalsIgnoreCase("true")) {

				Toast.makeText(Upload_Content.this, "Successfully posted", 5)
						.show();
				txtcomments1.setText("");
				 Fetch_comments f_comments=new Fetch_comments();
				 f_comments.execute();
			} else {
				Toast.makeText(Upload_Content.this,
						"Failed in posting....Try again!!", 5).show();
			}

		}

	}

	protected Void questions() {
		HttpClient htcp = new DefaultHttpClient();
		HttpPost post = new HttpPost(faqs_url);
		ResponseHandler<String> response = new BasicResponseHandler();
		List<NameValuePair> listed = new ArrayList<NameValuePair>();
		listed.add(new BasicNameValuePair("user_id", user_id));
		listed.add(new BasicNameValuePair("group_id", group_id));
		listed.add(new BasicNameValuePair("topic", Topic));
		listed.add(new BasicNameValuePair("comment", txtcomments1.getText()
				.toString()));
		Log.e("Listed", "" + listed);
		try {
			post.setEntity(new UrlEncodedFormEntity(listed));
			try {
				Repos = htcp.execute(post, response);
				try {
					JSONObject job = new JSONObject(Repos);
					JSONObject job1 = job.getJSONObject("response");
					stts = job1.getString("status");
					Log.i("status", "Status returned" + stts);
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

		return null;
	}

	// ---------------------------------------------------------
	// ---------------//

	// ------------------------To reflect questions
	// posted---------------------//
	public class Fetch_comments extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			fetch_comments();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			questions_list.setAdapter(new ArrayAdapter<String>(
					Upload_Content.this, R.layout.listing_item1, comments_list));

		}

	}

	protected Void fetch_comments() {
		String Response_fetched = "";
		
		HttpClient htcp = new DefaultHttpClient();
		HttpPost post = new HttpPost(fetch_comments_url);
		ResponseHandler<String> responser = new BasicResponseHandler();
		List<NameValuePair> Input_list = new ArrayList<NameValuePair>();
		Input_list.add(new BasicNameValuePair("user_id", user_id));
		Input_list.add(new BasicNameValuePair("group_id", group_id));
		Input_list.add(new BasicNameValuePair("topic", Topic));

		try {
			post.setEntity(new UrlEncodedFormEntity(Input_list));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Response_fetched = htcp.execute(post, responser);
			JSONObject RJob;
			try {
				RJob = new JSONObject(Response_fetched);
				JSONObject job1 = RJob.getJSONObject("response");
				status_op=job1.getString("status");
				JSONArray cmmnts=job1.getJSONArray("comments");
				if(status_op.equalsIgnoreCase("true")){
					for(int i=0;i<cmmnts.length();i++){
						comments_list.add(cmmnts.getString(i));
					}
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
		return null;

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	protected void recordVideo() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

		// set video quality
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name

		// start the video capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
	}

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				Config.IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG, "Oops! Failed create " + Config.IMAGE_DIRECTORY_NAME
						+ " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				// video successfully recorded
				// launching upload activity

				// // start playing
				// vview.start();

				filePath = fileUri.getPath();
				vview.setVideoPath(fileUri.getPath());
				vview.start();
				// launchUploadActivity(false);

			} else if (resultCode == RESULT_CANCELED) {

				// user cancelled recording
				Toast.makeText(getApplicationContext(),
						"User cancelled video recording", Toast.LENGTH_SHORT)
						.show();

			} else {
				// failed to record video
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to record video", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	// ------------------class to upload video and pdf to
	// server------------///////////////

	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			dlog = new Dialog(Upload_Content.this);
			dlog.setContentView(R.layout.dialog_upload);
			pbar = (ProgressBar) dlog.findViewById(R.id.upload_progress);
			txtPercentage = (TextView) dlog.findViewById(R.id.percent);
			pbar.setProgress(0);
			dlog.show();
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			// Making progress bar visible
			pbar.setVisibility(View.VISIBLE);

			// updating progress bar value
			pbar.setProgress(progress[0]);

			// updating percentage value
			txtPercentage.setText(String.valueOf(progress[0]) + "%");
			if (progress[0] == 100) {
				dlog.dismiss();
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			String responseString = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);
			try {
				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
						new AndroidMultiPartEntity.ProgressListener() {

							@Override
							public void transferred(long num) {
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});

				File sourceFile;
				if (global.getPdf_path().equalsIgnoreCase("")) {
					sourceFile = new File(filePath);
				} else {
					sourceFile = new File(global.getPdf_path());
					Log.i("Pdf path", "Pdf path" + global.getPdf_path());
				}
				entity.addPart("video_name", new FileBody(sourceFile));
				entity.addPart("user_id", new StringBody(user_id));
				entity.addPart("group_id", new StringBody(group_id));
				entity.addPart("topic", new StringBody(enter_topic.getText()
						.toString()));
				Log.d("User Id", "" + user_id);
				totalSize = entity.getContentLength();
				Log.i("Length of entity", "" + totalSize);
				httppost.setEntity(entity);

				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					// Server response
					responseString = EntityUtils.toString(r_entity);
					Log.i("Response fetched", "" + responseString);

				} else {
					responseString = "Error occurred! Http Status Code: "
							+ statusCode;
				}
				try {
					JSONObject job = new JSONObject("response");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}

			return responseString;

		}

		@Override
		protected void onPostExecute(String result) {
			Log.e(TAG, "Response from server: " + result);
			Topic = enter_topic.getText().toString();
			enter_topic.setText("");
			// showing the server response in an alert dialog
			showAlert(result);
			// openPdfIntent(global.getPdf_path());
			super.onPostExecute(result);
		}

		// ------------------------------------------------------------------------------------------------------//
		*//**
		 * Method to show alert dialog
		 * *//*
		private void showAlert(String message) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Upload_Content.this);
			builder.setMessage("Successfully uploaded.")
					.setTitle("Response from Servers")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// do nothing
									dlog.dismiss();
									upload_bttn.setVisibility(View.GONE);
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (global.getPdf_path().equalsIgnoreCase("")) {

		} else {
			upload_bttn.setVisibility(0);
			// UploadFileToServer upload_file = new UploadFileToServer();
			// upload_file.execute();
		}

	}
}
*/