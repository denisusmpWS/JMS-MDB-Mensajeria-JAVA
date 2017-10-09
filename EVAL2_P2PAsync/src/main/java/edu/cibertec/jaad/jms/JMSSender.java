package edu.cibertec.jaad.jms;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import edu.cibertec.jaad.jms.bean.Prestamo;
import edu.cibertec.jaad.jms.mock.PrestamoMock;


public class JMSSender {

	private static final String JMS_QUEUE_IN = "jms/QueueINPrueba";
	private static final String JMS_CONNECTION_FACT = "jms/QueueCFPrueba";
	
	private static final Logger LOG = Logger.getLogger(JMSSender.class);
		
	public static void main(String[] args) {
		try {
			//1.Conectarse al JMS para obtener una conexion
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) ctx.lookup(JMS_CONNECTION_FACT);
			Connection connection = factory.createConnection();
			
			//2.Crear una session JMS
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//3.Referenciar a la cola de mensajes el requerimiento y respuestas
			Destination colaIN = (Destination)ctx.lookup(JMS_QUEUE_IN); //cola para enviar el mensaje
			
			//4.Iniciar la conexion con el proveedor JMS
			connection.start();
			
			//5.Crear el componente productor JMS
			MessageProducer producer = session.createProducer(colaIN);
			ObjectMessage msgReq = session.createObjectMessage();
			
			String id = null;
			
			for (Prestamo prestamo : PrestamoMock.getLstPrestamos()) {
				msgReq.setObject(prestamo);
				id = UUID.randomUUID().toString();
				msgReq.setJMSCorrelationID(id);
				
				msgReq.setStringProperty("PRESTAMO", "SOL");
				producer.send(msgReq);
			}
			
			LOG.info("Envio los mensajes....");
			
			producer.close();
			session.close();
			connection.close();
			
		} catch (Exception e) {
			LOG.error("Error al enviar los mensajes", e);
		}
	}
	
}
