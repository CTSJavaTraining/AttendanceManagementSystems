package com.attendance.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Employee generated by hbm2java
 * 
 */
@Entity
@Table(name = "employee", catalog = "test")
public class Employee implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmployeeId id;

	@NotBlank(message = "First name must not be blank!")
	@Pattern(regexp = "[^A-Za-z0-9]", message = "Firstname should not contain special characters and numbers.Please enter a valid firstname.")
	private String firstname;
	@NotBlank(message = "Last name must not be blank!")
	@Pattern(regexp = "[^A-Za-z0-9]", message = "lastname should not contain special characters and numbers.Please enter a valid lastname.")
	private String lastname;
	@NotBlank(message = "Status must not be blank!")
	@Pattern(regexp = "[^A-Za-z0-9]", message = "Please enter the value in the format - 'ACTIVE' or 'INACTIVE'.")
	private String status;
	@NotBlank(message = "UserType must not be blank!")
	@Pattern(regexp = "[^A-Za-z0-9]", message = "Please enter the value in the format - 'Permanent' or 'Contract'.")
	private String usertype;
	//private Set<AttendanceDetails> attendancedetailses = new HashSet<AttendanceDetails>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locationId", nullable = false)
	private LocationDetails locationdetails;
	

	private Date joining_date;

	public Date getJoining_date() {
		return joining_date;
	}

	public void setJoining_date(Date joining_date) {
		this.joining_date = joining_date;
	}

	private Date relieving_date;

	public Date getRelieving_date() {
		return relieving_date;
	}

	public void setRelieving_date(Date relieving_date) {
		this.relieving_date = relieving_date;
	}

	public Employee() {
	}

	public Employee(EmployeeId id, LocationDetails locationdetails, String firstname, String lastname, String usertype,
			String status) {
		this.id = id;
		this.locationdetails = locationdetails;
		this.firstname = firstname;
		this.lastname = lastname;
		this.usertype = usertype;
		this.status = status;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "employeeid", column = @Column(name = "employeeid", nullable = false)),
			@AttributeOverride(name = "accessCardno", column = @Column(name = "access_cardno", nullable = false, length = 20)) })
	public EmployeeId getId() {
		return this.id;
	}

	public void setId(EmployeeId id) {
		this.id = id;
	}


	public LocationDetails getLocationDetails() {
		return this.locationdetails;
	}
	


	public void setLocationDetails(LocationDetails locationdetails) {
		this.locationdetails = locationdetails;
	}

	@Column(name = "firstname", nullable = false, length = 20)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", nullable = false, length = 20)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "usertype", nullable = false, length = 10)
	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	//public Set<AttendanceDetails> getAttendancedetailses() {
		//return this.attendancedetailses;
	//}

	//public void setAttendancedetailses(Set<AttendanceDetails> attendancedetailses) {
		//this.attendancedetailses = attendancedetailses;
	//}

}
