/**
 * 
 */
package com.attendance.DAOService;

import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.entity.LocationDetails;
import com.attendance.entity.MachineDetails;
import com.attendance.exception.DAOException;
import com.attendance.pojo.EmployeeMachineDetails;

/**
 * @author 542320 Holds the method signature to be implemented by the
 *         EmployeeDAOImpl.
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
	 * 
	 * @param empId
	 */

	public void deleteEmployee(EmployeeId empId) throws DAOException;
	
	public void insertLocationDetails(LocationDetails location)throws Exception;
	
	public void mapEmployeeToMachine(MachineDetails machineDetails)throws Exception;
	
	public void getMachineDetails(MachineDetails machineDetails)throws Exception;

}
