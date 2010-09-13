package com.arpia49;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class VistaSonidos extends ListActivity {

	// De los menús
	public static final int ACT_LISTA_DEL_SONIDO = 1;
	public static final int ACT_ADD_SONIDO1 = 2;
	public static final int ACT_ADD_SONIDO2 = 3;
	public static final int ACT_ADD_SONIDO3 = 4;
	static final private int ADD_SONIDO = Menu.FIRST;
	static final private int DEL_SONIDOS = Menu.FIRST + 2;
	String tempNombre;
	String tempDescripcion;
	short[] tempRuido = new short[320];
	short[] tempSonido = new short[320];

	ArrayAdapter<String> miArray = null;
	SharedPreferences sp = null;
	String[] sonidos = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int numSonidos = ListaSonidos.size();
		sonidos = new String[numSonidos];
		for (int i = 0; i < numSonidos; i++) {
			sonidos[i] = ListaSonidos.elementAt(i).toString();
		}

		StringBuilder sb = new StringBuilder(getString(R.string.ayudaSonidos));
		if (numSonidos > 0)
			sb
					.append("\nLos sonidos de la lista siguiente ya están en el sistema.");
		else
			sb.append("\nAún no hay sonidos en el sistema.");
		TextView tv = new TextView(this);
		tv.setId(100);
		tv.setText(sb.toString());
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
		tv.setTypeface(Typeface.DEFAULT, 2);
		ListView lv = getListView();
		lv.addHeaderView(tv);

		miArray = new ArrayAdapter<String>(this, R.layout.del_alarma, sonidos);

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

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (ADD_SONIDO): {
			Intent intent = new Intent(this, VistaSonidosCrear.class);
			startActivityForResult(intent, ACT_ADD_SONIDO1);
			return true;
		}
		case (DEL_SONIDOS): {
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
						"Paso 1 de 3 completado", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, VistaSonidosRuido.class);
				startActivityForResult(intent, ACT_ADD_SONIDO2);
			} else {
				Toast.makeText(getApplicationContext(),
						"El sonido no se ha creado", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case (ACT_ADD_SONIDO2): {
			if (resCode == Activity.RESULT_OK) {
				Toast.makeText(getApplicationContext(),
						"Paso 2 de 3 completado", Toast.LENGTH_SHORT).show();
				String[] tempString = data.getStringExtra("Ruido").split(",");
				for (int i = 0; i < 320; i++) {
					tempRuido[i] = Short.parseShort(tempString[i]);
				}
				Intent intent = new Intent(this, VistaSonidosTimbre.class);
				startActivityForResult(intent, ACT_ADD_SONIDO3);
			} else {
				Toast.makeText(getApplicationContext(),
						"El sonido no se ha creado", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case (ACT_ADD_SONIDO3): {
			if (resCode == Activity.RESULT_OK) {
				String[] tempString = data.getStringExtra("Sonido").split(",");
				for (int i = 0; i < 320; i++) {
					tempSonido[i] = Short.parseShort(tempString[i]);
				}

				short[] tempFinal = new short[320];
				for (int i = 0; i < 320; i++) {
					if (tempRuido[i] - tempSonido[i] > 0)
						tempFinal[i] = (short) (tempRuido[i] - tempSonido[i]);
				}

				StringBuilder sb = new StringBuilder();
				
				for (int i = 0; i < 320; i++){
					sb.append(Short.toString(tempSonido[i])+",");
				}
				
				Sonido nuevoSonido = new Sonido.Builder(tempNombre.toString(),
						tempDescripcion.toString(), sb.toString()).build();

				int numSonidos = ListaSonidos.size();
				sonidos = new String[numSonidos];
				for (int i = 0; i < numSonidos; i++) {
					sonidos[i] = ListaSonidos.elementAt(i).toString();
				}

				StringBuilder sb2 = new StringBuilder(
						getString(R.string.ayudaSonidos));
				if (numSonidos > 0)
					sb2
							.append("\nLos sonidos de la lista siguiente ya están en el sistema.");
				else
					sb2.append("\nAún no hay sonidos en el sistema.");
				TextView tv = (TextView) findViewById(100);
				tv.setText(sb2.toString());

				miArray = new ArrayAdapter<String>(this, R.layout.del_alarma,
						sonidos);
				getListView().setAdapter(miArray);
				miArray.notifyDataSetChanged();

				Toast.makeText(getApplicationContext(),
						"Paso 3 de 3 completado. Sonido creado.",
						Toast.LENGTH_SHORT).show();

			} else {
				tempNombre = null;
				tempDescripcion = null;
				Toast.makeText(getApplicationContext(),
						"El sonido no se ha creado", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case (ACT_LISTA_DEL_SONIDO): {
			if (resCode == Activity.RESULT_OK) {
				ListaSonidos.borrar();

				int numSonidos = ListaSonidos.size();
				sonidos = new String[numSonidos];
				for (int i = 0; i < numSonidos; i++) {
					sonidos[i] = ListaSonidos.elementAt(i).toString();
				}

				StringBuilder sb = new StringBuilder(
						getString(R.string.ayudaSonidos));
				if (numSonidos > 0)
					sb
							.append("\nLos sonidos de la lista siguiente ya están en el sistema.");
				else
					sb.append("\nAún no hay sonidos en el sistema.");
				TextView tv = (TextView) findViewById(100);
				tv.setText(sb.toString());

				miArray = new ArrayAdapter<String>(this, R.layout.del_alarma,
						sonidos);
				getListView().setAdapter(miArray);
				miArray.notifyDataSetChanged();

				Toast.makeText(getApplicationContext(), "¡Sonidos eliminados!",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"No se han borrado sonidos", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (id != -1)
			Toast.makeText(getApplicationContext(),
					"¿Quieres borrar esta alarma?", Toast.LENGTH_SHORT).show();
	}
}
