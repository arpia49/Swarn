package com.arpia49;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class AlertaEntrante extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);
		if (entering) {
			Log.d("ALERTA DE PROXIMIDAD", "entrada");
			Toast.makeText(context, "¡Has entrado!", Toast.LENGTH_SHORT).show();
			//Aquí deberemos (re)arrancar el servicio para que mire lo que sea
		} else {
			Log.d("ALERTA DE PROXIMIDAD", "salida");
			//Aquí deberemos parar/rearrancar el servicio
			Toast.makeText(context, "¡Has salido!", Toast.LENGTH_SHORT).show();
		}
	}
}