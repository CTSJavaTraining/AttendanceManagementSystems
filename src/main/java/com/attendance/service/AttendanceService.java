package com.attendance.service;

import java.util.List;
import com.attendance.entity.AttendanceDetails;

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
	 * 
	 * Sets the list of attendance details of employee to insert in DB and
	 * persist.
	 * 
	 * @param attendance
	 */

	public void insertSwipeHours(List<AttendanceDetails> attendance);

}
