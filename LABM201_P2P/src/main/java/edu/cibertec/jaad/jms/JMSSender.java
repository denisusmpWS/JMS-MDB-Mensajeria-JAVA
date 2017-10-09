package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;



public class JMSSender {

	private static Logger LOG=Logger.getLogger(JMSSender.class);
	private static String CONNECTION_FACTORY_QUEUE = "jms/QueueCF";
	private static String QUEUE_NAME = "jms/JAADQueue";
	
	public static void main(String[] args){
		
		try{
			//PASO 1: OBTENER EL JNDI Y ACCEDER
			Context ctx=new InitialContext();
			//PASO 2: OBTENER LA FACTORIA
			ConnectionFactory cnFactory=(ConnectionFactory) ctx.lookup(CONNECTION_FACTORY_QUEUE);
			//PASO 3: DEFINIR EL DESTINO
			Destination destination=(Destination) ctx.lookup(QUEUE_NAME);
			//PASO 4: CREAMOS LA CONEXION
			Connection connection=cnFactory.createConnection();
			//PASO 5: CREAMOS UNA SESSION
			Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//PASO 6: INICIAMOS LA CONEXION
			connection.start();
			/*HASTA AQUI TODO LAS SENTENCIAS SON IGUALES ENTRE EN SENDER Y EL RECEIVER*/
			//PASO 7: EL MENSAJE QUE PRODUCE LO ENVIARA COMO DESTINO AL OBJETO DESTINATION (SE LE ENVIA LOS OBJETOS QUE SE CONFIGURA EN EL CONECCTIONFACTORY)
			MessageProducer producer=session.createProducer(destination);
			//PASO 8: DEFINIMOS EL TIPO DE MENSAJE QUE USAREMOS
			ObjectMessage message=session.createObjectMessage();
			//PASO 9: LE ENVIAMOS UNA CLASE SERIALIZABLE DEPENDIENDO DEL OBJETO
			message.setObject(new Profesor("Liz","003"));
			//PASO 10: ENVIAMOS EL MENSAJE
			producer.send(message);
			
			LOG.info("Mensaje Enviado =["+message+"]");
			
			//PASO 11: CERRAMOS TODOS LAS INSTANCIAS ABIERTAS
			producer.close();
			session.close();
			connection.close();
			System.exit(0);
			
			
		}catch(Exception e){
			LOG.error("Error al procesar la metadata", e);
		}
		
		/*COMO PRUEBA DEL ENCOLAMIENTO:
		 * - HEMOS ENVIADO 3 MENSAJES PARA QUE SE GENERE LA COLA, PARA QUE AL MOMENTO DE RECIBIRLOS INGRESEN DE FORMA
		 * ORDENADA UNO X UNO DESDE EL PRIMERO QUE SE ENVIO HASTA EL ULTIMO
		 * 1ER ENVIADO: JUAN - 002
		 * 2DO ENVIADO: MARIA - 003
		 * 3ER ENVIADO: ALAN - 004
		 * 
		 * 
		 * */
		
		
	}
	
}
