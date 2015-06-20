package com.dng.gruupy;

public class ContactSec {
	//private variables
		String _title;
		String _image;
		String _channel_name;
		public String get_channel_name() {
			return _channel_name;
		}
		public void set_channel_name(String _channel_name) {
			this._channel_name = _channel_name;
		}

		String id;
		String chapter_taught;
		public String getChapter_taught() {
			return chapter_taught;
		}
		public void setChapter_taught(String chapter_taught) {
			this.chapter_taught = chapter_taught;
		}

		String number;
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}

		String creator="";
		public String getCreator() {
			return creator;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}

		String group_type;
		
		public String getGroup_type() {
			return group_type;
		}
		public void setGroup_type(String group_type) {
			this.group_type = group_type;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		// Empty constructor
		public ContactSec(){
			
		}
		// constructor
		public ContactSec(String title, String image,String type,String chapter,String creator,String channel_name,String number){
		
			this._title = title;
			this._image= image;
			this.group_type = type;
			this.chapter_taught=chapter;
			this.creator=creator;
			this._channel_name=channel_name;
			this.number=number;
		}
		
		// constructor
		public String getTitle(){
			return this._title;
		}
		
		// setting name
		public void setTitle(String title){
			this._title = title;
		}
		
		public String getPic(){
			return this._image;
		}
		
		// setting name
		public void setPic(String image){
			this._image = image;
		}
		
		
		
		}
