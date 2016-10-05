package com.attendance.generatereport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Class to generate report for invalid access using jasper.
 */
public class GenerateReport {
	
	private static final Logger logger = LoggerFactory.getLogger(GenerateReport.class);
/**
 * Method to get the JRDatasource which has the query element,fetch from DB and show it in report.
 * @param dataSource
 */
	public void showReport(JRDataSource dataSource) {
		JasperReportBuilder report = DynamicReports.report();// a new report
		report.columns(

				Columns.column("EmployeeId", "employeeId", DataTypes.integerType()),
				Columns.column("SwipeIn", "swipeIn", DataTypes.dateYearToSecondType()),
				Columns.column("SwipeOut", "swipeOut", DataTypes.dateYearToMinuteType())

		).title(// title of the report
				Components.text("Invalid access to ODC").setHorizontalAlignment(HorizontalAlignment.CENTER))
				.pageFooter(Components.pageXofY())// show page number on the
													// page footer
				.setDataSource(dataSource);
		try {
			// show the report
			report.show();

			// export the report to a pdf file
			report.toPdf(new FileOutputStream("D:/report.pdf"));

		} catch (DRException e) {
			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage+e);
		} catch (FileNotFoundException e) {
			String errorMessage = "Exception Occurred while inserting records in DB: ";
			logger.error(errorMessage+e);
		}
	}

}
