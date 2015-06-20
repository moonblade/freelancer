package com.dng.gruupy;

import java.util.List;

public class Object_class {
	String name;
	List<Object_class> objects;
	
	public Object_class(String name, List<Object_class> objects) {
		this.name= name;
		this.objects= objects;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Object_class> getObjects() {
		return this.objects;
	}
}
