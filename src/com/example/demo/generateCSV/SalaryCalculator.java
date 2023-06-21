package com.example.demo.generateCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SalaryCalculator {
    public static void main(String[] args) {
        String inputFile = "TestFolder\\emp_att_result_june.csv";
        String outputFile = "TestFolder\\TestResult.csv";
        double basicSalary = 400000.0;
        double hraPercentage = 0.4;
        double epfPercentage = 0.12;
        double professionalTax = 2500.0;

        try {
            // Read the attendance data from CSV file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            
            String line;
            // Skip the header line
            reader.readLine();
            
            // Write the header to the result file
            writer.write("EmployeeName,EmployeeID,NetSalary");
            writer.newLine();

            // Process each line of the CSV file
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String employeeName = data[0];
                String employeeID = data[1];
                String totalDaysWorked = (data[2]);
                String totalLeave = (data[3]);
                String totalHoursWorked = (data[4]);

                // Calculate the allowances and deductions
                double hra = basicSalary * hraPercentage;
                double epf = basicSalary * epfPercentage;

                // Calculate the gross salary
                double grossSalary = basicSalary + hra;

                // Calculate the net salary
                double netSalary = grossSalary - (epf + professionalTax);

                // Write the employee's details and net salary to the result file
                writer.write(employeeName + "," + employeeID + "," + netSalary);
                writer.newLine();
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
