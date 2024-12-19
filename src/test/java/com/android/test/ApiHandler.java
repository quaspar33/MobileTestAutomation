package com.android.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiHandler {
    private HttpClient client;
    Pattern pattern = Pattern.compile("\"err\":\"([^\"]+)\"");

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
                System.out.println("Udało się pobrać dane: " + response.body() + ", z endpointu: " + uri);
            } else {
                System.out.println("Nie udało się pobrać danych z endpointu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.body().isEmpty() ? "brak danych" : response.body();
    }

    public void POST(String uri, String body, String auth) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/hal+json")
                .header("Authorization", auth)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Udało się wysłać dane na endpoint. Response code: 200");
            } else {
                Matcher matcher = pattern.matcher(response.body());
                System.out.printf(
                        """
                        Nie udało się wysłać danych na endpoint dla: %s.
                        Powód: %s
                        Response code: %s
                        """,
                        matcher.find() ? matcher.group(1) : "nie udało się pobrać treści błędu",
                        response.body(),
                        response.statusCode()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
