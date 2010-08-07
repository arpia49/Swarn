package com.arpia49;



import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Alarma {
	
	//Para persistencia
	private static Context contexto = null;	
	private static SharedPreferences settings = null;
	private static SharedPreferences.Editor editor = null;
	public static final String PREFS_NAME = "PrefTimbre";
		
	//Sin builder
	private int id;

	//Obligatorias
	private String nombre;
	private String descripcion;
	
	//Opcionales
	private boolean marcada;
	private boolean activada;
	private Alerta alerta;

	public static class Builder {
		// Obligatorios
		private String nombre;
		private String descripcion;

		// Opcionales
		private boolean marcada = false;
		private boolean activada = false;
		private Alerta alerta = null;

		public Builder(String nombre, String descripcion) {
			this.nombre = nombre;
			this.descripcion = descripcion;
		}

		public Builder marcada(boolean val) {
			marcada = val;
			return this;
		}

		public Builder activada(boolean val) {
			activada = val;
			return this;
		}

		public Builder alerta(Alerta val) {
			alerta = val;
			return this;
		}

		public Alarma build(Boolean guardar) {
			Alarma temp = new Alarma(this, guardar);
			ListaAlarmas.add(temp);
			return temp;
		}
	}

	private Alarma(Builder builder, Boolean guardar) {
		
		//Guardamos en memoria
		id = ListaAlarmas.size() + 1;
		nombre = builder.nombre;
		descripcion = builder.descripcion;
		marcada = builder.marcada;
		activada = builder.activada;
		alerta = builder.alerta;
	
		if (guardar){
			//Guardamos de manera persistente
			editor.putInt("numeroAlarmas", ListaAlarmas.size()+1);
			editor.putString("alarmaNombre" + id, nombre);
			editor.putString("alarmaDescripcion" + id, descripcion);
			editor.putBoolean("alarmaMarcada" + id, marcada);
			editor.putBoolean("alarmaActivada" + id, activada);
			editor.putInt("alarmaIdAlerta" + id, alerta.getId());
			
			editor.commit();
		}
	}

	public void setId(int val) {
		id = val;
		//Guardamos de manera persistente
		editor.putString("alarmaNombre" + id, nombre);
		editor.putString("alarmaDescripcion" + id, descripcion);
		editor.putBoolean("alarmaMarcada" + id, marcada);
		editor.putBoolean("alarmaActivada" + id, activada);
		editor.putInt("alarmaIdAlerta" + id, alerta.getId());
		
		editor.commit();
	}

	public void setMarcada(boolean val) {
		marcada = val;
		
		//Guardamos de manera persistente
		editor.putBoolean("alarmaMarcada" + id, val);
		editor.commit();
	}

	public void setActivada(boolean val) {
		activada = val;

		//Guardamos de manera persistente
		editor.putBoolean("alarmaActivada" + id, val);
		editor.commit();
	}

	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public boolean getMarcada() {
		return marcada;
	}

	public boolean getActivada() {
		return activada;
	}
	
	public Alerta getAlerta() {
		return alerta;
	}
	
	public static void iniciarRegistro(Activity val){
		if(contexto==null){
			contexto = val.getApplicationContext();
			settings = contexto.getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();
			ListaAlarmas.inicializar(settings);
		}
	}

	public static void actualizar(){
		editor.putInt("numeroAlarmas", ListaAlarmas.size());
		editor. commit();
	}
}
