package com.arpia49;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VistaListarAlarmasEditar extends ListActivity {

	static final private int DEL_ALARMAS = Menu.FIRST;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int numAlarmas = ListaAlarmas.size();
		String[] alarmas = new String[numAlarmas];
		for (int i = 1; i <= numAlarmas; i++) {
			alarmas[i-1] = ListaAlarmas.element(i).getNombre();
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.del_alarma,
				alarmas));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent outData = new Intent();
		outData.putExtra("idAlarma", (int)id);
		setResult(Activity.RESULT_OK, outData);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Create and add new menu items.
		MenuItem itemDel = menu.add(0, DEL_ALARMAS, Menu.NONE,
				R.string.menu_del_todas_alarmas);
		// Assign icons
		itemDel.setIcon(R.drawable.del);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (DEL_ALARMAS): {
			Intent outData = new Intent();
			outData.putExtra("todas", true);
			setResult(Activity.RESULT_OK, outData);
			finish();
		}
		}
		return false;
	}
}
