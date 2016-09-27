package com.attendance.exception;

/**
 * 
 * @author 542320 User defined exception class to modify the system generated
 *         message by user defined message.
 *
 */

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param s
	 */
	public DAOException(String s) {
		super(s);
	}

}
