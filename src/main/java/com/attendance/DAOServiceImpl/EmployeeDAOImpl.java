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
import com.attendance.exception.DAOException;
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

}
