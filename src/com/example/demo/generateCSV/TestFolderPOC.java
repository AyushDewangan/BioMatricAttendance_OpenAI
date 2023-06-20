package com.example.demo.generateCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestFolderPOC {

	public static void main(String[] args) {
		copyCSVData("TestFolder/Test.csv", "TestFolder/Test1.csv", "Employee Attendance Dataset:",
				"Employee Leave Dataset:");
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
