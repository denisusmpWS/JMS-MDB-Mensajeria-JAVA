package edu.cibertec.jaad.jms.mock;

import java.util.ArrayList;
import java.util.List;

import edu.cibertec.jaad.jms.bean.Cliente;

public class ClienteMock {

	private static List<Cliente> lstClientes = new ArrayList<Cliente>();
	
	static{
		lstClientes.add(new Cliente("001", 3500, 200));
		lstClientes.add(new Cliente("002", 4500, 500));
		lstClientes.add(new Cliente("003", 3500, 0));
		lstClientes.add(new Cliente("004", 5000, 500));
		lstClientes.add(new Cliente("005", 3500, 180));
		
		lstClientes.add(new Cliente("006", 10000, 1200));
		lstClientes.add(new Cliente("007", 8000, 600));
		lstClientes.add(new Cliente("008", 7500, 1800));
		lstClientes.add(new Cliente("009", 7000, 2400));
		lstClientes.add(new Cliente("010", 6500, 1800));
	}
	
	public static List<Cliente> getLstClientes() {
		return lstClientes;
	}
	
}
