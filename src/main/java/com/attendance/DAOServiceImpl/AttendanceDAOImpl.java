package com.attendance.DAOServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.attendance.DAOService.AttendanceDAO;
import com.attendance.entity.AttendanceDetails;
import com.attendance.entity.EmployeeId;
import com.attendance.exception.DAOException;
import com.attendance.util.JPAUtil;
import com.attendance.util.Utility;

/**
 * 
 * AttendanceDAOImpl holds the implementation of methods used in AttendanceDAO
 * interface.
 *
 */
@Component
public class AttendanceDAOImpl implements AttendanceDAO {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceDAOImpl.class);

	private EntityManager entityManager = null;

	/**
	 * 
	 * Method used to fetch the attendance details of all the employees. returns
	 * the details fetched as list.
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceDetails> getAttendanceDetails() throws Exception {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT attendance FROM AttendanceDetails attendance");

		return (List<AttendanceDetails>) query.getResultList();
	}

	/**
	 * 
	 * Method to insert the swipe in time in the attendance details of the
	 * employee. Persist the inserted values in DB.
	 */

	@Override
	public void insertSwipeInHours(AttendanceDetails attendance) throws Exception {
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		EmployeeId id = attendance.getEmployee().getId();
		getEmployee(id.getEmployeeid(), id.getAccessCardno());
		validateEmployeeMachineDetails(id.getEmployeeid(), attendance.getMachinedetails().getMachineId());
		attendance.setSwipeIn(Utility.getCurrentDate());
		entityManager.persist(attendance);
		entityManager.getTransaction().commit();
		logger.info("Records inserted successfully");
	}

	/**
	 * 
	 * Method to check for valid employee Id and access card. Throws exception
	 * message incase of invalid input
	 * 
	 * @throws DAOException
	 */

	@Override
	public void getEmployee(int empId, String cardno) throws DAOException {
		logger.debug("The given Employee ID is: {}", empId);

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		// TODO: Wrong query employee_id is not available in POJO class
		Query query = entityManager.createQuery(
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
	public void insertSwipeOutHours(AttendanceDetails attendance) throws Exception {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery(
				"SELECT a.swipe_in FROM AttendanceDetails a WHERE a.employeeid= :id and a.access_cardno= :cardNo and a.swipe_in = :swipeIn and a.machine_id =:machineId");
		EmployeeId id = attendance.getEmployee().getId();
		query.setParameter("id", id.getEmployeeid());
		query.setParameter("cardNo", id.getAccessCardno());
		query.setParameter("machineId", attendance.getMachinedetails().getMachineId());
		query.setParameter("swipeIn", attendance.getSwipeIn());
		query.setParameter("status", "Y");

	}

	@Override
	public String calculateTotalHours(Date swipeInTime, Date swipeOutTime) throws Exception {
		long timeDifference = swipeInTime.getTime() - swipeOutTime.getTime();
		long diffMinutes = timeDifference / (60 * 1000) % 60;
		long diffHours = timeDifference / (60 * 60 * 1000);
		return diffHours + "h" + " " + diffMinutes + "m";

	}

	@Override
	public int calculateWeekAverage() throws Exception {

		return 0;
	}

	@Override
	public void validateEmployeeMachineDetails(int empId, String machineId) throws DAOException {

		logger.debug("The given Employee ID is: {},The given Machine ID is:{}", empId, machineId);

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery(
				"SELECT machine FROM machinedetails machine WHERE machine.employee_id= :empId and machine.machine_Id= :machineId and machine.activate_status= :activate_status");
		query.setParameter("empId", empId);
		query.setParameter("machineId", machineId);
		query.setParameter("activate_status", "Y");
		int validMachine = query.getResultList().size();
		if (validMachine == 0) {
			throw new DAOException("The given machine ID " + machineId + "is not mapped to employee" + empId);
		} else {
			logger.info("Machine Details validated for Employee Successfully");
		}

	}

	@Override
	public List<Integer> getInactiveEmployees() throws Exception {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery(
				"SELECT e.employee_id FROM Employee e WHERE  e.active_status= :status and DATE_ADD(e.relieving_date,INTERVAL 6 MONTH) = DATE(NOW())");
		query.setParameter("status", "INACTIVE");
		int employeeSize = query.getResultList().size();
		// TODO: Y reading from one list and moving data to other list?? need??
		@SuppressWarnings("unchecked")
		List<Integer> employeeIdList = (List<Integer>) query.getResultList();
//		List<Integer> employeeIds = new ArrayList<>();
//
//		if (employeeSize > 0) {
//			employeeIdList.forEach((emp) -> {
//
//				employeeIds.add(emp);
//
//			});
//
//		}

		return employeeIdList;
	}

	@Override
	public void deleteAttendanceDetails(List<Integer> employeeIds) throws Exception {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		logger.debug("Employee Id given:" + employeeIds);
		// TODO: please explain the below y remove and persist y nor save or
		// update??
		employeeIds.forEach(emp -> {

			entityManager.find(AttendanceDetails.class, emp);
			entityManager.remove(emp);
			

		});

		entityManager.getTransaction().commit();
		logger.info("Record Deleted Successfully");

	}

}
