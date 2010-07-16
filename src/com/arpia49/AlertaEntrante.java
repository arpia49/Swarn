package com.arpia49;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class AlertaEntrante extends BroadcastReceiver {
	public static final String PREFS_NAME = "PrefTimbre";
	@Override
	public void onReceive(Context context, Intent intent) {

		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);
		if (entering) {
			long fecha = System.currentTimeMillis();
		    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			
			int numAlertas = settings.getInt("numAlertas", 0)+1;
			editor.putInt("numAlertas", numAlertas);
			editor.putLong("fechaAlerta"+numAlertas, fecha);
			editor.putString("idAlerta"+numAlertas, intent.getExtras().getString("id"));
			editor.commit();
			Toast.makeText(context, "¡Has entrado! Num="+numAlertas, Toast.LENGTH_SHORT).show();
			//Aquí deberemos (re)arrancar el servicio para que mire lo que sea
		} else {
			Log.d("ALERTA DE PROXIMIDAD", "salida");
			//Aquí deberemos parar/rearrancar el servicio
			Toast.makeText(context, "¡Has salido!", Toast.LENGTH_SHORT).show();
		}
	}
}