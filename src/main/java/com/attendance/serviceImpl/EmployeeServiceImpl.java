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
 * @author 542320 EmployeeServiceImpl holds the implementation of methods used
 *         in EmployeeDAO interface.
 * 
 */
public class EmployeeServiceImpl implements EmployeeService {

	static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	ApplicationContext context = null;

	/**
	 * 
	 * Service method to invoke the insertEmployee of DAO class.
	 * 
	 * @throws Exception
	 */

	@Override
	public void insertEmployee(Employee employee) throws Exception {

		context = new ClassPathXmlApplicationContext("Beans.xml");

		EmployeeDAOImpl emp = (EmployeeDAOImpl) context.getBean("employee");

		String accessCard = generateAccessCard();

		employee.getId().setAccessCardno(accessCard);

		emp.insertEmployee(employee);

	}

	/**
	 * 
	 * Service method to invoke the deleteEmployee of DAO class.
	 * 
	 * @throws Exception
	 */

	@Override
	public void deleteEmployee(EmployeeId empId) throws Exception {

		context = new ClassPathXmlApplicationContext("Beans.xml");

		EmployeeDAOImpl emp = (EmployeeDAOImpl) context.getBean("employee");

		emp.deleteEmployee(empId);

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
