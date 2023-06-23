package com.example.demo.generateCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
 * 		      ...Pseudo Code...
 * 1. Initializing ApiKey, FilePath, Prompt.
 * 2. Prepare HTTP client.
 * 3. Prepare JSONObject, RequestBody, Authorization, API URL.
 * 4. Executing the OpenAI API.
 * 5. Retrieving the actual content from the response.
 * 6. Store response in CSV file.
 * 7. Separate the CSV files.
 * */

public class UseCase1 {

	public static void main(String[] args) throws Exception {

		// Initializing ApiKey, FilePath, Prompt
		String apiKey = "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";
		String filePath = "Present\\Test.csv";
		String promptContent = "Provide example csv dataset for employee Ayush,Romesh,Bhikam, for total '30 days'"
				+ " and mark 'weekend' on weekends with header as EmployeeName EmployeeID Date TimeIn TimeOut"
				+ " HoursWorked, where date format is dd-mm-yyyy that record store in emp_att_june.csv. Create"
				+ " another data where 5 employee leave approved or not approved in 'june' month with header as"
				+ " EmployeeName EmployeeID LeaveType LeaveSatrtDate LeaveEndDate LeaveDuration LeaveStatus, where"
				+ " date format is dd-mm-yyyy,'LeaveSatrtDate LeaveEndDate LeaveDuration' depend on each,"
				+ " LeaveStatus is randomly 'Approved' or Not Approved, LeaveType set as 'Half Day' or 'Full Day',"
				+ " EmployeeName are like 'Ayush,Romesh,Bhikam' , store that records in emp_leave_june.csv file."
				+ " Create one more dataset where dataset for only 2 holiday in june month with header as HolidayDate Day HolidayName Type,"
				+ " where date format ir dd-mm-yyyy and Type is 'public' store in emp_holiday_june.csv file. Create another dataset in csv format"
				+ " with Employee Name, Employee ID, Total Days Worked (Calculate), Total Leave(Calculte),"
				+ " Total Hours Worked(Calculate) store this records in emp_payroll_cal.csv.  create these in csv file.";

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
