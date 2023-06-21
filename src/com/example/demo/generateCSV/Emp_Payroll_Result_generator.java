package com.example.demo.generateCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Emp_Payroll_Result_generator {

	public static void main(String[] args) {
		String payrollFile = "TestFolder\\emp_payroll_cal.csv";
		String attendanceFile = "TestFolder\\emp_att_result_june.csv";
		String outputFile = "TestFolder\\emp_payroll_result.csv";

		try {
			// Read data from the payroll file
			BufferedReader payrollReader = new BufferedReader(new FileReader(payrollFile));
			String payrollHeader = payrollReader.readLine();
			String[] payrollColumns = payrollHeader.split(",");

			int empNameIndex = getColumnIndex(payrollColumns, "EmployeeName");
			int empIDIndex = getColumnIndex(payrollColumns, "EmployeeID");
			int grossSalaryIndex = getColumnIndex(payrollColumns, "GrossSalary");
			int allowanceIndex = getColumnIndex(payrollColumns, "Allowance");
			int deductionIndex = getColumnIndex(payrollColumns, "Tax_PF_Deduction");
			int leaveDeductionIndex = getColumnIndex(payrollColumns, "LeaveDeduction");
			int netSalaryIndex = getColumnIndex(payrollColumns, "NetSalary");

			// Read data from the attendance file
			BufferedReader attendanceReader = new BufferedReader(new FileReader(attendanceFile));
			String attendanceHeader = attendanceReader.readLine();
			String[] attendanceColumns = attendanceHeader.split(",");

			int totalDaysWorkedIndex = getColumnIndex(attendanceColumns, "TotalDaysWorked");
			int totalLeaveIndex = getColumnIndex(attendanceColumns, "TotalLeave");

			// Create the output file and write the header
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
			String finalHeader = "EmployeeName,EmployeeID,TotalDaysWorked,GrossSalary,Allowance,TotalLeave,Tax_PF_Deduction,LeaveDeduction,NetSalary";
			writer.write(finalHeader);
			writer.newLine();

			// Process each line of the payroll file
			String payrollLine;
			while ((payrollLine = payrollReader.readLine()) != null) {
				String[] payrollData = payrollLine.split(",");
				String employeeName = payrollData[empNameIndex];
				String employeeID = payrollData[empIDIndex];
				String grossSalary = payrollData[grossSalaryIndex];
				String allowance = payrollData[allowanceIndex];
				String deduction = payrollData[deductionIndex];
				String leavededuction = payrollData[leaveDeductionIndex];
				String netSalary = payrollData[netSalaryIndex];

				// Find the matching line in the attendance file
				String attendanceLine;
				while ((attendanceLine = attendanceReader.readLine()) != null) {
					String[] attendanceData = attendanceLine.split(",");
					String attEmployeeName = attendanceData[empNameIndex];

					if (employeeName.equals(attEmployeeName)) {
						String totalDaysWorked = attendanceData[totalDaysWorkedIndex];
						String totalLeave = attendanceData[totalLeaveIndex];

						// Write the combined data to the output file
						String finalData = employeeName + "," + employeeID + "," + totalDaysWorked + "," + grossSalary
								+ "," + allowance + "," + totalLeave + "," + deduction + "," + leavededuction + "," + netSalary;
						writer.write(finalData);
						writer.newLine();
						break;
					}
				}
				attendanceReader = new BufferedReader(new FileReader(attendanceFile)); // Reset the attendance reader
				attendanceReader.readLine(); // Skip the header line
			}

			// Close the file readers and writer
			payrollReader.close();
			attendanceReader.close();
			writer.close();

			System.out.println("Final result generation completed. Results saved in " + outputFile);
		} catch (IOException e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

	private static int getColumnIndex(String[] columns, String columnName) {
		for (int i = 0; i < columns.length; i++) {
			if (columns[i].equals(columnName)) {
				return i;
			}
		}
		return -1; // Column not found
	}

}
