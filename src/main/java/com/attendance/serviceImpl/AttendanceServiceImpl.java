package com.attendance.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
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
import com.attendance.entity.MachineDetails;
import com.attendance.exception.DAOException;
import com.attendance.pojo.Attendance;
import com.attendance.service.AttendanceService;
import com.attendance.util.Utility;
import java.io.FileWriter;


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
	 * @throws DAOException 
	 * @throws IOException 
	 * @throws ParseException 
	 */

	@Override
	public void exportToFile(int empId,LocalDate startDate,LocalDate  endDate,String fileFormat)throws DAOException, IOException, ParseException {

		List<AttendanceDetails> attendanceList = Collections.emptyList();
		
        attendanceList = attendanceDAOImpl.getEmployeeType(empId, startDate, endDate);
            
        if(fileFormat.equals("Excel")){
        	
        	exportToExcel(attendanceList );
        	
        }else{
        	
        	exportToCsv(attendanceList ) ;
        	
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
	public void insertSwipeOutHours(AttendanceDetails attendance) throws DAOException {

		attendanceDAOImpl.insertSwipeOutHours(attendance);

	}

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

		for (AttendanceDetails obj : attendanceDetails) {

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

		
	}

	@Override
	public void exportToCsv(List<AttendanceDetails> attendanceDetails) throws FileNotFoundException, IOException {
		
		  //Delimiter used in CSV file
		
		    final String COMMA_DELIMITER = ",";
		
		    final String NEW_LINE_SEPARATOR = "\n";
		
		     
		
		    //CSV file header
		
		    final String FILE_HEADER = "Employee Id,AccessCard No,Swipe in time,Swipe out time,Hours logged";
		    
		    FileWriter fileWriter = null;

		    
		      fileWriter = new FileWriter("D:\\AttendanceDetails.csv");
		     
		       
		     
		                  //Write the CSV file header
		     
		                  fileWriter.append(FILE_HEADER.toString());
		    
		                   
		    
		                  //Add a new line separator after the header
		     
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

		                  }
		                  
		                
		                  try {
		                	 
		                	                  fileWriter.flush();
		                	  
		                	                  fileWriter.close();
		                	  
		                	              } catch (IOException e) {
		                	 
		                	            	  logger.info("Error while flushing/closing fileWriter !!!");
		                	  
		                	                  e.printStackTrace();
		                	  
		                	              }



		
	}

}