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

/**
 * 
 * @author 559207
 *
 */
public class GenerateReport {

	/**
	 * 
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
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
