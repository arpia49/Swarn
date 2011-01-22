package com.arpia49.SwarnNG;

import com.arpia49.SwarnNG.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActNewAlarm1 extends Activity {
	
	//Activities IDs
	private static final int ACT_NEW_ALARM2 = 1;
	
	//UI elements
	static Button bt_next;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_alarm_step1);

		bt_next = (Button) findViewById(R.id.bt_next);
		bt_next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActNewAlarm1.this, ActNewAlarm2.class);
				startActivityForResult(intent, ACT_NEW_ALARM2);
			}
		});
	}
}