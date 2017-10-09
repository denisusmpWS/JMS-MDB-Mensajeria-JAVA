package edu.cibertec.jaad.jms;

import java.io.Serializable;

public class Persona implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private int edad;
	private String dni;
	private String categoria;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", edad=" + edad + ", dni=" + dni + ", categoria=" + categoria + "]";
	}
	public Persona(String nombre, int edad, String dni, String categoria) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.dni = dni;
		this.categoria = categoria;
	}
	public Persona() {
		super();
	}
	
	
	
	
	
	
	
	
	
}
