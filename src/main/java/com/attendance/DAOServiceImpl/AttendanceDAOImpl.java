package com.attendance.DAOServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
	 * Method to insert the swipe in time in the attendance
	 * details of the employee. Persist the inserted values in DB.
	 */

	@Override
	public void insertSwipeInHours(AttendanceDetails employee) throws Exception {
	    Date date = new Date();
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		getEmployee(employee.getEmployee().getId().getEmployeeid(),employee.getEmployee().getId().getAccessCardno());
		validateEmployeeMachineDetails(employee.getEmployee().getId().getEmployeeid(),employee.getMachinedetails().getMachineId());
		employee.setSwipeIn(date);
		entityManager.persist(employee);
		entityManager.getTransaction().commit();	
		logger.info("Records inserted successfully");		
		}

	/**
	 * 
	 * Method to check for valid employee Id and access card. Throws exception
	 * message incase of invalid input
	 * @throws DAOException 
	 */

	@Override
	public void getEmployee(int empId, String cardno) throws DAOException {
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
			logger.info("Employee ID successfully validated");
		}

	}

	@Override
	public void insertSwipeOutHours(AttendanceDetails employee) throws Exception {
		
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		query = entityManager.createQuery(
				"SELECT a.swipe_in FROM AttendanceDetails a WHERE a.employeeid= :id and e.access_cardno= :cardNo");
		query.setParameter("id", employee.getEmployee().getId().getEmployeeid());
		query.setParameter("cardNo", employee.getEmployee().getId().getAccessCardno());
		query.setParameter("status", "Y");
		
		
	}

	@Override
	public int calculateTotalHours(Date swipeInTime, Date swipeOutTime) throws Exception {
		
		return 0;
	}

	@Override
	public int calculateWeekAverage(int totalHours) throws Exception {
		
		return 0;
	}

	@Override
	public void validateEmployeeMachineDetails(int empId, String machineId) throws DAOException  {
		
		logger.debug("The given Employee ID is:" + empId);
		logger.debug("The given Machine ID is:" + machineId);

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		query = entityManager.createQuery(
				"SELECT machine FROM machinedetails machine WHERE machine.employee_id= :empId and machine.machine_Id= :machineId and machine.activate_status= :activate_status");
		query.setParameter("empId", empId);
		query.setParameter("machineId", machineId);
		query.setParameter("activate_status", "Y");
		int validMachine = query.getResultList().size();
		if (validMachine == 0) {
			throw new DAOException(
					"The given machine ID "+machineId+ "is not mapped to employee" +empId);
		} else {
			logger.info("Machine Details validated for Employee Successfully");
		}
		
		
		
	}

}
