package com.arpia49;

import android.app.Activity;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActNotificaciones extends ListActivity {

	static final private int DEL_NOTIFICACIONES = Menu.FIRST;
	static final private int COPIAR_NOTIFICACIONES = Menu.FIRST + 1;
	public static final int ACT_DEL_NOTIFICACIONES = 1;
	public static final int ACT_COPIAR_NOTIFICACIONES = 2;
	NotificationManager notificationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int numNotificaciones = ListaNotificaciones.size();
		String[] lugares = new String[numNotificaciones];
		for (int i = 0; i < numNotificaciones; i++) {
			lugares[i] = ListaNotificaciones.elementAt(i).toString();
		}
		TextView tv = new TextView(this);
		tv.setId(-1);
		tv.setText(getString(R.string.ayudaNotificacion));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
		tv.setTypeface(Typeface.DEFAULT, 2);
		ListView lv = getListView();
		lv.addHeaderView(tv);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.lista_con_texto,
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
		MenuItem itemUsb = menu.add(0, COPIAR_NOTIFICACIONES, Menu.NONE,
				R.string.menu_copiar_notificaciones);
		// Assign icons
		itemDel.setIcon(R.drawable.del);
		itemUsb.setIcon(R.drawable.usb);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (ACT_DEL_NOTIFICACIONES): {
			Intent outData = new Intent();
			outData.putExtra("todas", true);
			setResult(Activity.RESULT_OK, outData);
			finish();
		}
		case (ACT_COPIAR_NOTIFICACIONES): {
			Intent outData = new Intent();
			outData.putExtra("usb", true);
			setResult(Activity.RESULT_OK, outData);
			finish();
		}
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (id > -1) {
			Alarma actual = ListaAlarmas.obtenerDesdeId(ListaNotificaciones
					.elementAt((int) id).getClaveAlarma());
			if (actual.conUbicacion()) {
				Intent intent = new Intent(this, ActNotificacion.class);
				intent.putExtra("lat", actual.getLatitud());
				intent.putExtra("lng", actual.getLongitud());
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(),
						"Esta alerta no tiene una ubicación concreta",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}