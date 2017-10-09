package edu.cibertec.jaad.mdb.denis;

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


//CLASE PRODUCER MESSAGE
public class JMSSender {
	//DEFINIMOS LAS CONEXIONES CON EL JNDI (FACTORIA Y DESTINO) EN EL SERVIDOR GLASHFISH
	private static final String CONNECTION_FACTORY_NAME="jms/QueueMDBCF";
	private static final String QUEUE_NAME="jms/MDBQueue";
	
	private static final Logger LOG = Logger.getLogger(JMSSender.class);
	
	//CREAMOS EL METODO ENVIO DE MESAGGE
	public void sendMessage() {
		try {
			//1.Conectarse al JMS para obtener una conexion
			
			//1. CREAMOS UN CONTEXTO
			Context ctx = new InitialContext();
			//2. CREAMOS UN OBJETO CONECTION FACTORY
			ConnectionFactory factory = (ConnectionFactory) ctx.lookup(CONNECTION_FACTORY_NAME);
			//3. CREO UNA INSTANCIA DE CONEXION A TRAVES DE LA FACTORIA
			Connection connection = factory.createConnection();
			//4. CREO UNA SESION A TRAVES DE LA CONEXION Y DEFINO QUE ESTA TENGA UN ACUSE AUTOMATICO
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//5. DEFINO EL DESTINO A TRAVES DEL CONTEXTO Y SU METODO LOOKUP
			Destination colaIN = (Destination)ctx.lookup(QUEUE_NAME); //cola para enviar el mensaje
			//6. INICIO LA CONEXION
			connection.start();
			
			//7. CREO UN OBJETO PRODUCER MESSAGE Y LO ASOCIO A UN DESTINO
			MessageProducer producer = session.createProducer(colaIN);
			//8. CREO UN OBJECT MESSAGE COMO ELEMENTO MENSAJE A ENVIAR A TRAVES DE UNA SESION
			ObjectMessage msgReq = session.createObjectMessage();
			
			String id = null;
			//9. LLENO LOS MENSAJES
			for (Prestamo prestamo : PrestamoMock.getLstPrestamos()) {
				msgReq.setObject(prestamo);
				//se define un correlation id que actuara como identificador unico
				
				//paso 1: generamos un id random y lo convertimos a cadena
				id = UUID.randomUUID().toString();
				//seteamos el valor del correlation id del object message: ES EL IDENTIFICADOR DEL MENSAJE
				msgReq.setJMSCorrelationID(id);
				//definimos un filtro
				msgReq.setStringProperty("PRESTAMO", "SOL");
				//10. ENVIAMOS CADA MENSAJE AL MESSAGE PRODUCER EL CUAL ACTUARA COMO INTERMEDIARIO PARA EL ENVIO
				//DE LOS MENSAJES AL DESTINO CONFIGURADO.
				producer.send(msgReq);
			}
			
			LOG.info("Envio los mensajes....");
			//11. CERRAMOS TODAS LAS CONEXIONES
			producer.close();
			session.close();
			connection.close();
			
		} catch (Exception e) {
			LOG.error("Error al enviar los mensajes", e);
		}
	}
	
	//POR ULTIMO LLAMAMOS AL CLIENTE PARA PODER ENVIAR EL MENSAJE
	public static void main(String[] args){
		
		JMSSender client=new JMSSender();
		client.sendMessage();
		
		
	}

	
}
