/**
 * 
 */
package com.attendance.client;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.attendance.entity.Employee;
import com.attendance.serviceImpl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;

/**
 * @author 542320 
 * EmployeeAttendanceClient is a standalone java class using REST service to invoke the respective methods.
 *
 */
@ComponentScan
@EnableAutoConfiguration
@RestController
public class EmployeeAttendanceClient {

	static final Logger logger = Logger.getLogger(EmployeeAttendanceClient.class);

	/**
	 * Method to receive ResponseEntity POST requests and processes it saving to
	 * database.
	 * 
	 * @param employee JSON object passed as input to be inserted in DB.
	 *            
	 * @return
	 */
	@RequestMapping(value = "/employee/", method = RequestMethod.POST)
	public ResponseEntity<Void> insertEmployees(@RequestBody Employee employee) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		EmployeeServiceImpl empService = (EmployeeServiceImpl) context.getBean("employeeService");
		empService.insertEmployee(employee);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
