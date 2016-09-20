/**
 * 
 */
package com.attendance.service;

import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;

/**
 * @author 542320 
 * Holds the method signature to be implemented by the EmployeeServiceImpl.
 */
public interface EmployeeService {

	/**
	 * 
	 * Sets the employee list as input and persists the records.
	 * 
	 * @param empList
	 */

	public void insertEmployee(Employee employee);

	/**
	 * 
	 * Sets the employee id as input and search for the employee to deactivate.
	 * 
	 * @param empId
	 */

	public void deleteEmployee(EmployeeId empId);
	
	/**
	 * Returns the random generated value.
	 * @return
	 */
	
	public String generateAccessCard();

}
