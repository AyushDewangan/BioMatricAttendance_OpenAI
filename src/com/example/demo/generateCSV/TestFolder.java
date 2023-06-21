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

public class TestFolder {

	public static void main(String[] args) throws Exception {
		String apiKey = "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";
		String filePath = "TestFolder\\Test.csv";

		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS) // Set the connect
																								// timeout
				.readTimeout(120, TimeUnit.SECONDS) // Set the read timeout
				.build();

		MediaType mediaType = MediaType.parse("application/json");
		String jsonString = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"Provide example csv dataset for employee Ayush,Romesh,Bhikam, for total '30 days' and mark 'weekend' on weekends with header as EmployeeName EmployeeID Date TimeIn TimeOut HoursWorked, where date format is dd-mm-yyyy. Create another data where 5 employee leave approved or not approved in 'june' month with header as EmployeeName EmployeeID LeaveType LeaveSatrtDate LeaveEndDate LeaveDuration LeaveStatus, where date format is dd-mm-yyyy,'LeaveSatrtDate LeaveEndDate LeaveDuration' depend on each, LeaveStatus is randomly 'Approved' or Not Approved, LeaveType set as 'Half Day' or 'Full Day', EmployeeName are like 'Ayush,Romesh,Bhikam'. Create one more dataset where dataset for only 2 holiday in june month with header as HolidayDate Day HolidayName Type, where date format ir dd-mm-yyyy and Type is 'public'. Create another dataset in csv format with Employee Name, Employee ID, Total Days Worked (Calculate), Total Leave(Calculte), Total Hours Worked(Calculate).  create these in csv file.'\"}]}";
		JSONObject jsonObject = new JSONObject(jsonString);

		System.out.println(jsonObject);

		RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
		Request request = new Request.Builder().url("https://api.openai.com/v1/chat/completions").post(body)
				.addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer " + apiKey).build();

		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String responseBody = response.body().string();
				JSONObject jsonResponse = new JSONObject(responseBody);
				String generatedCode = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
						.getString("content");
				System.out.println("Generated Code:\n" + generatedCode);

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
	}
}