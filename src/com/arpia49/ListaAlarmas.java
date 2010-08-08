package com.arpia49;

import java.util.Vector;


import android.content.SharedPreferences;

public class ListaAlarmas {
	private static Vector<Alarma> listaAlarmas = new Vector<Alarma>();

	public static int size(){
		return listaAlarmas.size();
	}

	public static void add(Alarma val){
		listaAlarmas.add(val);
	}

	public static void del(int id){
		for (int i = id; i < size(); i++) {
			ListaAlarmas.elementAt(i).setId(i);
		}
		listaAlarmas.remove(id-1);
		Registro.guardarInt("numeroAlarmas", size());
	}
	
	public static Alarma elementAt(int val){
		return listaAlarmas.elementAt(val);
	}

	public static int lastElementClave(){
		if(listaAlarmas.size()>0){
			return listaAlarmas.lastElement().getClave();
		}
		return 0;
	}

	public static Alarma obtenerAlarma(int id) {
		for (int i = 0; i < ListaAlarmas.size(); i++) {
			if (elementAt(i).getId() == id) {
				return ListaAlarmas.elementAt(i);
			}
		}
		return null;
	}	
	
	public static Alarma obtenerDesdeClave(int clave) {
		for (int i = 0; i < ListaAlarmas.size(); i++) {
			if (elementAt(i).getClave() == clave) {
				return elementAt(i);
			}
		}
		return null;
	}
	
	public static void inicializar(SharedPreferences val){
		int total = val.getInt("numeroAlarmas", 0);
		for(int i = 1; i<=total; i++){
			Alarma nuevaAlarma = new Alarma.Builder(
					val.getString("alarmaNombre"+ i,""),
					val.getString("alarmaDescripcion"+ i,""),
					val.getString("alarmaUbicacion"+ i,""),
					val.getInt("alarmaRadio"+ i,0),
					val.getFloat("alarmaLatitud"+ i, 0),
					val.getFloat("alarmaLongitud"+ i, 0),
					val.getBoolean("alarmaMuyFuerte"+ i, false)).
					registrada(val.getBoolean("alarmaRegistrada" + i, false)).
					marcada(val.getBoolean("alarmaMarcada"+ i, false)).
					activada(val.getBoolean("alarmaActivada"+ i, false)).
					clave(val.getInt("alarmaClave"+ i, 0))
					.build(false);
		}
	}
}