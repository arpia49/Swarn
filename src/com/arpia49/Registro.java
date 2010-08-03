package com.arpia49;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Registro {

	private static Context contexto=null;	
	// Preferencias en el sistema
	private static SharedPreferences settings = null;
	private static SharedPreferences.Editor editor = null;
	public static final String PREFS_NAME = "PrefTimbre";
	
	public Registro(){}
	public static void iniciarRegistro(Activity val){
		if(contexto==null){
			contexto = val.getApplicationContext();
			settings = contexto.getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();
		}
	}
	public void add(Alarma val) {
	}
}
