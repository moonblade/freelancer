package com.dng.gruupy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.Bitmap_blur;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;

public class Chat extends Activity {
	EditText msg_text;
	TextView msg_sent;
	ImageButton cam_bttn, voice_bttn, emoji;
	Button cancel, send_optns;
	int count = 0;
	String chat_url = Global_Constants.BASE_URL + "groupchat.php";
	String get_chat = Global_Constants.BASE_URL + "find_chat.php";
	SharedPreferences sprefs;
	Global global;
	SoundPool mSoundPool;
	int wait = 0;
	ArrayList<HashMap<String, String>> chat_copy = new ArrayList<HashMap<String, String>>();
	Bitmap_blur blur;
	Bitmap bmp, highlight, blurred_bmp;
	Keyboard key;
	Dialog sound_dialog;
	private AudioManager mAudioManager;
	private HashMap<Integer, Integer> mSoundPoolMap;
	TextView txtPercentage;
	HashMap<String, String> chat_copy_hmap = new HashMap<String, String>();
	MediaPlayer myPlayer;
	Typeface face;
	private int mStream1 = 0;
	private int mStream2 = 0;
	RelativeLayout btttns_lay;
	ImageLoader iloader;
	TextView group_name;
	Dialog dlog;
	boolean shown = true;
	ImageView options_button;
	ImageButton send_bttn;
	int mid;
	ArrayList<String> c_copy = new ArrayList<String>();
	// boolean running = true;
	ImageView group_img;
	static RelativeLayout ll1;
	FrameLayout add_view;
	LinearLayout send_optns_lay, other_groups;
	RelativeLayout  group_detail;
	int kheight,turn = 1;
	Chat_adapter cadap;
	String UNAME;
	int laser, bark;
	int vol;
	DisplayImageOptions options = null;
	String UCLR = "";
	ImageView content;
	RelativeLayout rlayout_list;
	ListView msg_list;
	MediaRecorder myRecorder;
	GridView options_listview;
	int heightDifference;
	TextView mini_msg;
	ProgressBar Pbar;
	String outputFile;
	View gone_view;
	DisplayMetrics metrics;
	int height = 0, width = 0;
	static ArrayList<HashMap<String, String>> c_chat = new ArrayList<HashMap<String, String>>();
	String UID, GID, output, Result;
	final static int SOUND_FX_01 = 1;
	String val;
	int[] view_image = { R.drawable.search_btn, R.drawable.search_btn,
			R.drawable.tlogo, R.drawable.tlogo };
	final static int SOUND_FX_02 = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.chat);
		global = (Global) getApplicationContext();
		content = (ImageView) findViewById(R.id.bos);
		Intent inti = getIntent();
		val = inti.getStringExtra("chat_main");
		other_groups = (LinearLayout) findViewById(R.id.other_groups);
		face = Typeface.createFromAsset(getAssets(), "Aaargh.ttf");
		// gone_view = (View) findViewById(R.id.view_gone);
		Log.e("val", "Value from intent" + val);

		// --------------Screen Resolution-------------------//
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		height = metrics.heightPixels;
		width = metrics.widthPixels;
		Log.e("Size of screen", "Size of screen" + width);
//		kheight=key.getHeight();
//		Log.e("height of keyboard", "Keyboard" + kheight);
		// /-------------------------Audio Recording---------------------//
		outputFile = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/double_tick.3gpp";

		myRecorder = new MediaRecorder();
		myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		myRecorder.setOutputFile(outputFile);

		// --------------Sound effects--------------//
		mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mSoundPoolMap = new HashMap();
		// load fx
		mSoundPoolMap.put(SOUND_FX_01, mSoundPool.load(this, R.raw.laser, 1));

		iloader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this.getApplicationContext()).threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(1500000)
				// 1.5 Mb
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).build();
		ImageLoader.getInstance().init(config);
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		sprefs = getSharedPreferences("mprefs", Context.MODE_PRIVATE);
		UID = sprefs.getString("id", "");
		UNAME = sprefs.getString("Uname", "");
		UCLR = sprefs.getString("color_uname", "");
		Log.i("UID", "" + UID);
		Log.i("UNAME", "" + UNAME);
		if (global.getGroup_sub().size() != 0) {
			for (int i = 0; i < global.getGroup_sub().size(); i++) {
				ImageView imageView = new ImageView(Chat.this);
				imageView.setId(i);
				if (width > 720) {
					Picasso.with(Chat.this)
							.load(global.getGroup_sub().get(i)
									.get("group_image")).resize(180, 180)
							.into(imageView);
				} else {
					Picasso.with(Chat.this)
							.load(global.getGroup_sub().get(i)
									.get("group_image")).resize(100, 100)
							.into(imageView);
				}

				other_groups.addView(imageView);
			}
		}
		if (val.equalsIgnoreCase("1")) {
			content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					content.setImageResource(R.drawable.stng_icon);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							content.setImageResource(R.drawable.roundicon);
						}
					}, 1500);
					// startActivity(new Intent(Chat.this,
					// Uploaded_listing.class));
				}
			});
		} else {
			if (global.getSub_type().equalsIgnoreCase("student")) {

				content.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						startActivity(new Intent(Chat.this,
//								Uploaded_listing.class));
					}
				});

			} else if (global.getSub_type().equalsIgnoreCase("teacher")) {

				content.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						startActivity(new Intent(Chat.this, Signature.class));
					}
				});

			}

		}

		if (!global.getGroup_id_clicked().equalsIgnoreCase("")) {
			GID = global.getGroup_id_clicked();
			Log.i("id of group", "on click" + GID);
		} else {

			GID = global.getGroup_id();
			Log.i("id of group", "from global" + GID);
		}
		cam_bttn = (ImageButton) findViewById(R.id.cam_bttn);
		voice_bttn = (ImageButton) findViewById(R.id.voice_bttn);
		group_img = (ImageView) findViewById(R.id.grp_img);
		group_detail = (RelativeLayout) findViewById(R.id.group_detail);
		ImageLoader iloader = ImageLoader.getInstance();
		group_name = (TextView) findViewById(R.id.grp_name);
		msg_list = (ListView) findViewById(R.id.user1_detail);
		ll1 = (RelativeLayout) findViewById(R.id.LinearLayout1);
		group_name.setText(global.getGroup_name());
		options_button = (ImageView) findViewById(R.id.bos);
		iloader.displayImage(global.getGroup_image(), group_img);
		cancel = (Button) findViewById(R.id.cancel);
		emoji = (ImageButton) findViewById(R.id.smileys);
		// cancel.setTypeface(face);
		mini_msg = (TextView) findViewById(R.id.mini_msg);
		mini_msg.setTypeface(face);
		msg_text = (EditText) findViewById(R.id.msg_box);
		add_view = (FrameLayout) findViewById(R.id.msg_list);
		options_listview = (GridView) findViewById(R.id.options_list);
		send_bttn = (ImageButton) findViewById(R.id.send_bttn);
		group_name.setTypeface(face);
		msg_text.setTypeface(face);
		msg_text.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                Rect r = new Rect();
                ll1.getWindowVisibleDisplayFrame(r);

                int screenHeight = ll1.getRootView().getHeight();
                heightDifference = screenHeight - (r.bottom - r.top);
                Log.d("Keyboard Size", "Size: " + heightDifference);

                //boolean visible = heightDiff > screenHeight / 3;
            }
        });
		cam_bttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cam_bttn.setImageResource(R.drawable.camera_red);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						cam_bttn.setImageResource(R.drawable.camera3);
					}
				}, 1500);

			}
		});
		voice_bttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				voice_bttn.setImageResource(R.drawable.audio_red);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						voice_bttn.setImageResource(R.drawable.audio1);
					}
				}, 1500);

			}
		});
		emoji.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				emoji.setImageResource(R.drawable.smile_red);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						emoji.setImageResource(R.drawable.smile2);
					}
				}, 1500);
			}
		});

		msg_text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
								if (msg_text.getText().toString().equalsIgnoreCase("")) {
					cam_bttn.setVisibility(0);
					voice_bttn.setVisibility(0);
					send_bttn.setVisibility(9);
				} else {
					send_bttn.setVisibility(0);
					cam_bttn.setVisibility(9);
					voice_bttn.setVisibility(9);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				// cam_bttn.setImageResource(R.drawable.camera3);
				// voice_bttn.setVisibility(0);
			}
		});
		msg_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// group_detail.setVisibility(View.INVISIBLE);
				/*InputMethodManager imm = (InputMethodManager) Chat.this
			            .getSystemService(Context.INPUT_METHOD_SERVICE);

			    if (imm.isAcceptingText()) {
			        Log.e("KS","Software Keyboard was shown"+heightDifference);
			        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT, 51);
					lparams.setMargins(30, 0, 0, 332+20);
					Log.e("KS","Margin adjusted");
			    } else {
			    	Log.e("KNS","Software Keyboard was not shown");
			    }
				*/

				
				Chat.this
						.getWindow()
						.setSoftInputMode(
								WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
			}
		});
		options_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (options_listview.getVisibility() == 0) {
					options_listview.setVisibility(9);
					other_groups.setVisibility(0);
				} else {
					options_listview.setBackgroundColor(Color
							.parseColor("#B3DFF9"));
					other_groups.setVisibility(9);
					options_listview.setVisibility(0);

					Base_adapter badapter = new Base_adapter(Chat.this);
					options_listview.setAdapter(badapter);
				}

			}
		});
		options_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					sound_dialog = new Dialog(Chat.this,
							android.R.style.Theme_Translucent_NoTitleBar);
					bmp = takeScreenShot(Chat.this);
					blur = new Bitmap_blur();
					blurred_bmp = blur.fastblur(bmp, 10);
					sound_dialog.setCancelable(true);
					final Drawable draw = new BitmapDrawable(getResources(),
							blurred_bmp);
					sound_dialog.getWindow().setBackgroundDrawable(draw);
					sound_dialog.setContentView(R.layout.mic_dialog);
					final ImageView rec_start = (ImageView) sound_dialog
							.findViewById(R.id.start);
					rec_start.setId(0);
					rec_start.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							if (rec_start.getId() == 0) {
								rec_start.setId(1);
								rec_start.setImageResource(R.drawable.stop_rec);
								try {
									myRecorder.prepare();
									myRecorder.start();
								} catch (IllegalStateException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								rec_start.setId(0);
								rec_start
										.setImageResource(R.drawable.start_rec);
								if (myPlayer != null) {
									myPlayer.stop();
									myPlayer.release();
									myPlayer = null;
								}
								sound_dialog.dismiss();
								/*
								 * msg_text.setText(outputFile);
								 * msg_text.setBackgroundColor(Color
								 * .parseColor("#B3DFF9"));
								 */
								// msg_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.key_play_pause,
								// 0, 0, 0);
								/*
								 * msg_text.setOnClickListener(new
								 * OnClickListener() {
								 * 
								 * @Override public void onClick(View v) { //
								 * TODO Auto-generated method stub play(v);
								 * 
								 * dlog = new Dialog(Chat.this);
								 * dlog.setContentView (R.layout.dialog_upload);
								 * Pbar = (ProgressBar)
								 * dlog.findViewById(R.id.upload_progress );
								 * txtPercentage = (TextView)
								 * dlog.findViewById(R.id.percent);
								 * Pbar.setProgress(0); dlog.show();
								 * 
								 * 
								 * } });
								 */

							}
						}
					});

					sound_dialog.show();

				}
			}
		});

		msg_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// if (running == true) {
				// running = false;
				// } else {
				// running = true;
				// }
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				// running = true;
			}
		});
		send_bttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				msg_text.clearFocus();
				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				cam_bttn.setVisibility(0);
				voice_bttn.setVisibility(0);
				send_bttn.setVisibility(9);
				InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(msg_text.getWindowToken(), 0);
				// mSoundPool.stop(mStream1);
				// copy_adapter copada=new copy_adapter(Chat.this,
				// c_copy,UNAME);
				// msg_list.setAdapter(copada);
				if (msg_text.getText().toString().equalsIgnoreCase("")) {
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							cam_bttn.setImageResource(R.drawable.camera3);
							voice_bttn.setVisibility(0);
						}
					}, 1000);

				} else {

					Group_chat g_chat = new Group_chat();
					g_chat.execute();
				}
				group_detail.setVisibility(0);
			}
		});
		// -------------Call to thread--------------------//
		Get_chat get_chat = new Get_chat();
		get_chat.execute();
	}

	public void play(View view) {
		try {
			myPlayer = new MediaPlayer();
			myPlayer.setDataSource(outputFile);
			myPlayer.prepare();
			myPlayer.start();

			// playBtn.setEnabled(false);
			// stopPlayBtn.setEnabled(true);
			// text.setText("Recording Point: Playing");

			Toast.makeText(getApplicationContext(),
					"Start play the recording...", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ---------------Take screenshot----------------------//

	private static Bitmap takeScreenShot(Activity activity) {
		View v1 = ll1.getRootView();
		v1.setDrawingCacheEnabled(true);
		Bitmap bm = v1.getDrawingCache();
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
		return bm;
	}

	// -------------------Chat adapter---------------------------//

	public class Chat_adapter extends BaseAdapter {
		LayoutInflater Lift;
		Context c;
		int layoutResourceId;
		ArrayList<HashMap<String, String>> chat_list, temp_list;

		public Chat_adapter(Context c,
				ArrayList<HashMap<String, String>> chat_list) {
			this.c = c;
			this.Lift = LayoutInflater.from(c);
			this.chat_list = chat_list;
			this.temp_list = chat_list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return chat_list.size();
		}

		public void clear() {
			temp_list = chat_list;
			chat_list.clear();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return chat_list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View v, ViewGroup arg2) {
			Viewholder holder = null;
			if (v == null) {

				v = (LinearLayout) Lift.inflate(R.layout.new_chat, null);
				holder = new Viewholder();
				holder.sender_llay = (LinearLayout) v
						.findViewById(R.id.chat_sender);
				holder.rec_llay = (LinearLayout) v
						.findViewById(R.id.LinearLayout01);
				holder.sgroup_name = (TextView) v.findViewById(R.id.sname);
				holder.schat_msg = (TextView) v.findViewById(R.id.TextView001);
				holder.rgroup_name = (TextView) v.findViewById(R.id.rname);
				holder.rchat_msg = (TextView) v.findViewById(R.id.chat_msga);
				holder.stymchck = (TextView) v.findViewById(R.id.stime);
				holder.rtymchck = (TextView) v.findViewById(R.id.rtime);
				v.setTag(holder);
			} else {
				holder = (Viewholder) v.getTag();
			}
			int Uid = Integer.parseInt(UID);
			Random rand = new Random();

			if (chat_list.get(arg0).get("uname").equalsIgnoreCase(UNAME)) {
				// --------------My record-------------------//

				holder.sgroup_name.setTextColor(Integer.parseInt(UCLR));
				holder.sgroup_name.setText(chat_list.get(arg0).get("uname"));
				holder.sgroup_name.setTypeface(face);

				holder.stymchck.setText(chat_list.get(arg0).get("time")
						.substring(11, 16));
				holder.stymchck.setTypeface(face);
				holder.rchat_msg.setText(chat_list.get(arg0).get("chat"));
				holder.rchat_msg.setTypeface(face);
				holder.rec_llay.setVisibility(View.GONE);
			} else {
				holder.rgroup_name
						.setTextColor(Color.argb(255, rand.nextInt(256),
								rand.nextInt(256), rand.nextInt(256)));
				holder.rgroup_name.setText(chat_list.get(arg0).get("uname"));
				holder.rgroup_name.setTypeface(face);
				holder.rtymchck.setText(chat_list.get(arg0).get("time")
						.substring(11, 16));
				holder.rtymchck.setTypeface(face);
				holder.schat_msg.setText(chat_list.get(arg0).get("chat"));
				holder.schat_msg.setTypeface(face);
				holder.sender_llay.setVisibility(View.GONE);

			}

			return v;
		}

		public class Viewholder {
			TextView sgroup_name, schat_msg, schck, stymchck;
			TextView rgroup_name, rchat_msg, rchck, rtymchck;
			LinearLayout sender_llay, rec_llay;
			RelativeLayout reciever_lay, sender_lay;
		}

		public void add(Object object) {
			// TODO Auto-generated method stub
			chat_list = temp_list;
		}

	}

	public class Group_chat extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			wait = 6000;
		}

		@Override
		protected String doInBackground(Void... params) {
			group_chat();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (output.equalsIgnoreCase("true")) {
				float streamVolume = mAudioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC);
				streamVolume = streamVolume
						/ mAudioManager
								.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				switch (mAudioManager.getRingerMode()) {
				case AudioManager.RINGER_MODE_NORMAL:
					mStream1 = mSoundPool.play(mSoundPoolMap.get(SOUND_FX_01),
							streamVolume, streamVolume, 1, 1, 1f);
					break;
				case AudioManager.RINGER_MODE_SILENT:
					break;
				case AudioManager.RINGER_MODE_VIBRATE:
					break;
				}

				/*
				 * Toast.makeText(Chat.this, "Message sent successfully", 10)
				 * .show();
				 */
				msg_text.setText("");
				Get_chat ge_chat = new Get_chat();
				ge_chat.execute();
			} else {
				Toast.makeText(Chat.this, "Message failed", 10).show();
			}
		}
	}

	protected void group_chat() {
		DefaultHttpClient dhcp = new DefaultHttpClient();
		ResponseHandler<String> response = new BasicResponseHandler();
		HttpPost postmethod = new HttpPost(chat_url);
		List<NameValuePair> inputs = new ArrayList<NameValuePair>();
		inputs.add(new BasicNameValuePair("user_id", UID));
		inputs.add(new BasicNameValuePair("group_id", GID));
		inputs.add(new BasicNameValuePair("chat", msg_text.getText().toString()
				.trim()));
		Log.d("Inputs for posting message", "" + inputs);
		try {
			postmethod.setEntity(new UrlEncodedFormEntity(inputs));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String Rsponse = dhcp.execute(postmethod, response);
			Log.d("rsponse", "" + Rsponse);
			try {
				JSONObject job1 = new JSONObject(Rsponse);
				Log.i("Job1", "" + job1);
				JSONObject j1 = job1.getJSONObject("response");
				output = j1.getString("status");
				Log.d("Output", "" + output);
				if (output.equalsIgnoreCase("true")) {

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

	public class Get_chat extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Log.e("Wait", "Wait on pre execute" + wait);
			if (wait != 1) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

					}
				}, 5000);
			}

		}

		@Override
		protected String doInBackground(Void... params) {
			get_chat();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Log.e("C chat list", "C chat list" + c_chat.toString());
			Log.i("Turn value", "Turn value" + turn);
			wait = 1;
			Log.e("Wait reloaded", "Wait reset" + wait);
			try {
				if (output.equalsIgnoreCase("1")) {
					Log.i("Hellllllllllllllpo", "" + c_chat.toString());
					msg_list.setSelection(c_chat.size());
					cadap = new Chat_adapter(Chat.this, c_chat);
					msg_list.setAdapter(cadap);
					cadap.notifyDataSetChanged();
					msg_list.setSelection(c_chat.size());
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							Get_chat get_chat = new Get_chat();
							get_chat.execute();
						}
					}, 3000);

				} else {
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void get_chat() {
		DefaultHttpClient dhcp = new DefaultHttpClient();
		ResponseHandler<String> response = new BasicResponseHandler();
		HttpPost postmethod = new HttpPost(get_chat);
		List<NameValuePair> inputs = new ArrayList<NameValuePair>();
		inputs.add(new BasicNameValuePair("group_id", GID));
		Log.d("Inputs:::", "" + inputs);
		try {
			postmethod.setEntity(new UrlEncodedFormEntity(inputs));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String Rsponse = dhcp.execute(postmethod, response);
			Log.d("rsponse of get chat", "" + Rsponse);
			try {
				c_chat = new ArrayList<HashMap<String, String>>();
				JSONObject job1 = new JSONObject(Rsponse);
				Log.i("Job1", "" + job1);
				JSONObject j1 = job1.getJSONObject("response");
				JSONArray sj = j1.getJSONArray("status");
				output = sj.getString(0);
				JSONArray u_ob = j1.getJSONArray("user_id");
				JSONArray count = j1.getJSONArray("count");
				JSONArray u_name = j1.getJSONArray("user_name");
				JSONArray time = j1.getJSONArray("on_date");
				int counter = Integer.parseInt(count.getString(0));
				global.setCounter(counter);
				JSONArray c_ob = j1.getJSONArray("chat");
				Log.d("Output", "" + output);
				for (int i = 0; i < c_ob.length(); i++) {
					HashMap<String, String> hmap = new HashMap<String, String>();
					hmap.put("uname", u_name.getString(i));
					hmap.put("chat", c_ob.getString(i));
					hmap.put("time", time.getString(i));
					// chat_copy_hmap=hmap;
					c_chat.add(hmap);
					c_copy.add(c_ob.getString(i));
				}
				// Collections.reverse(c_chat);
				Log.e("Copied hashmap", "Hmap" + chat_copy_hmap.toString());
				Log.i("C_chat", "" + c_chat.toString());

				if (output.equalsIgnoreCase("1")) {
					Result = "true";
					Log.i("Result", "" + Result.toString());
				} else {
					Result = "false";
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

	/*
	 * public Bitmap highlightImage(Bitmap src) { // create new bitmap, which
	 * will be painted and becomes result image Bitmap bmOut =
	 * Bitmap.createBitmap(src.getWidth() + 96, src.getHeight() + 96,
	 * Bitmap.Config.ARGB_8888); // setup canvas for painting Canvas canvas =
	 * new Canvas(bmOut); // setup default color canvas.drawColor(0,
	 * PorterDuff.Mode.CLEAR); // create a blur paint for capturing alpha Paint
	 * ptBlur = new Paint(); ptBlur.setMaskFilter(new BlurMaskFilter(15,
	 * Blur.NORMAL)); int[] offsetXY = new int[2]; // capture alpha into a
	 * bitmap Bitmap bmAlpha = src.extractAlpha(ptBlur, offsetXY); // create a
	 * color paint Paint ptAlphaColor = new Paint();
	 * ptAlphaColor.setColor(0xFFFFFFFF); // paint color for captured alpha
	 * region (bitmap) canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1],
	 * ptAlphaColor); // free memory bmAlpha.recycle();
	 * 
	 * // paint the image source canvas.drawBitmap(src, 0, 0, null);
	 * 
	 * // return out final image return bmOut; }
	 */

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// running=false;
		// Get_chat g_c = new Get_chat();
		// g_c.cancel(true);
		// Chat.this.finish();

		Log.i("Back", "false");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		msg_text.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(msg_text, InputMethodManager.SHOW_IMPLICIT);
		Get_chat get_chat = new Get_chat();
		get_chat.execute();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// running=false;
		Log.i("Pause", "false");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		// Toast.makeText(Chat.this, "On Restart", 5).show();
		Get_chat get_chat = new Get_chat();
		get_chat.execute();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Editor ed_sprefs = sprefs.edit();
		ed_sprefs.putString("count", String.valueOf(global.getCounter()));
		ed_sprefs.commit();
		turn = 1;

		String count_sprefs = sprefs.getString("count", "");
		Log.i("count_sorefs", "" + count_sprefs);
		Log.i("Not Same", "");
		Get_chat get_chat = new Get_chat();
		get_chat.execute();

	}
}
