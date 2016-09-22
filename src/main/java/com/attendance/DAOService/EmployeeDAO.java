/**
 * 
 */
package com.attendance.DAOService;

import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;

/**
 * @author 542320 
 * Holds the method signature to be implemented by the EmployeeDAOImpl.
 *
 */
public interface EmployeeDAO {

	/**
	 * Sets the list of employees as input to insert into DB and persist.
	 * 
	 * @param employee
	 * @throws Exception
	 */

	public void insertEmployee(Employee employee) throws Exception;

	/**
	 * Sets a particular employee ID as input to delete from DB.
	 * @param empId
	 */

	public void deleteEmployee(EmployeeId empId) throws Exception;

}
