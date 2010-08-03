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

	public static void del(int val){
		listaAlarmas.remove(val);
	}
	
	public static Alarma elementAt(int val){
		return listaAlarmas.elementAt(val);
	}


	public static Alarma obtenerAlarma(int id) {
		for (int i = 0; i < ListaAlarmas.size(); i++) {
			if (elementAt(i).getId() == id) {
				return ListaAlarmas.elementAt(i);
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
					val.getBoolean("alarmaMuyFuerte"+ i, false)).
					marcada(val.getBoolean("alarmaMarcada"+ i, false)).
					activada(val.getBoolean("alarmaActivada"+ i, false)).
					alerta(ListaAlertas.obtenerAlerta(val.getInt("alarmaIdAlerta"+i, 0)))
					.build(false);
		}
	}
}