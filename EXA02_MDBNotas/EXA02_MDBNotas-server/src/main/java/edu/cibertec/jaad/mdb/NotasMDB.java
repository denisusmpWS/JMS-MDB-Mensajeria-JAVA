package edu.cibertec.jaad.mdb;

import javax.annotation.PostConstruct;import javax.annotation.Resource;import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


import org.apache.log4j.Logger;

				//nombre de clase - nombre del jndi         - configuraciones de destino,tipo de destibo + filtro
@MessageDriven(name="NotasMDB", mappedName="jms/NotasMDBTopic", activationConfig ={
		@ActivationConfigProperty(propertyName="destinationType", propertyValue ="javax.jms.Topic"),
		@ActivationConfigProperty(propertyName="messageSelector", propertyValue ="OPERACION = 'NOTAS'")
})

public class NotasMDB implements MessageListener {
	

	private static final Logger LOG = Logger.getLogger(NotasMDB.class);
	
	@Resource(mappedName="jms/NotasMDBCF")
	private ConnectionFactory factory;
	
	private Connection connection;
	
	public void onMessage(Message msg) {
		
		ObjectMessage message = (ObjectMessage) msg;
		
		try {
			/*RECOGEMOS AL MENSAJE*/
			Nota nota = (Nota) message.getObject();
			
			int notas=0;
			/*RECORREMOS LA LISTA MODULOS DE NOTAS DE UN SOLO MENSAJE PARA SUMAR LAS NOTAS ENVIADAS, MENSAJE X MENSAJE*/
			for(Curso c :nota.getModulos()){
				notas=notas+c.getNota();
			}
			int promedio = notas/4;
			String resultado="";
			if(promedio>=14){
				resultado="APROBADO";
			}else{
				resultado="DESAPROBADO";
			}
			
			LOG.info("MENSAJE RECIBIDO:"+msg+"\n\n");
			LOG.info("\n\nDETALLE *********************************************\n\n");
			LOG.info("\nCODIGO DE ALUMNO:" +nota.getCodigoAlumno());
			LOG.info("\nCODIGO CURSO:"+nota.getCodigoCurso());
			LOG.info("\nNOMBRE CURSO:"+nota.getNombreCurso());
			LOG.info("\nSEMESTRE ACTUAL:"+nota.getCiclo());
			/*CON LA LISTA ENVIADA DESDE EL CLIENT PRODUCTOR SE PROMEDIA LA NOTA*/
			LOG.info("\nNOTA FINAL:"+promedio);
			LOG.info("\nESTADO ALUMNO:"+resultado);
			
			} catch (Exception e) {
			LOG.error("ERROR AL RECIBIR EL MENSAJE",e);
		}
	}
	
	/*EL MDB, a traves del messageListener inicia y finaliza de forma automatica
	 * la transaccion del mensaje. Invocando a los metodos INIT / DESTROY una vez finalizado el envio de los
	 * mensajes.
	 * */
	
	@PostConstruct
	public void init(){
		try {
			connection = factory.createConnection();
			connection.start();
			LOG.info("RECURSO INICIADO!");
		} catch (Exception e) {
			LOG.error("ERROR AL INICIAR MDB");
		}
	}
	
	@PostConstruct
	public void end(){
		try {
			connection.close();
			LOG.info("RECURSO DEVUELTO");
		} catch (Exception e) {
			LOG.error("ERROR AL ENVIAR RECURSOS...",e);
		}
	}
	
	
	

}