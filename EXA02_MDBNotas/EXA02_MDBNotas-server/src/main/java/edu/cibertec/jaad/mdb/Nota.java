package edu.cibertec.jaad.mdb;

import java.io.Serializable;
import java.util.List;

public class Nota implements Serializable {

	private static final long serialVersionUID = 1L;
	private int notaFinal;
	private String codigoAlumno;
	private String codigoCurso;
	private String nombreCurso;
	private String ciclo;
	private List<Curso> modulos;
	public int getNotaFinal() {
		return notaFinal;
	}
	public void setNotaFinal(int notaFinal) {
		this.notaFinal = notaFinal;
	}
	public String getCodigoAlumno() {
		return codigoAlumno;
	}
	public void setCodigoAlumno(String codigoAlumno) {
		this.codigoAlumno = codigoAlumno;
	}
	public String getCodigoCurso() {
		return codigoCurso;
	}
	public void setCodigoCurso(String codigoCurso) {
		this.codigoCurso = codigoCurso;
	}
	public String getNombreCurso() {
		return nombreCurso;
	}
	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}
	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	public List<Curso> getModulos() {
		return modulos;
	}
	public void setModulos(List<Curso> modulos) {
		this.modulos = modulos;
	}
	@Override
	public String toString() {
		return "Nota [notaFinal=" + notaFinal + ", codigoAlumno=" + codigoAlumno + ", codigoCurso=" + codigoCurso
				+ ", nombreCurso=" + nombreCurso + ", ciclo=" + ciclo + ", modulos=" + modulos + "]";
	}
	public Nota(int notaFinal, String codigoAlumno, String codigoCurso, String nombreCurso, String ciclo,
			List<Curso> modulos) {
		super();
		this.notaFinal = notaFinal;
		this.codigoAlumno = codigoAlumno;
		this.codigoCurso = codigoCurso;
		this.nombreCurso = nombreCurso;
		this.ciclo = ciclo;
		this.modulos = modulos;
	}
	public Nota() {
		super();
	}

	
	
	
	

}
