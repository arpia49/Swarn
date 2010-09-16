package com.arpia49;

public class Alarma {

	// Sin builder
	private int id;

	// Obligatorias
	private String nombre;
	private String descripcion;
	private String ubicacion;
	private float latitud;
	private float longitud;
	private boolean muyFuerte;
	private int clave;
	private int claveSonido;

	// Opcionales
	private boolean marcada; // Tiene el tic
	private boolean activada; // Está el engine corriendo
	private boolean registrada; // La alerta de proximidad está metida

	public static class Builder {
		// Obligatorios
		private String nombre;
		private String descripcion;
		private String ubicacion;
		private float latitud;
		private float longitud;
		private boolean muyFuerte;
		private int clave;
		private int claveSonido;

		// Opcionales
		private boolean marcada = false;
		private boolean activada = false;
		private boolean registrada;

		public Builder(String nombre, String descripcion, String ubicacion, float latitud, float longitud, boolean muyFuerte,
				int clave, int claveSonido) {
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.ubicacion = ubicacion;
			this.latitud = latitud;
			this.longitud = longitud;
			this.clave = clave;
			this.claveSonido = claveSonido;
		}

//		public Builder clave(int val) {
//			clave = val;
//			return this;
//		}
//		
//		public Builder claveSonido(int val) {
//			claveSonido = val;
//			return this;
//		}

		public Builder registrada(boolean val) {
			registrada = val;
			return this;
		}

		public Builder marcada(boolean val) {
			marcada = val;
			return this;
		}

		public Builder activada(boolean val) {
			activada = val;
			return this;
		}

		public Alarma build(Boolean guardar) {
			Alarma temp = new Alarma(this, guardar);
			ListaAlarmas.add(temp);
			return temp;
		}
	}

	private Alarma(Builder builder, Boolean guardar) {

		// Guardamos en memoria
		id = ListaAlarmas.size() + 1;
		clave = builder.clave;
		nombre = builder.nombre;
		descripcion = builder.descripcion;
		marcada = builder.marcada;
		activada = builder.activada;
		registrada = builder.registrada;
		latitud = builder.latitud;
		longitud = builder.longitud;
		ubicacion = builder.ubicacion;
		muyFuerte = builder.muyFuerte;
		claveSonido = builder.claveSonido;

		if (guardar) {
			Registro.guardarAlarma(this);
		}
	}

	public void setId(int val) {
		id = val;
		Registro.guardarAlarma(this);
	}

	public void setNombre(String val) {
		nombre = val;
		Registro.guardarString("alarmaNombre" + id, val);
	}

	public void setDescripcion(String val) {
		descripcion = val;
		Registro.guardarString("alarmaDescripcion" + id, val);
	}

	public void setRegistrada(boolean val) {
		registrada = val;
		Registro.guardarBoolean("alarmaRegistrada" + id, val);
	}

	public void setMarcada(boolean val) {
		marcada = val;
		Registro.guardarBoolean("alarmaMarcada" + id, val);
	}

	public void setActivada(boolean val) {
		activada = val;
		Registro.guardarBoolean("alarmaActivada" + id, val);
	}

	public void setUbicacion(String val) {
		ubicacion = val;
		Registro.guardarString("alarmaUbicacion" + id, val);
	}
	
	public void setLatitud(float val) {
		latitud = val;
		Registro.guardarFloat("alarmaLatitud" + id, val);
	}

	public void setLongitud(float val) {
		longitud = val;
		Registro.guardarFloat("alarmaLongitud" + id, val);
	}

	public void setMuyFuerte(boolean val) {
		muyFuerte = val;
		Registro.guardarBoolean("alarmaMuyFuerte" + id, val);
	}

	public int getId() {
		return id;
	}

	public int getClave() {
		return clave;
	}
	
	public int getClaveSonido() {
		return claveSonido;
	}

	public String getUbicacion() {
		return ubicacion;
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

	public boolean conUbicacion() {
		return !(ubicacion.compareTo("Sin ubicación") == 0);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(nombre);
		if (descripcion.compareTo("Sin descripción") != 0) {
			result.append(": ");
			result.append(descripcion);
		}
		if (conUbicacion()) {
			result.append(" - ");
			result.append(ubicacion);
		}
		return result.toString();
	}
}
