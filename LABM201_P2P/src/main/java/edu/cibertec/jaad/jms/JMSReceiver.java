package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;


public class JMSReceiver {
	
	private static Logger LOG=Logger.getLogger(JMSReceiver.class);
	private static String CONNECTION_FACTORY_QUEUE = "jms/QueueCF";
	private static String QUEUE_NAME = "jms/JAADQueue";

	
	public static void main(String[] args){
		
		try{
		
			Context ctx=new InitialContext();
			ConnectionFactory cnFactory=(ConnectionFactory) ctx.lookup(CONNECTION_FACTORY_QUEUE);
			Destination destination=(Destination) ctx.lookup(QUEUE_NAME);
			//PASO II: 
			Connection connection=cnFactory.createConnection();
			Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			
			/********* hasta aqui es igual*/
			MessageConsumer consumer=session.createConsumer(destination);
						ObjectMessage message;
			message=(ObjectMessage) consumer.receive();
			
			LOG.info("Mensaje RECIBIDO=[" +  message +"]");
			
			Profesor profesor=(Profesor) message.getObject();
			
			LOG.info("Profesor RECIBIDO=["+profesor+"]");
			
			consumer.close();
			session.close();
			connection.close();
			
			System.exit(0);
			
			
			
		}catch(Exception e){
			LOG.error("Error al procesar la metadata", e);
		}
		
		
		
		
	}
	
	/*PARA EJECUTAR EL ENVIO DEL MENSAJE:
	 * 1. EJECUITAR LA CLASE DE ENVIO SENDER, EL CUAL LO ENCOLA
	 * 2. EJECUTAR LA CLASE DE RECIVER PARA RECIBIR Y VER EL MENSAJE
	 * OBS1: SI EJECUTAS DE ARRANQUE EL REICBER TE SALDRA UN EXCEPTION YA QUE HASTA EL MOMENTO NO RECIBE NADA Y NO HAY NADA EN LA COLA
	 * OBS2: PREVIO SE HAN ENVIADO 3 MENSAJES:
	 * - AHORA PASAREMOS A RECIBIRLOS, ESTOS LLEGARAN DE FORMA ORDENADA TAL COMO SE FUERON ENVIADOS:
	 *  
	 * 1RA EJECUCION -> 1ER: JUAN - 002
	 * 2DA EJECUCION -> 2DO: MARIA - 003
	 * 3ER EJECUCION -> 3ER: ALAN - 004
	 * 
	 * POR ULTIMO COMO SOLO FUERON ENVIADOS 3 MENSAJES DESDE EL SENDER A LA 4TA EJECUCION NO HABRA EN LA COLA NINGUN MENSAJE POR LO QUE
	 * SE NOS APARECERA UN EXCEPTION: MENSAJE RECIBIDO =[NULL] 
	 * 
	 * */
	
	/* EN RESUMEN:
	 * -------------------------------------------------------------------------------------------
	 * 
	A. ENVIADOS DESDE EL SENDER.JAVA:
	1ER ENVIADO: JUAN - 002
	2DO ENVIADO: MARIA - 003
	3ER ENVIADO: ALAN - 004
	
	B. RECIBIDOS DESDE EL RECEIVER.JAVA:
	
	1RA EJECUCION -> 1ER: JUAN - 002
	2DA EJECUCION -> 2DO: MARIA - 003
	3ER EJECUCION -> 3ER: ALAN - 004
	*/
	
}
