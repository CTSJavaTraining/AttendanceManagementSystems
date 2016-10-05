package com.attendance.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.attendance.entity.AttendanceDetails;
import com.attendance.exception.DAOException;
import com.attendance.pojo.Attendance;

/**
 * 
 * @author 542320 Holds the method signature to be implemented by the
 *         AttendanceServiceImpl
 * 
 */
public interface AttendanceService {

	/**
	 * 
	 * Checks for the employee type and invokes the format of the file to be
	 * shown.
	 */

	public void exportToFile(int empId, Date startDate, Date endDate, String fileFormat)
			throws DAOException, FileNotFoundException, IOException, ParseException;

	/**
	 * 
	 * Gets the attendance details and saves it as excel file.
	 */
	public void exportToExcel(List<AttendanceDetails> attendanceDetails) throws FileNotFoundException, IOException;

	/**
	 * 
	 * Gets the attendance details and saves it as csv file.
	 */
	public void exportToCsv(List<AttendanceDetails> attendanceDetails) throws FileNotFoundException, IOException;

	/**
	 * Sets the attendance details with swipe in time of the employee and
	 * inserts in DB.
	 * 
	 * @param swipeIn
	 * @throws Exception
	 */

	public void insertSwipeInHours(Attendance swipeIn) throws DAOException;

	/**
	 * Sets the attendance details with swipe out time of the employee and
	 * inserts in DB.
	 * 
	 * @param swipeOut
	 * @throws Exception
	 */
	public void insertSwipeOutHours(Attendance swipeOut) throws DAOException;

}
