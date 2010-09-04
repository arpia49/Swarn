package com.arpia49;

import android.content.SharedPreferences;

public class Configuracion {

	private static int radio;
	private static int muyFuerte;
	private static int fuerte;

	protected Configuracion() {
		// Exists only to defeat instantiation.
	}

	public static void inicializar(SharedPreferences settings) {
		radio = settings.getInt("configRadio", 2);
		fuerte = settings.getInt("configFuerte", 10);
		muyFuerte = settings.getInt("configMuyFuerte", 10);
}
	public static void setRadio(int val) {
		radio = val;
		Registro.guardarInt("configRadio", val);
	}

	public static void setFuerte(int val) {
		fuerte = val;
		Registro.guardarInt("configFuerte", val);
	}

	public static void setMuyFuerte(int val) {
		muyFuerte = val;
		Registro.guardarInt("configMuyFuerte", val);
	}

	public static int getRadio() {
		return radio;
	}

	public static int getFuerte() {
		return fuerte;
	}
	
	public static int getMuyFuerte() {
		return muyFuerte;
	}
}