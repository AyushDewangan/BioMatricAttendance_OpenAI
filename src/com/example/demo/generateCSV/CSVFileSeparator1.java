package com.example.demo.generateCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileSeparator1 {

	public static void main(String[] args) {
		copyDataToCSVFiles("TestFolder/Test.csv", "TestFolder/emp_att_june.csv", "TestFolder/emp_leave_june.csv",
				"TestFolder/emp_holiday_june.csv", "TestFolder/emp_att_result_june.csv");
	}

	public static void copyDataToCSVFiles(String sourceFilePath, String empAttFilePath, String empLeaveFilePath,
			String holidayFilePath, String empAttResultFilePath) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));

			BufferedWriter empAttWriter = new BufferedWriter(new FileWriter(empAttFilePath));
			BufferedWriter empLeaveWriter = new BufferedWriter(new FileWriter(empLeaveFilePath));
			BufferedWriter holidayWriter = new BufferedWriter(new FileWriter(holidayFilePath));
			BufferedWriter empAttResultWriter = new BufferedWriter(new FileWriter(empAttResultFilePath));

			boolean copyEmpAttData = false;
			boolean copyEmpLeaveData = false;
			boolean copyHolidayData = false;
			boolean copyEmpAttResultData = false;

			String line;

			while ((line = reader.readLine()) != null) {
				if (line.contains("Employee Attendance Dataset:")) {
					copyEmpAttData = true;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					continue;
				} else if (line.contains("Employee Leave Dataset:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = true;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					continue;
				} else if (line.contains("Holiday Dataset:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = true;
					copyEmpAttResultData = false;
					continue;
				} else if (line.contains("Employee Attendance Summary Dataset:")) {
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

			System.out.println("Data copied to CSV files successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
