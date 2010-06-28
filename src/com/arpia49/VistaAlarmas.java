package com.arpia49;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VistaAlarmas extends Activity {

	public static final String PREFS_NAME = "PrefTimbre";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		meterBasuras();
		setContentView(R.layout.main);
		cargarPosiciones((LinearLayout) findViewById(R.id.mainLay));

	}

	private void meterBasuras() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("numAlarmas", 2);
		editor.putString("nombreAlarma1","alarma a");
		editor.putString("nombreAlarma2","alarma b");
		editor.commit();
	}

	private void cargarPosiciones(LinearLayout lx) {

		// Leemos el número de alarmas
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		int numAlarmas = settings.getInt("numAlarmas", 0);
		for (int i = 1; i <= numAlarmas; i++) {
			
			CheckBox cb = new CheckBox(this);
			cb.setText(settings.getString("nombreAlarma" +i, "0"));

			lx.addView(cb);
		}
	}
}