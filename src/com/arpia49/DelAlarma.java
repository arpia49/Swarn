package com.arpia49;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DelAlarma extends ListActivity {

	public static final String PREFS_NAME = "PrefTimbre";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
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
}
