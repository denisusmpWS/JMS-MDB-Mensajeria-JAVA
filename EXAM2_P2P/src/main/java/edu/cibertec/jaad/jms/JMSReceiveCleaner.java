package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Message;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSReceiveCleaner {
	private static final Logger LOG = LoggerFactory.getLogger(JMSReceiveCleaner.class);
	private static final String JMS_JAAD_QUEUE = "jms/JAADQueueExam";
	private static final String JMS_QUEUE_CF = "jms/QueueCFExam";

	public void receiveMessage() {
		try {
			Context ctx = new InitialContext();
			ConnectionFactory cnFactory = (ConnectionFactory) ctx.lookup(JMS_QUEUE_CF);
			Connection connection = cnFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination queue = (Destination) ctx.lookup(JMS_JAAD_QUEUE);
			connection.start();

			// Creamos el consumidor
			MessageConsumer consumer = session.createConsumer(queue);

			Message message;
			while (true) {
				// Leyendo los mensajes de la cola
				message = consumer.receive(3000);
				if (message != null) {
					// LOG.info("Mensaje leido=[{}]", message.getText());
				} else {
					LOG.info("Sin mas mensajes");
					break;
				}
				Thread.sleep(1000);
			}

			// Cerrando conexiones
			consumer.close();
			session.close();
			connection.close();

		} catch (Exception ex) {
			LOG.error("Error al recibir el mensaje", ex);
		} finally {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		JMSReceiveCleaner receiver = new JMSReceiveCleaner();
		receiver.receiveMessage();
	}
}
