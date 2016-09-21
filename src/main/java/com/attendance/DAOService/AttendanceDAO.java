package com.attendance.DAOService;

import java.util.Date;
import java.util.List;
import com.attendance.entity.AttendanceDetails;
import com.attendance.exception.DAOException;

/**
 * 
 * @author 542320 Holds the method signature to be implemented by the
 *         AttendanceDAOImpl.
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

	public void getEmployee(int empId, String cardno) throws DAOException;

	/**
	 * 
	 * @return list of attendance details of the employee
	 * @throws Exception
	 */

	public List<AttendanceDetails> getAttendanceDetails() throws Exception;

	/**
	 * Sets the attendance details with swipe in time of the employee and
	 * inserts in DB.
	 * 
	 * @param employee
	 * @throws Exception
	 */

	public void insertSwipeInHours(AttendanceDetails employee) throws Exception;

	/**
	 * Sets the attendance details with swipe out time of the employee and
	 * inserts in DB.
	 * 
	 * @param employee
	 * @throws Exception
	 */
	public void insertSwipeOutHours(AttendanceDetails employee) throws Exception;

	/**
	 * Sets the swipe in and out time of the employee to calculate the total.
	 * hours logged for the day.
	 * 
	 * @param swipeInTime
	 * @param swipeOutTime
	 * @return
	 * @throws Exception
	 */

	public int calculateTotalHours(Date swipeInTime, Date swipeOutTime) throws Exception;

	/**
	 * Sets the total hours per day to calculate the average hours per week.
	 * 
	 * @param totalHours
	 * @return
	 * @throws Exception
	 */

	public int calculateWeekAverage(int totalHours) throws Exception;

	/**
	 * MachineId passed as input to validate whether employee has been mapped to
	 * the given machine.
	 * 
	 * @param machineId
	 * @throws Exception
	 */

	public void validateEmployeeMachineDetails(int empId,String machineId) throws DAOException;

}
