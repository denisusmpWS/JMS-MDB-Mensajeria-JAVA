package edu.cibertec.jaad.mdb;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;


//ESTE ACTUA COMO UN PROVEEDOR DE MENSAJES, NADA MAS NO COMO UN MDB
public class MDBNotasClient {
	
	private static final Logger LOG = Logger.getLogger(MDBNotasClient.class);
	//DEFINIMOS LOS OBJETOS ADMINISTRADOS (CONECCION FACTORY + DESTINO)
	private static final String CONNECTION_FACTORY_NAME ="jms/NotasMDBCF";
	private static final String QUEUE_NAME = "jms/NotasMDBTopic";
	
//CREAMOS UN METODO ENVIO DE MENSAJE()	
public void SendMessage(){
		
		try {
			/*BLOQUE I*/
			Context ctx = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup(CONNECTION_FACTORY_NAME);
			Connection connection = connectionFactory.createConnection();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = (Destination) ctx.lookup(QUEUE_NAME);
			
			connection.start();
			
			/*BLOQUE II*/
			/*CREAMOS EL MENSAJE DEL CLIENTE PRODUCTOR Y DIRECCIONAMOS AL TOPIC O TEMA COMO DESTINO*/
			MessageProducer messageProducer = session.createProducer(destination);
			
			/*SETEAMOS 20 MENSAJES, PARA ENVIAR EL CONTENIDO DE CADA UNO DE LOS MENSAJES*/
			for(int i = 0 ;i<20;i++){
			Nota nota = new Nota();
			/*INICIALIZO LOS VALORES*/
			nota.setNotaFinal(0);
			
			nota.setCiclo("CICLO VII");
			int cod=(int) (Math.random()*(9999 - 1111)+1111);
			nota.setCodigoAlumno("COD-"+cod);
			nota.setCodigoCurso("0512");
			nota.setNombreCurso("ARQUITEC DEVELOPER");
			
			
			List<Curso> modulos = new ArrayList<Curso>();
			modulos.add(new Curso((int)(Math.random()*(20 - 5)+5)));
			modulos.add(new Curso((int)(Math.random()*(20 - 5)+5)));
			modulos.add(new Curso((int)(Math.random()*(20 + 5)+5)));
			modulos.add(new Curso((int)(Math.random()*(20 + 5)+5)));
			nota.setModulos(modulos);
			
			/*CREAMOS EL OBJECTMESSAGE PARA ENVIAR EL CONTENIDO DEL MENSAJE*/
			ObjectMessage message = session.createObjectMessage(nota);
			/*ENVIAMOS UN FILTRO*/
			message.setStringProperty("OPERACION", "NOTAS");
			/*ENVIAMOS EL MESSAGE AL DESTINO DESDE EL MESSAGE PRODUCER*/
			messageProducer.send(message);
			/*MOSTRAMOS EL LOG DEL MENSAJE*/
			LOG.info("MENSAJE ENVIADO:["+message.getObject()+"]" );
			}
			
	
			//CERRAMOS TODAS LAS INSTANCIAS
			messageProducer.close();
			session.close();
			connection.close();
			
			System.exit(0);
			
			
		} catch (Exception e) {
			LOG.info("ERROR AL ENVIAR MENSAJE",e);
		}
	}
	
/*INVOCAMOS AL CLIENTE, A ESTA MISMA CLASE PARA ENVIO DE LOS MENSAJES*/
	public static void main(String[] args) {
		MDBNotasClient client = new MDBNotasClient();
		client.SendMessage();
	}
	
	

}
