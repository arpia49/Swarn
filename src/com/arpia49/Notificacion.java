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
	private String nombre;
	private String ubicacion;
	
	public static class Builder {
		//Obligatorias
		private long fecha;
		private String nombre;
		private String ubicacion;

		public Builder(long fecha, String nombre, String ubicacion) {
			this.fecha = fecha;
			this.nombre = nombre;
			this.ubicacion = ubicacion;
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
		nombre = builder.nombre;
		ubicacion = builder.ubicacion;
	
		if (guardar){
			//Guardamos de manera persistente
			editor.putInt("numeroNotificaciones", ListaNotificaciones.size()+1);
			editor.putLong("notificacionFecha" + id, fecha);
			editor.putString("notificacionNombre" + id, nombre);
			editor.putString("notificacionUbicacion" + id, ubicacion);
			
			editor.commit();
		}
	}

	public int getId() {
		return id;
	}
	
	public long getFecha() {
		return fecha;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	
	public static void iniciarRegistro(Activity val){
		if(contexto==null){
			contexto = val.getApplicationContext();
			settings = contexto.getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();
			ListaNotificaciones.inicializar(settings);
		}
	}
	
	public static void actualizar(){
		editor.putInt("numeroNotificaciones", ListaNotificaciones.size());
		editor. commit();
	}
}
