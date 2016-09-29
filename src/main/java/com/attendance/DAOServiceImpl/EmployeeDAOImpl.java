/**
 * 
 */
package com.attendance.DAOServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.attendance.DAOService.EmployeeDAO;
import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.entity.LocationDetails;
import com.attendance.entity.MachineDetails;
import com.attendance.exception.DAOException;
import com.attendance.pojo.EmployeeMachineDetails;
import com.attendance.util.JPAUtil;

/**
 * @author 542320 EmployeeDAOImpl holds the implementation of methods used in
 *         EmployeeDAO interface.
 *
 */
@Component
public class EmployeeDAOImpl implements EmployeeDAO {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

	private EntityManager entityManager = null;

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
	public void deleteEmployee(EmployeeId empId) throws DAOException {

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		logger.debug("Employee Id given:{}", empId.getEmployeeid());
		Query query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.id.employeeid= :id and e.status= :status");
		query.setParameter("id", empId.getEmployeeid());
	    query.setParameter("status", "Active");
	    if(query.getSingleResult()!= null){
	    	
	    	Employee employee = (Employee)query.getSingleResult();
	    	empId.setAccessCardno(employee.getId().getAccessCardno());
	    	
	    }
		Employee emp = entityManager.find(Employee.class, empId);
		if (emp != null) {

			emp.setStatus("Inactive");
			entityManager.persist(emp);
			entityManager.getTransaction().commit();
			logger.info("Record Deleted Successfully");
		} else {
			throw new DAOException("Entered EmpId is invalid.No such data present in DB");
		}
	}

	@Override
	public void insertLocationDetails(LocationDetails location) throws Exception {
		logger.info("inserting a location details");

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(location);
		entityManager.getTransaction().commit();
		logger.info("Records inserted successfully");
		
	}

	@Override
	public void mapEmployeeToMachine(MachineDetails machineDetails) throws Exception {
		logger.info("inserting a Machine details");

		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(machineDetails);
		entityManager.getTransaction().commit();
		logger.info("Records inserted successfully");
		
	}

	@Override
	public void getMachineDetails(MachineDetails machineDetails) throws Exception {
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		logger.debug("Employee Id given:{}", machineDetails.getLocationdetails().getLocationId());
		Query query = entityManager.createQuery("SELECT e FROM LocationDetails e WHERE e.locationId= :id");
		query.setParameter("id", machineDetails.getLocationdetails().getLocationId());
		LocationDetails machine = (LocationDetails)query.getSingleResult();
		machineDetails.setMachineName(machine.getMachineName());
		
	}

}
