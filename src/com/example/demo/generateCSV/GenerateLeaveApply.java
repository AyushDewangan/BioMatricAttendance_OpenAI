package com.example.demo.generateCSV;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateLeaveApply {
	public static void main(String[] args) throws Exception {
		String apiKey = "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";
		// String prompt = "Provide example csv dataset for employee Nachiket for total
		// 5 days with header as EmployeeName EmployeeID Date TimeIn TimeOut
		// HoursWorked";
		String filePath = "emp_leave_june.csv";
		int timeoutMiutes = 5;
		OkHttpClient client = new OkHttpClient().newBuilder()
				.callTimeout(timeoutMiutes, java.util.concurrent.TimeUnit.MINUTES).build();
		MediaType mediaType = MediaType.parse("application/json");
		String jsonString = "{ \"model\": \"gpt-3.5-turbo\",\"messages\":[{ \"role\":\"user\",\"content\":\"Provide example of csv dataset for 5 employee leave approved or not approved in 'june' month with header as EmployeeName EmployeeID LeaveType LeaveSatrtDate LeaveEndDate LeaveDuration LeaveStatus, where date format is dd-mm-yyyy,'LeaveSatrtDate LeaveEndDate LeaveDuration' depend on each, LeaveStatus is randomly 'Approved' or Not Approved, LeaveType set as 'Half Day' or 'Full Day', EmployeeName are like 'Ayush,Romesh,Bhikam' sequencialy EmployeeID are '123,456,789' \"}]}";

		JSONObject jsonObject = new JSONObject(jsonString);
		// System.out.println(jsonObject);

		RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
		Request request = new Request.Builder().url("https://api.openai.com/v1/chat/completions").post(body)
				.addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer " + apiKey).build();

		// Executing the OpenAI
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			String responseBody = response.body().string();
			JSONObject jsonResponse = new JSONObject(responseBody);
			String generatedCode = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
					.getString("content");
			System.out.println("Generated Code:\n" + generatedCode);

			// Storing response in csv file
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
	}

}
