package com.attendance.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.attendance.DAOServiceImpl.AttendanceDAOImpl;
import com.attendance.entity.AttendanceDetails;
import com.attendance.service.AttendanceService;

/**
 * @author 542320 
 * AttendanceServiceImpl holds the implementation of methods used  in AttendanceDAO interface.
 *        
 */

public class AttendanceServiceImpl implements AttendanceService {

	static final Logger logger = Logger.getLogger(AttendanceService.class);

	/**
	 * Method to get the attendance details and save it as excel.
	 */

	@Override
	public void exportToExcel() {

		List<AttendanceDetails> attendanceList = new ArrayList<AttendanceDetails>();

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			AttendanceDAOImpl attendanceDAO = (AttendanceDAOImpl) context.getBean("attendance");
			attendanceList = attendanceDAO.getAttendanceDetails();

			// Blank workbook
			XSSFWorkbook workbook = new XSSFWorkbook();

			// Create a blank sheet
			XSSFSheet sheet = workbook.createSheet("Employee Attendance Data");

			// Iterate over data and write to sheet

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Employee Id");
			header.createCell(1).setCellValue("AccessCard No");
			header.createCell(2).setCellValue("Swipe in time");
			header.createCell(3).setCellValue("Swipe out time");
			header.createCell(4).setCellValue("Hours logged");

			for (AttendanceDetails obj : attendanceList) {

				Row dataRow = sheet.createRow(1);
				dataRow.createCell(0).setCellValue(obj.getEmployee().getId().getEmployeeid());
				dataRow.createCell(1).setCellValue(obj.getEmployee().getId().getAccessCardno());
				dataRow.createCell(2).setCellValue(obj.getSwipeIn());
				dataRow.createCell(3).setCellValue(obj.getSwipeOut());
				dataRow.createCell(4).setCellValue(obj.getTotalHours());

			}

			FileOutputStream out = new FileOutputStream(new File("D:\\AttendanceDetails.xlsx"));
			workbook.write(out);
			out.close();
			logger.info("AttendanceDetails.xlsx written successfully on disk.");

		} catch (Exception e) {

			logger.error("Encountered exception:" + e);

		}

	}

	/**
	 * Method to pass the attendance details of the employees and invoke the DAO method to persist.
	 */
	@Override
	public void insertSwipeHours(List<AttendanceDetails> attendance) {

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			AttendanceDAOImpl attendanceDAO = (AttendanceDAOImpl) context.getBean("attendance");
			attendanceDAO.insertSwipeHours(attendance);
		} catch (Exception e) {

			logger.error("Encountered exception:" + e);
		}

	}

}