package com.arpia49;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.widget.Toast;

/**
 * @author  arpia49
 */
public class AlertaEntrante extends BroadcastReceiver {
	public static Handler messageHandler = null;
	/**
	 * @uml.property  name="engine"
	 * @uml.associationEnd  
	 */
	protected splEngine engine = null;

	@Override
	public void onReceive(final Context context, Intent intent) {

		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);

		engine = splEngine.getInstance();
		int clave = intent.getExtras().getInt("clave");
		Alarma alarmaActual = ListaAlarmas.obtenerDesdeClave(clave);
		if (entering) {
			Toast.makeText(context,
					"Has entrado en " + alarmaActual.getUbicacion(),
					Toast.LENGTH_SHORT).show();
			engine.start_engine(new Evento(clave,
					alarmaActual.getClaveSonido(), alarmaActual.getMuyFuerte(),
					Registro.actividad), false);
			alarmaActual.setActivada(true);
		} else {
			// Aquí deberemos parar/rearrancar el servicio
			Toast.makeText(context,
					"Has salido de " + alarmaActual.getUbicacion(),
					Toast.LENGTH_SHORT).show();
			engine.stop_engine(false, clave);
			alarmaActual.setActivada(false);
		}
	}
}