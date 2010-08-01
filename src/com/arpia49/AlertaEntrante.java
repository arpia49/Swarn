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
	public static Handler messageHandler = null;
	protected splEngine engine=null;

	@Override
	public void onReceive(final Context context, Intent intent) {

		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);

		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		messageHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Toast
						.makeText(context,
								"yeah baby!!" + msg.what,
								Toast.LENGTH_SHORT).show();
			}
		};
		engine = splEngine.getInstance(messageHandler);
		String id = intent.getExtras().getString("id");
		if (entering) {
			long fecha = System.currentTimeMillis();

			int numAlertas = settings.getInt("numAlertas", 0) + 1;
			editor.putInt("numAlertas", numAlertas);
			editor.putLong("fechaAlerta" + numAlertas, fecha);
			editor.putString("idAlerta" + numAlertas, id);
			editor.putBoolean("activada" + id,
					true);
			editor.commit();
			Toast.makeText(context, "¡Has entrado! Num=" + numAlertas,
					Toast.LENGTH_SHORT).show();
			// Aquí deberemos (re)arrancar el servicio para que mire lo que sea
				engine.start_engine(settings.getBoolean("sonidoFuerte"+id, false));
		} else {
			Log.d("ALERTA DE PROXIMIDAD", "salida");
			// Aquí deberemos parar/rearrancar el servicio
			Toast.makeText(context, "¡Has salido!", Toast.LENGTH_SHORT).show();
				engine.stop_engine();
			editor.putBoolean("activada" + id,false);
			editor.commit();
		}
	}
}