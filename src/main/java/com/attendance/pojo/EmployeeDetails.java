package com.attendance.pojo;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author 559207
 *
 */
public class EmployeeDetails {

	private int employeeId;
	private String accessCardNo;
	private int locationId;
	@NotBlank(message = "Firstname must not be blank!")
	@Pattern(regexp = "[a-zA-Z]*", message = "Firstname should not contain special characters and numbers.Please enter a valid firstname.")
	private String firstName;
	@NotBlank(message = "Lastname must not be blank!")
	@Pattern(regexp = "[a-zA-Z]*", message = "Lastname should not contain special characters and numbers.Please enter a valid lastname.")
	private String lastName;
	@NotBlank(message = "Usertype must not be blank!")
	@Pattern(regexp = "[a-zA-Z]*", message = "Please enter the value in the format - 'Permanent' or 'Contract'.")
	private String userType;
	@NotBlank(message = "Status must not be blank!")
	@Pattern(regexp = "[a-zA-Z]*", message = "Please enter the value in the format - 'ACTIVE' or 'INACTIVE'.")
	private String status;
	private Date joiningDate;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getAccessCardNo() {
		return accessCardNo;
	}

	public void setAccessCardNo(String accessCardNo) {
		this.accessCardNo = accessCardNo;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the joiningDate
	 */
	public Date getJoiningDate() {
		return joiningDate;
	}

	/**
	 * @param joiningDate
	 *            the joiningDate to set
	 */
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

}
