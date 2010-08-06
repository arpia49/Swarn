package com.arpia49;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class Alerta {
	//Para persistencia
	private static Context contexto = null;	
	private static SharedPreferences settings = null;
	private static SharedPreferences.Editor editor = null;
	public static final String PREFS_NAME = "PrefTimbre";
		
	//Sin builder
	private int id;
	private boolean activada;

	//Obligatorias
	private String ubicacion;
	private int radio;
	private float latitud;
	private float longitud;
	private boolean muyFuerte;
	
	//Opcionales
	private boolean registrada;
	
	public static class Builder {
		
		//Sin builder
		private boolean activada;
		
		// Obligatorios
		private String ubicacion;
		private int radio;
		private float latitud;
		private float longitud;
		private boolean muyFuerte;
		
		// Opcionales
		private boolean registrada;

		public Builder(String ubicacion,int radio,float latitud,float longitud, boolean muyFuerte) {
			this.ubicacion=ubicacion;
			this.radio=radio;
			this.latitud=latitud;
			this.longitud=longitud;
			this.muyFuerte = muyFuerte;
		}

		public Builder activada(boolean val) {
			activada = val;
			return this;
		}

		public Builder registrada(boolean val) {
			registrada = val;
			return this;
		}

		public Alerta build(Boolean guardar) {
			Alerta temp = new Alerta(this, guardar);
			ListaAlertas.add(temp);
			return temp;
		}
	}
	
	private Alerta(Builder builder, Boolean guardar) {
		
		id=1;
		//Guardamos en memoria
		if(ListaAlertas.size()!=0){
			id = ListaAlertas.lastElement().id+1;
		}
		registrada = builder.registrada;
		activada = false;
		radio = builder.radio;
		latitud = builder.latitud;
		longitud = builder.longitud;
		ubicacion = builder.ubicacion;
		muyFuerte = builder.muyFuerte;
	
		if (guardar){

			//Guardamos de manera persistente
			editor.putInt("numeroAlertas", ListaAlertas.size()+1);
			editor.putInt("alertaId" + id, id);
			editor.putBoolean("alertaRegistrada" + id, registrada);
			editor.putBoolean("alertaActivada" + id, activada);
			editor.putInt("alertaRadio" + id, radio);
			editor.putFloat("alertaLatitud" + id, latitud);
			editor.putFloat("alertaLongitud" + id, longitud);
			editor.putString("alertaUbicacion" + id, ubicacion);
			editor.putBoolean("alertaMuyFuerte" + id, muyFuerte);
			
			editor.commit();
		}
	}

	public void setActivada(boolean val) {
		activada = val;

		//Guardamos de manera persistente
		editor.putBoolean("alertaActivada" + id, val);
		editor.commit();
	}

	public void setRegistrada(boolean val) {
		registrada = val;

		//Guardamos de manera persistente
		editor.putBoolean("alertaRegistrada" + id, val);
		editor.commit();
	}
	
	public int getId() {
		return id;
	}

	public boolean getActivada() {
		return activada;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public int getRadio() {
		return radio;
	}

	public float getLatitud() {
		return latitud;
	}

	public float getLongitud() {
		return longitud;
	}
	
	public boolean getRegistrada() {
		return registrada;
	}
	
	public boolean getMuyFuerte() {
		return muyFuerte;
	}

	public boolean conUbicacion() {
		return !(ubicacion.compareTo("Sin ubicación") == 0);
	}
	
	public static void iniciarRegistro(Activity val){
		if(contexto==null){
			contexto = val.getApplicationContext();
			settings = contexto.getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();
			ListaAlertas.inicializar(settings);
		}
	}
}