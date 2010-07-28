package com.arpia49;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DelAlarmas extends ListActivity {

	public static final String PREFS_NAME = "PrefTimbre";
	static final private int DEL_ALARMAS = Menu.FIRST;
	private SharedPreferences settings = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		settings = getSharedPreferences(PREFS_NAME, 0);
		super.onCreate(savedInstanceState);
		int numAlarmas = settings.getInt("numAlarmas", 0);
		String[] alarmas = new String[numAlarmas];
		for (int i = 1; i <= numAlarmas; i++) {
			alarmas[i - 1] = settings.getString("nombreAlarma" + i,
					"sin nombre");
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.del_alarma,
				alarmas));
	}
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

		Intent outData = new Intent();
		outData.putExtra("idAlarma", Long.toString(id));
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
