package com.example.demo.generateCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileSeparator {

	public static void main(String[] args) {
		copyCSVData("TestFolder\\Test.csv", "TestFolder/emp_att_june.csv", "Employee Attendance Dataset:",
				"Employee Leave Dataset:");

		copyCSVData("TestFolder\\Test.csv", "TestFolder/emp_leave_june.csv", "Employee Leave Dataset:", "Holiday Dataset:");

		copyCSVData("TestFolder\\Test.csv", "TestFolder/emp_holiday_june.csv", "Holiday Dataset:",
				"Employee Attendance Summary Dataset:");

		copyCSVData("TestFolder\\Test.csv", "TestFolder/emp_att_result_june.csv", "Employee Attendance Summary Dataset:", "end");
	}

	public static void copyCSVData(String sourceFilePath, String destinationFilePath, String startText,
			String endText) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
			BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath));

			boolean copyData = false;
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.contains(startText)) {
					copyData = true;
					continue;
				} else if (line.contains(endText)) {
					copyData = false;
					break;
				}

				if (copyData) {
					writer.write(line);
					writer.newLine();
				}
			}

			reader.close();
			writer.close();

			System.out.println("Data copied successfully from " + sourceFilePath + " to " + destinationFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
