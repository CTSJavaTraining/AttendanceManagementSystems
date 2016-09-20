package com.attendance.DAOService;

import java.util.List;
import com.attendance.entity.AttendanceDetails;

/**
 * 
 * @author 542320 
 * Holds the method signature to be implemented by the AttendanceDAOImpl.
 *
 */

public interface AttendanceDAO {

	/**
	 * Sets the empId and Access Card No as input to fetch the respective
	 * employee.
	 * 
	 * @param empId
	 * @param cardno
	 * @throws Exception
	 */

	public void getEmployee(int empId, String cardno) throws Exception;

	/**
	 * 
	 * @return list of attendance details of the employee
	 * @throws Exception
	 */

	public List<AttendanceDetails> getAttendanceDetails() throws Exception;

	/**
	 * Sets the list of attendance details of the employee to insert in DB.
	 * 
	 * @param employee
	 * @throws Exception
	 */

	public void insertSwipeHours(List<AttendanceDetails> employee) throws Exception;

}
