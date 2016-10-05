/**
 * 
 */
package com.attendance.pojo;

/**
 * @author 523696
 *
 */
public class EmployeeMachineDetails {

	private String machineName;
	private int locationId;
	private int employeeId;
	private String accessCardNo;
	private String activationStatus;

	/**
	 * @return the machineName
	 */
	public String getMachineName() {
		return machineName;
	}

	/**
	 * @param machineName
	 *            the machineName to set
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	/**
	 * @return the locationId
	 */
	public int getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
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
	 * @param accessCardNo
	 *            the accessCardNo to set
	 */
	public void setAccessCardNo(String accessCardNo) {
		this.accessCardNo = accessCardNo;
	}

	/**
	 * @return the activationStatus
	 */
	public String getActivationStatus() {
		return activationStatus;
	}

	/**
	 * @param activationStatus
	 *            the activationStatus to set
	 */
	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}

}
