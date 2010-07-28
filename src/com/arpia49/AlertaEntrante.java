package com.arpia49;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class AlertaEntrante extends BroadcastReceiver {
	public static final String PREFS_NAME = "PrefTimbre";
	protected splEngine engine;

	@Override
	public void onReceive(final Context context, Intent intent) {

		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);

		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		engine = new splEngine();
		if (entering) {
			long fecha = System.currentTimeMillis();

			int numAlertas = settings.getInt("numAlertas", 0) + 1;
			editor.putInt("numAlertas", numAlertas);
			editor.putLong("fechaAlerta" + numAlertas, fecha);
			editor.putString("idAlerta" + numAlertas, intent.getExtras()
					.getString("id"));
			editor.putBoolean("activada" + intent.getExtras().getString("id"),
					true);
			editor.commit();
			Toast.makeText(context, "¡Has entrado! Num=" + numAlertas,
					Toast.LENGTH_SHORT).show();
			// Aquí deberemos (re)arrancar el servicio para que mire lo que sea
			if (!engine.isRunning) {
				final Handler messageHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						Toast.makeText(context, "yeah!!"+msg.what, Toast.LENGTH_SHORT)
								.show();
					}

				};
				engine.start_engine(messageHandler, settings.getInt("sonidoFuerte", 0)>0);

			}
		} else {
			Log.d("ALERTA DE PROXIMIDAD", "salida");
			// Aquí deberemos parar/rearrancar el servicio
			Toast.makeText(context, "¡Has salido!", Toast.LENGTH_SHORT).show();
			int corriendo = 0;
			int numAlarmas = settings.getInt("numAlarmas", 0);
			for (int i = 1; i <= numAlarmas; i++) {
				if (settings.getBoolean("activada" + i, false) == true)
					corriendo++;
			}
			if (corriendo <= 1)
				engine.stop_engine();
			editor.putBoolean("activada" + intent.getExtras().getString("id"),
					false);
			editor.commit();
		}
	}
}