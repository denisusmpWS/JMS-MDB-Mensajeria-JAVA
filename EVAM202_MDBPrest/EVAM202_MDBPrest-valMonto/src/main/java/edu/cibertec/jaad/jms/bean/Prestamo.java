package edu.cibertec.jaad.jms.bean;

import java.io.Serializable;

public class Prestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dni;
	private String tipoPrestamo;
	private Integer monto;
	private boolean estado;

	public Prestamo() {
	}

	public Prestamo(String dni, String tipoPrestamo, Integer monto, boolean estado) {
		this.dni = dni;
		this.tipoPrestamo = tipoPrestamo;
		this.monto = monto;
		this.estado = estado;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTipoPrestamo() {
		return tipoPrestamo;
	}

	public void setTipoPrestamo(String tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	public Integer getMonto() {
		return monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Prestamo [dni=" + dni + ", tipoPrestamo=" + tipoPrestamo + ", monto=" + monto + ", estado=" + estado
				+ "]";
	}

}
