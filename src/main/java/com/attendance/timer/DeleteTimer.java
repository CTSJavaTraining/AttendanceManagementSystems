/**
 * 
 */
package com.attendance.timer;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import com.attendance.DAOServiceImpl.AttendanceDAOImpl;

/**
 * @author 523696
 *
 */
public class DeleteTimer {
	
	static final Logger logger = Logger.getLogger(DeleteTimer.class);
	
	ApplicationContext context = null;
	
	private static final String propertyFile = "Beans.xml";

	private static final String beanName = "attendance";
	
	@Scheduled(cron="0 0 16 ? 1/6 MON#1 *")
	public void deleteAttendanceDetails() throws Exception
	{
		logger.info("Delete record every six months for inactive employees");
		List<Integer> employeeIds = new ArrayList<Integer>();
		context = new ClassPathXmlApplicationContext(propertyFile);

		AttendanceDAOImpl attendanceDAO = (AttendanceDAOImpl) context.getBean(beanName);
		employeeIds = attendanceDAO.getInactiveEmployees();
		
		
	}

}
