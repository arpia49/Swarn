package com.arpia49;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
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

public class VistaAlarmasBorrar extends ListActivity {

	static final private int DEL_ALARMAS = Menu.FIRST;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int numAlarmas = ListaAlarmas.size();
		String[] alarmas = new String[numAlarmas];
		for (int i = 1; i <= numAlarmas; i++) {
			alarmas[i - 1] = ListaAlarmas.element(i).getNombre();
		}

		TextView tv = new TextView(this);
		tv.setId(-1);
		tv.setText(getString(R.string.ayudaLista));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
		tv.setTypeface(Typeface.DEFAULT, 2);
		ListView lv = getListView();
		lv.addHeaderView(tv);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.lista_con_texto,
				alarmas));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position,
			final long id) {
		if (id > -1 
				&& !ListaNotificaciones.contienAlarma(ListaAlarmas.elementAt((int) id)
						.getClave()) && !ListaAlarmas.elementAt((int) id).getMarcada()) {
			super.onListItemClick(l, v, position, id);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
					.setMessage(
							"¿Quieres borrar "
									+ ListaAlarmas.elementAt((int) id)
											.toString() + "?").setCancelable(
							false).setPositiveButton("Sí",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int dialogId) {
									Intent outData = new Intent();
									outData.putExtra("idAlarma", (int) id);
									setResult(Activity.RESULT_OK, outData);
									finish();
								}
							}).setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		} else if (id != -1) {
			Toast.makeText(getApplicationContext(),
					"No se puede borrar la alarma, está en uso o alguna notificación la necesita",
					Toast.LENGTH_SHORT).show();
		}
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
