package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AcceptCommissionPage extends AbstractPage {
    private JsonHandler jsonHandler;

    public AcceptCommissionPage(AndroidDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("accept_commission");
    }

    private void sendDataToEndpoint() {
        HttpClient client = HttpClient.newHttpClient();

        String jsonBody = jsonHandler.getStrFromJson("body");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(jsonHandler.getStrFromJson("uri")))
                .header("Content-Type", "application/hal+json")
                .header("Authorization", jsonHandler.getStrFromJson("authorization"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Udało się wysłać dane na endpoint");
            } else {
                System.out.println("Nie udało się wysłać danych na endpoint.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
