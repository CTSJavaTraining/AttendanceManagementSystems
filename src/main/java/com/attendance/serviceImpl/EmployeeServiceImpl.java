/**
 * 
 */
package com.attendance.serviceImpl;

import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.attendance.DAOServiceImpl.EmployeeDAOImpl;
import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.service.EmployeeService;

/**
 * @author 542320 
 * EmployeeServiceImpl holds the implementation of methods used in EmployeeDAO interface.
 *         
 */
public class EmployeeServiceImpl implements EmployeeService {

	static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	/**
	 * 
	 * Service method to invoke the insertEmployee of DAO class.
	 */

	@Override
	public void insertEmployee(Employee employee) {

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			EmployeeDAOImpl emp = (EmployeeDAOImpl) context.getBean("employee");

			String accessCard = generateAccessCard();

			employee.getId().setAccessCardno(accessCard);

			emp.insertEmployee(employee);

		} catch (Exception e) {

			logger.error("Encountered exception:" + e);

		}

	}

	/**
	 * 
	 * Service method to invoke the deleteEmployee of DAO class.
	 */

	@Override
	public void deleteEmployee(EmployeeId empId) {

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			EmployeeDAOImpl emp = (EmployeeDAOImpl) context.getBean("employee");

			emp.deleteEmployee(empId);

		} catch (Exception e) {

			logger.error("Encountered exception:" + e);

		}

	}

	/**
	 * Returns the random generated string value.
	 * 
	 * @return String
	 */

	@Override
	public String generateAccessCard() {

		String randomStr = UUID.randomUUID().toString();

		return randomStr;
	}

}
