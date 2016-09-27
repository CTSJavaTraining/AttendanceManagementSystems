/**
 * 
 */
package com.attendance.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.attendance.DAOServiceImpl.EmployeeDAOImpl;
import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.entity.LocationDetails;
import com.attendance.pojo.EmployeeDetails;
import com.attendance.service.EmployeeService;
import com.attendance.util.Utility;

/**
 * @author 542320 EmployeeServiceImpl holds the implementation of methods used
 *         in EmployeeDAO interface.
 * 
 */
@Component
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
	
	@Autowired
	private EmployeeDAOImpl employeeDAOImpl;

	/**
	 * 
	 * Service method to invoke the insertEmployee of DAO class.
	 * 
	 * @throws Exception
	 */

	@Override
	public void insertEmployee(EmployeeDetails employeedetails) throws Exception {
		
	   Employee employee = new Employee();
	   
	   EmployeeId employeeId = new EmployeeId();
	   
	   LocationDetails locationdetails = new LocationDetails();
	   
	   employeeId.setEmployeeid(employeedetails.getEmployeeId());
	   
	   employeeId.setAccessCardno(Utility.generateAccessCard());
	   
	   employee.setId(employeeId);
	   
	   locationdetails.setLocationId(employeedetails.getLocationId());
	   
	   employee.setLocationDetails(locationdetails);

		employee.setFirstname(employeedetails.getFirstName().trim());

		employee.setLastname(employeedetails.getLastName().trim());

		employee.setStatus(employeedetails.getStatus().trim());

		employee.setUsertype(employeedetails.getUserType().trim());

		employeeDAOImpl.insertEmployee(employee);

	}

	/**
	 * 
	 * Service method to invoke the deleteEmployee of DAO class.
	 * 
	 * @throws Exception
	 */

	@Override
	public void deleteEmployee(EmployeeId empId) throws Exception {

		employeeDAOImpl.deleteEmployee(empId);

	}

}
