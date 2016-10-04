package com.attendance.dao.service;

import java.text.ParseException;
import java.time.LocalDate;
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

	public List<AttendanceDetails> getAttendanceDetails(LocalDate startDate, LocalDate endDate) throws Exception;

	public List<AttendanceDetails> getEmployeeType(int empId, LocalDate startDate, LocalDate endDate)
			throws DAOException, ParseException;

	/**
	 * Sets the attendance details with swipe in time of the employee and
	 * inserts in DB.
	 * 
	 * @param employee
	 * @throws Exception
	 */

	public void insertSwipeInHours(AttendanceDetails employee) throws DAOException;

	/**
	 * Sets the attendance details with swipe out time of the employee and
	 * inserts in DB.
	 * 
	 * @param employee
	 * @throws Exception
	 */
	public void insertSwipeOutHours(AttendanceDetails employee) throws DAOException;

	/**
	 * Sets the swipe in and out time of the employee to calculate the total.
	 * hours logged for the day.
	 * 
	 * @param swipeInTime
	 * @param swipeOutTime
	 * @return
	 * @throws Exception
	 */

	public String calculateTotalHours(Date swipeInTime, Date swipeOutTime) throws Exception;

	/**
	 * Sets the total hours per day to calculate the average hours per week.
	 * 
	 * @param totalHours
	 * @return
	 * @throws Exception
	 */

	public int calculateWeekAverage() throws Exception;

	/**
	 * MachineId passed as input to validate whether employee has been mapped to
	 * the given machine.
	 * 
	 * @param machineId
	 * @param empId
	 * @throws Exception
	 */

	public void validateEmployeeMachineDetails(int empId, String accessCardNo, String machineId) throws DAOException;

	/**
	 * Gets the employees inactive for past six months.
	 * 
	 * @throws Exception
	 */

	public List<Integer> getInactiveEmployees() throws Exception;

	/**
	 * Deletes the attendance details after six months for inactive employees.
	 * 
	 * @param employeeIds
	 * @throws Exception
	 */

	public void deleteAttendanceDetails(List<Integer> employeeIds) throws Exception;

}
