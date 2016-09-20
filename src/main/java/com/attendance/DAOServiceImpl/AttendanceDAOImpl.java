package com.attendance.DAOServiceImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import com.attendance.DAOService.AttendanceDAO;
import com.attendance.entity.AttendanceDetails;
import com.attendance.exception.DAOException;
import com.attendance.util.JPAUtil;

/**
 * 
 * AttendanceDAOImpl holds the implementation of methods used in AttendanceDAO interface.
 *
 */
public class AttendanceDAOImpl implements AttendanceDAO {

	static final Logger logger = Logger.getLogger(AttendanceDAOImpl.class);

	EntityManager entityManager = null;

	Query query = null;

	/**
	 * 
	 * Method used to fetch the attendance details of all the employees. returns
	 * the details fetched as list.
	 */

	@Override
	public List<AttendanceDetails> getAttendanceDetails() throws Exception {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		query = entityManager.createQuery("SELECT attendance FROM AttendanceDetails attendance");

		List<AttendanceDetails> resultList = (List<AttendanceDetails>) query.getResultList();
		return resultList;
	}

	/**
	 * 
	 * Method to insert the swipe in and swipe out time in the attendance
	 * details of the employee. Persist the inserted values in DB.
	 */

	@Override
	public void insertSwipeHours(List<AttendanceDetails> employee) throws Exception {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		employee.forEach(emp -> {
			try {
				getEmployee(emp.getEmployee().getId().getEmployeeid(),emp.getEmployee().getId().getAccessCardno());
				entityManager.persist(emp);

			} catch (Exception e) {
				logger.info(e);
			}
		});

		entityManager.getTransaction().commit();
		logger.info("Records inserted successfully");

	}

	/**
	 * 
	 * Method to check for valid employee Id and access card. Throws exception
	 * message incase of invalid input
	 */

	@Override
	public void getEmployee(int empId, String cardno) throws Exception {
		logger.debug("The given Employee ID is:" + empId);

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		query = entityManager.createQuery(
				"SELECT e FROM Employee e WHERE e.employee_id= :id and e.access_cardno= :cardNo and e.active_status= :status");
		query.setParameter("id", empId);
		query.setParameter("cardNo", cardno);
		query.setParameter("status", "Y");
		int employeeSize = query.getResultList().size();
		if (employeeSize == 0) {
			throw new DAOException(
					"The given Employee ID and access card no combination doesn't match with any data or doesn't exists in DB");
		} else {
			logger.info("Inserting the attendance details in DB");
		}

	}

}
