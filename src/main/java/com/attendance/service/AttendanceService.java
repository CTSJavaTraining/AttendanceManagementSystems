package com.attendance.service;


import java.util.List;

import com.attendance.entity.AttendanceDetails;
import com.attendance.entity.Employee;

/**
 * 
 * @author 542320 
 * Holds the method signature to be implemented by the  AttendanceServiceImpl
 *        
 */
public interface AttendanceService {

	/**
	 * 
	 * Gets the attendance details and saves it as excel file.
	 */

	public void exportToExcel();

	/**
	 * Sets the attendance details with swipe in time of the employee and
	 * inserts in DB.
	 * 
	 * @param swipeIn
	 * @throws Exception
	 */

	public void insertSwipeInHours(AttendanceDetails swipeIn) throws Exception;

	/**
	 * Sets the attendance details with swipe out time of the employee and
	 * inserts in DB.
	 * 
	 * @param swipeOut
	 * @throws Exception
	 */
	public void insertSwipeOutHours(AttendanceDetails swipeOut) throws Exception;
	


}
