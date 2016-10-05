package com.attendance.DAOServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.attendance.dao.service.AttendanceDAO;
import com.attendance.entity.AttendanceDetails;
import com.attendance.entity.Employee;
import com.attendance.exception.DAOException;
import com.attendance.generatereport.GenerateReport;
import com.attendance.pojo.Attendance;
import com.attendance.util.JPAUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * AttendanceDAOImpl holds the implementation of methods used in AttendanceDAO
 * interface.
 *
 */
@Repository
public class AttendanceDAOImpl implements AttendanceDAO {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceDAOImpl.class);

	private EntityManager entityManager = null;

	/**
	 * 
	 * Method used to fetch the attendance details of all the employees. returns
	 * the details fetched as list.
	 * 
	 * @throws ParseException
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceDetails> getAttendanceDetails(Date startDate, Date endDate,String employeeType)
			throws DAOException, ParseException {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		Query query;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd");
		String startFormattedDate = dateFormat.format(startDate);
		String endFormattedDate = dateFormat.format(endDate);

		logger.info("start date:" + startFormattedDate);
		logger.info("end date:" + endFormattedDate);

		if (employeeType.equals("Contract")) {
			query = entityManager.createQuery("SELECT attendance FROM AttendanceDetails attendance");
		} else {

			query = entityManager.createQuery(
					"SELECT attendance FROM AttendanceDetails attendance WHERE DATE(attendance.lastUpdated) BETWEEN STR_TO_DATE(:stDate,'%Y,%m,%d') AND STR_TO_DATE(:endDate,'%Y,%m,%d') ");
			query.setParameter("stDate", startFormattedDate);
			query.setParameter("endDate", endFormattedDate);

		}

		return (List<AttendanceDetails>) query.getResultList();
	}

	/**
	 * 
	 * Method to insert the swipe in time in the attendance details of the
	 * employee. Persist the inserted values in DB.
	 */

	@Override
	public void insertSwipeInHours(AttendanceDetails attendance) throws DAOException {
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		boolean swipeOutExist;
		if (attendance != null) {
			getEmployee(attendance.getEmployee().getId().getEmployeeid(),
					attendance.getEmployee().getId().getAccessCardno());
			validateEmployeeMachineDetails(attendance.getEmployee().getId().getEmployeeid(),
					attendance.getEmployee().getId().getAccessCardno(), attendance.getMachineName());
			boolean lastUpdated = getLastUpdatedTime(attendance.getEmployee().getId().getEmployeeid(),
					attendance.getSwipeIn());
			if (!lastUpdated) {
				swipeOutExist = getCurrentDateAttendance(attendance.getEmployee().getId().getEmployeeid());
				if (!swipeOutExist) {
					throw new DAOException("Swipe Out doesn't Exists.Report generated");
				}
			}

			entityManager.persist(attendance);
			entityManager.getTransaction().commit();
			logger.info("Records inserted successfully");
		} else {

			throw new DAOException("The AttendanceDetails is null");
		}

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
		logger.debug("The given Employee ID is: {},The given Machine ID is:{}", empId, cardno);

		logger.info("The given Employee2 ID is: {},The given Machine ID is:{}" + " " + empId + " " + cardno);

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery(
				"SELECT e FROM Employee e WHERE e.id.employeeid= :id and e.id.accessCardno= :cardNo and e.status= :status");
		query.setParameter("id", empId);
		query.setParameter("cardNo", cardno);
		query.setParameter("status", "Active");

		if (query.getSingleResult() != null) {

			logger.info("Employee ID successfully validated");

		} else {

			throw new DAOException(
					"The given Employee ID and access card no combination doesn't match with any data or doesn't exists in DB");
		}

	}

	@Override
	public void insertSwipeOutHours(AttendanceDetails attendance) throws DAOException {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		if (attendance != null) {
			getEmployee(attendance.getEmployee().getId().getEmployeeid(),
					attendance.getEmployee().getId().getAccessCardno());
			validateEmployeeMachineDetails(attendance.getEmployee().getId().getEmployeeid(),
					attendance.getEmployee().getId().getAccessCardno(), attendance.getMachineName());
			boolean swipeInExists = checkSwipeInExists(attendance.getEmployee().getId().getEmployeeid());
			if (swipeInExists) {

				Query query = entityManager.createQuery(
						"UPDATE AttendanceDetails attendance SET attendance.swipeOut = :swipeOut "
								+ "WHERE attendance.employee.id.employeeid= :empId having MAX(attendance.lastUpdated )");
				query.setParameter("swipeOut", attendance.getSwipeOut());
				query.setParameter("empId", attendance.getEmployee().getId().getEmployeeid());
				query.executeUpdate();
				entityManager.getTransaction().commit();
				logger.info("Records inserted successfully");

			} else {
				throw new DAOException("Swipe In doesn't exists for today.Report has been generated");

			}
		} else {

			throw new DAOException("The AttendanceDetails is null");
		}

	}

	@Override
	public long calculateTotalHours(Date swipeInTime, Date swipeOutTime) throws Exception {
		long timeDifference = swipeInTime.getTime() - swipeOutTime.getTime();
		long diffMinutes = timeDifference / (60 * 1000) % 60;
		long diffHours = timeDifference / (60 * 60 * 1000);
		return diffHours;

	}

	@Override
	public void validateEmployeeMachineDetails(int empId, String accessCardNo, String machineId) throws DAOException {

		logger.debug("The given Employee ID is: {},The given Machine ID is:{}", empId, machineId);
		logger.info("The given Machine ID is:" + machineId + " " + empId + " " + accessCardNo);
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery(
				"SELECT machine FROM MachineDetails machine WHERE machine.employee.id.employeeid= :empId and machine.employee.id.accessCardno= :accessCardNo and machine.machineName= :machineName and machine.activationStatus=:activate_status");
		query.setParameter("empId", empId);
		query.setParameter("accessCardNo", accessCardNo);
		query.setParameter("machineName", machineId);
		query.setParameter("activate_status", "Yes");

		if (query.getSingleResult() != null) {
			logger.info("Machine Details validated for Employee Successfully");
		} else {

			throw new DAOException("The given machine ID " + machineId + "is not mapped to employee" + empId);
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

	

		return (List<Integer>) query.getResultList();
	}

	@Override
	public void deleteAttendanceDetails(List<Integer> employeeIds) throws Exception {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		logger.debug("Employee Id given:" + employeeIds);

		employeeIds.forEach(emp -> {

			entityManager.find(AttendanceDetails.class, emp);
			entityManager.remove(emp);

		});

		entityManager.getTransaction().commit();
		logger.info("Record Deleted Successfully");

	}

	public boolean getCurrentDateAttendance(int empId) {
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		boolean swipeOutExists = true;
		Query query = entityManager.createQuery(
				"SELECT MAX(attendance.swipeIn) FROM AttendanceDetails attendance WHERE  attendance.employee.id.employeeid= :empId and attendance.swipeOut is not null");
		query.setParameter("empId", empId);

		logger.info("The size is:{}" + query.getResultList().size());
		

		if (query.getResultList().get(0) == null) {
			logger.info("Entering into reports");
			swipeOutExists = false;
			Query queryReport = entityManager.createQuery(
					"select attendance.employee.id.employeeid,MAX(attendance.swipeIn),attendance.swipeOut from AttendanceDetails attendance");
			List<Object[]> attdet = queryReport.getResultList();
			List<Attendance> attendanceList = new ArrayList<Attendance>();
			Attendance attendance = new Attendance();

			for (Object[] elements : attdet) {

				attendance.setEmployeeId((int) (elements[0]));
				attendance.setSwipeIn((Date) (elements[1]));
				attendance.setSwipeOut((Date) (elements[2]));
			}

			attendanceList.add(attendance);

			JRDataSource dataSource = new JRBeanCollectionDataSource(attendanceList);
			GenerateReport report = new GenerateReport();
			report.showReport(dataSource);

		}

		return swipeOutExists;
	}

	public boolean getLastUpdatedTime(int empId, Date swipeIn) {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		logger.info("The given employee id is:" + empId);
		logger.info("The given swipe in is:" + swipeIn);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd");
		String swipeInFormat = dateFormat.format(swipeIn);

		Query query = entityManager.createQuery(
				"SELECT attendance FROM AttendanceDetails attendance WHERE  attendance.employee.id.employeeid= :empId and DATE(attendance.lastUpdated)= STR_TO_DATE(:swipein,'%Y,%m,%d')");
		query.setParameter("empId", empId);
		query.setParameter("swipein", swipeInFormat);

		boolean lastUpdated = false;

		if (query.getResultList().isEmpty()) {
			lastUpdated = true;
		}

		logger.info("The lastUpdated is:" + lastUpdated);
		return lastUpdated;

	}

	public boolean checkSwipeInExists(int empId) {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		boolean swipeInExists = true;
		logger.info("The given employee id is:" + empId);

		Query query = entityManager.createQuery(
				"SELECT attendance FROM AttendanceDetails attendance WHERE attendance.employee.id.employeeid= :empId AND DATE(attendance.lastUpdated) = CURDATE() AND attendance.swipeIn is not null AND attendance.swipeOut is null");
		query.setParameter("empId", empId);

		if (query.getResultList().isEmpty()) {
			logger.info("Entering into reports");
			swipeInExists = false;
			Query queryReport = entityManager.createQuery(
					"SELECT attendance.employee.id.employeeid,attendance.swipeIn,attendance.swipeOut,MAX(attendance.lastUpdated) FROM AttendanceDetails attendance WHERE attendance.employee.id.employeeid= :empId");
			queryReport.setParameter("empId", empId);
			List<Object[]> attdet = queryReport.getResultList();
			List<Attendance> attendanceList = new ArrayList<Attendance>();
			Attendance attendance = new Attendance();

			for (Object[] elements : attdet) {

				attendance.setEmployeeId((int) (elements[0]));
				attendance.setSwipeIn((Date) (elements[1]));
				attendance.setSwipeOut((Date) (elements[2]));
				attendance.setLastUpdated((Date) (elements[3]));
			}

			attendanceList.add(attendance);

			JRDataSource dataSource = new JRBeanCollectionDataSource(attendanceList);
			GenerateReport report = new GenerateReport();
			report.showReport(dataSource);

		}

		return swipeInExists;

	}

	@Override
	public List<AttendanceDetails> getEmployeeType(int empId, Date startDate, Date endDate)
			throws DAOException, ParseException {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		List<AttendanceDetails> attendanceDetails = new ArrayList<>();
		String employeeType;
		Query query = entityManager
				.createQuery("SELECT employee FROM Employee employee WHERE employee.id.employeeid= :empId and employee.status= :status");
		query.setParameter("empId", empId);
		query.setParameter("status", "Active");
		List<Employee> employee = query.getResultList();

		if (query.getResultList().isEmpty()) {

			for (Employee elements : employee) {

				employeeType = elements.getUsertype();

				if (employeeType.equals("Contract") && (startDate != null) && (endDate != null)) {

					throw new DAOException("Contract Employee cannot customize with start and end date");

				}

				else {

					attendanceDetails = getAttendanceDetails(startDate, endDate,employeeType);
				}

			}

		} else {
			throw new DAOException("No such employee Id exists in DB");
		}

		return attendanceDetails;

	}

}
