package com.arpia49;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActAlarmasEditar extends ListActivity {

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
		tv.setText(getString(R.string.ayudaEditar));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
		tv.setTypeface(Typeface.DEFAULT, 2);
		ListView lv = getListView();
		lv.addHeaderView(tv);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.lista_con_texto,
				alarmas));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (id > -1) {
			Intent outData = new Intent();
			outData.putExtra("idAlarma", (int) id);
			setResult(Activity.RESULT_OK, outData);
			finish();
		}
	}
}
