/**
 * 
 */
package com.attendance.DAOServiceImpl;

import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import com.attendance.DAOService.EmployeeDAO;
import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.util.JPAUtil;

/**
 * @author 542320 EmployeeDAOImpl holds the implementation of methods used in
 *         EmployeeDAO interface.
 *
 */

public class EmployeeDAOImpl implements EmployeeDAO {

	static final Logger logger = Logger.getLogger(EmployeeDAOImpl.class);

	EntityManager entityManager = null;

	/**
	 * 
	 * Method to insert employee details in DB and persist.
	 * 
	 */

	@Override
	public void insertEmployee(Employee employee) throws Exception {

		logger.info("inserting a employee");

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(employee);
		entityManager.getTransaction().commit();
		logger.info("Records inserted successfully");

	}

	/**
	 * 
	 * Method to delete the employee by changing the status flag to inactive.
	 * 
	 */

	@Override
	public void deleteEmployee(EmployeeId empId) throws Exception {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		logger.debug("Employee Id given:" + empId);
		Employee emp = entityManager.find(Employee.class, empId);
		if (emp != null) {

			emp.setStatus("Inactive");
			entityManager.persist(emp);
			entityManager.getTransaction().commit();
			logger.info("Record Deleted Successfully");
		} else {
			logger.info("Entered EmpId is invalid.No such data present in DB");
		}
	}

}
