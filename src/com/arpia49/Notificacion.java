package com.arpia49;

public class Notificacion {
	
	//Sin builder
	private int id;

	//Obligatorias
	private long fecha;
	private String nombre;
	private String ubicacion;
	
	public static class Builder {
		//Obligatorias
		private long fecha;
		private String nombre;
		private String ubicacion;

		public Builder(long fecha, String nombre, String ubicacion) {
			this.fecha = fecha;
			this.nombre = nombre;
			this.ubicacion = ubicacion;
		}

		public Notificacion build(Boolean guardar) {
			Notificacion temp = new Notificacion(this, guardar);
			ListaNotificaciones.add(temp);
			return temp;
		}
	}

	private Notificacion(Builder builder, Boolean guardar) {
		
		//Guardamos en memoria
		id = ListaNotificaciones.size() + 1;
		fecha = builder.fecha;
		nombre = builder.nombre;
		ubicacion = builder.ubicacion;
	
		if (guardar){
			Registro.guardarNotificacion(this);
		}
	}

	public int getId() {
		return id;
	}
	
	public long getFecha() {
		return fecha;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
}
