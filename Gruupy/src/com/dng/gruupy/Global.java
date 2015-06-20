package com.dng.gruupy;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;


public class Global extends Application {

	
	int index;
	int counter;
	int lesson_resumed;
	String Topic="",Uname = "";
	String topic_retained="";
	ArrayList<String> all1=new ArrayList<String>(); //----------Chapters name listing for adapter----**to select when reume function used-------------//
	public ArrayList<String> getAll1() {
		return all1;
	}

	public void setAll1(ArrayList<String> all1) {
		this.all1 = all1;
	}

	String fold,opt_selected="";
	public String getFold() {
		return fold;
	}

	public void setFold(String fold) {
		this.fold = fold;
	}

	String sess="";
	public String getSess() {
		return sess;
	}

	public void setSess(String sess) {
		this.sess = sess;
	}

	String incoming_number="";
	String topic_recd="",chap_name="";
	
	
	public String getTopic_recd() {
		return topic_recd;
	}

	public void setTopic_recd(String topic_recd) {
		this.topic_recd = topic_recd;
	}

	public String getChap_name() {
		return chap_name;
	}

	public void setChap_name(String chap_name) {
		this.chap_name = chap_name;
	}

	public String getIncoming_number() {
		return incoming_number;
	}

	public void setIncoming_number(String incoming_number) {
		this.incoming_number = incoming_number;
	}

	public String getOpt_selected() {
		return opt_selected;
	}

	public void setOpt_selected(String opt_selected) {
		this.opt_selected = opt_selected;
	}

	String regd_no="";
	public String getTopic() {
		return Topic;
	}

	public void setTopic(String topic) {
		Topic = topic;
	}

	String bitmap_path = "";// Capturesignature act
	String selected_chapter = "";

	public String getSelected_chapter() {
		return selected_chapter;
	}

	public void setSelected_chapter(String selected_chapter) {
		this.selected_chapter = selected_chapter;
	}

	public String getBitmap_path() {
		return bitmap_path;
	}

	public void setBitmap_path(String bitmap_path) {
		this.bitmap_path = bitmap_path;
	}

	String sub_type = "";
	int once = 0;

	public int getOnce() {
		return once;
	}

	public void setOnce(int once) {
		this.once = once;
	}

	public String getSub_type() {
		return sub_type;
	}

	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}

	public String getUname() {
		return Uname;
	}

	public void setUname(String uname) {
		Uname = uname;
	}

	String lat_long = "";
	String pdf_path = "";

	public String getPdf_path() {
		return pdf_path;
	}

	public void setPdf_path(String pdf_path) {
		this.pdf_path = pdf_path;
	}

	String category_base_group = "";

	public String getCategory_base_group() {
		return category_base_group;
	}

	public void setCategory_base_group(String category_base_group) {
		this.category_base_group = category_base_group;
	}

	public String getLat_long() {
		return lat_long;
	}

	public void setLat_long(String lat_long) {
		this.lat_long = lat_long;
	}

	String ID = "";

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	String group_id_clicked = "";

	public String getGroup_id_clicked() {
		return group_id_clicked;
	}

	public void setGroup_id_clicked(String group_id_clicked) {
		this.group_id_clicked = group_id_clicked;
	}

	ArrayList<HashMap<String, String>> group_sub = new ArrayList<HashMap<String, String>>();

	public ArrayList<HashMap<String, String>> getGroup_sub() {
		return group_sub;
	}

	public void setGroup_sub(ArrayList<HashMap<String, String>> group_sub) {
		this.group_sub = group_sub;
	}

	public ArrayList<String> getGroups_subscribed_created() {
		return groups_subscribed_created;
	}

	public void setGroups_subscribed_created(
			ArrayList<String> groups_subscribed_created) {
		this.groups_subscribed_created = groups_subscribed_created;
	}

	ArrayList<String> groups_subscribed_created = new ArrayList<String>();
	
	ArrayList<String> images_indexed = new ArrayList<String>();

	public ArrayList<String> getImages_indexed() {
		return images_indexed;
	}

	public void setImages_indexed(ArrayList<String> images_indexed) {
		this.images_indexed = images_indexed;
	}

	ArrayList<HashMap<String, String>> grp_subscribed = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> old_user = new ArrayList<HashMap<String, String>>();

	public ArrayList<HashMap<String, String>> getOld_user() {
		return old_user;
	}

	public void setOld_user(ArrayList<HashMap<String, String>> old_user) {
		this.old_user = old_user;
	}

	public ArrayList<HashMap<String, String>> getGrp_subscribed() {
		return grp_subscribed;
	}

	public void setGrp_subscribed(
			ArrayList<HashMap<String, String>> grp_subscribed) {
		this.grp_subscribed = grp_subscribed;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	String group_id = "";
	String group_name = "";
	String group_image = "";

	public String getGroup_image() {
		return group_image;
	}

	public void setGroup_image(String group_image) {
		this.group_image = group_image;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGroup_id() {
		return group_id;
	}

	public int getLesson_resumed() {
		return lesson_resumed;
	}

	public void setLesson_resumed(int lesson_resumed) {
		this.lesson_resumed = lesson_resumed;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	ArrayList<HashMap<String, String>> grp_sub = new ArrayList<HashMap<String, String>>();

	public ArrayList<HashMap<String, String>> getGrp_sub() {
		return grp_sub;
	}

	public void setGrp_sub(ArrayList<HashMap<String, String>> grp_sub) {
		this.grp_sub = grp_sub;
	}

	String group_subscribed = "";

	public String getGroup_subscribed() {
		return group_subscribed;
	}

	public void setGroup_subscribed(String group_subscribed) {
		this.group_subscribed = group_subscribed;
	}

	public String getRegd_no() {
		return regd_no;
	}

	public String getTopic_retained() {
		return topic_retained;
	}

	public void setTopic_retained(String topic_retained) {
		this.topic_retained = topic_retained;
	}

	public void setRegd_no(String regd_no) {
		this.regd_no = regd_no;
	}
}
