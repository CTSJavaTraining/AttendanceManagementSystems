package com.attendance.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * 
 * @author 542320 This class acts as the startup to autodeploy the war in
 *         tomcat.
 *
 */

@SpringBootApplication
@ImportResource("classpath:Beans.xml")
public class SpringBoot {

	private static final Logger logger = LoggerFactory.getLogger(SpringBoot.class);

	/**
	 * Passing the startup class as argument to automatically start the tomcat
	 * and build the war and deploy it .
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {

		logger.debug("Application going to start...");

		SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBoot.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
		logger.debug("Application started...");

	}
}
