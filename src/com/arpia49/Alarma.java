package com.arpia49;

/**
 * @author  arpia49
 */
public class Alarma {

	// Sin builder
	/**
	 * @uml.property  name="id"
	 */
	private int id;

	// Obligatorias
	/**
	 * @uml.property  name="nombre"
	 */
	private String nombre;
	/**
	 * @uml.property  name="descripcion"
	 */
	private String descripcion;
	/**
	 * @uml.property  name="ubicacion"
	 */
	private String ubicacion;
	/**
	 * @uml.property  name="latitud"
	 */
	private float latitud;
	/**
	 * @uml.property  name="longitud"
	 */
	private float longitud;
	/**
	 * @uml.property  name="muyFuerte"
	 */
	private boolean muyFuerte;
	/**
	 * @uml.property  name="clave"
	 */
	private int clave;
	/**
	 * @uml.property  name="claveSonido"
	 */
	private int claveSonido;

	// Opcionales
	/**
	 * @uml.property  name="marcada"
	 */
	private boolean marcada; // Tiene el tic
	/**
	 * @uml.property  name="activada"
	 */
	private boolean activada; // Está el engine corriendo
	/**
	 * @uml.property  name="registrada"
	 */
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

	/**
	 * @param val
	 * @uml.property  name="id"
	 */
	public void setId(int val) {
		id = val;
		Registro.guardarAlarma(this);
	}

	/**
	 * @param val
	 * @uml.property  name="nombre"
	 */
	public void setNombre(String val) {
		nombre = val;
		Registro.guardarString("alarmaNombre" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="descripcion"
	 */
	public void setDescripcion(String val) {
		descripcion = val;
		Registro.guardarString("alarmaDescripcion" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="registrada"
	 */
	public void setRegistrada(boolean val) {
		registrada = val;
		Registro.guardarBoolean("alarmaRegistrada" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="marcada"
	 */
	public void setMarcada(boolean val) {
		marcada = val;
		Registro.guardarBoolean("alarmaMarcada" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="activada"
	 */
	public void setActivada(boolean val) {
		activada = val;
		Registro.guardarBoolean("alarmaActivada" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="ubicacion"
	 */
	public void setUbicacion(String val) {
		ubicacion = val;
		Registro.guardarString("alarmaUbicacion" + id, val);
	}
	
	/**
	 * @param val
	 * @uml.property  name="latitud"
	 */
	public void setLatitud(float val) {
		latitud = val;
		Registro.guardarFloat("alarmaLatitud" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="longitud"
	 */
	public void setLongitud(float val) {
		longitud = val;
		Registro.guardarFloat("alarmaLongitud" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="muyFuerte"
	 */
	public void setMuyFuerte(boolean val) {
		muyFuerte = val;
		Registro.guardarBoolean("alarmaMuyFuerte" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="claveSonido"
	 */
	public void setClaveSonido(int val) {
		claveSonido = val;
		Registro.guardarInt("alarmaClaveSonido" + id, val);
	}

	/**
	 * @return
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return
	 * @uml.property  name="clave"
	 */
	public int getClave() {
		return clave;
	}
	
	/**
	 * @return
	 * @uml.property  name="claveSonido"
	 */
	public int getClaveSonido() {
		return claveSonido;
	}

	/**
	 * @return
	 * @uml.property  name="ubicacion"
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * @return
	 * @uml.property  name="latitud"
	 */
	public float getLatitud() {
		return latitud;
	}

	/**
	 * @return
	 * @uml.property  name="longitud"
	 */
	public float getLongitud() {
		return longitud;
	}

	/**
	 * @return
	 * @uml.property  name="registrada"
	 */
	public boolean getRegistrada() {
		return registrada;
	}

	/**
	 * @return
	 * @uml.property  name="muyFuerte"
	 */
	public boolean getMuyFuerte() {
		return muyFuerte;
	}

	/**
	 * @return
	 * @uml.property  name="nombre"
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return
	 * @uml.property  name="descripcion"
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return
	 * @uml.property  name="marcada"
	 */
	public boolean getMarcada() {
		return marcada;
	}

	/**
	 * @return
	 * @uml.property  name="activada"
	 */
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
