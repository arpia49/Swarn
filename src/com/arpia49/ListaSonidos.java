package com.arpia49;

import java.util.Vector;


import android.content.SharedPreferences;

public class ListaSonidos {
	private static int ultimaClave=0;
	
	private static Vector<Sonido> listaSonidos = new Vector<Sonido>();

	public static int size(){
		return listaSonidos.size();
	}

	public static void add(Sonido val){
		listaSonidos.add(val);
	}

	public static void del(int id){
		for (int i = id; i <= size(); i++) {
			ListaSonidos.element(i).setId(i-1);
		}
		listaSonidos.remove(id-1);
		Registro.guardarInt("numeroSonidos", size());
	}
	
	public static Sonido element(int id){
		return listaSonidos.elementAt(id-1);
	}

	public static Sonido elementAt(int val){
		return listaSonidos.elementAt(val);
	}

	public static int obtenerIdDesdeClave(int clave) {
		for (int i = 1; i <= ListaSonidos.size(); i++) {
			if (element(i).getClave() == clave) {
				return i;
			}
		}
		return 0;
	}	
	
	public static int obtenerClaveDesdeId(int id) {
		for (int i = 1; i <= ListaSonidos.size(); i++) {
			if (element(i).getId() == id) {
				return element(i).getClave();
			}
		}
		return 0;
	}	
	
	public static int siguienteClave(){
		ultimaClave++;
		Registro.guardarInt("sonidoUltimaClave", ultimaClave);
		return ultimaClave;
	}
	
	public static void inicializar(SharedPreferences val){
		ultimaClave = val.getInt("sonidoUltimaClave", 0);
		int total = val.getInt("numeroSonidos", 0);
		for(int i = 1; i<=total; i++){
			Sonido nuevoSonido = new Sonido.Builder(
					val.getString("sonidoNombre"+ i,""),
					val.getString("sonidoDescripcion"+ i,""),
					val.getString("sonidoDatos"+ i,""),
					val.getInt("sonidoClave"+ i, 0))
					.build();
		}
	}	
	
	public static String[] arrayString(){
		int total = listaSonidos.size()+1;
		String[] sonidos = new String[total];
		sonidos[0] = "Predeterminado";
		for(int i = 1; i<total; i++){
			sonidos[i] = element(i).getNombre();
		}
		return sonidos;
	}
	
	public static void borrar() {
		listaSonidos.clear();
		Registro.guardarInt("numeroSonidos", size());
	}
	
}