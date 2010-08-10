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
		
		engine = splEngine.getInstance();
		int id = intent.getExtras().getInt("id");
		Alarma alarmaActual = ListaAlarmas.obtenerDesdeClave(id);
		if (entering) {
			Toast.makeText(context, "¡Has entrado! Num=" + id,
					Toast.LENGTH_SHORT).show();
			// OJO; AQUÍ TIENE QUE PILLAR DE ALERTA
				engine.start_engine(new Evento(id, alarmaActual.getMuyFuerte(),VistaAlarmas.actividad));
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