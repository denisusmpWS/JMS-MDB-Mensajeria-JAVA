package edu.cibertec.jaad.jms;

import java.io.Serializable;

public class Oferta implements Serializable{
	
	private static final long serialVersionUID= 1L;
	
	private String descripcion;
	private Double monto;
	private String producto;
	
	public Oferta() {
		// TODO Auto-generated constructor stub
	}	
	
	public Oferta(String descripcion, Double monto, String producto) {
		super();
		this.descripcion = descripcion;
		this.monto = monto;
		this.producto = producto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Oferta [descripcion=" + descripcion + ", monto=" + monto + ", producto=" + producto + "]";
	}
	
	
	
	
	
}
