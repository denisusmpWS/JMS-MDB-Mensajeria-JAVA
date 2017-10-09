package edu.cibertec.jaad.mdb.denis;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;

import javax.jms.Message;

import javax.jms.MessageListener;
import javax.jms.ObjectMessage;



import org.apache.log4j.Logger;

import edu.cibertec.jaad.jms.bean.Cliente;
import edu.cibertec.jaad.jms.bean.Prestamo;
import edu.cibertec.jaad.jms.mock.ClienteMock;
import edu.cibertec.jaad.jms.mock.PrestamoMock;
//CLASE EJB
@MessageDriven(name="JMSReceiver", mappedName="jms/MDBQueue", activationConfig={
		//aqui va el KEY / VALUE
		@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Queue"),
		//A ESTA COLA LE VAMOS A INYECTAR LO ENVIADO DE OPERACION, CONSUMO
		@ActivationConfigProperty(propertyName="messageSelector",propertyValue="PRESTAMO='SOL'")
})
public class JMSReceiver implements MessageListener {

	//private static final String JMS_QUEUE_IN = "jms/QueueINPrueba";
	//private static final String JMS_CONNECTION_FACT = "jms/QueueCFPrueba";
	
	private static final Logger LOG = Logger.getLogger(JMSReceiver.class);
	
	@Resource(mappedName="jms/QueueMDBCF")
	private ConnectionFactory factory;
	private Connection connection;
	
	
	int cont = 0;
	int solAprobadas = 0;
	
	//Este metodo procesa y evalua el mensaje que llego
	
	public void onMessage(Message message) {
		cont++;
		//Recibe el mensaje y muestra algunos valores
		ObjectMessage msg = (ObjectMessage) message;
		try {
	
			
			LOG.info("Objeto =[" + msg.toString() + "]");
			LOG.info("Objeto,getObject() =[" + msg.getObject() + "]");
			
			Prestamo prestamo = (Prestamo) msg.getObject();
			
			for (Cliente cliente : ClienteMock.getLstClientes()) {
				if(prestamo.getDni().equals(cliente.getDni())){
					if(prestamo.getTipoPrestamo().equals("P")){
						if(cliente.getDeuda()*4 > prestamo.getMonto()){
							for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
								if(prestamo.getDni().equals(oPrestamo.getDni())){
									oPrestamo.setEstado(false);
								}
							}
						}else{
							solAprobadas ++;
							for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
								if(prestamo.getDni().equals(oPrestamo.getDni())){
									oPrestamo.setEstado(true);
								}
							}
							Integer deudaActual = cliente.getDeuda() + prestamo.getMonto();
							cliente.setDeuda(deudaActual);
						}
					}else{
						double deuda = cliente.getDeuda() / 12.0;
						double cuota = prestamo.getMonto() * 0.1 / 12;
						double total = deuda + cuota;
						LOG.info("TOTAL:" + total);
						LOG.info("cliente.getDeuda():" + 0.6*cliente.getSueldo());
						if(total > 0.6 * cliente.getSueldo()){
							for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
								if(prestamo.getDni().equals(oPrestamo.getDni())){
									oPrestamo.setEstado(false);
								}
							}
						}else{
							solAprobadas ++;
							for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
								if(prestamo.getDni().equals(oPrestamo.getDni())){
									oPrestamo.setEstado(true);
								}
							}
							Integer deudaActual = cliente.getDeuda() + prestamo.getMonto();
							cliente.setDeuda(deudaActual);
						}
					}
				}
			}
			
			if(cont == 10){
				for (Prestamo oPrestamo : PrestamoMock.getLstPrestamos()) {
					if(oPrestamo.isEstado()){
						LOG.info("Solicitud del cliente con dni " + oPrestamo.getDni()  + " fue aprobada");
					}
				}
				
				LOG.info("Solicitudes aprobadas:" + solAprobadas);
				
				for (Cliente oCliente : ClienteMock.getLstClientes()) {
					LOG.info("Deuda Actual del cliente con dni " + oCliente.getDni() + " es:" + oCliente.getDeuda());
				}
				
			}
			
		} catch (Exception e) {
			LOG.error("Error en recuperar el mensaje....", e);
		}
	}
	

	@PostConstruct
	public void init(){
		
		try{
			connection=factory.createConnection();
			connection.start();
			LOG.info("RECURSO INICIADO...!");			
		}catch(Exception e){
			LOG.error("ERROR AL INICIAR MDB...",e);
		}
		
		
	}
	
	
	@PostConstruct
	public void end(){
		
		try{
			connection.close();
			LOG.info("RECURSO DEVUELTO");			
		}catch(Exception e){
			LOG.error("ERROR AL LIBERAR RECURSOS...",e);
		}
		
		
	}
	
	
}
