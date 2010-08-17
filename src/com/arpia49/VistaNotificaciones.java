package com.arpia49;

import android.app.Activity;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class VistaNotificaciones extends ListActivity {

	public static final int ACT_DEL_NOTIFICACIONES = 3;
	static final private int DEL_NOTIFICACIONES = Menu.FIRST;
	NotificationManager notificationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int numNotificaciones = ListaNotificaciones.size();
		String[] lugares = new String[numNotificaciones];
		for (int i = 0; i < numNotificaciones; i++) {
			lugares[i] = ListaNotificaciones.elementAt(i).toString();
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.del_alarma,
				lugares));
		String svcName = Context.NOTIFICATION_SERVICE;
		notificationManager = (NotificationManager) getSystemService(svcName);
		notificationManager.cancel(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Create and add new menu items.
		MenuItem itemDel = menu.add(0, DEL_NOTIFICACIONES, Menu.NONE,
				R.string.menu_del_notificaciones);
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
		Alarma actual = ListaAlarmas.obtenerDesdeClave(ListaNotificaciones.elementAt((int)id).getIdAlarma());
			if(actual.conUbicacion()){
				Intent intent = new Intent(this, VistaNotificacion.class);
				intent.putExtra("lat", actual.getLatitud());
				intent.putExtra("lng", actual.getLongitud());
				startActivity(intent);
			}else{
				Toast.makeText(getApplicationContext(), "Esta alerta no tiene una ubicación concreta",
						Toast.LENGTH_SHORT).show();
			}
	}
}
