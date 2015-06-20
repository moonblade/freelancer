package com.dng.gruupy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.Comments_adapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CaptureSignature extends Activity {

	LinearLayout mContent, hidden, sec_view, controls, controls2;
	signature mSignature;
	RelativeLayout preview_lay, header_layout, send_layout;
	LinearLayout parent_layout;
	RelativeLayout lClear, lGetSign, back_tuition, lCancel, lNew, lExpand,
			llinked_bttn;
	ImageButton mClear, mGetSign, mCancel, mNew, mExpand, linked_bttn;
	public static String tempDir, topic;
	ListView L1;
	String[] more_options = { "Back to Tuition", "Listen to Speech",
			"Send as Speech", "Play Lesson" };
	public static String Close_chapter_url = "http://andriodiosdevelopers.com/groupy/close_chapter.php";
	public static String online_url = "http://andriodiosdevelopers.com/groupy/online_users.php";
	public static String resume_url = "http://andriodiosdevelopers.com/groupy/resume.php";
	public int count = 1;
	public String current = null;
	String msg_resp, responseString = "";
	Global global;
	public static int RESULT_LOAD_IMG = 1;
	private Bitmap mBitmap;
	int choice;
	static int posted_msg = 1;// ---for refreshing first time
								// only---------------//
	static String c_id = "";
	static int scroll_active = 0, reset = 0;
	Bitmap bmp1;
	Dialog dlog, first_dialog, dd;
	int orientation;
	Uri selectedImage;
	String resume_topic, resume_chapter, sstatus_op = "";
	static String topic_retrnd = "";
	static int resumed;
	String topic_recd, imgDecodableString, end_chapter = "";
	EditText comments;
	String chapter_name, rresponse = "";
	int Dheight;
	String[] opt_given = { "Resume Last Lesson", "Resume Saved Lesson",
			"Create New Chapter and Lesson", "View Lessons",
			"Play saved lesson", "Import Image", "Unlock Whiteboard",
			"Unlock Text" };
	String[] opt_stu = { "Play saved lesson" };
	String chptr_retrnd, SstatuS, StatuS = "";
	String indx = "";
	SpinnerAdapter sadap;
	DisplayMetrics dmetrix;
	ImageView i1;
	static int keypadHeight;
	String status_op = "";
	int Index, saved;
	ProgressDialog pdial;
	static boolean link_status = true;
	static boolean activity_active = true;
	String response, last_index_fromresume;
	String stts, color, status_l = "";
	ImageButton settings, edit_topic;
	static String chapter_name_start = "";
	private CurlView mCurlView;
	EditText topic_Ed, show_sub_topic, sub_topic_Ed;
	ListView questions_list;
	int one_time;
	ByteArrayBody byte_img;
	static ArrayList<String> img_path = new ArrayList<String>();
	static ArrayList<String> f_img_path = new ArrayList<String>();
	static ArrayList<String> Iindex = new ArrayList<String>();
	static ArrayList<String> f_Iindex = new ArrayList<String>();
	ArrayList<String> index_list = new ArrayList<String>();
	ArrayList<String> names_list;
	ArrayList<String> chapter_list = new ArrayList<String>();
	ArrayList<String> chapter_list_new = new ArrayList<String>();
	ArrayList<String> Ids_list;
	ArrayList<String> topic_index = new ArrayList<String>();
	ArrayList<HashMap<String, String>> lesson_index = new ArrayList<HashMap<String, String>>();
	private Bitmap bitmap;
	SharedPreferences sprefs;
	ImageView preview_bttn, attach_btn, send_play, plus_bttn, online,
			img_preview;
	ImageButton post_note;
	String previewlay_clckd = "0", Lesson_index = "";
	String Response_fetched = "";
	TextView indexing_tv, Group_name;
	Spinner lesson_detail;
	ImageButton options_bttn, repeat_req, opt_wheel;
	// ImageView preview;
	AndroidMultiPartEntity entity;
	ArrayList<String> images_indexed = new ArrayList<String>();
	ArrayList<String> images_paths = new ArrayList<String>();
	ArrayList<String> indexes = new ArrayList<String>();
	ArrayList<String> comments_list;
	long totalSize = 0;
	Uri file_uri, img_uri;
	ListView l1;
	public static float stroke;
	View mView;
	static int open = 0;
	TextView channel1, Preview_mode_text, channel2, channel3;
	String topic1, session;
	TableLayout base_buttons;
	Dialog features_dialog, options_list, more_opt_dialog, topic_dialog,
			confirm_dial;
	float x1, x2, y1, y2;
	String user_id, url = "";
	String fpath, int_col = "#ebebeb", error = "";
	private int chkBitmap = 0;
	Comments_adapter cadap;
	TextToSpeech tts; // Text to speech library
	Button b1, b2;
	private Bitmap thumb;
	String resp_msg = "";
	String faqs_url = Global_Constants.BASE_URL + "post_comments.php";
	String fetch_comments_url = "http://andriodiosdevelopers.com/groupy/get_comments.php";
	String fetch_lesson_detail_url = "http://andriodiosdevelopers.com/groupy/syllabus.php";
	String save_topic_url = "http://andriodiosdevelopers.com/groupy/user_syllabus.php";
	static int check_chapter = 0;
	int index = 0;
	ProgressBar progressBar;
	File mypath;
	ArrayAdapter<String> arr_adptr;
	ByteArrayOutputStream boutstream;
	ImageView imgView;
	float int_stro;
	Bitmap bmp11;
	String next_index, channel_no, start = "3", path_fetched = "",
			group_name_clckd = "";
	private String uniqueId;
	Intent intn;
	static String creator_ID, creator_id = "";
	String UID, aa = "aa";

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		activity_active = true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_upload);
		global = (Global) getApplicationContext();
		sprefs = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		UID = sprefs.getString("id", "");
		next_index = sprefs.getString("upcoming_index", "");
		repeat_req = (ImageButton) findViewById(R.id.repeat_req);
		controls2 = (LinearLayout) findViewById(R.id.controls2);
		// ----------------------network thread

		// exception------------------------//
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// img_preview = (ImageView) findViewById(R.id.content_preview);
		intn = getIntent();
		group_name_clckd = intn.getStringExtra("name");
		channel_no = intn.getStringExtra("channel_no");
		creator_ID = intn.getStringExtra("creator_id");
		show_sub_topic = (EditText) findViewById(R.id.s_topic);
		show_sub_topic.setText("");
		imgView = (ImageView) findViewById(R.id.imported_img);

		int_stro = intn.getFloatExtra("stroke", 5f);
		if (intn.hasExtra("creator_id")) {
			creator_id = intn.getStringExtra("creator_id");
			Log.d("creator_id", "intent" + creator_id);
			Editor eddit = sprefs.edit();
			eddit.putString("creator_id", creator_id);
			eddit.commit();
		} else {
			creator_id = sprefs.getString("creator_id", "");
			Log.d("creator_id", "sprefs" + creator_id);
		}
		if (intn.hasExtra("topic_avail")) {
			Log.e("I", "Have" + intn.getStringExtra("topic_avail"));
			Resume_topic res_top = new Resume_topic();
			res_top.execute();
		} else {
			Log.e("nothing", "" + intn.getStringExtra("topic_avail"));
		}
		if (intn.hasExtra("start")) {
			start = intn.getStringExtra("start");
			Log.e("sStart", "" + start);
		} else {
			start = "3";
		}
		Log.e("sStart", "" + start);
		if (intn.hasExtra("path")) {
			imgView.setVisibility(9);
			path_fetched = intn.getStringExtra("path");
			Log.e("path_fetched1", "path_fetched" + path_fetched);
		} else {
			path_fetched = "";
		}

		Log.e("path_fetched2", "path_fetched" + path_fetched);
		if (intn.hasExtra("color")) {
			imgView.setVisibility(9);
			int_col = intn.getStringExtra("color");

		} else {
			imgView.setVisibility(9);
			int_col = "#000000";
		}

		Log.e("int_col", "int_col" + int_col + activity_active);

		// -----------------To check whether resume lesson is clicked or
		// noot-----------------//

		tts = new TextToSpeech(CaptureSignature.this, new OnInitListener() {

			@Override
			public void onInit(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == TextToSpeech.SUCCESS) {

					tts.setLanguage(Locale.getDefault());

				} else {
					tts = null;
					Toast.makeText(CaptureSignature.this,
							"Failed to initialize TTS engine.",

							Toast.LENGTH_SHORT).show();

				}
			}
		});

		controls = (LinearLayout) findViewById(R.id.controls);
		mExpand = (ImageButton) findViewById(R.id.expand_bttn);
		lNew = (RelativeLayout) findViewById(R.id.new_lay2);
		lClear = (RelativeLayout) findViewById(R.id.new_lay3);
		mNew = (ImageButton) findViewById(R.id.new_bttn2);
		back_tuition = (RelativeLayout) findViewById(R.id.new_lay2sd);
		llinked_bttn = (RelativeLayout) findViewById(R.id.new_lay4);
		lExpand = (RelativeLayout) findViewById(R.id.new_lay4e);
		lGetSign = (RelativeLayout) findViewById(R.id.new_lay5);
		preview_lay = (RelativeLayout) findViewById(R.id.preview_lay);
		mExpand.setTag("Expand");
		plus_bttn = (ImageView) findViewById(R.id.plus_bttn);
		post_note = (ImageButton) findViewById(R.id.post_note);
		channel1 = (TextView) findViewById(R.id.chnl1);
		channel2 = (TextView) findViewById(R.id.chnl2);
		channel3 = (TextView) findViewById(R.id.chnl3);
		send_play = (ImageView) findViewById(R.id.send_play);
		attach_btn = (ImageView) findViewById(R.id.attach_btn);
		parent_layout = (LinearLayout) findViewById(R.id.parent);
		send_layout = (RelativeLayout) findViewById(R.id.new_lay52);
		header_layout = (RelativeLayout) findViewById(R.id.view_videos);
		if (channel_no.equalsIgnoreCase("")) {
			channel1.setText("N");
			channel2.setText("A");
			channel3.setText("");
		} else {
			channel1.setText(channel_no.substring(0, 1));
			channel2.setText(channel_no.substring(1, 2));
			channel3.setText(channel_no.substring(2, 3));
		}

		preview_bttn = (ImageView) findViewById(R.id.preview_bttn);
		online = (ImageView) findViewById(R.id.online);
		options_bttn = (ImageButton) findViewById(R.id.new_bttn);
		linked_bttn = (ImageButton) findViewById(R.id.linked_bttn);
		options_bttn.setTag("up");
		AndroidBug5497Workaround.assistActivity(this);
		indexing_tv = (TextView) findViewById(R.id.index_numbering);
		// if (!next_index.equalsIgnoreCase("")) {
		// indexing_tv.setText("1." + (Integer.parseInt(next_index) + 1));
		// }
		comments = (EditText) findViewById(R.id.comments1);
		// edit_topic = (ImageButton) findViewById(R.id.edittopic);
		opt_wheel = (ImageButton) findViewById(R.id.options_wheel);
		sec_view = (LinearLayout) findViewById(R.id.sec_view);
		// topic = (EditText) findViewById(R.id.enter_topic);
		Log.i("Sub_type", "Sub_Type" + sprefs.getString("Sub_type", ""));
		hidden = (LinearLayout) findViewById(R.id.hidden_view);
		one_time = 1;
		Log.i("Reset", "Reset" + start);
		questions_list = (ListView) findViewById(R.id.questionaire);
		comments_list = new ArrayList<String>();
		Ids_list = new ArrayList<String>();
		names_list = new ArrayList<String>();

		if (sprefs.getString("Sub_type", "").equalsIgnoreCase("teacher")) {

		} else {
			repeat_req.setVisibility(9); // /Icon of repeat reqst hidden to show
											// live icon only----//
			preview_bttn.setVisibility(View.INVISIBLE);
			hidden.setVisibility(0);
			controls.setVisibility(9);
			fetch_last_images f_lim = new fetch_last_images();
			f_lim.execute();
		}
		back_tuition.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(comments.getWindowToken(), 0);
			}
		});

		plus_bttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i("Welcome", "Wedfjds");
				more_opt_dialog = new Dialog(CaptureSignature.this,
						android.R.style.Theme_Translucent_NoTitleBar);
				more_opt_dialog.setContentView(R.layout.more_options_list);
				more_opt_dialog.setCanceledOnTouchOutside(true);
				Window window = more_opt_dialog.getWindow();
				window.setLayout(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				window.setGravity(Gravity.CENTER);
				L1 = (ListView) more_opt_dialog
						.findViewById(R.id.options_listing);
				arr_adptr = new ArrayAdapter<String>(CaptureSignature.this,
						android.R.layout.simple_list_item_1, more_options);
				L1.setAdapter(arr_adptr);
				L1.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (arg2 == 2) {
							if (tts != null) {
								String text =

								comments.getText().toString();
								if (text != null) {
									if (!tts.isSpeaking()) {
										tts.speak(text,
												TextToSpeech.QUEUE_FLUSH, null);

									}

								}

							}
						}
					}
				});
				more_opt_dialog.show();
			}
		});

		// ----------------------Confirm deletion of prvious chapter
		// dialog------------//

		confirm_dial = new Dialog(CaptureSignature.this,
				android.R.style.Theme_Light_NoTitleBar);
		confirm_dial.setContentView(R.layout.end_chapter);
		WindowManager.LayoutParams wmlp11 = confirm_dial.getWindow()
				.getAttributes();
		wmlp11.gravity = Gravity.TOP | Gravity.LEFT;
		wmlp11.x = 100; // The new position of the X
						// coordinates
		wmlp11.y = 100; // The new position of the Y
						// coordinates
		wmlp11.width = wmlp11.MATCH_PARENT; // Width
		wmlp11.height = 500; // Height
		confirm_dial.getWindow().setAttributes(wmlp11);
		b1 = (Button) confirm_dial.findViewById(R.id.end_bttn);
		b2 = (Button) confirm_dial.findViewById(R.id.cnclb);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				confirm_dial.dismiss();
				end_chapter end = new end_chapter();
				end.execute();
				show_sub_topic.setText("");
				hidden.setVisibility(9);
				Intent intent = getIntent();
				finish();
				overridePendingTransition(0, 0);
				intent.putExtra("path", "");
				startActivity(intent);
				if (global.getChap_name().equalsIgnoreCase("")
						|| start.equalsIgnoreCase("1")) {
					show_sub_topic.setText("");
					topic_dialog.show();

				} else {

				}
			}
		});
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				confirm_dial.dismiss();
			}
		});
		send_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// send as speech
			}
		});

		// ------------------chapter and lesson dialog-----------------//

		topic_dialog = new Dialog(CaptureSignature.this,
				android.R.style.Theme_Light_NoTitleBar);
		topic_dialog.setContentView(R.layout.topic_dialog);
		WindowManager.LayoutParams wmlp1 = topic_dialog.getWindow()
				.getAttributes();
		wmlp1.gravity = Gravity.TOP | Gravity.LEFT;
		wmlp1.x = 100; // The new position of the X
						// coordinates
		wmlp1.y = 100; // The new position of the Y
						// coordinates
		wmlp1.width = wmlp1.MATCH_PARENT; // Width
		wmlp1.height = 500; // Height
		topic_dialog.getWindow().setAttributes(wmlp1);
		topic_Ed = (EditText) topic_dialog.findViewById(R.id.enter_topic);
		sub_topic_Ed = (EditText) topic_dialog
				.findViewById(R.id.enter_sub_topic);
		Button save_bttn = (Button) topic_dialog.findViewById(R.id.set_topic);
		Button cancel = (Button) topic_dialog.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				topic_dialog.dismiss();
			}
		});

		save_bttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (topic_Ed.getText().toString().equalsIgnoreCase("")
						|| sub_topic_Ed.getText().toString()
								.equalsIgnoreCase("")) {
					Toast.makeText(CaptureSignature.this,
							"Please enter complete details", 5).show();
				} else {
					topic_dialog.dismiss();
					chapter_name = topic_Ed.getText().toString();
					global.setChap_name(chapter_name);
					Log.i("Chapter_name", "Chapter_name" + chapter_name);
					topic = sub_topic_Ed.getText().toString();
					global.setTopic(topic);
					Log.i("Topic", "Topic" + topic.toString());
					show_sub_topic.setText(topic);
					topic1 = show_sub_topic.getText().toString();
					global.setTopic(topic1);
					Log.i("Topic ", "Stored" + global.getTopic());
					session = "1";
					global.setSess("1");
					show_sub_topic.setEnabled(false);
					topic_dialog.dismiss();
					Save_topic save_topic = new Save_topic();
					save_topic.execute();
				}
			}
		});

		repeat_req.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Check_rep_req chk_req = new Check_rep_req();
				chk_req.execute();
			}
		});

		opt_wheel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				options_list = new Dialog(CaptureSignature.this,
						android.R.style.Theme_Translucent_NoTitleBar);
				options_list.setContentView(R.layout.options_list_dialog);
				l1 = (ListView) options_list.findViewById(R.id.options_listing);
				if (sprefs.getString("Sub_type", "")
						.equalsIgnoreCase("teacher")) {
					arr_adptr = new ArrayAdapter<String>(CaptureSignature.this,
							android.R.layout.simple_list_item_1, opt_given);
					l1.setAdapter(arr_adptr);

					l1.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							choice = arg2;
							switch (choice) {
							case 0:
								options_list.dismiss();
								global.setLesson_resumed(1);
								Resume_topic r_topic = new Resume_topic();
								r_topic.execute();
								break;
							case 1:
								options_list.dismiss();
								global.setFold("1");
								startActivity(new Intent(CaptureSignature.this,
										All_chapters.class));
								break;
							case 2:
								options_list.dismiss();
								// -------------------Dialog to end previous
								// chapter-----------//
								Log.e("Value getchap saved", " v getchap saved"
										+ global.getChap_name());

								if (!global.getChap_name().equalsIgnoreCase("")) {
									confirm_dial.show();
								} else {
									if (global.getChap_name().equalsIgnoreCase(
											"")
											|| start.equalsIgnoreCase("1")) {

										topic_dialog.show();

									} else {

									}
								}

								global.setFold("1");
								Log.e("Value saved",
										" v saved" + global.getFold());
								break;

							case 3:
								options_list.dismiss();
								global.setFold("0");
								Log.e("Value saved",
										" v saved" + global.getFold());
								startActivity(new Intent(CaptureSignature.this,
										All_chapters.class));
								break;
							case 5:
								options_list.dismiss();
								Log.e("Value saved", " 5 saved");
								Intent galleryIntent = new Intent(
										Intent.ACTION_PICK,
										android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
								// Start the Intent
								startActivityForResult(galleryIntent,
										RESULT_LOAD_IMG);
								break;

							case 6:
								if (opt_given[6]
										.equalsIgnoreCase("Unlock Whiteboard")) {
									opt_given[6] = "Lock Whiteboard";
									// ---------------Unlock wb------------//
									l1.setAdapter(arr_adptr);
									options_list.dismiss();

								} else {
									opt_given[0] = "Unlock Whiteboard";
									options_list.dismiss();
								}
								break;
							case 7:
								if (opt_given[7]
										.equalsIgnoreCase("Unlock Text")) {
									opt_given[7] = "Lock Text";
									options_list.dismiss();
								} else {
									opt_given[7] = "Unlock Text";
									options_list.dismiss();
								}
								break;
							}
							/*
							 * if (arg2 == 1) { opt_given[1] = "Lock Text"; }
							 * else { // Unlock whiteboard code }
							 */
						}
					});

				} else {
					arr_adptr = new ArrayAdapter<String>(CaptureSignature.this,
							android.R.layout.simple_list_item_1, opt_stu);
					l1.setAdapter(arr_adptr);

					l1.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							choice = arg2;
							switch (choice) {
							case 0:
								options_list.dismiss();
								global.setFold("1");
								startActivity(new Intent(CaptureSignature.this,
										All_chapters.class));
								break;
							}
						}
					});

				}

				options_list.show();
			}
		});
		if (global.getTopic().equalsIgnoreCase("")) {

		} else {
			String trim_topic = global.getTopic();
			if (trim_topic.contains(".")) {
				int i = trim_topic.indexOf(".");
				show_sub_topic.setText(trim_topic.substring(0, i));
			} else {
				show_sub_topic.setText(trim_topic);
			}
		}
		if (global.getSelected_chapter().equalsIgnoreCase("")) {

		} else {
			check_chapter = 1;
		}
		Group_name = (TextView) findViewById(R.id.view_uploaded_videos);
		Group_name.setText(group_name_clckd);

		lesson_detail = (Spinner) findViewById(R.id.lesson_detail);
		sprefs = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		Preview_mode_text = (TextView) findViewById(R.id.indicator);

		// -------------------Fetch lesson detail---------------//
		// Fetch_lesson_detail fld = new Fetch_lesson_detail();
		// fld.execute();

		mCurlView = (CurlView) findViewById(R.id.curl);

		// ---------------------------------------------------------------//
		dmetrix = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dmetrix);
		Dheight = dmetrix.heightPixels;

		tempDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
				+ "/" + getResources().getString(R.string.external_dir) + "/";
		Log.i("Tempdir", "Tempdir" + tempDir);
		ContextWrapper cw = new ContextWrapper(getApplicationContext());
		File directory = cw.getDir(
				getResources().getString(R.string.external_dir),
				Context.MODE_PRIVATE);
		directory.mkdir();
		// ------------------------------------------------------------//

		SharedPreferences preferences = getSharedPreferences("mprefs",
				Context.MODE_PRIVATE);
		user_id = preferences.getString("id", "");
		// prepareDirectory();
		uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_"
				+ Math.random();
		current = uniqueId + ".png";
		mypath = new File(directory, current);
		// settings = (ImageButton) findViewById(R.id.settings_bttn);

		// ---------------Whiteboard------------------------//
		create_whiteboard(int_stro, int_col);

		sec_view = (LinearLayout) findViewById(R.id.sec_view);
		// topic = (EditText) findViewById(R.id.enter_topic);
		hidden = (LinearLayout) findViewById(R.id.hidden_view);
		// mCancel = (Button) findViewById(R.id.cancel);
		mView = mContent;

		post_note.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Questions quest = new Questions();
				quest.execute();
			}
		});

		options_bttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("tag", "settag" + options_bttn.getTag().toString());
				if (options_bttn.getTag().toString().equalsIgnoreCase("up")) {
					options_bttn.setTag("down");
					options_bttn.setImageResource(R.drawable.a5);
				} else {
					options_bttn.setImageResource(R.drawable.a55);
					options_bttn.setTag("up");
				}
				features_dialog = new Dialog(CaptureSignature.this,
						android.R.style.Theme_Translucent_NoTitleBar);
				features_dialog.setContentView(R.layout.dialog_features);
				features_dialog.setCanceledOnTouchOutside(true);
				Window window = features_dialog.getWindow();
				window.setLayout(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				window.setGravity(Gravity.LEFT);
				RelativeLayout p1 = (RelativeLayout) features_dialog
						.findViewById(R.id.pencil_lay1);
				RelativeLayout p2 = (RelativeLayout) features_dialog
						.findViewById(R.id.pencil_lay2);
				RelativeLayout p3 = (RelativeLayout) features_dialog
						.findViewById(R.id.pencil_lay3);
				ImageView p_1 = (ImageView) features_dialog
						.findViewById(R.id.p_1);
				ImageView p_2 = (ImageView) features_dialog
						.findViewById(R.id.p_2);
				ImageView p_3 = (ImageView) features_dialog
						.findViewById(R.id.p_3);
				ImageView p_4 = (ImageView) features_dialog
						.findViewById(R.id.p_4);
				ImageView p_5 = (ImageView) features_dialog
						.findViewById(R.id.p_5);
				ImageView p_6 = (ImageView) features_dialog
						.findViewById(R.id.p_6);
				RelativeLayout e1 = (RelativeLayout) features_dialog
						.findViewById(R.id.e1);
				RelativeLayout e2 = (RelativeLayout) features_dialog
						.findViewById(R.id.e2);
				RelativeLayout e3 = (RelativeLayout) features_dialog
						.findViewById(R.id.e3);
				p1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						stroke = 2f;
						// Bundle bundl1=savedInstanceState;
						mSignature.save(mView);
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("stroke", stroke));
						// create_whiteboard(stroke);
						features_dialog.dismiss();
					}
				});
				p2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						stroke = 6f;
						mSignature.save(mView);
						// Bundle bundl1=savedInstanceState;
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("stroke", stroke));
						features_dialog.dismiss();
					}
				});
				p3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						stroke = 12f;
						mSignature.save(mView);
						// Bundle bundl1=savedInstanceState;
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("stroke", stroke));
						features_dialog.dismiss();
					}
				});

				p_1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						color = "#000000";
						mSignature.save(mView);
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						aa = "bb";
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});
				p_2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						color = "#2195BE";
						mSignature.save(mView);
						Log.e("finished", "finished");
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});
				p_3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						color = "#FCE80F";
						mSignature.save(mView);
						Log.e("finished", "finished");
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});
				p_4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						color = "#DE2523";
						mSignature.save(mView);
						Log.e("finished", "finished");
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});
				p_5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						color = "#199213";
						mSignature.save(mView);
						Log.e("finished", "finished");
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});
				p_6.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						color = "#C72266";
						mSignature.save(mView);
						Log.e("finished", "finished");
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});
				e1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						color = "#ffffff";
						mSignature.save(mView);
						Log.e("finished", "finished");
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						intent.putExtra("stroke", 10f);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});
				e2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						color = "#ffffff";
						mSignature.save(mView);
						Log.e("finished", "finished");
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						intent.putExtra("stroke", 15f);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});
				e3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						color = "#ffffff";
						mSignature.save(mView);
						Log.e("finished", "finished");
						Intent intent = getIntent();
						finish();
						overridePendingTransition(0, 0);
						intent.putExtra("path", fpath);
						intent.putExtra("stroke", 25f);
						startActivity(intent.putExtra("color", color));
						features_dialog.dismiss();
					}
				});

				features_dialog.show();
			}
		});
		llinked_bttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (link_status == true) {
					link_status = false;
					linked_bttn.setImageResource(R.drawable.i3_o);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							linked_bttn.setImageResource(R.drawable.i3);
						}
					}, 2000);
				} else {
					link_status = true;
					linked_bttn.setImageResource(R.drawable.c4_o);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							linked_bttn.setImageResource(R.drawable.c4);
						}
					}, 2000);
				}
			}
		});
		lExpand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mExpand.getTag().toString().equalsIgnoreCase("Expand")) {
					sec_view.setVisibility(9);

					mExpand.setImageResource(R.drawable.chunjaan_o);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mExpand.setImageResource(R.drawable.chunjaan);
						}
					}, 2000);
					mContent.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
					Log.e("Dheight", "Dht" + Dheight / 4);
					Picasso.with(CaptureSignature.this).load(selectedImage)
							.resize(200, 200).into(imgView);
					mExpand.setTag("Collapse");
				} else {
					sec_view.setVisibility(0);
					if (img_path.size() == 0) {
						mCurlView.setPageProvider(new PageProvider(
								CaptureSignature.this, img_path));
						mCurlView
								.setSizeChangedObserver(new SizeChangedObserver());
						Log.e("Index", "iinnddeexx" + index);
						// mCurlView.setCurrentIndex(0);
						mCurlView.setBackgroundColor(0xFF202830);
					} else {

					}
					mExpand.setImageResource(R.drawable.i5_o);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mExpand.setImageResource(R.drawable.i5);
						}
					}, 2000);
					mContent.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, Dheight / 2));
					mExpand.setTag("Expand");
					Picasso.with(CaptureSignature.this).load(selectedImage)
							.into(imgView);
				}

			}
		});
		lNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.v("log_tag", "Panel Cleared");
				Preview_mode_text.setVisibility(9);
				mNew.setImageResource(R.drawable.i1_o);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mNew.setImageResource(R.drawable.i1);
					}
				}, 3500);

				hidden.setVisibility(9);
				Intent intent = getIntent();
				finish();
				overridePendingTransition(0, 0);
				intent.putExtra("path", "");
				startActivity(intent);
				// mSignature.clear();
				preview_bttn.setVisibility(0);
				mGetSign.setEnabled(false);
				indexing_tv.setText("0");

			}
		});

		// ---------------Keyboard active or not----------------//

		parent_layout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {

						Rect r = new Rect();
						parent_layout.getWindowVisibleDisplayFrame(r);
						int screenHeight = parent_layout.getRootView()
								.getHeight();

						// r.bottom is the position above soft keypad or device
						// button.
						// if keypad is shown, the r.bottom is smaller than that
						// before.
						keypadHeight = screenHeight - r.bottom;

						Log.d("Kheight", "keypadHeight = " + keypadHeight);

						if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio
							// keyboard is opened
							preview_lay.setVisibility(9);
							send_play.setVisibility(0);
							attach_btn.setVisibility(0);
							plus_bttn.setVisibility(0);
							controls2.setVisibility(0);
							mContent.setVisibility(9);
							header_layout.setVisibility(9);
						} else {
							// keyboard is closed
							preview_lay.setVisibility(0);
							controls2.setVisibility(9);
							send_play.setVisibility(9);
							attach_btn.setVisibility(9);
							plus_bttn.setVisibility(9);
							mContent.setVisibility(0);
							header_layout.setVisibility(0);
						}
					}
				});

		send_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (show_sub_topic.getText().toString().equalsIgnoreCase("")) {

				} else if (comments.getText().toString().equalsIgnoreCase("")) {
				} else {
					Questions q1 = new Questions();
					q1.execute();
				}

			}
		});

		preview_lay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				preview_bttn.setVisibility(View.INVISIBLE);
				Preview_mode_text.setVisibility(0);
				hidden.setVisibility(0);
				imgView.setVisibility(9);
				if (!show_sub_topic.getText().toString().equalsIgnoreCase("")) {
					last_images l_im = new last_images();
					l_im.execute();
				} else {

					previewlay_clckd = "1";
					fetch_last_images f_il = new fetch_last_images();
					f_il.execute();
				}
				System.out.println("preview_clicked" + previewlay_clckd);
			}
		});
		lClear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("log_tag", "Panel Cleared"
						+ show_sub_topic.getText().toString());
				Preview_mode_text.setVisibility(9);
				mClear.setImageResource(R.drawable.i1_o);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mClear.setImageResource(R.drawable.i1);
					}
				}, 1500);
				hidden.setVisibility(9);
				Intent intent = getIntent();
				finish();
				overridePendingTransition(0, 0);
				intent.putExtra("path", "");
				if (!show_sub_topic.getText().toString().equalsIgnoreCase("")) {
					intent.putExtra("topic_avail", show_sub_topic.getText()
							.toString());
				}
				startActivity(intent);
				// mSignature.clear();
				preview_bttn.setVisibility(0);
				mGetSign.setEnabled(false);
				reset = 1;
				// indexing_tv.setText("0");
			}
		});

		lGetSign.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("log_tag", "Panel Saved");
				if (show_sub_topic.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(CaptureSignature.this, "Enter lesson name",
							5).show();
				} else {

					mGetSign.setImageResource(R.drawable.send_refined_o);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mGetSign.setImageResource(R.drawable.send_refined);
						}
					}, 2500);

					mSignature.save(mView);
					controls.setVisibility(0);
					Bundle b = new Bundle();
					b.putString("status", "done");
					Intent intent = new Intent();
					intent.putExtras(b);
					setResult(RESULT_OK, intent);
					UploadFileToServer upload_file = new UploadFileToServer();
					upload_file.execute();
				}

			}
		});

	}

	public class end_chapter extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ResponseHandler<String> res_handler = new BasicResponseHandler();
			HttpClient httpclient = new DefaultHttpClient();
			ResponseHandler<String> respo = new BasicResponseHandler();
			HttpPost httppost = new HttpPost(Close_chapter_url);
			List<NameValuePair> inputs = new ArrayList<NameValuePair>();
			inputs.add(new BasicNameValuePair("chapter_name", global
					.getChap_name()));
			Log.e("INPTS", "INPUTS" + inputs);

			try {
				httppost.setEntity(new UrlEncodedFormEntity(inputs));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				end_chapter = httpclient.execute(httppost, respo);
				Log.i("output", "output" + end_chapter);
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
			// show_sub_topic.setText(topic);
			global.setTopic("");
			global.setChap_name("");
			start = "1";

		}
	}

	public class Check_rep_req extends AsyncTask<Void, Void, String> {
		ProgressDialog pdial;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdial = ProgressDialog.show(CaptureSignature.this, "",
					"Sending request..");
		}

		@Override
		protected String doInBackground(Void... params) {
			Check_rep_req();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdial.dismiss();
			Toast.makeText(CaptureSignature.this, "Doooneee", 6).show();
		}
	}

	public void Check_rep_req() { // TODO Auto-generated method stub
		String server_response = "";
		HttpClient htcp = new DefaultHttpClient();
		ResponseHandler<String> resp_hndl = new BasicResponseHandler();
		HttpPost post_mthd = new HttpPost(Global_Constants.BASE_URL
				+ "get_repeat_req.php");
		List<NameValuePair> linp = new ArrayList<NameValuePair>();
		linp.add(new BasicNameValuePair("chapter_name", chptr_retrnd));
		linp.add(new BasicNameValuePair("topic", topic_retrnd));
		Log.e("LAST IMAGE", "LIM" + linp);

		try {
			post_mthd.setEntity(new UrlEncodedFormEntity(linp));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			server_response = htcp.execute(post_mthd, resp_hndl);
			JSONObject job1 = new JSONObject();
			try {
				job1 = new JSONObject(server_response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				JSONObject job2 = job1.getJSONObject("response");
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

		// ----------------------Rest to do---------------------//

	}

	public class Save_topic extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			DefaultHttpClient dhcp = new DefaultHttpClient();
			HttpPost postmthd = new HttpPost(save_topic_url);
			ResponseHandler<String> resp_hndlr = new BasicResponseHandler();
			List<NameValuePair> linputs = new ArrayList<NameValuePair>();
			linputs.add(new BasicNameValuePair("chapter_name", global
					.getChap_name()));
			linputs.add(new BasicNameValuePair("topic", show_sub_topic
					.getText().toString()));
			linputs.add(new BasicNameValuePair("group_id", global.getGroup_id()));
			linputs.add(new BasicNameValuePair("user_id", user_id));
			Log.i("LINPUTS", "LINPUTS_FLfD" + linputs.toString());
			try {
				postmthd.setEntity(new UrlEncodedFormEntity(linputs));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rresponse = dhcp.execute(postmthd, resp_hndlr);
				Log.i("RR", "Response received" + rresponse);
				try {
					JSONObject job1 = new JSONObject(rresponse);
					JSONObject job2 = job1.getJSONObject("data");
					SstatuS = job2.getString("status");
					if (SstatuS.equalsIgnoreCase("true")) {
						global.lesson_resumed = 0;
						c_id = job2.getString("c_id");
						topic_recd = job2.getString("topic");
						global.setTopic_recd(topic_recd);
						Log.e("C_ID", "Chapter_id" + topic_recd + c_id);
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

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			topic_dialog.dismiss();

			int u = global.getTopic_recd().indexOf(".");

			Editor edtt = sprefs.edit();
			edtt.putString("topic_recd", global.getTopic_recd().toString()
					.substring(0, u));
			edtt.putString("topic_uncut", global.getTopic_recd().toString());
			edtt.commit();
			/*
			 * if (SstatuS.equalsIgnoreCase("true")) {
			 * Toast.makeText(CaptureSignature.this, "Done", 5).show(); } else {
			 * 
			 * }
			 */
		}

	}

	private void create_whiteboard(float size, String col) {
		// TODO Auto-generated method stub

		mContent = (LinearLayout) findViewById(R.id.board);
		mContent.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, Dheight / 2));
		mSignature = new signature(this, null, size, col);
		mSignature.setBackgroundColor(Color.WHITE);
		Log.i("STRK", "STROKE1" + size);
		Log.i("Path", "path_fetched" + path_fetched);
		if (!path_fetched.equalsIgnoreCase("")) {
			File file = new File(path_fetched);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			Bitmap bmp11 = BitmapFactory.decodeFile(file.getAbsolutePath(),
					options);
			Log.i("BMP11else", "bmp11" + file.getAbsolutePath());
			BitmapDrawable background = new BitmapDrawable(bmp11);
			mSignature.setBackgroundDrawable(background);
		} else {
			mSignature.setBackgroundColor(Color.WHITE);
		}

		mContent.addView(mSignature, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mClear = (ImageButton) findViewById(R.id.clear_bttn);
		mGetSign = (ImageButton) findViewById(R.id.send_bttn);
		mGetSign.setEnabled(false);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		activity_active = false;
	}

	@Override
	protected void onDestroy() {
		Log.w("GetSignature", "onDestroy");
		if (tts != null) {

			tts.stop();

			tts.shutdown();

		}
		super.onDestroy();
	}

	private String getTodaysDate() {

		final Calendar c = Calendar.getInstance();
		int todaysDate = (c.get(Calendar.YEAR) * 10000)
				+ ((c.get(Calendar.MONTH) + 1) * 100)
				+ (c.get(Calendar.DAY_OF_MONTH));
		Log.w("DATE:", String.valueOf(todaysDate));
		return (String.valueOf(todaysDate));

	}

	private String getCurrentTime() {

		final Calendar c = Calendar.getInstance();
		int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000)
				+ (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
		Log.w("TIME:", String.valueOf(currentTime));
		return (String.valueOf(currentTime));

	}

	private boolean prepareDirectory() {
		try {
			if (makedirs()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(
					this,
					"Could not initiate File System.. Is Sdcard mounted properly?",
					1000).show();
			return false;
		}
	}

	private boolean makedirs() {
		File tempdir = new File(tempDir);
		if (!tempdir.exists())
			tempdir.mkdirs();

		if (tempdir.isDirectory()) {
			File[] files = tempdir.listFiles();
			for (File file : files) {
				if (!file.delete()) {
					System.out.println("Failed to delete " + file);
				}
			}
		}
		return (tempdir.isDirectory());
	}

	public class signature extends View {
		private static final float STROKE_WIDTH = 5f;
		private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		private Paint paint = new Paint();
		private Path path = new Path();

		private float lastTouchX;
		private float lastTouchY;
		private final RectF dirtyRect = new RectF();

		public signature(Context context, AttributeSet attrs, float stroke,
				String color) {
			super(context, attrs);
			paint.setAntiAlias(true);
			if (color.equalsIgnoreCase("")) {
				color = "#000000";
				paint.setColor(Color.parseColor(color));
			} else {
				paint.setColor(Color.parseColor(color));
			}
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			Log.e("SW", "Stroke width" + stroke);
			paint.setStrokeWidth(stroke);
		}

		public void save(View v) {
			Log.v("log_tag", "Width: " + v.getWidth());
			Log.v("log_tag", "Height: " + v.getHeight());
			Log.v("log_tag", "Height: " + mContent.getHeight());
			controls.setVisibility(View.INVISIBLE);
			// if (mBitmap == null) {
			mBitmap = Bitmap.createBitmap(mContent.getWidth(),
					mContent.getHeight(), Bitmap.Config.ARGB_8888);
			Log.v("Bitmap", "Height: " + mBitmap.getHeight());
			// }
			Canvas canvas = new Canvas(mBitmap);
			// canvas.drawColor(0, Mode.CLEAR);
			try {
				FileOutputStream mFileOutStream = new FileOutputStream(mypath);
				boutstream = new ByteArrayOutputStream();
				v.draw(canvas);
				mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
				mBitmap.compress(Bitmap.CompressFormat.PNG, 90, boutstream);
				mFileOutStream.flush();
				mFileOutStream.close();
				url = Images.Media.insertImage(getContentResolver(), mBitmap,
						"title", null);
				file_uri = Uri.parse(url);
				fpath = getRealPathFromURI(CaptureSignature.this, file_uri);
				Log.i("FPaths", "FP" + fpath);
				byte_img = executeMultipartPost(fpath);
				// Toast.makeText(CaptureSignature.this,
				// "Saved successfully",
				// 5).show();
				Log.v("log_tag", "url: " + url);
				global.setBitmap_path(fpath);
				// controls.setVisibility(0);
				// In case you want to delete the file
				// boolean deleted = mypath.delete();
				// Log.v("log_tag","deleted: " + mypath.toString() +
				// deleted);
				// If you want to convert the image to string use base64
				// converter

			} catch (Exception e) {
				Log.v("log_tag", e.toString());
			}
			// }
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

		public void clear() {
			path.reset();
			invalidate();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawPath(path, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX();
			float eventY = event.getY();
			mGetSign.setEnabled(true);
			Log.i("Scroll", "Controlling" + controls.getVisibility());
			/*
			 * if (scroll_active == 1) { Log.i("Scroll", "Controlling"); return
			 * false; } else { Log.i("Scroll", "failed");
			 */switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.i("ACTION_DOWN", "ACTION_DOWN" + controls.getVisibility());
				path.moveTo(eventX, eventY);
				lastTouchX = eventX;
				lastTouchY = eventY;
				return true;

			case MotionEvent.ACTION_MOVE:
				Log.i("ACTION_MOVE", "ACTION_MOVE" + controls.getVisibility());

			case MotionEvent.ACTION_UP:
				Log.i("ACTION_UP", "ACTION_UP" + controls.getVisibility());
				resetDirtyRect(eventX, eventY);
				int historySize = event.getHistorySize();
				for (int i = 0; i < historySize; i++) {
					float historicalX = event.getHistoricalX(i);
					float historicalY = event.getHistoricalY(i);
					expandDirtyRect(historicalX, historicalY);
					path.lineTo(historicalX, historicalY);
				}
				path.lineTo(eventX, eventY);
				break;

			default:
				debug("Ignored touch event: " + event.toString());
				return false;
			}
			invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
					(int) (dirtyRect.top - HALF_STROKE_WIDTH),
					(int) (dirtyRect.right + HALF_STROKE_WIDTH),
					(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

			lastTouchX = eventX;
			lastTouchY = eventY;
			return true;
			// }
		}

		private void debug(String string) {
		}

		private void expandDirtyRect(float historicalX, float historicalY) {
			if (historicalX < dirtyRect.left) {
				dirtyRect.left = historicalX;
			} else if (historicalX > dirtyRect.right) {
				dirtyRect.right = historicalX;
			}

			if (historicalY < dirtyRect.top) {
				dirtyRect.top = historicalY;
			} else if (historicalY > dirtyRect.bottom) {
				dirtyRect.bottom = historicalY;
			}
		}

		private void resetDirtyRect(float eventX, float eventY) {
			dirtyRect.left = Math.min(lastTouchX, eventX);
			dirtyRect.right = Math.max(lastTouchX, eventX);
			dirtyRect.top = Math.min(lastTouchY, eventY);
			dirtyRect.bottom = Math.max(lastTouchY, eventY);
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

	// -------------get bitmap--------------------//
	public static Bitmap getBitmapFromURL(String src) {
		String new_src = "";
		try {
			// if(src.contains(" ")){
			// new_src=src.replace(" ", "%20");
			// }
			Log.e("Source123", "src " + src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			Log.e("myBitmap", "myBitmap " + myBitmap);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// ----------------------For student fetch last
	// details-----------------------//
	public class fetch_last_images extends AsyncTask<Void, Void, String> {
		// ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// pd = ProgressDialog.show(CaptureSignature.this, "",
			// "Updating...");
			f_img_path.clear();
			f_Iindex.clear();
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			fetch_last_images();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// pd.dismiss();
			if (status_l.equalsIgnoreCase("true")) {
				if (topic_retrnd.contains(".")) {
					int ip = topic_retrnd.indexOf(".");
					show_sub_topic.setText(topic_retrnd.substring(0, ip));
				} else {

					show_sub_topic.setText(topic_retrnd);
				}

				if (f_Iindex.size() == 0) {
					indexing_tv.setText("0");
				} else {
					indexing_tv.setText(f_Iindex.get(0));
					Log.e("Index", "iinnddeexx" + f_Iindex.get(0).substring(2));
				}
				mCurlView.setPageProvider(new PageProvider(
						CaptureSignature.this, f_img_path));
				mCurlView.setSizeChangedObserver(new SizeChangedObserver());

				mCurlView.setBackgroundColor(0xFF202830);

				// if (one_time == 1) {
				// one_time = 2;
				// } else {
				Log.i("Fes yetch", "Yes fetch");
				Fetch_comments f_commnts = new Fetch_comments();// ---------------To
																// check adapter
																// setting-------------//
				f_commnts.execute();
				// new Handler().postDelayed(new Runnable() {
				//
				// @Override
				// public void run() { // TODO Auto-generated method stub
				// fetch_last_images f_lim = new fetch_last_images();
				// f_lim.execute();
				// }
				// }, 4000);

				// }
			} else {

			}

		}

	}

	public String fetch_last_images() {
		String server_response = "";
		HttpClient htcp = new DefaultHttpClient();
		ResponseHandler<String> resp_hndl = new BasicResponseHandler();
		HttpPost post_mthd = new HttpPost(Global_Constants.BASE_URL
				+ "get_last_data.php");
		List<NameValuePair> linp = new ArrayList<NameValuePair>();
		linp.add(new BasicNameValuePair("group_id", global.getGroup_id()));
		Log.e("LAST IMAGEs", "LIM" + linp);

		try {
			post_mthd.setEntity(new UrlEncodedFormEntity(linp));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			server_response = htcp.execute(post_mthd, resp_hndl);
			try {
				JSONObject job1 = new JSONObject(server_response);
				Log.i("Job1", "Job1" + job1.toString());
				JSONObject job2 = job1.getJSONObject("data");
				status_l = job2.getString("status");
				JSONArray img_arr = new JSONArray();
				JSONArray index_arr = new JSONArray();
				if (status_l.equalsIgnoreCase("true")) {

					try {
						img_arr = job2.getJSONArray("image");
						index_arr = job2.getJSONArray("index");
						for (int i = 0; i < img_arr.length(); i++) {
							f_img_path.add(img_arr.getString(i));
							f_Iindex.add(index_arr.getString(i));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					JSONArray topic_retd = job2.getJSONArray("topic");
					JSONArray chptr_retrrnd = job2.getJSONArray("cname");
					topic_retrnd = topic_retd.getString(0);
					global.setTopic_recd(topic_retrnd); // ----------------Topic_recd
														// global var
														// overwritten
					chptr_retrnd = chptr_retrrnd.getString(0);
					global.setChap_name(chptr_retrnd); // ----------------chap_name
														// global var
														// overwritten
					Log.i("topic", "returnd" + chptr_retrnd + topic_retrnd);

				} else {

				}
				Log.e("Fis", "Fis" + f_Iindex.size());
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
		Log.e("IMAGES", "ffIMAGES FETCHED" + f_img_path);
		Log.e("INDEXES", "ffINDEXES FETCHED" + f_Iindex);
		return current;

	}

	// Getting last images
	public class last_images extends AsyncTask<Void, Void, String> {
		// ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			img_path.clear();
			// Iindex.clear();
			// pd = ProgressDialog.show(CaptureSignature.this, "",
			// "Updating...");
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			last_images();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (status_l.equalsIgnoreCase("true")) {
				if (Iindex.size() == 0) {
					indexing_tv.setText("0");
				} else {
					indexing_tv.setText(Iindex.get(0));
					Log.e("Index", "last imgs" + Iindex.get(0).substring(2));
				}

				mCurlView.setPageProvider(new PageProvider(
						CaptureSignature.this, img_path));
				mCurlView.setSizeChangedObserver(new SizeChangedObserver());
				Log.e("Index", "iinnddeexx" + index);
				// mCurlView.setCurrentIndex(0);
				mCurlView.setBackgroundColor(0xFF202830);
				Toast.makeText(CaptureSignature.this, "Yes get", 5).show();
				Fetch_comments f_commnts = new Fetch_comments();
				f_commnts.execute();
			} else {
				Toast.makeText(CaptureSignature.this, "No data available", 5)
						.show();
			}

		}

	}

	public String last_images() {
		String server_response = "";
		HttpClient htcp = new DefaultHttpClient();
		ResponseHandler<String> resp_hndl = new BasicResponseHandler();
		HttpPost post_mthd = new HttpPost(Global_Constants.BASE_URL
				+ "get_group_image.php");
		List<NameValuePair> linp = new ArrayList<NameValuePair>();
		linp.add(new BasicNameValuePair("group_id", global.getGroup_id()));
		linp.add(new BasicNameValuePair("topic", global.getTopic())); // topic
		Log.e("LAST IMAGE", "LIM" + linp);

		try {
			post_mthd.setEntity(new UrlEncodedFormEntity(linp));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			server_response = htcp.execute(post_mthd, resp_hndl);
			Log.i("Response", "Responseoip" + server_response);
			try {
				JSONObject job1 = new JSONObject(server_response);
				JSONObject job2 = job1.getJSONObject("response");
				JSONArray sjob = job2.getJSONArray("status");
				JSONArray jarr2 = job2.getJSONArray("index");
				status_l = sjob.getString(0);
				if (status_l.equalsIgnoreCase("true")) {
					JSONArray img_arr = job2.getJSONArray("image");
					for (int i = 0; i < img_arr.length(); i++) {
						img_path.add(img_arr.getString(i));
						Iindex.add(jarr2.getString(i));

					}
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
		Log.e("IMAGES", "IMAGES FETCHED" + img_path);
		Log.e("INDEXES", "INDEXES FETCHED" + Iindex);
		return current;

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

				Toast.makeText(CaptureSignature.this, "Successfully posted", 5)
						.show();
				comments.setText("");
				comments.clearFocus();
				// mContent.setVisibility(0);
				// InputMethodManager imm = (InputMethodManager)
				// getSystemService(Context.INPUT_METHOD_SERVICE);
				// imm.hideSoftInputFromWindow(comments.getWindowToken(), 0);
				// -------to refresh contents when msg posted
				// first-------------------//
				if (posted_msg == 1) {
					posted_msg = 2;
					Fetch_comments f_cmm = new Fetch_comments();
					f_cmm.execute();

				}

				// --------------------------------------------------------------------//
			} else {
				Toast.makeText(CaptureSignature.this,
						"Failed in posting....Try again!!", 5).show();
			}

		}

	}

	protected Void questions() {
		HttpClient htcp = new DefaultHttpClient();
		HttpPost post = new HttpPost(faqs_url);
		String Repos = "";
		ResponseHandler<String> response = new BasicResponseHandler();
		List<NameValuePair> listed = new ArrayList<NameValuePair>();
		listed.add(new BasicNameValuePair("user_id", user_id));
		listed.add(new BasicNameValuePair("group_id", global.getGroup_id()));
		if (sprefs.getString("Sub_type", "").equalsIgnoreCase("teacher")) {
			if (global.getLesson_resumed() == 1) {
				listed.add(new BasicNameValuePair("topic", global.getTopic()));
			} else {
				listed.add(new BasicNameValuePair("topic", global
						.getTopic_recd()));
			}

		} else {
			listed.add(new BasicNameValuePair("topic", topic_retrnd));
		}
		// ----------------changed---------------//
		if (!indexing_tv.getText().toString().equalsIgnoreCase("0")
				&& link_status == true) {
			listed.add(new BasicNameValuePair("index", indexing_tv.getText()
					.toString()));
		} else {
			listed.add(new BasicNameValuePair("index", ""));
		}
		listed.add(new BasicNameValuePair("comment", comments.getText()
				.toString()));
		Log.e("Listed", "Listed inpps" + listed);
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

		// ProgressDialog pdial;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			// pdial=ProgressDialog.show(CaptureSignature.this, "",
			// "Loading comments for this slide...");

		}

		@Override
		protected String doInBackground(Void... params) {
			fetch_comments();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// pdial.dismiss();
			if (status_op.equalsIgnoreCase("true")) {
				Log.e("log", "comments_list" + comments_list.size());
				cadap = new Comments_adapter(CaptureSignature.this,
						comments_list, Ids_list, names_list, indx);
				questions_list.setAdapter(cadap);
				cadap.notifyDataSetChanged();
				if (comments_list.size() > 0) {
					Helper.getListViewSize(questions_list);
				} else {

				}
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Fetch_comments c_cm = new Fetch_comments();
						c_cm.execute();
					}
				}, 5000);
			} else {

			}

		}

	}

	protected Void fetch_comments() {
		if (comments_list.size() > 0) {
			Log.i("I am", "IN");
			comments_list = new ArrayList<String>();
			Ids_list = new ArrayList<String>();
			names_list = new ArrayList<String>();
		}

		DefaultHttpClient htcp = new DefaultHttpClient();
		HttpPost post = new HttpPost(fetch_comments_url);
		// comments_list = new ArrayList<String>();
		// Ids_list = new ArrayList<String>();
		// names_list = new ArrayList<String>();
		ResponseHandler<String> responser = new BasicResponseHandler();
		List<NameValuePair> Input_list = new ArrayList<NameValuePair>();
		Input_list.add(new BasicNameValuePair("user_id", user_id));
		Input_list
				.add(new BasicNameValuePair("group_id", global.getGroup_id()));
		if (!indexing_tv.getText().toString().equalsIgnoreCase("")
				&& link_status == true) {
			Input_list.add(new BasicNameValuePair("index", indexing_tv
					.getText().toString()));
		} else {
			Input_list.add(new BasicNameValuePair("index", ""));
		}
		if (sprefs.getString("Sub_type", "").equalsIgnoreCase("teacher")) {
			if (global.getTopic().equalsIgnoreCase("")) {
				Input_list.add(new BasicNameValuePair("topic", global
						.getTopic_recd()));
			} else {
				Input_list.add(new BasicNameValuePair("topic", global
						.getTopic()));
			}
		} else {
			Input_list.add(new BasicNameValuePair("topic", topic_retrnd));
		}

		Log.e("Inp", "Input List" + Input_list.toString());
		try {
			post.setEntity(new UrlEncodedFormEntity(Input_list));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Response_fetched = htcp.execute(post, responser);

			try {
				JSONObject RJob = new JSONObject(Response_fetched);
				JSONObject job1 = RJob.getJSONObject("response");
				Log.i("respons", "response fetched" + job1.toString());
				JSONArray stt1 = job1.getJSONArray("status");
				status_op = stt1.get(0).toString();

				if (status_op.equalsIgnoreCase("true")) {

					JSONArray ids = job1.getJSONArray("sender");
					Log.i("Idddd", "dddds" + ids.toString());
					JSONArray name = job1.getJSONArray("name");
					JSONArray cmmnts = job1.getJSONArray("comments");
					JSONArray indexx = job1.getJSONArray("index");
					indx = indexx.get(0).toString();
					for (int i = 0; i < cmmnts.length(); i++) {
						comments_list.add(cmmnts.getString(i));
						names_list.add(name.getString(i));
						Ids_list.add(ids.getString(i));
					}
				} else {

				}
				Log.i("Idsdsdddd", "dsdsdddds" + comments_list.toString());
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

	/**
	 * Uploading the file to server
	 * */
	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			// progressBar.setProgress(0);
			// super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			int ii = 1;
			ResponseHandler<String> res_handler = new BasicResponseHandler();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);
			// MultipartEntity reqEntity = new MultipartEntity(
			// HttpMultipartMode.BROWSER_COMPATIBLE);
			try {
				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
						new AndroidMultiPartEntity.ProgressListener() {

							@Override
							public void transferred(long num) {
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});

				// Log.e("URL","URL of the image"+url);
				// File sourceFile = new File(url);
				entity.addPart("group_id", new StringBody(global.getGroup_id()));
				try {
					entity.addPart("video_name", byte_img);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				entity.addPart("user_id", new StringBody(user_id));
				entity.addPart("chapter_name",
						new StringBody(global.getChap_name()));
				Log.e("Chap", "Name" + global.getChap_name());
				entity.addPart("session", new StringBody("1"));
				// if(global.getSess().equalsIgnoreCase("1")){
				// if(ii==1){

				if (global.getLesson_resumed() == 1) {
					Log.i("topic_recvdi", "topic_recvdi" + global.getTopic());
					entity.addPart("topic", new StringBody(global.getTopic()));
				} else {
					Log.i("topic_recvde",
							"topic_recvde" + global.getTopic_recd());
					entity.addPart("topic",
							new StringBody(global.getTopic_recd()));
				}

				// ii=0;
				// }else{
				Log.i("Else", "Else" + show_sub_topic.getText().toString());
				// }
				// }
				Log.i("Else", "Else" + c_id);
				entity.addPart("chapter_id", new StringBody(c_id));

				// entity.addPart("chat",new StringBody(chat.toString()));
				Log.d("User Id", "" + user_id);
				totalSize = entity.getContentLength();
				Log.i("Length of entity", "" + totalSize);
				httppost.setEntity(entity);

				// Making server call
				response = httpclient.execute(httppost, res_handler);
				try {
					JSONObject job1 = new JSONObject(response);
					Log.i("Response", "response from server" + job1.toString());
					JSONObject job2 = job1.getJSONObject("response");
					// responseString = job2.toString();
					// JSONArray jarr1 = job2.getJSONArray("video");
					JSONArray jarr3 = job2.getJSONArray("status");
					JSONArray jarr4 = job2.getJSONArray("msg");
					responseString = jarr3.get(0).toString();
					if (responseString.equalsIgnoreCase("true")) {
						JSONArray jarr2 = job2.getJSONArray("index");

						for (int i = 0; i < jarr2.length(); i++) {
							images_indexed.add(jarr2.getString(i));
							indexes.add(jarr2.getString(i));
						}
					} else {

						resp_msg = jarr4.get(0).toString();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}
			Log.i("Response fetched", "" + resp_msg);
			return responseString;

		}

		@Override
		protected void onPostExecute(String result) {
			Log.e("Response", "Response from server: " + result);

			super.onPostExecute(result);
			// global.setSess("0");
			if (responseString.equalsIgnoreCase("true")) {
				Toast.makeText(CaptureSignature.this, "Saved successfully", 5)
						.show();
				// hidden.setVisibility(9);
				imgView.setVisibility(9);
				// Log.i("Indexes last",
				// "Indexes last"+indexes.get(indexes.size()-1));
				if (indexes.size() > 0) {
					if (indexes.get(indexes.size() - 1).toString()
							.contains(".")) {
						String locl = indexes.get(indexes.size() - 1)
								.toString();
						int j = indexes.get(indexes.size() - 1).toString()
								.indexOf(".");
						int ind = Integer.parseInt(locl.substring(j + 1));
						ind = ind + 1;
						indexing_tv.setText("1." + ind);

					} else {

					}

				}
			} else {
				if (resp_msg.equalsIgnoreCase("chapter closed")) {
					Toast.makeText(CaptureSignature.this,
							"This Chapter has been ended.", 5).show();
				} else {
					Toast.makeText(CaptureSignature.this,
							"File uploading failed...Please try again!!!", 5)
							.show();
				}
			}
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	private class PageProvider implements CurlView.PageProvider {

		// Bitmap resources.
		// private int[] mBitmapIds = { R.drawable._tick, R.drawable.doubletick,
		// R.drawable.doubletick_s, R.drawable.thumb_pdf };
		Context ctx;
		ArrayList<String> imgs;

		public PageProvider(Context ctx, ArrayList<String> images) {
			// TODO Auto-generated constructor stub
			this.ctx = ctx;
			this.imgs = images;
		}

		@Override
		public int getPageCount() {
			if (imgs.size() == 0) {
				return 3;
			} else {
				return imgs.size();
			}
		}

		String path = "";

		private Bitmap loadBitmap(int width, int height, int index) {
			Drawable d;
			Log.i("Images1", "Images contained" + imgs + Iindex);
			if (imgs.size() == 0) {
				d = getResources().getDrawable(R.drawable.not_available_icon);

			} else {
				if (imgs.get(index).contains(" ")) {
					path = imgs.get(index).replace(" ", "%20");
				} else {
					path = imgs.get(index);
				}
				Log.e("PP", "PP" + path);
				d = new BitmapDrawable(getBitmapFromURL(path));
			}
			/*
			 * LayoutInflater inflater = (LayoutInflater)
			 * getSystemService(Context.LAYOUT_INFLATER_SERVICE); View v =
			 * inflater.inflate(R.layout.demo_view, null); i1 = (ImageView)
			 * v.findViewById(R.id.immg); i1.setImageDrawable(d);
			 * v.measure(MeasureSpec.makeMeasureSpec(width,
			 * MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height,
			 * MeasureSpec.EXACTLY)); v.layout(0, 0, v.getMeasuredWidth(),
			 * v.getMeasuredHeight());
			 */
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);

			int margin = 7;
			int border = 3;
			Rect r = new Rect(margin, margin, width - margin, height - margin);
			// int imageWidth = r.width() - (border * 2);
			int imageWidth = r.width();
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			// r.left += ((r.width() - imageWidth) / 2) - border;
			// r.right = r.left + imageWidth + border + border;
			r.top += ((r.height() - imageHeight) / 2) - border;
			r.bottom = r.top + imageHeight + border + border;

			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);

			return b;
		}

		@Override
		public void updatePage(CurlPage page, int width, int height, int index) {
			Log.e("change",
					"I am changing" + previewlay_clckd + index + Iindex.size()
							+ f_Iindex.size());
			Bitmap front = loadBitmap(width, height, index);
			if (sprefs.getString("Sub_type", "").equalsIgnoreCase("teacher")) {
				if (previewlay_clckd.equalsIgnoreCase("1")) {
					if (f_Iindex.size() == 0) { // fffIndex to do still
						Log.e("updateIF INDEX LIST", "" + Iindex.size());
					} else {
						Log.e("updateELSE INDEX LIST", "" + Iindex.size());
						changeindex(index);
					}
				} else {
					if (Iindex.size() == 0) { // fffIndex to do still
						Log.e("IF INDEX LIST", "" + Iindex.size());
					} else {
						Log.e("ELSE INDEX LIST", "" + Iindex.size());
						changeindex(index);
					}
				}

			} else {
				if (f_Iindex.size() == 0) { // fffIndex to do still
					Log.e("IF ddINDEX LIST", "" + f_Iindex.size());
				} else {
					Log.e("ELSE ddINDEX LIST", "" + f_Iindex.size());
					changeindex(index);
				}
			}

			page.setTexture(front, CurlPage.SIDE_FRONT);
			page.setColor(Color.rgb(255, 255, 255), CurlPage.SIDE_BACK);

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// last_images l_im = new last_images();
		// l_im.execute();

		// topic avail will be available only when clear button is used.

		if (intn.hasExtra("topic_avail")) {

		} else {
			show_sub_topic.setText("");
		}

		if (sprefs.getString("Sub_type", "").equalsIgnoreCase("student")) {
			Teacher_online t_ol = new Teacher_online();
			t_ol.execute();
		}

	}

	public void changeindex(int index) {
		// TODO Auto-generated method stub

		final int another = index;
		Log.i("Final", "another" + another + f_Iindex.toString());

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("Value", "value of" + previewlay_clckd);
				if (sprefs.getString("Sub_type", "")
						.equalsIgnoreCase("teacher")) {

					if (previewlay_clckd.equalsIgnoreCase("1")) {

						// previewlay_clckd = "0";
						if (f_Iindex.equals(null)) {
							indexing_tv.setText("0");
						} else {
							if (!f_Iindex.get(another).toString()
									.equalsIgnoreCase("0")) {
								indexing_tv.setText("1."
										+ f_Iindex.get(another).substring(2));
								Log.i("IindexsELSE", "Iindex" + another);
							} else {
								indexing_tv.setText("0");
							}
						}
					} else {

						if (Iindex.isEmpty()) {
							indexing_tv.setText("0");
							Log.i("IindextIF", "Iindex" + Iindex.get(0));
						} else {
							Log.i("IIIIndeeesszz",
									"mdfnjbdjj"
											+ Iindex.get(another).toString());
							if (!Iindex.get(another).equalsIgnoreCase("0")) {
								indexing_tv.setText("1."
										+ Iindex.get(another).substring(2));
								Log.i("IindextELSE", "Iindex" + Iindex.get(0));
							} else {
								indexing_tv.setText("0");
							}

						}
					}

				} else {
					Log.i("IindexsELSEval", "Iindex" + f_Iindex.get(0));
					if (f_Iindex.equals(null)) {
						indexing_tv.setText("0");
					} else {
						if (!f_Iindex.get(another).toString()
								.equalsIgnoreCase("0")) {
							indexing_tv.setText("1."
									+ f_Iindex.get(another).substring(2));
							Log.i("IindexsELSE", "Iindex" + another);
						} else {
							indexing_tv.setText("0");
						}
					}
				}
			}
		});

	}

	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			/*
			 * if (w > h) { mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
			 * mCurlView.setMargins(.1f, .05f, .1f, .05f); } else {
			 */
			mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
			// mCurlView.setMargins(.1f, .1f, .1f, .1f);
			// }
		}
	}

	public class Teacher_online extends AsyncTask<Void, Void, String> {

		// ProgressDialog pdial;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(Void... params) {
			teacher_online();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// pdial.dismiss();
			if (sstatus_op.equalsIgnoreCase("true")) {
				online.setVisibility(0);
			} else {
				online.setVisibility(9);
			}
		}

		protected Void teacher_online() {
			DefaultHttpClient htcp = new DefaultHttpClient();
			HttpPost post = new HttpPost(online_url);
			// comments_list=new ArrayList<String>();
			ResponseHandler<String> responser = new BasicResponseHandler();
			List<NameValuePair> Input_list = new ArrayList<NameValuePair>();
			Input_list.add(new BasicNameValuePair("user_id", user_id));
			Input_list.add(new BasicNameValuePair("group_id", global
					.getGroup_id()));
			Input_list.add(new BasicNameValuePair("login_id", creator_id));

			Log.e("Inp tol", "Input List tol" + Input_list.toString());
			try {
				post.setEntity(new UrlEncodedFormEntity(Input_list));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Response_fetched = htcp.execute(post, responser);
				Log.i("respons tol", "response fetched tol" + Response_fetched);
				try {
					JSONObject RJob = new JSONObject(Response_fetched);
					JSONObject job1 = RJob.getJSONObject("response");
					JSONArray status_obj = job1.getJSONArray("status");
					sstatus_op = status_obj.get(0).toString();
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
	}

	// -----------------Resume topic-------------------//
	public class Resume_topic extends AsyncTask<Void, Void, String> {

		// ProgressDialog pdial;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(Void... params) {
			resume_topic();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// pdial.dismiss();
			if (sstatus_op.equalsIgnoreCase("true")
					&& msg_resp.equalsIgnoreCase("topic resumed")) {
				if (!global.getTopic().equalsIgnoreCase("")) {
					String trim_topic = global.getTopic();

					// --------To show upcoming index--------------//
					indexing_tv.setText("1."
							+ (Integer.parseInt(last_index_fromresume) + 1));
					Editor edit = sprefs.edit();
					edit.putString("upcoming_index", last_index_fromresume);
					edit.commit();

					if (trim_topic.contains(".")) {
						int i = trim_topic.indexOf(".");
						show_sub_topic.setText(trim_topic.substring(0, i));
					} else {

					}

				}

			} else {
				Toast.makeText(
						CaptureSignature.this,
						"Topic is either not available or has been ended already.",
						5).show();
			}
		}

		protected Void resume_topic() {
			DefaultHttpClient htcp = new DefaultHttpClient();
			HttpPost post = new HttpPost(resume_url);
			// comments_list=new ArrayList<String>();
			ResponseHandler<String> responser = new BasicResponseHandler();
			List<NameValuePair> Input_list = new ArrayList<NameValuePair>();
			Input_list.add(new BasicNameValuePair("group_id", global
					.getGroup_id()));

			Log.e("Inp res", "Input List res" + Input_list.toString());
			try {
				post.setEntity(new UrlEncodedFormEntity(Input_list));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Response_fetched = htcp.execute(post, responser);
				Log.i("respons res", "response fetched res" + Response_fetched);
				try {
					JSONObject RJob = new JSONObject(Response_fetched);
					JSONObject job1 = RJob.getJSONObject("data");
					sstatus_op = job1.getString("status");
					msg_resp = job1.getString("msg");
					if (sstatus_op.equalsIgnoreCase("true")
							&& msg_resp.equalsIgnoreCase("topic resumed")) {
						last_index_fromresume = job1.getString("last_index");
						resume_topic = job1.getString("topic");
						global.setTopic(resume_topic);
						resume_chapter = job1.getString("chapter");
						global.setChap_name(resume_chapter);
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
			return null;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
				&& null != data) {
			selectedImage = data.getData();

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			// Get the cursor
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			// Move to first row
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			imgDecodableString = cursor.getString(columnIndex);
			cursor.close();
			// hidden.setVisibility(0);
			try {
				ExifInterface exif = new ExifInterface(imgDecodableString);
				int orientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);
				Log.e("Orientation","OT"+orientation);
				int rotate = 0;
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate -= 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate -= 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate -= 90;
					break;
				}
				Log.d("Fragment", "EXIF info for file " + imgDecodableString + ": "
						+ rotate);
			} catch (IOException e) {
				Log.d("Fragment", "Could not get EXIF info for file "
						+ imgDecodableString + ": " + e);
			}
			imgView.setVisibility(0);
			// Set the Image in ImageView after decoding the String
			if (mContent.getHeight() == Dheight / 2) {
				Picasso.with(CaptureSignature.this).load(selectedImage)
						.into(imgView);
				mSignature.save(mView);
				Intent intent = getIntent();
				finish();
				overridePendingTransition(0, 0);
				intent.putExtra("path", imgDecodableString);
				startActivity(intent);
			} else {
				Picasso.with(CaptureSignature.this).load(selectedImage)
						.resize(350, 350).into(imgView);
			}

			// imgView.setImageBitmap(BitmapFactory
			// .decodeFile(imgDecodableString));
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}
}