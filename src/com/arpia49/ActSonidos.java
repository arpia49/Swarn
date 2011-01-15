package com.arpia49;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActSonidos extends ListActivity {

	// De los menús
	public static final int ACT_LISTA_DEL_SONIDO = 1;
	public static final int ACT_ADD_SONIDO1 = 2;
	public static final int ACT_ADD_SONIDO2 = 3;
	public static final int ACT_ADD_SONIDO3 = 4;
	static final private int ADD_SONIDO = Menu.FIRST;
	static final private int DEL_SONIDOS = Menu.FIRST + 1;
	String tempNombre;
	String tempDescripcion;
	double[] tempRuido = new double[319];
	double[] tempSonido = new double[319];

	ArrayAdapter<String> miArray = null;
	SharedPreferences sp = null;
	String[] sonidos = null;
	private static splEngine engine = null;
	int numSonidos;
	TextView tv;
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		engine = splEngine.getInstance();
		splEngine.setPreferences(sp);
		numSonidos = ListaSonidos.size();
		sonidos = new String[numSonidos];
		for (int i = 0; i < numSonidos; i++) {
			sonidos[i] = ListaSonidos.elementAt(i).toString();
		}
		tv = new TextView(this);
		tv.setId(0);
		tv.setText(getString(R.string.ayudaSonidos));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
		tv.setTypeface(Typeface.DEFAULT, 2);
		lv = getListView();
		lv.addHeaderView(tv);

		miArray = new ArrayAdapter<String>(this, R.layout.lista_con_texto,
				sonidos);

		setListAdapter(miArray);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Create and add new menu items.
		MenuItem itemAdd = menu.add(0, ADD_SONIDO, Menu.NONE,
				R.string.menu_add_sonido);
		MenuItem itemDel = menu.add(0, DEL_SONIDOS, Menu.NONE,
				R.string.menu_del_sonidos);

		// Assign icons
		itemAdd.setIcon(R.drawable.add);
		itemDel.setIcon(R.drawable.del);

		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem itemDel = menu.getItem(1);
		itemDel.setEnabled(ListaSonidos.size() > 0);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (ADD_SONIDO): {
			Intent intent = new Intent(this, ActSonidosCrear.class);
			startActivityForResult(intent, ACT_ADD_SONIDO1);
			return true;
		}
		case (DEL_SONIDOS): {
			for (int i = ListaSonidos.size(); i > 0; i--) {
				if (!ListaAlarmas.contienSonido(ListaSonidos.element(
						(int) i).getClave())) {
					ListaSonidos.del((int) i);
				}
			}
			int numSonidos = ListaSonidos.size();
			sonidos = new String[numSonidos];

			for (int i = 0; i < numSonidos; i++) {
				sonidos[i] = ListaSonidos.elementAt(i).toString();
			}
			miArray = new ArrayAdapter<String>(
					getApplicationContext(),
					R.layout.lista_con_texto, sonidos);
			getListView().setAdapter(miArray);
			miArray.notifyDataSetChanged();
			Toast.makeText(getApplicationContext(), "¡Eliminados todos los sonidos posibles!",
					Toast.LENGTH_SHORT).show();
			return true;
		}
		}
		return false;
	}

	@Override
	public void onActivityResult(int reqCode, int resCode, final Intent data) {
		super.onActivityResult(reqCode, resCode, data);
		switch (reqCode) {
		case (ACT_ADD_SONIDO1): {
			if (resCode == Activity.RESULT_OK) {
				tempNombre = data.getStringExtra("nombreSonido");
				tempDescripcion = data.getStringExtra("descSonido");
				Toast.makeText(getApplicationContext(),
						"Paso 1 de 2 completado", Toast.LENGTH_SHORT).show();
				engine.stop_engine(true, 0);
				Intent intent = new Intent(this, ActSonidosTimbre.class);
				startActivityForResult(intent, ACT_ADD_SONIDO2);
			} else {
				Toast.makeText(getApplicationContext(),
						"El sonido no se ha creado", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case (ACT_ADD_SONIDO2): {
			if (resCode == Activity.RESULT_OK) {
				String tempString = data.getStringExtra("Sonido");
				Boolean sonidoValido = false;
				String[] tmp = tempString.split(",");
				for(int i=0; i<tmp.length; i++){
					if(!tmp[i].equals("0") && !tmp[i].equals("NaN")){
						sonidoValido = true;
						break;
					}
				}

				if(!sonidoValido){
					tempNombre = null;
					tempDescripcion = null;
					AlertDialog.Builder noReconocible = new AlertDialog.Builder(this);
					noReconocible.setMessage(
									"No se ha podido reconocer el sonido.").setCancelable(
									false).setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int id) {
											dialog.cancel();
										}
									});
					AlertDialog alert = noReconocible.create();
					alert.show();
					engine.start_engine(null,true);
					break;
				}
				
				@SuppressWarnings("unused")
				Sonido nuevoSonido = new Sonido.Builder(tempNombre.toString(),
						tempDescripcion.toString(), tempString, ListaSonidos
								.siguienteClave()).build();

				int numSonidos = ListaSonidos.size();
				sonidos = new String[numSonidos];
				for (int i = 0; i < numSonidos; i++) {
					sonidos[i] = ListaSonidos.elementAt(i).toString();
				}
				TextView tv = (TextView) findViewById(0);
				if (numSonidos == 0)
					tv.setText(getString(R.string.ayuda));

				miArray = new ArrayAdapter<String>(this,
						R.layout.lista_con_texto, sonidos);
				getListView().setAdapter(miArray);
				miArray.notifyDataSetChanged();

				Toast.makeText(getApplicationContext(),
						"Paso 2 de 2 completado. Sonido creado.",
						Toast.LENGTH_SHORT).show();

			} else {
				tempNombre = null;
				tempDescripcion = null;
				Toast.makeText(getApplicationContext(),
						"El sonido no se ha creado", Toast.LENGTH_SHORT).show();
			}

			engine.start_engine(null,true);
		}
			break;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position,
			final long id) {
		if (id != -1
				&& !ListaAlarmas.contienSonido(ListaSonidos.elementAt((int) id)
						.getClave())) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
					.setMessage(
							"¿Quieres borrar "
									+ ListaSonidos.elementAt((int) id)
											.toString() + "?").setCancelable(
							false).setPositiveButton("Sí",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int dialogId) {
									ListaSonidos.del((int) id + 1);
									int numSonidos = ListaSonidos.size();
									sonidos = new String[numSonidos];

									for (int i = 0; i < numSonidos; i++) {
										sonidos[i] = ListaSonidos.elementAt(i)
												.toString();
									}
									miArray = new ArrayAdapter<String>(
											getApplicationContext(),
											R.layout.lista_con_texto, sonidos);
									getListView().setAdapter(miArray);
									miArray.notifyDataSetChanged();
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
					"No se puede borrar el sonido, alguna alarma lo necesita",
					Toast.LENGTH_SHORT).show();
		}
	}
}