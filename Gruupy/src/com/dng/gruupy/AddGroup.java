package com.dng.gruupy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import utilities.CropOption;
import utilities.CropOptionAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.InputFilter;
import android.text.Spanned;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddGroup extends Activity {
	private ImageView groupPic;
	private EditText groupName;
	private Button next, close;
	Uri selectedImageUri;
	EditText ed_channel_name, ed_location, ed_desc, ed_years, long_desc;
	String Location_fetched = "";
	String selectedPath;
	private int chkBitmap = 0;
	String Visibility = "";
	private static final int CROP_FROM_CAMERA = 2;
	private Bitmap thumb;
	Global global;
	String channel_no="";
	private Bitmap bitmap;
	ProgressDialog pdlog;
	Dialog dialog;
	InputFilter alphaNumericFilter;
	ByteArrayBody pro_image;
	String str_response = "";
	RadioGroup visibility;
	RadioButton rbttn, g_tbuttn, ppub, ppri, vbttn, inst, req;
	RadioGroup membership, group_type;
	Spinner sp1, sp2;
	Typeface face;
	String group_name = "";
	TextView text_addgroup, visibile, Membership;
	String Response_location, id, status, main_type, idGroup, lat_long,
			location, sub_type;
	String pp = "http://www.p3-group.com/img/dummy_avatar.png?v1.3.12";
	Bitmap img_bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.addgroup_screen);
		global = (Global) getApplicationContext();
		main_type = global.getCategory_base_group();
		alphaNumericFilter = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence arg0, int arg1, int arg2,
					Spanned arg3, int arg4, int arg5) {
				for (int k = arg1; k < arg2; k++) {
					if (!Character.isLetterOrDigit(arg0.charAt(k))) {
						return "";
					} // the first editor deleted this bracket when it is
						// definitely necessary...
				}
				return null;
			}
		};

		GetDataA gdd = new GetDataA();
		gdd.execute();
		sp1 = (Spinner) findViewById(R.id.sp1);
		sp2 = (Spinner) findViewById(R.id.sp2);
		SharedPreferences pref = getSharedPreferences("mprefs",
				Context.MODE_PRIVATE);
		id = pref.getString("id", "");
		System.out.println("9999 in AddGrp shr====" + id);
		setupView();
		Get_location get_loc = new Get_location();
		get_loc.execute();
	}

	private void setupView() {
		// TODO Auto-generated method stub
		// groupName = (EditText) findViewById(R.id.edit_subject);
		groupPic = (ImageView) findViewById(R.id.image_group);
		groupPic.setOnClickListener(groupPicClicked);
		next = (Button) findViewById(R.id.save_details);
		ed_location = (EditText) findViewById(R.id.ed_location);
		ed_desc = (EditText) findViewById(R.id.ed_desc);
		ed_channel_name = (EditText) findViewById(R.id.ed_channel_name);
		long_desc = (EditText) findViewById(R.id.long_desc);
		group_type = (RadioGroup) findViewById(R.id.group_type);
		visibility = (RadioGroup) findViewById(R.id.visibility);
		Membership = (TextView) findViewById(R.id.Membership);
		ppub = (RadioButton) findViewById(R.id.ppublic);

		inst = (RadioButton) findViewById(R.id.instant_mem);

		req = (RadioButton) findViewById(R.id.request_mem);

		text_addgroup = (TextView) findViewById(R.id.text_addgroup);

		ppri = (RadioButton) findViewById(R.id.pprivate);
		visibile = (TextView) findViewById(R.id.visibile);

		membership = (RadioGroup) findViewById(R.id.membership_options);
		close = (Button) findViewById(R.id.close_bttn);
		ed_channel_name.setFilters(new InputFilter[] { alphaNumericFilter });
		next.setOnClickListener(nextClicked);

		// ----------Set font style --------------//
		Typeface face = Typeface.createFromAsset(getAssets(), "Aaargh.ttf");
		// groupName.setTypeface(face);
		ed_desc.setTypeface(face);
		ed_location.setTypeface(face);
		long_desc.setTypeface(face);
		next.setTypeface(face);
		ppub.setTypeface(face);
		inst.setTypeface(face);
		req.setTypeface(face);
		close.setTypeface(face);
		Membership.setTypeface(face);
		text_addgroup.setTypeface(face);
		visibile.setTypeface(face);

		ppri.setTypeface(face);

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AddGroup.this, ShowAllGroup.class));
				AddGroup.this.finish();
			}
		});
	}

	public OnClickListener groupPicClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent("android.intent.action.PICK",
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			startActivityForResult(
					Intent.createChooser(intent, "Select file to upload "), 10);
		}
	};
	public OnClickListener nextClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (validate()) {
				Getwants dl = new Getwants();
				dl.execute();
			} else {
				Toast.makeText(AddGroup.this, "Please enter complete details",
						5).show();
			}

			// }

		}
	};

	public class Get_location extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {

			DefaultHttpClient dhcp = new DefaultHttpClient();
			ResponseHandler<String> respo_string = new BasicResponseHandler();
			Log.e("Lat long","long lat"+global.getLat_long());
			HttpPost post = new HttpPost(
					Global_Constants.BASE_URL + "find_location.php?lat_long="
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
			ed_location.setText(Location_fetched);
		}

	}

	public class Getwants extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			int selected = membership.getCheckedRadioButtonId();
			rbttn = (RadioButton) findViewById(selected);
			int vis_selected = visibility.getCheckedRadioButtonId();
			vbttn = (RadioButton) findViewById(selected);
			int type_selected = group_type.getCheckedRadioButtonId();
			g_tbuttn = (RadioButton) findViewById(type_selected);
			if (vbttn.getText().toString().equalsIgnoreCase("public")) {
				Visibility = "1";
			} else {
				Visibility = "0";
			}
			Log.e("Visibility", "Vbility" + Visibility);
			pdlog = new ProgressDialog(AddGroup.this).show(AddGroup.this, "",
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
			pdlog.dismiss();
			if (status.equals("false")) {
				Toast.makeText(getApplicationContext(),
						"Failed to upload Profile!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Group created successfully with location:  "
								+ location, Toast.LENGTH_LONG).show();
				Intent in = new Intent(AddGroup.this, CaptureSignature.class).putExtra("channel_no", channel_no);
				startActivity(in);
				AddGroup.this.finish();
				// Intent in=new Intent(AddGroup.this,ShowAllGroup.class);
				// startActivity(in);
			}

		}

		protected void getwants() throws NullPointerException {

			HttpClient httpclient = new DefaultHttpClient();
			ResponseHandler<String> response_handler = new BasicResponseHandler();
			HttpPost httppost = new HttpPost(Global_Constants.BASE_URL
					+ "add_group.php");
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			try {
				group_name = sp1.getSelectedItem().toString() + "-"
						+ sp2.getSelectedItem().toString();
				Log.e("Group_name", "Group_name" + group_name);
				System.out.println("usr id is--" + id);
				reqEntity.addPart("user_id", new StringBody(id));
				reqEntity.addPart("channel_name", new StringBody(
						ed_channel_name.getText().toString()));
				reqEntity.addPart("group_name", new StringBody(group_name));
				if (long_desc.getText().toString().equalsIgnoreCase("")) {
					reqEntity.addPart("details", new StringBody(
							"No details available"));
				} else {
					reqEntity.addPart("details", new StringBody(long_desc
							.getText().toString()));
				}
				reqEntity.addPart("description", new StringBody(ed_desc
						.getText().toString()));
				reqEntity.addPart("membership", new StringBody(rbttn.getText()
						.toString()));
				reqEntity.addPart("group_type", new StringBody(g_tbuttn
						.getText().toString()));
				reqEntity.addPart("visible", new StringBody(Visibility));
				// System.out.println("usr id is--"
				// + groupName.getText().toString());

				/*
				 * reqEntity.addPart("lat_long", new
				 * StringBody(global.getLat_long())); Log.i("LL", "" +
				 * global.getLat_long());
				 */
				reqEntity.addPart("image", pro_image);
				Log.i("pro_image", "" + pro_image);
				reqEntity.addPart("main_type", new StringBody("schools"));
				Log.i("main_type", "" + main_type.toLowerCase());
				reqEntity.addPart("user_type", new StringBody("teacher"));
				reqEntity.addPart("lat_long",
						new StringBody(global.getLat_long()));
				Log.i("main_type", "" + main_type.toLowerCase());
				httppost.setEntity(reqEntity);
				str_response = httpclient.execute(httppost, response_handler);
				System.out.println("-------Groupppppppppp---------"
						+ str_response);
				JSONObject js = new JSONObject(str_response);
				JSONObject job = js.getJSONObject("response");
				if (!job.getString("status").equals(null)) {
					status = job.getString("status");
					if(status.equalsIgnoreCase("true")){
						channel_no=job.getString("token");
					}
				} else {
					status = "false";
				}

				String group_id = job.getString("group_id");
				Log.i("ID created", "Group created with ID" + group_id);

				SharedPreferences preferences = getSharedPreferences("mprefs",
						Context.MODE_PRIVATE);
				Editor edit = preferences.edit();
				edit.putString("group_id", group_id);
				edit.commit();
				location = job.getString("location");
				Log.i("Status recieved", "" + location);
			} catch (ClientProtocolException e) {
				System.out.println("ClientProtocolException--" + e);
			} catch (IOException e) {
				System.out.println("IOException--" + e);
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	protected boolean validate() {
		// TODO Auto-generated method stub
		if (ed_desc.getText().toString().equalsIgnoreCase("")) {
			return false;
		} else if(ed_channel_name.getText().toString().equalsIgnoreCase("")){
			return false;
		}else{
			return true;
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (data.getData() != null) {
				selectedImageUri = data.getData();
				Log.e("in ok", "selected");
			}
			if (requestCode == 10)

			{
				selectedPath = getRealPathFromURI(AddGroup.this,
						selectedImageUri);
				Bitmap prom = compressImage(selectedPath);
				pro_image = executeMultipartPost(selectedPath);
				groupPic.setBackground(null);
				doCrop();

			} else if (requestCode == CROP_FROM_CAMERA) {
				Bundle extras = data.getExtras();

				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					// groupPic.setImageBitmap(prom);
					groupPic.setImageBitmap(photo);
				}

				File f = new File(selectedImageUri.getPath());

				if (f.exists())
					f.delete();

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
		System.out.println("=======================" + name);
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

	//
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

	// llllllllllll

	public class GetDataA extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			img_bitmap = getBitmapFromURL(pp);
			selectedImageUri = getImageUri(AddGroup.this, img_bitmap);
			selectedPath = getRealPathFromURI(AddGroup.this, selectedImageUri);
			pro_image = executeMultipartPost(selectedPath);
			Bitmap proimage = compressImage(selectedPath);
			System.out.println("pro_image path******========" + pro_image);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}
	}

	public Bitmap compressImage(String imageUri) {
		String filePath = getRealPathFromURI(AddGroup.this, selectedImageUri);
		Bitmap scaledBitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;
		float maxHeight = 816.0f;
		float maxWidth = 612.0f;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;
		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;
			}
		}
		options.inSampleSize = calculateInSampleSize(options, actualWidth,
				actualHeight);
		options.inJustDecodeBounds = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];
		try {
			bmp = BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}
		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,
					Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}
		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;
		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
				middleY - bmp.getHeight() / 2, new Paint(
						Paint.FILTER_BITMAP_FLAG));
		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);

			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, 0);
			Log.d("EXIF", "Exif: " + orientation);
			Matrix matrix = new Matrix();
			if (orientation == 6) {
				matrix.postRotate(90);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 3) {
				matrix.postRotate(180);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 8) {
				matrix.postRotate(270);
				Log.d("EXIF", "Exif: " + orientation);
			}
			scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
					scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
					true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scaledBitmap;
	}

	public int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		final float totalPixels = width * height;
		final float totalReqPixelsCap = reqWidth * reqHeight * 2;
		while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
			inSampleSize++;
		}
		return inSampleSize;
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, 0);

		int size = list.size();

		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();

			return;
		} else {
			intent.setData(selectedImageUri);

			intent.putExtra("outputX", 400);
			intent.putExtra("outputY", 400);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));

				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(
						AddGroup.this, cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								startActivityForResult(
										cropOptions.get(item).appIntent,
										CROP_FROM_CAMERA);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

						if (selectedImageUri != null) {
							getContentResolver().delete(selectedImageUri, null,
									null);
							selectedImageUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}
}