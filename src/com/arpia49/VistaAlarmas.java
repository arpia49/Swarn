package com.arpia49;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class VistaAlarmas extends Activity {

	public static final String PREFS_NAME = "PrefTimbre";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		meterBasuras();
		setContentView(R.layout.main);
		cargarPosiciones((LinearLayout) findViewById(R.id.mainLay));

		final CheckBox checkbox = (CheckBox) findViewById(1);
		final CheckBox checkbox2 = (CheckBox) findViewById(2);
		checkbox.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				showDialog(1);
				return true;
			}
		});

		checkbox2.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				showDialog(2);
				return true;
			}
		});
		
		checkbox.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("estadoAlarma1", true);
					editor.commit();
				} else {
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("estadoAlarma1", false);
					editor.commit();
				}
			}
		});

		checkbox2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("estadoAlarma2", true);
					editor.commit();
				} else {
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("estadoAlarma2", false);
					editor.commit();
				}
			}
		});
		
	}

	@Override
	public Dialog onCreateDialog(int id) {
		switch (id) {
		default:
			LayoutInflater li = LayoutInflater.from(this);
			View opcionesAlarmas = li.inflate(R.layout.detalle_alarmas, null);
			AlertDialog.Builder dialogoAlarmas = new AlertDialog.Builder(this);
			dialogoAlarmas.setTitle("Opciones sobre la alarma");
			dialogoAlarmas.setView(opcionesAlarmas);
			return dialogoAlarmas.create();
		}
	}


	private void meterBasuras() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("numAlarmas", 2);
		editor.putString("nombreAlarma1", "alarma a");
		editor.putString("nombreAlarma2", "alarma b");
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