package com.attendance.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author 523696 EmployeeUtil is a stand-alone Java program that creates
 *         persistance unit for employee bean.
 * 
 */

public class JPAUtil {

	private static final EntityManagerFactory entityManagerFactory;

	private JPAUtil() {
	}

	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory(EmployeeConstants.PERSISTENCE_UNIT_NAME);
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}

	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
