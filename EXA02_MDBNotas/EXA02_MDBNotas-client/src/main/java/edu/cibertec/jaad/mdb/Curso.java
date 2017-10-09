package edu.cibertec.jaad.mdb;

import java.io.Serializable;

public class Curso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nota;
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	@Override
	public String toString() {
		return "Curso [nota=" + nota + "]";
	}
	public Curso(int nota) {
		super();
		this.nota = nota;
	}
	public Curso() {
		super();
	}
	
	
	
	
}
