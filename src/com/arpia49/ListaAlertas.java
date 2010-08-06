package com.arpia49;

import java.util.Vector;

import android.content.SharedPreferences;

public class ListaAlertas {
	private static Vector<Alerta> listaAlertas = new Vector<Alerta>();
	
	public static int size(){
		return listaAlertas.size();
	}

	public static void add(Alerta val){
		listaAlertas.add(val);
	}

	public static void del(int val){
		listaAlertas.remove(val);
	}

	public static Alerta elementAt(int val){
		return listaAlertas.elementAt(val);
	}
	
	public static Alerta lastElement(){
		return listaAlertas.lastElement();
	}

	public static Alerta obtenerAlerta(int id) {
		for (int i = 0; i < ListaAlertas.size(); i++) {
			if (elementAt(i).getId() == id) {
				return elementAt(i);
			}
		}
		return null;
	}
	
	public static void inicializar(SharedPreferences val){
		int total = val.getInt("numeroAlertas", 0);
		for(int i = 1; i<=total; i++){
			Alerta nuevaAlerta = new Alerta.Builder(
					val.getString("alertaUbicacion"+ i,""),
					val.getInt("alertaRadio"+ i,0),
					val.getFloat("alertaLatitud"+ i, 0),
					val.getFloat("alertaLongitud"+ i, 0),
					val.getBoolean("alertaMuyFuerte"+ i, false)).
					registrada(val.getBoolean("alertaRegistrada" + i, false)).
					activada(val.getBoolean("alertaActivada" + i, false))
					.build(false);
		}
	}
}