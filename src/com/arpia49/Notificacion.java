package com.arpia49;

import java.text.SimpleDateFormat;

/**
 * @author  arpia49
 */
public class Notificacion {

	// Sin builder
	private int id;

	// Obligatorias
	private long fecha;
	private String nombre;
	String ubicacion;
	private int claveAlarma;

	public static class Builder {
		// Obligatorias
		private long fecha;
		private String nombre;
		private String ubicacion;
		private int claveAlarma;

		public Builder(long fecha, String nombre, String ubicacion, int claveAlarma) {
			this.fecha = fecha;
			this.nombre = nombre;
			this.ubicacion = ubicacion;
			this.claveAlarma = claveAlarma;
		}

		public Notificacion build(Boolean guardar) {
			Notificacion temp = new Notificacion(this, guardar);
			ListaNotificaciones.add(temp);
			return temp;
		}
	}

	private Notificacion(Builder builder, Boolean guardar) {
		// Guardamos en memoria
		id = ListaNotificaciones.size() + 1;
		fecha = builder.fecha;
		nombre = builder.nombre;
		ubicacion = builder.ubicacion;
		claveAlarma = builder.claveAlarma;
		if (guardar) {
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

	public int getClaveAlarma() {
		return claveAlarma;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(new SimpleDateFormat("dd-MM-yyyy H:mm:ss").format(fecha));
		result.append(" - ");
		result.append(nombre);
		if(ubicacion.compareTo("Sin ubicación")!=0){
			result.append(" - ");
			result.append(ubicacion);
		}
		return result.toString();
	}
}
