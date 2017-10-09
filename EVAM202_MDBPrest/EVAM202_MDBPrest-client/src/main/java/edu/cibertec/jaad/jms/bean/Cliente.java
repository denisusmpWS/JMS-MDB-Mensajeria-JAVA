package edu.cibertec.jaad.jms.bean;

public class Cliente {

	private String dni;
	private Integer sueldo;
	private Integer deuda;
	
	public Cliente() {}
	
	public Cliente(String dni, Integer sueldo, Integer deuda) {
		this.dni = dni;
		this.sueldo = sueldo;
		this.deuda = deuda;
	}

	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public Integer getSueldo() {
		return sueldo;
	}
	
	public void setSueldo(Integer sueldo) {
		this.sueldo = sueldo;
	}
	
	public Integer getDeuda() {
		return deuda;
	}
	
	public void setDeuda(Integer deuda) {
		this.deuda = deuda;
	}

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", sueldo=" + sueldo + ", deuda=" + deuda + "]";
	}
	
}
