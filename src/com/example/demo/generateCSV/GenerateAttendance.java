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

public class GenerateAttendance {
    public static void main(String[] args) throws Exception {
        String apiKey = "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";
        String filePath = "emp_att_june.csv";

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS) // Set the connect timeout
                .readTimeout(120, TimeUnit.SECONDS) // Set the read timeout
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        String jsonString = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"Provide example csv dataset for employee Ayush,Romesh,Bhikam, for total '30 days' and mark 'weekend' on weekends with header as EmployeeName EmployeeID Date TimeIn TimeOut HoursWorked, where date format is dd-mm-yyyy\"}]}";
        JSONObject jsonObject = new JSONObject(jsonString);

        System.out.println(jsonObject);

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

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