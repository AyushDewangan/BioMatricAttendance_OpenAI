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
 * Generating the Employee Designation with headers be like - 
 * Designation, Salary
 * 
 * */
public class GenerateEmpDesignation {

	public static void main(String[] args) throws Exception {

		// Initializing ApiKey, FilePath, Prompt
		String apiKey = "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";
		String filePath = "TestFolder\\EmpDesignation.csv";
		String promptContent = "Generate data in CSV file format for It Designations where headers are"
				+ " 'Designation, Package where designation are like"
				+ " 'Software Engineer/Senior software Engineer/Full Stack Developer/ITconsultant' "
				+ "and for the Package are in Indian Rupees";

		// Prepare HTTP client with handling Timeout Exception
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS).build();

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
	}

}
