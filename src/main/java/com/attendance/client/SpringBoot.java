package com.attendance.client;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.apache.log4j.Logger;


/**
 * 
 * @author 542320 This class acts as the startup to autodeploy the war in
 *         tomcat.
 *
 */
@ComponentScan
@SpringBootApplication

public class SpringBoot {

	static final Logger logger = Logger.getLogger(SpringBoot.class);

	/**
	 * Passing the startup class as argument to automatically start the tomcat
	 * and build the war and deploy it .
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {

		SpringApplication.run(SpringBoot.class, args);

	}
}
