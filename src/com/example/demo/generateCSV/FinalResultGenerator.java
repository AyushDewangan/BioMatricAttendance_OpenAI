package com.example.demo.generateCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FinalResultGenerator {

	public static void main(String[] args) {
        String inputFile = "TestFolder\\emp_att_result_june.csv";
        String outputFile = "TestFolder\\emp_payroll_cal.csv";
        double hraPercentage = 0.4;
        double epfPercentage = 0.12;
        double professionalTax = 2500.0;
        int totalWorkingDays = 20;

        // CTC values for each employee (in the same order as the CSV data)
        double[] ctcArray = { 500000.0, 600000.0, 450000.0 };

        try {
            // Read the attendance data from CSV file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            String line;
            // Skip the header line
            reader.readLine();

            // Write the header to the result file
            writer.write("EmployeeName,EmployeeID,GrossSalary,Allowance,Tax_PF_Deduction,LeaveDeduction,NetSalary");
            writer.newLine();

            // Process each line of the CSV file
            int index = 0;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String employeeName = data[0];
                String employeeID = data[1];
                Double totalDaysWorked = Double.parseDouble(data[2]);
                Double totalLeave = Double.parseDouble(data[3]);
                int totalHoursWorked = Integer.parseInt(data[4]);

                // Get the CTC for the current employee
                double ctc = ctcArray[index];

                // Calculate the salary based on attendance data and CTC
                double dailySalary = (ctc/12) / totalWorkingDays;
                double leaveDeduction = dailySalary * totalLeave;
                double salary = (ctc/12) - leaveDeduction;

                // Calculate the allowances and deductions
                double hra = salary * hraPercentage;
                double epf = salary * epfPercentage;

                // Calculate the gross salary
                double grossSalary = salary + hra;

                // Calculate the net salary
                double netSalary = grossSalary - (epf + professionalTax);

                // Write the employee's details and salary components to the result file
                writer.write(employeeName + "," + employeeID + "," + grossSalary + "," + hra + "," + (epf + professionalTax) + "," + leaveDeduction + "," + netSalary);
                writer.newLine();

                // Increment the index to access the next employee's CTC
                index++;
            }

            // Close the file reader and writer
            reader.close();
            writer.close();

            System.out.println("Salary calculation completed. Results saved in " + outputFile);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
