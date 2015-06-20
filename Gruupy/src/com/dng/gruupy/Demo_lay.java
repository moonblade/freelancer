package com.dng.gruupy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Demo_lay extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_teacher_reg);
	}
}
