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



public class JMSSender {
	
	private static final Logger LOG = Logger.getLogger(JMSSender.class);
	private static final String JMS_JAAD_QUEUE = "jms/QueuePersona";//COLA
	private static final String JMS_QUEUE_CF = "jms/QueuePersonaCF"; //FACTORIA COLA
	

	public static void main(String[] args) {
	
		try {

			Context contex = new InitialContext(); //CONTEXTO
			ConnectionFactory cxFactory = (ConnectionFactory) contex.lookup(JMS_QUEUE_CF);//FACTORIA
			Connection cx  = cxFactory.createConnection();//CONEXION

         
			Session sesion = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);//SESION
			
			Destination  colaIN = (Destination) contex.lookup(JMS_JAAD_QUEUE);//DESTINO

			cx.start();//INICIO    
			
			
			//DEFINO LA LOCALIZACION DE MI DESTINO AL MENSAJE PRODUCER
			MessageProducer produce = sesion.createProducer(colaIN);						
			
            ObjectMessage objMessaje = null;
            objMessaje = sesion.createObjectMessage();

        	   		   for(int a=0; a<30; a++){
            		           			 
            			  String cat="";
            			   int edad=(int) Math.floor(Math.random()*(100-14+1)+(14));
            			   if(edad>=14 && edad<18){
            			
            				  cat="ADOLESCENTE";
            		//objMessaje.setStringProperty("categoria", "ADOLESCENTE"); 
            				 
            			   }else if(edad>=18 && edad<=30){
            
            				   cat="JOVEN";
            		//objMessaje.setStringProperty("categoria", "JOVEN");            			
            			   }else if(edad>30 &&  edad<=50){
            				   
            				   cat="ADULTO JOVEN";
            		//objMessaje.setStringProperty("categoria","ADULTO JOVEN");
            				
            			   }else{
            				  
            				   cat="ADULTO MAYOR";
            
            				   //nivel x defecto = 4
            		//objMessaje.setStringProperty("categoria", "ADULTO MAYOR");
            				   
            			   }
            			//SETEAMOS EL OBJETO PARA ENVIARLE DATA X CADA OBJETO
            			objMessaje.setObject(new Persona("Persona "+a, edad, ""+a+1,cat));
            			//DEFINIMOS IDENTIFICADOR UNICO CORREATION ID PARA QUE EL MENSAJE SEA UNICO TIPO UN TOKEN EN RESTFUL
            			String id = UUID.randomUUID().toString();
            			//ASOCIA EL ULTIMO MENSAJE CON EL ANTERIOR TAL CUAL COMO VAN LLEGANDO, USO PARA ASINCRONO
               			objMessaje.setJMSCorrelationID(id); 
               			//DEFINIMOS EL FILTRO PARA NUESTRO SELECTOR
               			objMessaje.setStringProperty("OPERACIONES", "categoria"); 
               			               			
               			produce.send(objMessaje);
           			   
            		   }
            	 
            
            	LOG.info("ENVIANDO EL MENSAJE  A LA COLA.."+objMessaje); 		

            	produce.close(); 			
            	sesion.close();
            	cx.close();
            	System.exit(0);
            	
			} catch (Exception e){
				LOG.error("ERROR AL ENVIAR LA COLA...",e); 
		}
		
		
		
		
	}
}
