package com.dng.gruupy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Service_reciever extends BroadcastReceiver {

	SharedPreferences sprefs;
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		
		
		if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
				TelephonyManager.EXTRA_STATE_RINGING)) {
			String incomingNumber = intent
					.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			Toast.makeText(context, "Call from:" + incomingNumber,
					Toast.LENGTH_LONG).show();
			sprefs = context.getSharedPreferences("mprefs", Context.MODE_PRIVATE);
			Editor edit1=sprefs.edit();
			edit1.putString("incoming",incomingNumber);
			edit1.commit();
			
			/*Intent i1 = new Intent(context, RegistrationActivity.class);
			i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			i1.putExtra("incoming", "hello");
			
			
			context.startActivity(i1);*/

		} else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
				TelephonyManager.EXTRA_STATE_IDLE)
				|| intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
						TelephonyManager.EXTRA_STATE_OFFHOOK)) {
			Toast.makeText(context, "Detected call hangup event",
					Toast.LENGTH_LONG).show();

		}

	}
}