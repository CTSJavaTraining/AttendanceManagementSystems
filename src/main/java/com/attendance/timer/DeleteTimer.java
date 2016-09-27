/**
 * 
 */
package com.attendance.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.attendance.DAOServiceImpl.AttendanceDAOImpl;

/**
 * @author 523696
 *
 */
@Component
public class DeleteTimer {

	private static final Logger logger = LoggerFactory.getLogger(DeleteTimer.class);

	@Autowired
	private AttendanceDAOImpl attendanceDAO;

	@Scheduled(cron = "0 0 16 ? 1/6 MON#1 *")
	public void deleteAttendanceDetails() throws Exception {
		logger.info("Delete record every six months for inactive employees");
		attendanceDAO.getInactiveEmployees();

	}

}
