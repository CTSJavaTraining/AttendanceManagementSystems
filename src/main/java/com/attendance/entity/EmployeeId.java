package com.attendance.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * EmployeeId generated by hbm2java
 */
@Embeddable
public class EmployeeId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int employeeid;
	private String accessCardno;

	public EmployeeId() {
		/**
		 * 
		 */
	}

	/**
	 * 
	 * @param employeeid
	 * @param accessCardno
	 */
	public EmployeeId(int employeeid, String accessCardno) {
		this.employeeid = employeeid;
		this.accessCardno = accessCardno;
	}

	@Column(name = "employeeid", nullable = false)
	public int getEmployeeid() {
		return this.employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	@Column(name = "access_cardno", nullable = false, length = 20)
	public String getAccessCardno() {
		return this.accessCardno;
	}

	public void setAccessCardno(String accessCardno) {
		this.accessCardno = accessCardno;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other instanceof EmployeeId))
			return false;
		EmployeeId castOther = (EmployeeId) other;

		return (this.getEmployeeid() == castOther.getEmployeeid())
				&& ((this.getAccessCardno() == castOther.getAccessCardno())
						|| (this.getAccessCardno() != null && castOther.getAccessCardno() != null
								&& this.getAccessCardno().equals(castOther.getAccessCardno())));
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getEmployeeid();
		result = 37 * result + (getAccessCardno() == null ? 0 : this.getAccessCardno().hashCode());
		return result;
	}

}