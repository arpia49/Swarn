package com.arpia49;

import android.app.Activity;

public class Alarma {
	private int id;
	private String nombre;
	private String descripcion;
	private boolean marcada;
	private boolean activada;
	private boolean alertaRegistrada;
	private boolean muyFuerte;
	private String ubicacion;
	private int radio;
	private float latitud;
	private float longitud;

	public static class Builder {
		// Obligatorios
		private String nombre;
		private String descripcion;
		private boolean muyFuerte;

		// Opcionales
		private boolean marcada = false;
		private boolean activada = false;
		private boolean alertaRegistrada = false;
		private String ubicacion = "";
		private int radio = 0;
		private float latitud = 0;
		private float longitud = 0;

		public Builder(String nombre, String descripcion, boolean muyFuerte) {
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.muyFuerte = muyFuerte;
		}

		public Builder marcada(boolean val) {
			marcada = val;
			return this;
		}

		public Builder activada(boolean val) {
			activada = val;
			return this;
		}

		public Builder alertaRegistrada(boolean val) {
			alertaRegistrada = val;
			return this;
		}

		public Builder radio(int val) {
			radio = val;
			return this;
		}

		public Builder ubicacion(String val) {
			ubicacion = val;
			return this;
		}

		public Builder latitud(float val) {
			latitud = val;
			return this;
		}

		public Builder longitud(float val) {
			longitud = val;
			return this;
		}

		public Alarma build() {
			Alarma temp = new Alarma(this);
			ListaAlarmas.add(temp);
			return temp;
		}
	}

	private Alarma(Builder builder) {
		id = ListaAlarmas.size() + 1;
		nombre = builder.nombre;
		descripcion = builder.descripcion;
		marcada = builder.marcada;
		activada = builder.activada;
		alertaRegistrada = builder.alertaRegistrada;
		muyFuerte = builder.muyFuerte;
		radio = builder.radio;
		latitud = builder.latitud;
		longitud = builder.longitud;
		ubicacion = builder.ubicacion;

		// Si hay internet rellenamos lat y long
	}

	public void setMarcada(boolean val) {
		marcada = val;
	}

	public void setActivada(boolean val) {
		activada = val;
	}

	public void setAlertaRegistrada(boolean val) {
		alertaRegistrada = val;
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

	public boolean getAlertaRegistrada() {
		return alertaRegistrada;
	}

	public boolean getMuyFuerte() {
		return muyFuerte;
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

	public boolean conUbicacion() {
		return !(radio == 0 || (latitud == 0 && longitud == 0 && ubicacion
				.compareTo("") == 0));
	}

	public static int numAlarmas() {
		return ListaAlarmas.size();
	}

	public static Alarma obtenerAlarma(int id) {
		for (int i = 0; i < ListaAlarmas.size(); i++) {
			if (ListaAlarmas.elementAt(i).id == id) {
				return ListaAlarmas.elementAt(i);
			}
		}
		return null;
	}
}
