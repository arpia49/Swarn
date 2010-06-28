package com.arpia49;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class VistaAlarmas extends Activity {

	public static final String PREFS_NAME = "PrefTimbre";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		meterBasuras();
		setContentView(R.layout.main);
		cargarPosiciones((LinearLayout) findViewById(R.id.mainLay));

		final CheckBox checkbox = (CheckBox) findViewById(1);

		checkbox.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				// Perform action on clicks, depending on whether it's now
				// checked
				if (((CheckBox) v).isChecked()) {
					Toast.makeText(getApplicationContext(), "----ok",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "ko----",
							Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
	}

	private void meterBasuras() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("numAlarmas", 2);
		editor.putString("nombreAlarma1", "alarma a");
		editor.putBoolean("estadoAlarma1", true);
		editor.putString("nombreAlarma2", "alarma b");
		editor.putBoolean("estadoAlarma2", false);
		editor.commit();
	}

	private void cargarPosiciones(LinearLayout lx) {

		// Leemos el número de alarmas
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		int numAlarmas = settings.getInt("numAlarmas", 0);
		for (int i = 1; i <= numAlarmas; i++) {

			CheckBox cb = new CheckBox(this);
			cb.setId(i);
			cb.setText(settings.getString("nombreAlarma" + i, "0"));
			cb.setChecked(settings.getBoolean("estadoAlarma" + i, false));

			lx.addView(cb);
		}
	}
}