package com.dng.gruupy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

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

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class All_chapters extends Activity {
	String main, topic, sub_topic;
	public static CustomExpandableListView list_exp;
	List<Object_class> objectsLvl1;
	JSONArray sub_topics_arr;
	JSONArray chapters_arr;
	List<String> splltd;
	Button resume, playback;
	String stt = "";
	ImageView options_img;
	ListView l1;
	RelativeLayout parent;
	String[] splitted;
	Dialog topic_dial,options_list;
	ArrayAdapter<String> arr_adptr;
	int choice;
	HashMap<String, String> chap_Topic;
	HashMap<String, String> it, unsplit_keys;
	static HashMap<String, ArrayList<String>> it1, chptr_top, top_img, h_map;
	HashMap<String, ArrayList<String>> it_copy, sub_topic_copy;
	String[] opt_given = { "Create New Chapter and Lesson","Create New Chapter","Create New Lesson","Start New Lesson","Resume Lesson",
			"Play Lesson" };
	String fold, chapters_name = "", Sub_topics = "", paths_images = "",
			result = "";
	int main_loop;
	ArrayList<String> keys;
	Global global;
	StringTokenizer tokens;
	JSONObject inner_arr;
	ArrayList<String> key_list;
	ArrayList<String> sub_new_list, al1, al11, al2, new_list,
			sub_topics_unsplit, temp_list, temp_list1, sub_temp_list, chapters,
			demo, demo1, sub_topics_list, sub_topics_list1;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_chapter_dialog);

		int noObjectsLevel1 = 5;
		int noObjectsLevel2 = 4;
		int noObjectsLevel3 = 7;
		it = new HashMap<String, String>();
		it1 = new HashMap<String, ArrayList<String>>();
		sub_topics_list = new ArrayList<String>();
		chap_Topic = new HashMap<String, String>();
		objectsLvl1 = new ArrayList<Object_class>();
		key_list = new ArrayList<String>();
		sub_topics_unsplit = new ArrayList<String>();
		parent = (RelativeLayout) findViewById(R.id.parent);
		options_img=(ImageView)findViewById(R.id.options);
		global = (Global) getApplicationContext();
		list_exp = new CustomExpandableListView(All_chapters.this);
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
		        ViewGroup.LayoutParams.WRAP_CONTENT);

		p.addRule(RelativeLayout.BELOW, R.id.header_layout);

		list_exp.setLayoutParams(p);
		
		options_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				options_list = new Dialog(All_chapters.this,
						android.R.style.Theme_Translucent_NoTitleBar);
				options_list.setContentView(R.layout.options_list_dialog);
				l1 = (ListView) options_list.findViewById(R.id.options_listing);
				arr_adptr = new ArrayAdapter<String>(All_chapters.this,
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
							global.setTopic_recd("");
							global.setTopic("");
							global.setChap_name("");
							startActivity(new Intent(All_chapters.this,
									CaptureSignature.class).putExtra("start", "1"));
							All_chapters.this.finish();
							break;
						case 1:
						case 2:
							break;

						case 3:
							options_list.dismiss();
							/*global.setFold("0");
							Log.e("Value saved", " v saved" + global.getFold());
							startActivity(new Intent(All_chapters.this,
									All_chapters.class));
							break;*/
						}

						/*if (arg2 == 1) {
							opt_given[1] = "Lock Text";
						} else {
							// Unlock whiteboard code
						}*/
					}
				});

				options_list.show();
			}
		});
		
	/*	Log.i("FOLD11", "FOLD11" + global.getFold());
		if (global.getFold().equalsIgnoreCase("0")) {

		} else if (global.getFold().equalsIgnoreCase("1")) {
			topic_dial = new Dialog(All_chapters.this,
					android.R.style.Theme_Translucent_NoTitleBar);
			topic_dial.setContentView(R.layout.topic_dial);
			Window window = topic_dial.getWindow();
			window.setLayout(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			window.setGravity(Gravity.CENTER);
			resume = (Button) topic_dial.findViewById(R.id.resume);
			playback = (Button) topic_dial.findViewById(R.id.playback);
			resume.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					topic_dial.dismiss();
				}
			});
			playback.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					topic_dial.dismiss();
					global.setTopic_recd("");
					global.setTopic("");
					global.setChap_name("");
					startActivity(new Intent(All_chapters.this,
							CaptureSignature.class).putExtra("start", "1"));
					All_chapters.this.finish();
				}

			});

		}
*/
		// Show is after loading contents/////////
	}

	public class Get_all_chapters extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			get_all_chapters();
			Log.e("Result", "Result" + stt);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// if(stt.equalsIgnoreCase("true")){
			sub_temp_list = new ArrayList<String>();
			sub_topic_copy = new HashMap<String, ArrayList<String>>();
			// top_img = new HashMap<String, ArrayList<String>>();

			int k;
			new_list = new ArrayList<String>();
			temp_list = new ArrayList<String>();
			it_copy = new HashMap<String, ArrayList<String>>();
			if(stt.equalsIgnoreCase("success")){
				for (int i = 0; i < inner_arr.length(); i++) {
					new_list = new ArrayList<String>();
					JSONArray j1 = new JSONArray();
					try {
						j1 = inner_arr.getJSONArray(sub_topics_list.get(i));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (k = 0; k < j1.length(); k++) {

						String img_path = it.get(key_list.get(i)
								+ String.valueOf(k));
						Log.i("sub_topics_list", "sub_topics_list" + img_path);
						new_list.add(img_path);
						Log.i("new_list", "new_list" + new_list);
					}
					temp_list.add(sub_topics_list.get(i));
					it_copy.put(sub_topics_list.get(i), new_list);
					Log.e("IT", "IT Copy list" + temp_list);
					Log.e("IT", "IT Copy" + it_copy);

				}

				List<Object_class> objectsLvl1 = new ArrayList<Object_class>();
				for (int i = 0; i < chapters.size(); i++) {
					al1 = new ArrayList<String>();
					al11 = new ArrayList<String>();
					al2 = new ArrayList<String>();
					h_map = new HashMap<String, ArrayList<String>>();
					List<Object_class> objectsLvl2 = new ArrayList<Object_class>();
					for (int j = 0; j < chptr_top.size(); j++) {
						al11 = chptr_top.get(chapters.get(j) + j);
						Log.i("al11", "al11 size ff" + al11.toString());
//						global.setAll1(al1);
						List<Object_class> objectsLvl3 = new ArrayList<Object_class>();
						Log.i("al1", "al1 size" + al11.toString());

						for (int h = 0; h < al11.size(); h++) {
							Log.i("al1ree", "al1tdkjdshjhdsrwd" + al11.get(h));
							for (int y = 0; y < it1.get(al11.get(h)).size(); y++) {
								Log.i("VC",
										"value check"
												+ al11.get(h)
												+ it1.get(al11.get(h)).get(y)
														.toString());
								objectsLvl3.add(new Object_class(it1
										.get(al11.get(h)).get(y).toString(), null));
							}

						}

						if (i == j) {
							Log.e("2", "2"
									+ chptr_top.get(chapters.get(j) + j).toString());
							al1 = chptr_top.get(chapters.get(j) + j);
							Log.i("al1", "al1" + al1);
							for (int h = 0; h < al1.size(); h++) {
								if (al1.get(h).contains(".")) {
									int p = al1.get(h).indexOf(".");
									Log.e("index of .",
											".." + al1.get(h).indexOf("."));
									String topic = al1.get(h).substring(0, p);
									Log.i("Topic11", "Topic11" + topic);
									objectsLvl2.add(new Object_class(topic,
											objectsLvl3));
								}

							}

						}

					}
					objectsLvl1.add(new Object_class(chapters.get(i), objectsLvl2));
				}

				RelativeLayout parent = (RelativeLayout) findViewById(R.id.parent);

				// list_exp = new CustomExpandableListView(All_chapters.this);
				Adapter adapter = new Adapter(All_chapters.this, objectsLvl1);
				list_exp.setAdapter(adapter);
				parent.removeView(list_exp);
				parent.addView(list_exp);
				if (global.getFold().equalsIgnoreCase("1")) {
//					topic_dial.show();
				} else {

				}
			}else{
				 Toast.makeText(All_chapters.this, "No data available",5).show(); 
				 All_chapters.this.finish(); 
			}
			
			/*
			 * }else{ Toast.makeText(All_chapters.this, "No data available",
			 * 5).show(); All_chapters.this.finish(); }
			 */
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		All_chapters.this.finish();
	}

	public String get_all_chapters() {
		// TODO Auto-generated method stub
		chapters = new ArrayList<String>();
		demo = new ArrayList<String>();
		sub_new_list = new ArrayList<String>();
		chptr_top = new HashMap<String, ArrayList<String>>();
		unsplit_keys = new HashMap<String, String>();
		keys = new ArrayList<String>();
		int u, j, j_1, k = 0;
		String main_chapter = "", sub_topics = "", response = "";
		String output = "";
		DefaultHttpClient dhcp = new DefaultHttpClient();
		ResponseHandler<String> responsehandler = new BasicResponseHandler();
		HttpPost httppost = new HttpPost(Global_Constants.BASE_URL + "demo.php");
		List<NameValuePair> Inputted_list = new ArrayList<NameValuePair>();
		Inputted_list.add(new BasicNameValuePair("group_id", global
				.getGroup_id()));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(Inputted_list));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response = dhcp.execute(httppost, responsehandler);
			try {
				JSONObject respo = new JSONObject(response);
				JSONArray resp = respo.getJSONArray("response");
				JSONArray status_arr = resp.getJSONArray(4);
				stt = status_arr.get(0).toString();
				Log.i("Response", "Rsp" + stt);
				 if(stt.equalsIgnoreCase("success")){
				chapters_arr = new JSONArray();
				chapters_arr = resp.getJSONArray(0);
				for (int i = 0; i < chapters_arr.length(); i++) {
					main_chapter = chapters_arr.getString(i);
					chapters.add(main_chapter);
				}

				Log.i("Chapters", "Chapters Listed" + chapters);
				sub_topics_arr = resp.getJSONArray(1);

				for (j = 0; j < sub_topics_arr.length(); j++) {
					sub_topics_list1 = new ArrayList<String>();
					sub_topics = sub_topics_arr.getString(j);
					splitted = sub_topics.split(",", 0);
					splltd = Arrays.asList(splitted);
					Log.i("splltd", "splltd" + splltd.get(0));
					sub_topics_list.addAll(splltd);
					sub_topics_list1.addAll(splltd);
					for (int h = 0; h < splitted.length; h++) {
						unsplit_keys.put(splitted[h], j + "" + h);
						Log.i("Sub topics1",
								"Sub topics list1"
										+ unsplit_keys.get(splitted[h]));
					}
					chptr_top.put(chapters.get(j) + "" + j, sub_topics_list1);
					demo.add(chapters.get(j));
				}

				Log.i("Chapter_Top", "Chapter_Top" + chptr_top.toString());
				Log.i("Sub topicsfd1",
						"Sub topics list1df" + unsplit_keys.toString());
				// chapter_topic.add(chptr_top);

				JSONObject images_job = resp.getJSONObject(2);
				Log.i("images", "Images" + images_job.toString());
				inner_arr = new JSONObject();
				inner_arr = images_job.getJSONObject("topicElement");
				Log.i("images1", "Images1" + inner_arr.length());
				// HashMap<String, ArrayList<String>> i=new HashMap<String,
				// ArrayList<String>>();
				Set<String> keys = unsplit_keys.keySet();
				Log.e("Keys", "Keys" + keys);
				key_list = new ArrayList<String>(keys);
				Log.e("Key_list", "Key_list" + key_list.toString());
				JSONArray j1 = new JSONArray();
				for (int i = 0; i < inner_arr.length(); i++) {
					j1 = inner_arr.getJSONArray(sub_topics_list.get(i));
					for (k = 0; k < j1.length(); k++) {
						it.put(sub_topics_list.get(i) + String.valueOf(k),
								j1.getString(k));
					}
				}

				Log.e("It", "IT" + it.toString());

				for (u = 0; u < key_list.size(); u++) {
					sub_new_list = new ArrayList<String>();
					j1 = inner_arr.getJSONArray(key_list.get(u));
					Log.i("J1", "J1" + j1.toString());
					for (k = 0; k < j1.length(); k++) {
						sub_new_list.add(j1.getString(k));
					}
					it1.put(key_list.get(u), sub_new_list);
					Log.e("It content", "IT11" + it1.toString());
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
		return output;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Get_all_chapters get_Chapters = new Get_all_chapters();
		get_Chapters.execute();
	}

}

class CustomExpandableListView extends ExpandableListView {
	public CustomExpandableListView(Context context) {
		super(context);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * Adjust height
		 */
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
