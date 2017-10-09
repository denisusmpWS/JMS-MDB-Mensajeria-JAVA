package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import edu.cibertec.jaad.jms.bean.Cliente;
import edu.cibertec.jaad.jms.bean.Prestamo;
import edu.cibertec.jaad.jms.mock.ClienteMock;
import edu.cibertec.jaad.jms.mock.PrestamoMock;


public class JMSReceiver implements MessageListener {

	private static final String JMS_QUEUE_IN = "jms/QueueINPrueba";
	private static final String JMS_CONNECTION_FACT = "jms/QueueCFPrueba";
	
	private static final Logger LOG = Logger.getLogger(JMSReceiver.class);
	
	private Session session;
	int cont = 0;
	int solAprobadas = 0;
	
	//Este metodo procesa y evalua el mensaje que llego
	@Override
	public void onMessage(Message message) {
		cont++;
		try {
			//Recibe el mensaje y muestra algunos valores
			ObjectMessage msg = (ObjectMessage) message;
			
			LOG.info("Objeto =[" + msg.toString() + "]");
			LOG.info("Objeto,getObject() =[" + msg.getObject() + "]");
			
			Prestamo prestamo = (Prestamo) msg.getObject();
			
			for (Cliente cliente : ClienteMock.getLstClientes()) {
				if(prestamo.getDni().equals(cliente.getDni())){
					if(prestamo.getTipoPrestamo().equals("P")){
						if(cliente.getDeuda()*4 > prestamo.getMonto()){
							for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
								if(prestamo.getDni().equals(oPrestamo.getDni())){
									oPrestamo.setEstado(false);
								}
							}
						}else{
							solAprobadas ++;
							for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
								if(prestamo.getDni().equals(oPrestamo.getDni())){
									oPrestamo.setEstado(true);
								}
							}
							Integer deudaActual = cliente.getDeuda() + prestamo.getMonto();
							cliente.setDeuda(deudaActual);
						}
					}else{
						double deuda = cliente.getDeuda() / 12.0;
						double cuota = prestamo.getMonto() * 0.1 / 12;
						double total = deuda + cuota;
						LOG.info("TOTAL:" + total);
						LOG.info("cliente.getDeuda():" + 0.6*cliente.getSueldo());
						if(total > 0.6 * cliente.getSueldo()){
							for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
								if(prestamo.getDni().equals(oPrestamo.getDni())){
									oPrestamo.setEstado(false);
								}
							}
						}else{
							solAprobadas ++;
							for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
								if(prestamo.getDni().equals(oPrestamo.getDni())){
									oPrestamo.setEstado(true);
								}
							}
							Integer deudaActual = cliente.getDeuda() + prestamo.getMonto();
							cliente.setDeuda(deudaActual);
						}
					}
				}
			}
			
			if(cont == 10){
				for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
					if(oPrestamo.isEstado()){
						LOG.info("Solicitud del cliente con dni " + oPrestamo.getDni()  + " fue aprobada");
					}
				}
				
				LOG.info("Solicitudes aprobadas:" + solAprobadas);
				
				for (Cliente oCliente : ClienteMock.getLstClientes()) {
					LOG.info("Deuda Actual del cliente con dni " + oCliente.getDni() + " es:" + oCliente.getDeuda());
				}
				
			}
			
		} catch (Exception e) {
			LOG.error("Error en recuperar el mensaje....", e);
		}
	}
	
	public void start(){
		try {
			//1.Conectarse al JMS para obtener una conexion
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) ctx.lookup(JMS_CONNECTION_FACT);
			Connection connection = factory.createConnection();
			
			//2.Crear una session JMS
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//3.Referenciar a la cola de mensajes el requerimiento y respuestas
			Destination colaIN = (Destination)ctx.lookup(JMS_QUEUE_IN); 
			
			//4.Iniciar la conexion con el proveedor JMS
			connection.start();
			
			//Establecer filtro
			String selector = "PRESTAMO = 'SOL'";
			
			//5.Crear el componente consumidor JMS. Usando el filtro necesario
			MessageConsumer consumer = session.createConsumer(colaIN, selector);
			
			//6. Se asigna como listener la clase actual para recibir los mensajes de una manera asíncrona
			consumer.setMessageListener(this);
			LOG.info("Esperando Mensaje......");
			
		} catch (Exception e) {
			LOG.error("Error al iniciar el lector", e);
		}
	}

	public static void main(String[] args) {
		JMSReceiver rs = new JMSReceiver();
		rs.start();
	}
	
}
