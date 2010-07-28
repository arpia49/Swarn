package com.arpia49;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListarAlertas extends ListActivity {

	public static final String PREFS_NAME = "PrefTimbre";
	public static final int ACT_DEL_ALERTAS = 1;
	static final private int DEL_ALERTAS = Menu.FIRST;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		int numAlertas = settings.getInt("numAlertas", 0);
		String[] alertas = new String[numAlertas];
		for (int i = 1; i <= numAlertas; i++) {
			String alarma = settings.getString("nombreAlarma"
					+ settings.getString("idAlerta" + i, "0"), "Sin nombre");

			String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
					.format(new java.util.Date(settings.getLong("fechaAlerta"
							+ i, 0)));
			alertas[i - 1] = "Alarma: " + alarma + "\n" + date;
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.lista_alertas,
				alertas));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Create and add new menu items.
		MenuItem itemDel = menu.add(0, DEL_ALERTAS, Menu.NONE,
				R.string.menu_del_alertas);
		// Assign icons
		itemDel.setIcon(R.drawable.del);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (DEL_ALERTAS): {
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			int numAlertas = settings.getInt("numAlertas", 0);
			for (int i = 1; i <= numAlertas; i++) {
				editor.remove("idAlerta" + i);
				editor.remove("fechaAlerta" + i);
			}
			editor.remove("numAlertas");
			editor.commit();
			Toast.makeText(getApplicationContext(), "Alertas borradas",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

	}
}
