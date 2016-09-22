package com.attendance.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Machinedetails generated by hbm2java
 */
@Entity
@Table(name = "machinedetails", catalog = "test")
public class MachineDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String machineId;
	private String location;
	private String machineType;
	private Set<AttendanceDetails> attendancedetailses = new HashSet<AttendanceDetails>();
	private Set<Employee> employees = new HashSet<Employee>();

	public MachineDetails() {
		/**
		 * 
		 */
	}

	/**
	 * 
	 * @param machineId
	 * @param location
	 * @param machineType
	 */

	public MachineDetails(String machineId, String location, String machineType) {
		this.machineId = machineId;
		this.location = location;
		this.machineType = machineType;
	}

	/**
	 * 
	 * @param machineId
	 * @param location
	 * @param machineType
	 * @param attendancedetailses
	 * @param employees
	 */
	public MachineDetails(String machineId, String location, String machineType,
			Set<AttendanceDetails> attendancedetailses, Set<Employee> employees) {
		this.machineId = machineId;
		this.location = location;
		this.machineType = machineType;
		this.attendancedetailses = attendancedetailses;
		this.employees = employees;
	}

	@Id

	@Column(name = "machine_id", unique = true, nullable = false, length = 20)
	public String getMachineId() {
		return this.machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	@Column(name = "location", nullable = false, length = 20)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "machine_type", nullable = false, length = 20)
	public String getMachineType() {
		return this.machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "machinedetails")
	public Set<AttendanceDetails> getAttendancedetailses() {
		return this.attendancedetailses;
	}

	public void setAttendancedetailses(Set<AttendanceDetails> attendancedetailses) {
		this.attendancedetailses = attendancedetailses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "machinedetails")
	public Set<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

}