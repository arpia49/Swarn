package com.arpia49.SwarnNG;

import com.arpia49.SwarnNG.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActMain extends Activity {

	// Activities shortcuts
	static final int ACT_NEW_ALARM = 1;
	
	//UI elements
	static Button bt_add_alarm;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		bt_add_alarm = (Button) findViewById(R.id.bt_add_alarm);
		
		bt_add_alarm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActMain.this, ActNewAlarm1.class);
				startActivity(intent);
			}
		});
		
	}

//	public boolean onOptionsItemSelected(MenuItem item) {
//		super.onOptionsItemSelected(item);
//		switch (item.getItemId()) {
//		case (ADD_ALARMA): {
//			Intent intent = new Intent(this, ActAlarmaCrear.class);
//			startActivityForResult(intent, ACT_ADD_ALARMA);
//			return true;
//		}
//		}
//		return false;
//	}

	@Override
	public void onActivityResult(int reqCode, int resCode, final Intent data) {
		super.onActivityResult(reqCode, resCode, data);
		switch (reqCode) {
		case (ACT_NEW_ALARM): {
		
		}
			break;
		}
	}
}