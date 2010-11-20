package com.arpia49;

/**
 * @author  arpia49
 */
public class Sonido {

	// Sin builder
	/**
	 * @uml.property  name="id"
	 */
	private int id;
	/**
	 * @uml.property  name="clave"
	 */
	private int clave;

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
	 * @uml.property  name="datos"
	 */
	private String datos;

	public static class Builder {
		// Obligatorios
		private String nombre;
		private String descripcion;
		private String datos;
		private int clave;

		public Builder(String nombre, String descripcion, String datos, int clave) {
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.datos = datos;
			this.clave = clave;
		}

//		public Builder clave(int val) {
//			clave = val;
//			return this;
//		}

		public Sonido build() {
			Sonido temp = new Sonido(this);
			ListaSonidos.add(temp);
			return temp;
		}
	}

	private Sonido(Builder builder) {

		// Guardamos en memoria
		id = ListaSonidos.size() + 1;
		clave = builder.clave;
		nombre = builder.nombre;
		descripcion = builder.descripcion;
		datos = builder.datos;

		Registro.guardarSonido(this);
	}

	/**
	 * @param val
	 * @uml.property  name="id"
	 */
	public void setId(int val) {
		id = val;
		Registro.guardarSonido(this);
	}

	/**
	 * @param val
	 * @uml.property  name="nombre"
	 */
	public void setNombre(String val) {
		nombre = val;
		Registro.guardarString("sonidoNombre" + id, val);
	}

	/**
	 * @param val
	 * @uml.property  name="descripcion"
	 */
	public void setDescripcion(String val) {
		descripcion = val;
		Registro.guardarString("sonidoDescripcion" + id, val);
	}

	public void setDatos(short[] val) {
		StringBuilder tempBuffer = new StringBuilder();
		for (int i = 0; i < 300; i++) {
			tempBuffer.append(Short.toString(val[i])).append(',');
		}
		Registro.guardarString("sonidoDescripcion" + id, tempBuffer.toString());
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
	 * @uml.property  name="datos"
	 */
	public String getDatos() {
		return datos;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(nombre);
		if (descripcion.compareTo("Sin descripción") != 0) {
			result.append(": ");
			result.append(descripcion);
		}
		return result.toString();
	}
}
