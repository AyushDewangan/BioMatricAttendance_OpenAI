package com.example.demo.generateCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EmployeePayrollCalculator {
    private static final String FILE_NAME = "TestFolder\\emp_payroll_cal.csv";
    private static final String HEADER = "Tax Type,Percent";

    public static void main(String[] args) {
        try {
            generateEmployeePayroll();
            System.out.println("Employee payroll calculation completed successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while generating employee payroll: " + e.getMessage());
        }
    }

    private static void generateEmployeePayroll() throws IOException {
        PrintWriter payrollWriter = new PrintWriter(new FileWriter(FILE_NAME));

        String[] taxTypes = {"PF", "HR", "Health"};
        double[] percentages = {10.0, 5.0, 2.5};

        payrollWriter.println(HEADER);

        for (int i = 0; i < taxTypes.length; i++) {
            payrollWriter.println(taxTypes[i] + "," + percentages[i]);
        }

        payrollWriter.close();
    }
}
