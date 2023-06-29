package com.example.demo.S;

import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.example.demo.generateCSV.UseCaseSeperator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UseCaseS {

	public static void main(String[] args) throws Exception {

		// Initializing ApiKey, FilePath, Prompt
		String apiKey = "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";
		String filePath = "FinalFolder\\Test.csv";

		String promptContent = " Provide example of dataset for employee 'Ayush, Bhikam, Manish, Romesh, Sagar' for the June month 2023 for '30 days' and mark 'weekend' on Weekends 'Saturday and Sunday' as weekends off, Store the data in CSV format with the header 'Employee Name, Employee ID, Date (dd-mm-yyyy), Time In, Time Out, Hours Worked' in Empfile.csv. Create another data for 'Ayush, Bhikam, Manish, Romesh, Sagar' to track approved or not approved leaves in June month for random days. Store the data in another CSV format with the headers 'Employee Name, Leave Type, Start Date, End Date, Leave Duration, Leave Status'. These two CSV files are interconnected, and the Leave Type should be 'Full day' or 'Half days'. If the total working days are less than '20 days', the salary of the employee will be deducted. Store this information in emp_leave_june.csv. Create another CSV format with headers 'Date, Day, Holiday Name, Type' for 2 public holidays in June 2023 and store it in emp_holiday_june.csv. Finally, create another CSV format with headers 'Employee Name, Employee ID, Total Days Worked, Total Leave, Total Hours Worked' and store it in emp_att_result_june.csv. Take a reference of the contents before generating the data. Generate data in CSV file format for IT Designations with headers 'Designation, Package' where designations are 'Software Engineer', 'Senior Software Engineer', 'Full Stack Developer', 'IT Consultant', and the package values are in Indian Rupees. Generate data in CSV file format for 5 Employee from It-Corporate where headers are  'EmployeeName, EmployeeID, Designation, Billing' where designation are like 'Software Engineer/Senior software Engineer/Full Stack Developer/ITconsultant' and for the Billing are randomly set 'Billable' and 'Non-Billable' that record store in EmpProfile.csv."
				+ "Generate the Billiable csv file format for employees for june month based on the following parameters which is total billiable hours is 8 ,bill rates for Software engineer 300 per hour,Senior Software Engineer 900 per hour, full stack developer 400 per hour,IT Consultant 300 per hours ,csv file contain the headers as Employee Name, Employee Id, Designation, Total Billable Hours(Calculated), Bill Rate, Billable Amount(Calculated) record store in EmppayrollBill.csv.and Genarate the employee productivity csv file format which contains the headers as Employee Name, Employee ID, Designation, Total Hours Worked, Billable Hours, Non-Billable Hours, Billable Productivity (%), Non-Billable Productivity (%), Bonus Amount. so Calculate the employee productivity and assign bonuses based on the following criteria,percentage of productivity for both billable and non-billable employees would be calculated on this parameter which is 'Billable Productivity % = (Billable Hours(Calculated)/ Hours Worked) * 100' and 'Non-Billable Productivity % = (Non-Billable Hours(Calculated) / Hours Worked) * 100' and Bonus assignment would be done on the following condition 'A bonus of $15,000 to the top 2 billable employees and the top 2 non-billable employees'.Please use the previously generated datasets for the month of June to perform the calculations and record store in EmpProductPay.csv.and Generate the payroll csv file format for employees for June month which contain headers as Employee Name, Employee Id, Designation, Salary, Tax Deduction, Net Salary.apply the applicable tax rates and deductions based on Indian tax laws and record store in EmppaySlip.csv.and Generate the final payroll csv file format for employees for june month which contains headers as Employee Name, Employee Id, Designation, Salary, Tax Deduction(Calculated), Net Salary(Calculated),Total Working Day(Calculated),Hours Worked(Calculated), Final Payroll Amount(Calculated) and record store in finalPay.csv";

		// Prepare HTTP client with handling Timeout Exception
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
				.readTimeout(120, TimeUnit.SECONDS).build();

		// Prepare JSON Object
		MediaType mediaType = MediaType.parse("application/json");
		String jsonString = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\""
				+ promptContent + "\"}]}";
		JSONObject jsonObject = new JSONObject(jsonString);

		// Print JSONObject for Debugging purpose.
		System.out.println(jsonObject);

		// Prepare body
		RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
		Request request = new Request.Builder().url("https://api.openai.com/v1/chat/completions").post(body)
				.addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer " + apiKey).build();

		// Execution of OpenAI
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String responseBody = response.body().string();
				JSONObject jsonResponse = new JSONObject(responseBody);
				String generatedCode = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
						.getString("content");

				// Print Generated Output/Response
				System.out.println("Generated Code:\n" + generatedCode);

				// Store response in file
				try (FileWriter fileWriter = new FileWriter(filePath)) {
					fileWriter.write(generatedCode);
					System.out.println("CSV file generated successfully.");
				} catch (IOException e) {
					System.out.println("An error occurred while generating the CSV file: " + e.getMessage());
				}
			} else {
				System.out.println("Request failed with code: " + response.code());
				System.out.println("Error message: " + response.body().string());
			}
		} catch (SocketTimeoutException e) {
			System.out.println("The request timed out. Please check your network connection.");
		} catch (IOException e) {
			System.out.println("An error occurred during the HTTP request: " + e.getMessage());
		}

		// File Separation
		Thread.sleep(1000);
		UseCaseSeperator.copyDataToCSVFiles();
	}

}
