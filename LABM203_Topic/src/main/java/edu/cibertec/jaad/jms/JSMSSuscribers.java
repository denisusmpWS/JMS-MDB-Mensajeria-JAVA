package edu.cibertec.jaad.jms;

import org.apache.log4j.Logger;

public class JSMSSuscribers {

	private static final Logger LOG = Logger.getLogger(JMSSuscriber.class);
	
	private static final String JMS_TOPIC = "jms/JAADTopic";
	private static final String JMS_CONNFACT = "jms/TopicCF";
	
	public static void main(String[] args) {
		for(int i=0;i<3;i++){
			JMSSuscriber j = new JMSSuscriber("A");
			j.start();
		}
	}
}
