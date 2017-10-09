package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSPublisher {
	 
	private static final Logger LOG = Logger.getLogger(JMSPublisher.class);
	
	private static final int WAITING_MSG = 60;
	private static final String JMS_TOPIC = "jms/JAADTopic";
	private static final String JMS_CONNFACT = "jms/TopicCF";
	
	public static void main(String[] args) {

		try {
			//
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory)ctx.lookup(JMS_CONNFACT);
			Connection connection = factory.createConnection();
			
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination topic = (Destination)ctx.lookup(JMS_TOPIC);
			
			connection.start();
			
			MessageProducer producer = session.createProducer(topic);
			
			ObjectMessage msgReq = session.createObjectMessage();
			
			msgReq.setObject(null);
			msgReq.setObject(new Oferta("Recien Llegado",20.0d,"Television"));
			msgReq.setStringProperty("OPERACION", "oferta");
			producer.send(msgReq);
			
			LOG.info("Mensaje Enviado: "+msgReq);
			producer.close();
			connection.close();
			System.exit(0);
			
		} catch (Exception ex) {
			LOG.error("Error al enviar/recibir el mensaje", ex);
		}
	
	}
}
