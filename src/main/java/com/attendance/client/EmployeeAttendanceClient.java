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
import com.attendance.exception.DAOException;
import com.attendance.pojo.Attendance;
import com.attendance.pojo.EmployeeDetails;
import com.attendance.pojo.EmployeeMachineDetails;
import com.attendance.pojo.EmployeeType;
import com.attendance.pojo.Location;
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
	public ResponseEntity<String> insertEmployees(@Valid @RequestBody EmployeeDetails employee) {
		try {
			if (employee != null) {
				employeeServiceImpl.insertEmployee(employee);
			}
		} catch (Exception e) {

			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage + e);

			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Record Inserted Successfully", HttpStatus.CREATED);
	}

	/**
	 * Method to receive ResponseEntity DELETE requests for the given employee
	 * ID and processes it saving to database.
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/deleteemployee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteEmployees(@PathVariable("id") int id) {
		try {
			employeeServiceImpl.deleteEmployee(id);
		} catch (DAOException e) {

			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage + e);

			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Record Deleted Successfully", HttpStatus.NO_CONTENT);
	}

	/**
	 * Method to receive ResponseEntity POST requests to insert the swipein
	 * hours of the employee and processes it saving to database.
	 * 
	 * @param swipeIn
	 * @return
	 */

	@RequestMapping(value = "/insertswipein", method = RequestMethod.POST)
	public ResponseEntity<String> insertSwipeInHours(@RequestBody Attendance swipeIn) {

		try {
			if (swipeIn != null) {
				attendanceServiceImpl.insertSwipeInHours(swipeIn);
			}
		} catch (DAOException e) {
			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage + e);

			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Swipe In time Inserted Successfully", HttpStatus.CREATED);
	}

	/**
	 * Method to receive ResponseEntity POST requests to insert the swipeout
	 * hours of the employee and processes it saving to database.
	 * 
	 * @param swipeOut
	 * @return
	 */

	@RequestMapping(value = "/insertswipeout", method = RequestMethod.POST)
	public ResponseEntity<String> insertSwipeOutHours(@RequestBody AttendanceDetails swipeOut) {
		try {
			if (swipeOut != null) {
				attendanceServiceImpl.insertSwipeOutHours(swipeOut);
			}
		} catch (Exception e) {

			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage + e);

			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Swipe Out time Inserted Successfully", HttpStatus.CREATED);
	}

	/**
	 * 
	 * @return
	 */

	@RequestMapping(value = "/deletePastRecords", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAttendanceDetails() {
		try {
			deleteTimer.deleteAttendanceDetails();
		} catch (Exception e) {

			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage + e);

			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Employee Successfully deleted in DB", HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/insertlocationdetails", method = RequestMethod.POST)
	public ResponseEntity<String> insertLocationDetails(@RequestBody Location locationDetails) {
		try {
			if (locationDetails != null) {
				employeeServiceImpl.insertLocationDetails(locationDetails);
			}
		} catch (Exception e) {

			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage + e);

			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Location Details Successfully inserted in DB", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/insertmachinedetails", method = RequestMethod.POST)
	public ResponseEntity<String> insertMachineDetails(@RequestBody EmployeeMachineDetails machineDetails) {
		try {
			if (machineDetails != null) {
				employeeServiceImpl.mapEmployeeToMachine(machineDetails);
			}
		} catch (Exception e) {

			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage + e);

			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Machine Details Successfully inserted in DB", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/exportToFile", method = RequestMethod.POST)
	public ResponseEntity<String> exportToExcel(@RequestBody EmployeeType employeeType) {

		try {
			if (employeeType != null) {
				attendanceServiceImpl.exportToFile(employeeType.getEmpId(), employeeType.getStartDate(),
						employeeType.getEndDate(), employeeType.getFileFormat());
			}
		} catch (Exception e) {

			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage + e);

			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Export to Excel or CSV is done", HttpStatus.OK);

	}

}
