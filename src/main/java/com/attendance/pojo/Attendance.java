/**
 * 
 */
package com.attendance.pojo;

import java.util.Date;

/**
 * @author 523696
 *
 */
public class Attendance {
	
	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the accessCardNo
	 */
	public String getAccessCardNo() {
		return accessCardNo;
	}
	/**
	 * @param accessCardNo the accessCardNo to set
	 */
	public void setAccessCardNo(String accessCardNo) {
		this.accessCardNo = accessCardNo;
	}
	/**
	 * @return the swipeIn
	 */
	public Date getSwipeIn() {
		return swipeIn;
	}
	/**
	 * @param swipeIn the swipeIn to set
	 */
	public void setSwipeIn(Date swipeIn) {
		this.swipeIn = swipeIn;
	}
	/**
	 * @return the swipeOut
	 */
	public Date getSwipeOut() {
		return swipeOut;
	}
	/**
	 * @param swipeOut the swipeOut to set
	 */
	public void setSwipeOut(Date swipeOut) {
		this.swipeOut = swipeOut;
	}
	/**
	 * @return the totalHours
	 */
	public int getTotalHours() {
		return totalHours;
	}
	/**
	 * @param totalHours the totalHours to set
	 */
	public void setTotalHours(int totalHours) {
		this.totalHours = totalHours;
	}
	/**
	 * @return the machineId
	 */
	public String getMachineId() {
		return machineId;
	}
	/**
	 * @param machineId the machineId to set
	 */
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	private int employeeId;
	private String accessCardNo;
	private String machineId;
	private Date swipeIn;
	private Date swipeOut;
	private int totalHours;

}
