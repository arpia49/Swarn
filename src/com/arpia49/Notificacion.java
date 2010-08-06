package com.arpia49;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Notificacion {
	
	//Para persistencia
	private static Context contexto = null;	
	private static SharedPreferences settings = null;
	private static SharedPreferences.Editor editor = null;
	public static final String PREFS_NAME = "PrefTimbre";
		
	//Sin builder
	private int id;

	//Obligatorias
	private long fecha;
	private Alerta alerta;
	
	public static class Builder {
		//Obligatorias
		private long fecha;
		private Alerta alerta;

		public Builder(long fecha, Alerta alerta) {
			this.fecha = fecha;
			this.alerta = alerta;
		}

		public Notificacion build(Boolean guardar) {
			Notificacion temp = new Notificacion(this, guardar);
			ListaNotificaciones.add(temp);
			return temp;
		}
	}

	private Notificacion(Builder builder, Boolean guardar) {
		
		//Guardamos en memoria
		id = ListaNotificaciones.size() + 1;
		fecha = builder.fecha;
		alerta = builder.alerta;
	
		if (guardar){
			//Guardamos de manera persistente
			editor.putInt("numeroNotificaciones", ListaAlarmas.size()+1);
			editor.putLong("notificacionFecha" + id, fecha);
			editor.putInt("notificacionIdAlerta" + id, alerta.getId());
			
			editor.commit();
		}
	}

	public int getId() {
		return id;
	}
	
	public long getFecha() {
		return fecha;
	}

	public Alerta getAlerta() {
		return alerta;
	}
	
	public static void iniciarRegistro(Activity val){
		if(contexto==null){
			contexto = val.getApplicationContext();
			settings = contexto.getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();
			ListaNotificaciones.inicializar(settings);
		}
	}
}
