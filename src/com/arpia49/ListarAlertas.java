package com.arpia49;

import com.arpia49.R;
import com.arpia49.R.drawable;
import com.arpia49.R.layout;
import com.arpia49.R.string;

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

public class ListarAlertas extends ListActivity {

	public static final int ACT_DEL_NOTIFICACIONES = 1;
	static final private int DEL_NOTIFICACIONES = Menu.FIRST;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int numNotificaciones = ListaNotificaciones.size();
		String[] lugares = new String[numNotificaciones];
		for (int i = 0; i < numNotificaciones; i++) {
			String temp = ListaAlarmas.elementAt(i).getNombre(); 
			if(temp.compareTo("")==0){
				temp="Sin especificar";
			}
			lugares[i]=temp;
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.del_alarma,
				lugares));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Create and add new menu items.
		MenuItem itemDel = menu.add(0, DEL_NOTIFICACIONES, Menu.NONE,
				R.string.menu_del_alertas);
		// Assign icons
		itemDel.setIcon(R.drawable.del);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (DEL_NOTIFICACIONES): {
			Intent outData = new Intent();
			outData.putExtra("todas", true);
			setResult(Activity.RESULT_OK, outData);
			finish();
		}
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

	}
}
