package com.arpia49;

public class Sonido {

	// Sin builder
	private int id;
	private int clave;

	// Obligatorias
	private String nombre;
	private String descripcion;
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

	public void setId(int val) {
		id = val;
		Registro.guardarSonido(this);
	}

	public void setNombre(String val) {
		nombre = val;
		Registro.guardarString("sonidoNombre" + id, val);
	}

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

	public int getId() {
		return id;
	}

	public int getClave() {
		return clave;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

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
