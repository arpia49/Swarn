package com.arpia49;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Registro {
	//Para persistencia
	private static Context contexto = null;	
	private static SharedPreferences settings = null;
	private static SharedPreferences.Editor editor = null;
	public static final String PREFS_NAME = "PrefTimbre";
	
	public static void guardarAlarma (Alarma val){
		editor.putInt("numeroAlarmas", val.getId());
		editor.putInt("alarmaClave" + val.getId(), val.getClave());
		editor.putString("alarmaNombre" + val.getId(), val.getNombre());
		editor.putString("alarmaDescripcion" + val.getId(), val.getDescripcion());
		editor.putBoolean("alarmaMarcada" + val.getId(), val.getMarcada());
		editor.putBoolean("alarmaActivada" + val.getId(), val.getActivada());
		editor.putBoolean("alarmaRegistrada" + val.getId(), val.getRegistrada());
		editor.putFloat("alarmaLatitud" + val.getId(), val.getLatitud());
		editor.putFloat("alarmaLongitud" + val.getId(), val.getLongitud());
		editor.putString("alarmaUbicacion" + val.getId(), val.getUbicacion());
		editor.putBoolean("alarmaMuyFuerte" + val.getId(), val.getMuyFuerte());
		editor.commit();
	}	
	
	public static void guardarNotificacion (Notificacion val){
		editor.putInt("numeroNotificaciones", ListaNotificaciones.size()+1);
		editor.putLong("notificacionFecha" + val.getId(), val.getFecha());
		editor.putString("notificacionNombre" + val.getId(), val.getNombre());
		editor.putString("notificacionUbicacion" + val.getId(), val.getUbicacion());
		editor.putInt("notificacionIdAlarma" + val.getId(), val.getIdAlarma());
		editor.commit();
	}
	
	public static void guardarBoolean(String clave, boolean valor){
		editor.putBoolean(clave, valor);
		editor.commit();
	}	
	
	public static void guardarInt(String clave, int valor){
		editor.putInt(clave, valor);
		editor.commit();
	}

	public static void guardarFloat(String clave, float valor) {
		editor.putFloat(clave, valor);
		editor.commit();
	}

	public static void guardarString(String clave, String valor) {
		editor.putString(clave, valor);
		editor.commit();
	}
	
	public static void iniciar(Activity val){
		if(contexto==null){
			contexto = val.getApplicationContext();
			settings = contexto.getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();
			ListaAlarmas.inicializar(settings);
			ListaNotificaciones.inicializar(settings);
		}
	}
}
