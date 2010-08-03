package com.arpia49;

import java.util.Vector;

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
}
