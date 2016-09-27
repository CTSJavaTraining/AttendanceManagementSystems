/**
 * 
 */
package com.attendance.service;

import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.pojo.EmployeeDetails;

/**
 * @author 542320 Holds the method signature to be implemented by the
 *         EmployeeServiceImpl.
 */
public interface EmployeeService {

	/**
	 * 
	 * Sets the employee list as input and persists the records.
	 * 
	 * @param empList
	 */

	public void insertEmployee(EmployeeDetails employee) throws Exception;

	/**
	 * 
	 * Sets the employee id as input and search for the employee to deactivate.
	 * 
	 * @param empId
	 */

	public void deleteEmployee(EmployeeId empId) throws Exception;

}
