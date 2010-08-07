package com.arpia49;

import java.util.Vector;


import android.content.SharedPreferences;

public class ListaNotificaciones {
	private static Vector<Notificacion> listaNotificaciones = new Vector<Notificacion>();
	
	public static int size(){
		return listaNotificaciones.size();
	}

	public static void add(Notificacion val){
		listaNotificaciones.add(val);
	}

	public static void del(int val){
		listaNotificaciones.remove(val);
	}
	
	public static Notificacion elementAt(int val){
		return listaNotificaciones.elementAt(val);
	}

	public static Notificacion obtenerNotificacion(int id) {
		for (int i = 0; i < ListaNotificaciones.size(); i++) {
			if (elementAt(i).getId() == id) {
				return ListaNotificaciones.elementAt(i);
			}
		}
		return null;
	}

	public static void borrar() {
		listaNotificaciones.clear();
		actualizar();
	}
	
	public static void inicializar(SharedPreferences val){
		int total = val.getInt("numeroNotificaciones", 0);
		for(int i = 1; i<=total; i++){
			Notificacion nuevaNotificacion = new Notificacion.Builder(
					val.getLong("notificacionFecha"+ i,0),
					val.getString("notificacionNombre"+ i,""),
					val.getString("notificacionUbicacion"+ i,"")					)
					.build(false);
		}
	}

	public static void actualizar(){
		Notificacion.actualizar();
	}
}