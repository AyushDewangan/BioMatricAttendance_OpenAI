package com.example.demo.generateCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UseCaseSeperator {
	public static void copyDataToCSVFiles() {
		try {
			
			// Set Source file path
			BufferedReader reader = new BufferedReader(new FileReader("Present/Test.csv"));

			// Set Destination file path
			BufferedWriter empAttWriter = new BufferedWriter(new FileWriter("Present/emp_att_june.csv"));
			BufferedWriter empLeaveWriter = new BufferedWriter(new FileWriter("Present/emp_leave_june.csv"));
			BufferedWriter holidayWriter = new BufferedWriter(new FileWriter("Present/emp_holiday_june.csv"));
			BufferedWriter empAttResultWriter = new BufferedWriter(new FileWriter("Present/emp_att_result_june.csv"));

			boolean copyEmpAttData = false;
			boolean copyEmpLeaveData = false;
			boolean copyHolidayData = false;
			boolean copyEmpAttResultData = false;

			String line;

			//Logic for CSV separation
			while ((line = reader.readLine()) != null) {
				if (line.contains("emp_att_june.csv:")) {
					copyEmpAttData = true;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					continue;
				} else if (line.contains("emp_leave_june.csv:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = true;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					continue;
				} else if (line.contains("emp_holiday_june.csv:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = true;
					copyEmpAttResultData = false;
					continue;
				} else if (line.contains("emp_payroll_cal.csv:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = true;
					continue;
				}

				if (copyEmpAttData) {
					empAttWriter.write(line);
					empAttWriter.newLine();
				} else if (copyEmpLeaveData) {
					empLeaveWriter.write(line);
					empLeaveWriter.newLine();
				} else if (copyHolidayData) {
					holidayWriter.write(line);
					holidayWriter.newLine();
				} else if (copyEmpAttResultData) {
					empAttResultWriter.write(line);
					empAttResultWriter.newLine();
				}
			}

			reader.close();
			empAttWriter.close();
			empLeaveWriter.close();
			holidayWriter.close();
			empAttResultWriter.close();

			System.out.println("Data seperated to CSV files successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
