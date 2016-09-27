/**
 * 
 */
package com.attendance.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.attendance.DAOServiceImpl.EmployeeDAOImpl;
import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.service.EmployeeService;
import com.attendance.util.Utility;

/**
 * @author 542320 EmployeeServiceImpl holds the implementation of methods used
 *         in EmployeeDAO interface.
 * 
 */
@Component
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	private EmployeeDAOImpl employeeDAOImpl;

	/**
	 * 
	 * Service method to invoke the insertEmployee of DAO class.
	 * 
	 * @throws Exception
	 */

	@Override
	public void insertEmployee(Employee employee) throws Exception {

		employee.getId().setAccessCardno(Utility.generateAccessCard());

		employee.setFirstname(employee.getFirstname().trim());

		employee.setLastname(employee.getLastname().trim());

		employee.setStatus(employee.getStatus().trim());

		employee.setUsertype(employee.getUsertype().trim());

		employeeDAOImpl.insertEmployee(employee);

	}

	/**
	 * 
	 * Service method to invoke the deleteEmployee of DAO class.
	 * 
	 * @throws Exception
	 */

	@Override
	public void deleteEmployee(EmployeeId empId) throws Exception {

		employeeDAOImpl.deleteEmployee(empId);

	}

}
