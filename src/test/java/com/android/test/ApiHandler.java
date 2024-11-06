package com.android.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiHandler {
    private HttpClient client;

    public ApiHandler() {
        this.client = HttpClient.newHttpClient();
    }

    public String GET(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpResponse<String> response = null;
        
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Udało się pobrać dane z endpointu: " + response.body());
            } else {
                System.out.println("Nie udało się pobrać danych z endpointu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.body().isEmpty() ? "brak danych" : response.body();
    }

    private void POST(String uri, String body, String auth) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/hal+json")
                .header("Authorization", auth)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Udało się wysłać dane na endpoint.");
            } else {
                System.out.println("Nie udało się wysłać danych na endpoint.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
