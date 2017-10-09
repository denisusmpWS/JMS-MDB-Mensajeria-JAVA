package edu.cibertec.jaad.mdb;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class MDBClient {
	private static final Logger LOG = Logger.getLogger(MDBClient.class);
	//PASO 1: DEFINIR VALOR DEL CONECTION FACTORIA
	private static final String CONNECTION_FACTORY_NAME = "jms/QueueMDBCF";
	//PASO 2. DEFINIR EL VALOR DEL DESTINO O CANAL
	private static final String QUEUE_NAME = "jms/MDBQueue";
	
	//PASO 3: DEFINIR EL METODO SENDMESSAGE (PARA EL ENVIO DEL MENSAJE)
	public void sendMessage(){
		try {
			//PASO 4: CREAR LA INICIALIZACION DE UN CONTEXTO (PROVEEDOR SERVER, GLASHFISH)
			Context ctx = new InitialContext();
			//PASO 5: CREAR EL CONECTION FACTORIA A TRAVES DEL METODO LOOKUP DEL CONTEXTO
			ConnectionFactory cf = (ConnectionFactory)ctx.lookup(CONNECTION_FACTORY_NAME);
			//PASO 6: ESTABLECER LA CONEXION A TRAVES DEL CONECTION FACTORY
			Connection connection = cf.createConnection();
			//PASO 7: CREAR UNA SESION PARA EL ENVIO DEL MESSAGE DE FORMA AUTOMATICA EN ACUSE
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//PASO 8: ESTABLECEMOS EL DESTINO A DONDE SE ENVIARAN LOS MENSAJES (COLA O TOPIC)
			Destination destination = (Destination)ctx.lookup(QUEUE_NAME);
			//PASO 9: INICIAMOS LA CONEXION
			connection.start();
			//PASO 10: CREAMOS UN MESSAGE PRODUCER A TRAVES DEL OBJETO SESSION Y DEFINIENDO SU DESTINO
			MessageProducer producer = session.createProducer(destination);
			//PASO 11: LLENAMOS LA DATA A TRAVES DEL OBJETO BEAN
			Orden orden = new Orden(1, "Parihuela", new Date(), 40.0, 5.0, 35.0, "RECIBIDO", "001");
			//PASO 12: CREAMOS UN OBJETO MESSAGE A TRAVES DE LA SESSION CAPTURANDO COMO VALOR AL OBJETO BEAN
			ObjectMessage message = session.createObjectMessage(orden);
			//PASO 13: A TRAVES DEL OBJECT MESSAGE PRODUCER SE ENVIARA EN OBJETO MESSAGE.
			producer.send(message);
			LOG.info("Mensaje enviado=[" + message.getObject() + "]");
			//PASO 14: CERRAMOS TODAS LAS CONEXIONES POSIBLES.
			producer.close();
			session.close();
			connection.close();
			
			System.exit(0);
		} catch (Exception e) {
			LOG.error("Error en Sender", e);
		}
	}
	
	//PASO 15: EJECUTAMOS LA CLASE CON UN MAIN PARA LA CREACION Y ENVIO DE LOS MENSAJES.
	public static void main(String[] args) {
		MDBClient client =new MDBClient();
		client.sendMessage();
	}
}
