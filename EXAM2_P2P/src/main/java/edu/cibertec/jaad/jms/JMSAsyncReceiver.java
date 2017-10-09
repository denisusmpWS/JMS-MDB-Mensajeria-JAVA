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





public class JMSAsyncReceiver implements MessageListener {
	
	private static final Logger LOG = Logger.getLogger(JMSAsyncReceiver.class);
	private static final String JMS_JAAD_QUEUE = "jms/QueuePersona";
	private static final String JMS_QUEUE_CF = "jms/QueuePersonaCF";
	
	private Session session;

	
	
	public void start(){
		try {
		
			Context contex = new InitialContext();
			ConnectionFactory cxFactory = (ConnectionFactory) contex.lookup(JMS_QUEUE_CF);
			Connection cx  = cxFactory.createConnection();

         
			session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination  colaIN = (Destination) contex.lookup(JMS_JAAD_QUEUE);
			cx.start();    
			//AÑADIMOS FILTRO DECLARADO PARA NUESTRO SELECTOR
			//String selector = "categoria = 'ADULTO MAYOR'";
			String selector = "OPERACIONES = 'categoria'";
			//CREAMOS EL CONSUMIDOR PARA ENVIAR COMO PARAMETRO AL FILTER YA DEFINIDO
			MessageConsumer consumer = session.createConsumer(colaIN,selector);
		
			
			//MessageConsumer consumer = session.createConsumer(colaIN);
			
			//LUEGO ASIGANAMOS A TRAVES DEL LISTENER QUE ESTA CLASE ESCUCHE PARA RECIBIR LOS MENSAJES DE FORMA ASINCRONA.
			consumer.setMessageListener(this);
			LOG.info("ESPERANDO MENSAJE ......"); 
			
			
		} catch (Exception e) {
           LOG.error("ERROR AL INICIAL LISTENING DE MENSAJE..",e); 
		}
	}
	

	private int contA=0;
	private int contB=0;
	private int contC=0;
	private int contD=0;
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
	try{
		
		ObjectMessage msg = (ObjectMessage) message;
		
		LOG.info("MENSAJE RECIBIDO.."+msg);       	
    	
    	Persona persona = (Persona) msg.getObject();       	
    	
    	LOG.info("MENSAJE PERSONA.."+persona); 
    
   
    	//edad>=14 && edad<18
    	if(persona.getEdad()>=14 && persona.getEdad()<18){
    		contA++;
    		//edad>=18 && edad<=30
    	}else if(persona.getEdad()>=18 && persona.getEdad()<=30){
    		contB++;
    		//edad>30 &&  edad<=50
    	}else if(persona.getEdad()>30 && persona.getEdad()<=50){
    		contC++;
    	}else{
    		contD++;
    	}
    	
    	LOG.info("\n\n ========================================DETALLE ==========================================\n\n" + persona + "\n NOMBRE:"+persona.getNombre()+"\n EDAD:"+persona.getEdad()+"\n DNI:"+persona.getDni()+"\n CATEGORIA:"+persona.getCategoria()+"\n\n");
    	
   
    	//LOG.info("\n\n******TOTALES SEGUN CATEGORIA ******\n\n NRO. DE ADOLESCENTE:"+contA+"\n NRO. DE JOVEN:"+contB+"\n NRO. DE ADULTO JOVEN:"+contC+"\n NRO. DE ADULTO MAYOR:"+contD+"\n Gracias por la espera...!");
    	
					
	} catch (Exception e) {
		LOG.info("ERROR AL ENVIAR EL MENSAJE",e); 
	}
	
		
  	LOG.info("\n\n******TOTALES SEGUN CATEGORIA ******\n\n NRO. DE ADOLESCENTE:"+contA+"\n NRO. DE JOVEN:"+contB+"\n NRO. DE ADULTO JOVEN:"+contC+"\n NRO. DE ADULTO MAYOR:"+contD+"\n Gracias por la espera...!");
		
	}
	
	
	
	public static void main(String[] args) {
		JMSAsyncReceiver rs = new JMSAsyncReceiver();
		rs.start();
	}
	
	
	
	
	
	
	
	


}
