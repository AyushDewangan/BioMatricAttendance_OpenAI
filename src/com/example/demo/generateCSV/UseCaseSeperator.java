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
			BufferedReader reader = new BufferedReader(new FileReader("FinalFolder/Test.csv"));

			// Set Destination file path
			BufferedWriter empAttWriter = new BufferedWriter(new FileWriter("FinalFolder/emp_att_june.csv"));
			BufferedWriter empLeaveWriter = new BufferedWriter(new FileWriter("FinalFolder/emp_leave_june.csv"));
			BufferedWriter holidayWriter = new BufferedWriter(new FileWriter("FinalFolder/emp_holiday_june.csv"));
			BufferedWriter empAttResultWriter = new BufferedWriter(new FileWriter("FinalFolder/emp_att_result_june.csv"));
			BufferedWriter empProfileWriter = new BufferedWriter(new FileWriter("FinalFolder/EmpProfile.csv"));
			BufferedWriter empDesignationWriter = new BufferedWriter(new FileWriter("FinalFolder/EmpDesignation.csv"));
			BufferedWriter empFinalPayWriter = new BufferedWriter(new FileWriter("FinalFolder/finalPay.csv"));
			BufferedWriter empEmpPaySlipWriter = new BufferedWriter(new FileWriter("FinalFolder/EmpPaySlip.csv"));
			BufferedWriter empEmpProductPayWriter = new BufferedWriter(new FileWriter("FinalFolder/EmpProductPay.csv"));
			BufferedWriter empEmpPayRollBillWriter = new BufferedWriter(new FileWriter("FinalFolder/EmpPayRollBill.csv"));
			//finalPay.csv: -> Done
			//EmppaySlip.csv: - EmpPaySlip.csv -> Done
			//EmpProductPay.csv: -> Done
			//EmppayrollBill.csv: - EmpPayRollBill.csv -> Done


			boolean copyEmpAttData = false;
			boolean copyEmpLeaveData = false;
			boolean copyHolidayData = false;
			boolean copyEmpAttResultData = false;
			boolean copyProfileData = false;
			boolean copyDesignationData = false;
			boolean copyFinalPayData = false;
			boolean copyEmpPaySlipData = false;
			boolean copyEmpProductPayData = false;
			boolean copyEmpPayRollBillData = false;
			
			String line;

			//Logic for CSV separation
			while ((line = reader.readLine()) != null) {
				if (line.contains("emp_att_june.csv")) {
					copyEmpAttData = true;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					copyProfileData = false;
					copyDesignationData = false;
					copyFinalPayData = false;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = false;
					continue;
				} else if (line.contains("emp_leave_june.csv")) {
					copyEmpAttData = false;
					copyEmpLeaveData = true;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					copyProfileData = false;
					copyDesignationData = false;
					copyFinalPayData = false;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = false;
					continue;
				} else if (line.contains("emp_holiday_june.csv")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = true;
					copyEmpAttResultData = false;
					copyProfileData = false;
					copyDesignationData = false;
					copyFinalPayData = false;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = false;
					continue;
				} else if (line.contains("emp_att_result_june.csv:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = true;
					copyProfileData = false;
					copyDesignationData = false;
					copyFinalPayData = false;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = false;
					continue;
				}
				else if (line.contains("ItDesignation.csv")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					copyDesignationData = true;
					copyProfileData = false;
					copyFinalPayData = false;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = false;
					continue;
				}
				else if (line.contains("EmpProfile.csv")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					copyDesignationData = false;
					copyProfileData = true;
					copyFinalPayData = false;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = false;
					continue;
				}
				else if (line.contains("finalPay.csv:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					copyDesignationData = false;
					copyProfileData = false;
					copyFinalPayData = true;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = false;
					continue;
				}
				else if (line.contains("EmpPaySlip.csv:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					copyDesignationData = false;
					copyProfileData = false;
					copyFinalPayData = false;
					copyEmpPaySlipData = true;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = false;
					continue;
				}
				else if (line.contains("EmpProductPay.csv:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					copyDesignationData = false;
					copyProfileData = false;
					copyFinalPayData = false;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = true;
					copyEmpPayRollBillData = false;
					continue;
				}
				else if (line.contains("EmpPayRollBill.csv:")) {
					copyEmpAttData = false;
					copyEmpLeaveData = false;
					copyHolidayData = false;
					copyEmpAttResultData = false;
					copyDesignationData = false;
					copyProfileData = false;
					copyFinalPayData = false;
					copyEmpPaySlipData = false;
					copyEmpProductPayData = false;
					copyEmpPayRollBillData = true;
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
				else if (copyProfileData) {
					empProfileWriter.write(line);
					empProfileWriter.newLine();
				}
				else if (copyDesignationData) {
					empDesignationWriter.write(line);
					empDesignationWriter.newLine();
				}
				else if (copyFinalPayData) {
					empFinalPayWriter.write(line);
					empFinalPayWriter.newLine();
				}
				else if (copyEmpPaySlipData) {
					empEmpPaySlipWriter.write(line);
					empEmpPaySlipWriter.newLine();
				}
				else if (copyEmpProductPayData) {
					empEmpProductPayWriter.write(line);
					empEmpProductPayWriter.newLine();
				}
				else if (copyEmpPayRollBillData) {
					empEmpPayRollBillWriter.write(line);
					empEmpPayRollBillWriter.newLine();
				}
			}

			reader.close();
			empAttWriter.close();
			empLeaveWriter.close();
			holidayWriter.close();
			empAttResultWriter.close();
			empProfileWriter.close();
			empDesignationWriter.close();
			empFinalPayWriter.close();
			empEmpPaySlipWriter.close();
			empEmpProductPayWriter.close();
			empEmpPayRollBillWriter.close();
			
			System.out.println("Data seperated to CSV files successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
