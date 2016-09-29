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
import com.attendance.entity.MachineDetails;
import com.attendance.exception.DAOException;
import com.attendance.pojo.EmployeeDetails;
import com.attendance.pojo.EmployeeMachineDetails;
import com.attendance.pojo.Location;
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
	   
	   employee.setLocationdetails(locationdetails);;

		employee.setFirstname(employeedetails.getFirstName().trim());

		employee.setLastname(employeedetails.getLastName().trim());

		employee.setStatus(employeedetails.getStatus().trim());

		employee.setUsertype(employeedetails.getUserType().trim());
		
		employee.setJoiningDate(Utility.getCurrentDate());

		employeeDAOImpl.insertEmployee(employee);

	}

	/**
	 * 
	 * Service method to invoke the deleteEmployee of DAO class.
	 * 
	 * @throws Exception
	 */

	@Override
	public void deleteEmployee(int empId) throws DAOException {
		
		logger.info("Fetching & Deleting User with id:{} ",empId);
		
		EmployeeId employeeId = new EmployeeId();
		employeeId.setEmployeeid(empId);
		employeeDAOImpl.deleteEmployee(employeeId);

	}

	@Override
	public void insertLocationDetails(Location locationDetails) throws Exception {
	
		LocationDetails locationInfo = new LocationDetails();
		locationInfo.setLocationId(locationDetails.getLocationId());
		locationInfo.setLocationName(locationDetails.getLocationName());
		locationInfo.setFloor(locationDetails.getFloor());
		locationInfo.setBlock(locationDetails.getBlock());
		locationInfo.setWing(locationDetails.getWing());
		locationInfo.setMachineName(Utility.generateAccessCard());
		employeeDAOImpl.insertLocationDetails(locationInfo);
	}

	@Override
	public void mapEmployeeToMachine(EmployeeMachineDetails machineDetails) throws Exception {
        
        MachineDetails machinedetails = new MachineDetails();
        LocationDetails location = new LocationDetails();
        Employee emp = new Employee();
        location.setLocationId(machineDetails.getLocationId());
        machinedetails.setLocationdetails(location);
        employeeDAOImpl.getMachineDetails(machinedetails);
        EmployeeId empid = new EmployeeId();
        empid.setEmployeeid(machineDetails.getEmployeeId());
        empid.setAccessCardno(machineDetails.getAccessCardNo());
        emp.setId(empid);
        machinedetails.setEmployee(emp);
        machinedetails.setActivationStatus("Yes");
        employeeDAOImpl.mapEmployeeToMachine(machinedetails);
		
	}

}
