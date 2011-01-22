package com.arpia49.SwarnNG;

import com.arpia49.SwarnNG.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActNewAlarm2 extends Activity {
	
	//UI elements
	static Button bt_next = null;
	static Button bt_previous = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_alarm_step2);

		bt_next = (Button) findViewById(R.id.bt_next);
		bt_previous = (Button) findViewById(R.id.bt_previous);
		bt_previous.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED, null);
				finish();
			}
		});
	}
}