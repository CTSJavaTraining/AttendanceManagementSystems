/**
 * 
 */
package com.attendance.client;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
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
import com.attendance.timer.DeleteTimer;

/**
 * @author 542320 EmployeeAttendanceClient is a standalone java class using REST
 *         service to invoke the respective methods.
 *
 */
@ComponentScan
@EnableAutoConfiguration
@RestController
public class EmployeeAttendanceClient {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeAttendanceClient.class);

	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	@Autowired
	private AttendanceServiceImpl attendanceServiceImpl;
	@Autowired
	private DeleteTimer deleteTimer;

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
		try {
			employeeServiceImpl.insertEmployee(employee);
		} catch (Exception e) {

			logger.error("", e);
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
		try {
			employeeServiceImpl.deleteEmployee(id);
		} catch (Exception e) {

			logger.error("", e);
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
			attendanceServiceImpl.insertSwipeInHours(swipeIn);
		} catch (Exception e) {

			logger.error("", e);
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
		try {
			attendanceServiceImpl.insertSwipeOutHours(swipeOut);
		} catch (Exception e) {

			logger.error("", e);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * 
	 * @return
	 */

	@RequestMapping(value = "/deletePastRecords", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAttendanceDetails() {
		try {
			deleteTimer.deleteAttendanceDetails();
		} catch (Exception e) {

			logger.error("", e);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
