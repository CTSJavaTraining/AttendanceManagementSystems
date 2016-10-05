package com.attendance.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.dao.service.AttendanceDAO;

import com.attendance.entity.AttendanceDetails;
import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.exception.DAOException;
import com.attendance.pojo.Attendance;
import com.attendance.service.AttendanceService;
import com.attendance.util.Utility;

/**
 * @author 542320 AttendanceServiceImpl holds the implementation of methods used
 *         in AttendanceDAO interface.
 * 
 */

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

	@Autowired
	private  AttendanceDAO attendanceDAOImpl;

	/**
	 * Method to get the employee type and invoke the respective file format.
	 * 
	 * @throws DAOException
	 * @throws IOException
	 * @throws ParseException
	 */

	@Override
	public void exportToFile(int empId, Date startDate, Date endDate, String fileFormat)
			throws DAOException, IOException, ParseException {

		List<AttendanceDetails> attendanceList = Collections.emptyList();

		attendanceList = attendanceDAOImpl.getEmployeeType(empId, startDate, endDate);

		if (fileFormat.equals("Excel")) {

			exportToExcel(attendanceList);

		} else {

			exportToCsv(attendanceList);

		}

	}

	/**
	 * Method to insert the swipe in hours in DB calling the method in DAO
	 * layer.
	 */

	@Override
	public void insertSwipeInHours(Attendance attendance) throws DAOException {
		AttendanceDetails attendanceDetails = new AttendanceDetails();
		Employee employee = new Employee();
		EmployeeId empId = new EmployeeId();

		empId.setEmployeeid(attendance.getEmployeeId());
		empId.setAccessCardno(attendance.getAccessCardNo());
		employee.setId(empId);
		attendanceDetails.setEmployee(employee);
		attendanceDetails.setMachineName(attendance.getMachineId());
		attendanceDetails.setLastUpdated(Utility.getCurrentDate());
		attendanceDetails.setSwipeIn(Utility.getCurrentDate());

		attendanceDAOImpl.insertSwipeInHours(attendanceDetails);

	}

	/**
	 * Method to insert the swipe out hours in DB calling the method in DAO
	 * layer.
	 */
	@Override
	public void insertSwipeOutHours(Attendance attendance) throws DAOException {

		AttendanceDetails attendanceDetails = new AttendanceDetails();
		Employee employee = new Employee();
		EmployeeId empId = new EmployeeId();

		empId.setEmployeeid(attendance.getEmployeeId());
		empId.setAccessCardno(attendance.getAccessCardNo());
		employee.setId(empId);
		attendanceDetails.setEmployee(employee);
		attendanceDetails.setMachineName(attendance.getMachineId());
		attendanceDetails.setLastUpdated(Utility.getCurrentDate());
		attendanceDetails.setSwipeOut(Utility.getCurrentDate());

		attendanceDAOImpl.insertSwipeOutHours(attendanceDetails);

	}

	/**
	 * Method to get the attendance details and download it in excel format.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */

	@Override
	public void exportToExcel(List<AttendanceDetails> attendanceDetails) throws FileNotFoundException, IOException {

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
		int rowNum = 1;
		for (AttendanceDetails obj : attendanceDetails) {

			Row dataRow = sheet.createRow(rowNum++);
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

	}

	/**
	 * Method to get the attendance details and download it in csv format.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */

	@Override
	public void exportToCsv(List<AttendanceDetails> attendanceDetails) throws FileNotFoundException, IOException {

		// Delimiter used in CSV file

		final String COMMA_DELIMITER = ",";

		final String NEW_LINE_SEPARATOR = "\r\n";

		// CSV file header

		final String FILE_HEADER = "Employee Id,AccessCard No,Swipe in time,Swipe out time,Hours logged";

		FileWriter fileWriter = null;

		fileWriter = new FileWriter("D:\\AttendanceDetails.csv");

		// Write the CSV file header

		fileWriter.append(FILE_HEADER.toString());
		fileWriter.append(NEW_LINE_SEPARATOR);

		// Add a new line separator after the header

		fileWriter.append(NEW_LINE_SEPARATOR);

		for (AttendanceDetails obj : attendanceDetails) {

			fileWriter.append(String.valueOf(obj.getEmployee().getId().getEmployeeid()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(obj.getEmployee().getId().getAccessCardno()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(obj.getSwipeIn()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(obj.getSwipeOut()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(obj.getTotalHours()));
			fileWriter.append(NEW_LINE_SEPARATOR);

		}

		try {

			fileWriter.flush();

			fileWriter.close();

		} catch (IOException e) {

			logger.error("Error while flushing/closing fileWriter !!!" + e);

		}

	}

}