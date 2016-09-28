package com.attendance.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.attendance.DAOServiceImpl.AttendanceDAOImpl;
import com.attendance.entity.AttendanceDetails;
import com.attendance.entity.Employee;
import com.attendance.entity.EmployeeId;
import com.attendance.entity.LocationDetails;
import com.attendance.entity.MachineDetails;
import com.attendance.pojo.Attendance;
import com.attendance.service.AttendanceService;
import com.attendance.util.Utility;

/**
 * @author 542320 AttendanceServiceImpl holds the implementation of methods used
 *         in AttendanceDAO interface.
 * 
 */

@Component
public class AttendanceServiceImpl implements AttendanceService {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

	@Autowired
	private AttendanceDAOImpl attendanceDAOImpl;

	/**
	 * Method to get the attendance details and save it as excel.
	 */

	@Override
	public void exportToExcel() {

		List<AttendanceDetails> attendanceList = Collections.emptyList();

		try {

			attendanceList = attendanceDAOImpl.getAttendanceDetails();

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

			logger.error("Encountered exception:" , e);

		}

	}

	/**
	 * Method to insert the swipe in hours in DB calling the method in DAO
	 * layer.
	 */

	@Override
	public void insertSwipeInHours(Attendance attendance) throws Exception {
		AttendanceDetails attendanceDetails = new AttendanceDetails();
		Employee employee = new Employee();
		EmployeeId empId = new EmployeeId();
		LocationDetails locationDetails = new LocationDetails();
		empId.setEmployeeid(attendance.getEmployeeId());
		empId.setAccessCardno(attendance.getAccessCardNo());
		employee.setId(empId);
		attendanceDetails.setEmployee(employee);
	    locationDetails.setMachineName(attendance.getMachineId());
		attendanceDetails.setSwipeIn(Utility.getCurrentDate());
		attendanceDAOImpl.insertSwipeInHours(attendanceDetails);

	}

	/**
	 * Method to insert the swipe out hours in DB calling the method in DAO
	 * layer.
	 */
	@Override
	public void insertSwipeOutHours(AttendanceDetails attendance) throws Exception {

		attendanceDAOImpl.insertSwipeOutHours(attendance);

	}

}