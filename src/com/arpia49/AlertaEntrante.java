package com.arpia49;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class AlertaEntrante extends BroadcastReceiver {
	public static Handler messageHandler = null;
	protected splEngine engine=null;

	@Override
	public void onReceive(final Context context, Intent intent) {

		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);

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
		int id = intent.getExtras().getInt("id");
		Alarma alarmaActual = ListaAlarmas.obtenerDesdeClave(id);
		if (entering) {
			Notificacion nuevaNotificacion = new Notificacion.Builder(
					System.currentTimeMillis(),
					alarmaActual.getNombre(), alarmaActual.getUbicacion())
					.build(true);

			Toast.makeText(context, "¡Has entrado! Num=" + id,
					Toast.LENGTH_SHORT).show();
			// OJO; AQUÍ TIENE QUE PILLAR DE ALERTA
				engine.start_engine(alarmaActual.getMuyFuerte());
				alarmaActual.setActivada(true);
		} else {
			Log.d("ALERTA DE PROXIMIDAD", "salida");
			// Aquí deberemos parar/rearrancar el servicio
			Toast.makeText(context, "¡Has salido!", Toast.LENGTH_SHORT).show();
				engine.stop_engine();
				alarmaActual.setActivada(false);
		}
	}
}