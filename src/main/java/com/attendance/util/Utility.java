package com.attendance.util;

import java.util.Date;
import java.util.UUID;

/**
 * 
 * @author 559207
 *
 */
public class Utility {

	private Utility() {

	}

	/**
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * Returns the random generated string value.
	 * 
	 * @return String
	 */

	public static String generateAccessCard() {

		String randomStr = UUID.randomUUID().toString();

		return randomStr;
	}
}
