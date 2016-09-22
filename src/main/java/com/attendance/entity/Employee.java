package com.attendance.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Employee generated by hbm2java
 */
@Entity
@Table(name = "employee", catalog = "test")
public class Employee implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmployeeId id;
	private MachineDetails machinedetails;
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
	private Set<AttendanceDetails> attendancedetailses = new HashSet<AttendanceDetails>();

	public Employee() {
	}

	public Employee(EmployeeId id, MachineDetails machinedetails, String firstname, String lastname, String status,
			String usertype) {
		this.id = id;
		this.machinedetails = machinedetails;
		this.firstname = firstname;
		this.lastname = lastname;
		this.status = status;
		this.usertype = usertype;
	}

	/**
	 * 
	 * @param id
	 * @param machinedetails
	 * @param firstname
	 * @param lastname
	 * @param status
	 * @param usertype
	 * @param attendancedetailses
	 */
	public Employee(EmployeeId id, MachineDetails machinedetails, String firstname, String lastname, String status,
			String usertype, Set<AttendanceDetails> attendancedetailses) {
		this.id = id;
		this.machinedetails = machinedetails;
		this.firstname = firstname;
		this.lastname = lastname;
		this.status = status;
		this.usertype = usertype;
		this.attendancedetailses = attendancedetailses;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "machine_id", nullable = true)
	public MachineDetails getMachinedetails() {
		return this.machinedetails;
	}

	public void setMachinedetails(MachineDetails machinedetails) {
		this.machinedetails = machinedetails;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<AttendanceDetails> getAttendancedetailses() {
		return this.attendancedetailses;
	}

	public void setAttendancedetailses(Set<AttendanceDetails> attendancedetailses) {
		this.attendancedetailses = attendancedetailses;
	}

}