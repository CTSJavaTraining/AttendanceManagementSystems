/**
 * 
 */
package com.attendance.service;


import com.attendance.exception.DAOException;
import com.attendance.pojo.EmployeeDetails;
import com.attendance.pojo.EmployeeMachineDetails;
import com.attendance.pojo.Location;

/**
 * @author 542320 Holds the method signature to be implemented by the
 *         EmployeeServiceImpl.
 */
public interface EmployeeService {

	/**
	 * 
	 * Sets the employee list as input and persists the records.
	 * 
	 * @param employee
	 */

	public void insertEmployee(EmployeeDetails employee) throws Exception;

	/**
	 * 
	 * Sets the employee id as input and search for the employee to deactivate.
	 * 
	 * @param empId
	 */

	public void deleteEmployee(int empId) throws DAOException;
	
	public void insertLocationDetails(Location locationDetails)throws Exception;
	
	public void mapEmployeeToMachine(EmployeeMachineDetails machineDetails)throws Exception;

}
