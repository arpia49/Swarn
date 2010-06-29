package com.arpia49;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class VistaAlarmas extends Activity {

	public static final String PREFS_NAME = "PrefTimbre";
	static final private int ADD_ALARMA = Menu.FIRST;
	static final private int INFO = Menu.FIRST + 1;
	int usando;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// meterBasuras();
		setContentView(R.layout.main);
		cargarPosiciones((LinearLayout) findViewById(R.id.mainLay));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Create and add new menu items.
		MenuItem itemAdd = menu.add(0, ADD_ALARMA, Menu.NONE,
				R.string.menu_add_alarma);
		MenuItem itemInfo = menu.add(0, INFO, Menu.NONE, R.string.menu_info);
		// Assign icons
		itemAdd.setIcon(R.drawable.add);
		itemInfo.setIcon(R.drawable.help);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (ADD_ALARMA): {
			addAlarma();
			Toast.makeText(getApplicationContext(), "Alarma añadida!",
					Toast.LENGTH_SHORT).show();
			return true;
		}
		case (INFO): {
			Toast.makeText(getApplicationContext(),
					"Mostrando ayuda! (mentira)", Toast.LENGTH_SHORT).show();
			return true;
		}
		}
		return false;
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

	@Override
	public void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		default:
			final AlertDialog alarmasDialog = (AlertDialog) dialog;
			alarmasDialog.setTitle("Opciones sobre la alarma");
			Button bt = (Button) alarmasDialog.findViewById(R.id.botonEditar);
			bt.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(),
							"Editado! (mentirita)", Toast.LENGTH_SHORT).show();
					alarmasDialog.dismiss();
				}
			});
			Button bt2 = (Button) alarmasDialog
					.findViewById(R.id.botonEliminar);
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(),
							"Borrado! (mentirita)", Toast.LENGTH_SHORT).show();
					alarmasDialog.dismiss();
				}
			});
			break;
		}
	}

	private void cargarPosiciones(LinearLayout lx) {

		// Leemos el número de alarmas
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		int numAlarmas = settings.getInt("numAlarmas", 0);
		for (int i = 1; i <= numAlarmas; i++) {

			CheckBox cb = new CheckBox(this);
			cb.setId(i);
			String nombre = settings
					.getString("nombreAlarma" + i, "sin nombre");
			if (settings.getBoolean("configuradaAlarma" + i, false) == false) {
				nombre = nombre + " (sin configurar)";
			}
			cb.setText(nombre);
			cb.setChecked(settings.getBoolean("estadoAlarma" + i, false));
			cb.setOnLongClickListener(new OnLongClickListener() {
				public boolean onLongClick(View v) {
					showDialog(v.getId());
					return true;
				}
			});

			cb.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					SharedPreferences settings = getSharedPreferences(
							PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					if (((CheckBox) v).isChecked()) {
						editor.putBoolean("estadoAlarma" + v.getId(), true);
						editor.commit();
					} else {
						editor.putBoolean("estadoAlarma" + v.getId(), false);
						editor.commit();
					}
				}
			});
			lx.addView(cb);
		}
	}

	private void addAlarma() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		int numAlarmas = settings.getInt("numAlarmas", 0) + 1;
		editor.putInt("numAlarmas", numAlarmas);
		editor.putString("nombreAlarma" + numAlarmas, "alarma " + numAlarmas);
		editor.putBoolean("estadoAlarma" + numAlarmas, false);
		editor.putBoolean("configuradaAlarma" + numAlarmas, false);
		editor.commit();
	}
}