package com.main.app;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

public class Main {

	private static final Logger logger = Logger.getLogger(Main.class);
	@SuppressWarnings("resource")
	public static void main(String args[]) {

		ApplicationContext context;
		context = new ClassPathXmlApplicationContext( "spring-config.xml" );
		MongoTemplate mongoTemplate =context.getBean("mongoTemplate",MongoTemplate.class);
		Socket s = null;
		ServerSocket ss2 = null;
		try {
			ss2 = new ServerSocket(10000);
			logger.info("created server --> server listening.....");
		} catch (Exception e) {
			//e.printStackTrace();
			logger.info("Cannot create server with port [ "+10000+" ] , PORT already in use!");
			System.exit(1);
		}
		while (true) {
			try {
				s = ss2.accept();
				logger.info("connection Established with --> "+ s.getRemoteSocketAddress());

				ServerThread st = new ServerThread(s,context,mongoTemplate);
				//logger.info("Thread status -- > "+ServerThread.currentThread().getState());
				st.start();
			}

			catch (Exception e) {
				
				logger.info("Connection Error  caused by [ " + e.getCause() + " ]");

			}
		}
		

	}

}
