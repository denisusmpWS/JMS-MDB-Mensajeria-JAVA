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

public class JMSSuscriber implements MessageListener{

	private static final Logger LOG = Logger.getLogger(JMSSuscriber.class);
	
	private static final String JMS_TOPIC = "jms/JAADTopic";
	private static final String JMS_CONNFACT = "jms/TopicCF";
	
	private String id;
	private Session session;
	
	public JMSSuscriber(String id){
		super();
		this.id=id;
	}
	
	public void start(){
		try {
			//1. Conectarse al JMS para obtener una conexion
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory)ctx.lookup(JMS_CONNFACT);
			Connection connection = factory.createConnection();
			
			//2. crear una session JMS
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//3. Refrencar a la cola de mensajes el requerimiento
			Destination topic = (Destination)ctx.lookup(JMS_TOPIC);
			
			//4. Iniciar la conexion con el proveedor JMS
			connection.start();
			
			//5 Consumir el mensaje
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(this);
			
			LOG.info("Esperando el mensaje =["+id+"]");
			
		} catch (Exception e) {
			LOG.error("Error al iniciar el lector de mensajes.");
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			ObjectMessage msg = (ObjectMessage) message;
			LOG.info("["+id+"] Recibido =¨["+msg.getObject()+"]");
		} catch (Exception e) {
			LOG.error("Error al RECIBIR EL MENSAJE");
		}
		
	}
	
	public static void main(String[] args) {
		JMSSuscriber rs = new JMSSuscriber("A");
		rs.start();
	}
			
}
