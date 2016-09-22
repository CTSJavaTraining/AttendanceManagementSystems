/**
 * 
 */
package com.attendance.client;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.entity.AttendanceDetails;
import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.serviceImpl.AttendanceServiceImpl;
import com.attendance.serviceImpl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;

/**
 * @author 542320 EmployeeAttendanceClient is a standalone java class using REST
 *         service to invoke the respective methods.
 *
 */
@ComponentScan
@EnableAutoConfiguration
@RestController
public class EmployeeAttendanceClient {

	static final Logger logger = Logger.getLogger(EmployeeAttendanceClient.class);

	ApplicationContext context = null;

	private static final String propertyFile = "Beans.xml";

	/**
	 * Method to receive ResponseEntity POST requests and processes it saving to
	 * database.
	 * 
	 * @param employee
	 *            JSON object passed as input to be inserted in DB.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/insertemployee", method = RequestMethod.POST)
	public ResponseEntity<Void> insertEmployees(@Valid @RequestBody Employee employee) {
		context = new ClassPathXmlApplicationContext(propertyFile);
		EmployeeServiceImpl empService = (EmployeeServiceImpl) context.getBean("employeeService");
		try {
			empService.insertEmployee(employee);
		} catch (Exception e) {

			logger.error(e);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * Method to receive ResponseEntity DELETE requests for the given employee
	 * ID and processes it saving to database.
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/deleteemployee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Employee> deleteEmployees(@PathVariable("id") EmployeeId id) {
		context = new ClassPathXmlApplicationContext(propertyFile);
		EmployeeServiceImpl empService = (EmployeeServiceImpl) context.getBean("employeeService");
		try {
			empService.deleteEmployee(id);
		} catch (Exception e) {

			logger.error(e);
		}
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Method to receive ResponseEntity POST requests to insert the swipein
	 * hours of the employee and processes it saving to database.
	 * 
	 * @param swipeIn
	 * @return
	 */

	@RequestMapping(value = "/insertswipein", method = RequestMethod.POST)
	public ResponseEntity<Void> insertSwipeInHours(@RequestBody AttendanceDetails swipeIn) {

		try {
			context = new ClassPathXmlApplicationContext(propertyFile);
			AttendanceServiceImpl attendanceService = (AttendanceServiceImpl) context.getBean("attendance");
			attendanceService.insertSwipeInHours(swipeIn);
		} catch (Exception e) {

			logger.error(e);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * Method to receive ResponseEntity POST requests to insert the swipeout
	 * hours of the employee and processes it saving to database.
	 * 
	 * @param swipeOut
	 * @return
	 */

	@RequestMapping(value = "/insertswipeout", method = RequestMethod.POST)
	public ResponseEntity<Void> insertSwipeOutHours(@RequestBody AttendanceDetails swipeOut) {
		context = new ClassPathXmlApplicationContext(propertyFile);
		AttendanceServiceImpl attendanceService = (AttendanceServiceImpl) context.getBean("attendance");
		try {
			attendanceService.insertSwipeOutHours(swipeOut);
		} catch (Exception e) {

			logger.error(e);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
