package com.dng.gruupy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Privileges extends Activity {

	ListView priv;
	String[] features = { "Add students", "Post questionaire",
			"Upload Video/Pdf", "View Uploaded Video/Pdf", "Delete Video/Pdf" };
	Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.privileges);
		priv = (ListView) findViewById(R.id.privilleges);
		ok = (Button) findViewById(R.id.agreed);
		ArrayAdapter<String> array_Adptr = new ArrayAdapter<String>(
				Privileges.this,R.layout.priv_text, features);
		priv.setAdapter(array_Adptr);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Privileges.this, CaptureSignature.class));
				Privileges.this.finish();
			}
		});
	}

}
