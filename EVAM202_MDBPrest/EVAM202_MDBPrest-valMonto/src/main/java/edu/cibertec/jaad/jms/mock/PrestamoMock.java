package edu.cibertec.jaad.jms.mock;

import java.util.ArrayList;
import java.util.List;

import edu.cibertec.jaad.jms.bean.Prestamo;

public class PrestamoMock {

	private static List<Prestamo> lstPrestamos = new ArrayList<Prestamo>();
	
	static{
		//Preferentes
		lstPrestamos.add(new Prestamo("001", "P", 3000,false));
		lstPrestamos.add(new Prestamo("002", "P", 2000,false));
		lstPrestamos.add(new Prestamo("003", "P", 3500,false));
		lstPrestamos.add(new Prestamo("004", "P", 5000,false));
		lstPrestamos.add(new Prestamo("005", "P", 1500,false));
		
		//Hipotecarios
		lstPrestamos.add(new Prestamo("006", "H", 300000,false));
		lstPrestamos.add(new Prestamo("007", "H", 250000,false));
		lstPrestamos.add(new Prestamo("008", "H", 150000,false));
		lstPrestamos.add(new Prestamo("009", "H", 185000,false));
		lstPrestamos.add(new Prestamo("010", "H", 220000,false));
		
	}
	
	public static List<Prestamo> getLstPrestamos() {
		return lstPrestamos;
	}

}
