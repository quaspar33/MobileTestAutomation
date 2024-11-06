package com.android.test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Services {

    private SecureRandom random = new SecureRandom();
    private JsonHandler jsonHandler = new JsonHandler("services.json");

    public String generateRandomString(int length, String chars) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public String generatePesel(LocalDate birthDate, char gender) {
        StringBuilder pesel = new StringBuilder();

        int year = birthDate.getYear();
        int month = birthDate.getMonthValue();
        int day = birthDate.getDayOfMonth();

        if (year >= 2000) {
            month += 20;
        }

        pesel.append(String.format("%02d%02d%02d", year % 100, month, day));

        int serialNumber = generateSerialNumber(gender);
        pesel.append(String.format("%03d", serialNumber));

        pesel.append(calculateChecksum(pesel.toString()));

        return pesel.toString();
    }

    private int generateSerialNumber(char gender) {
        int start = (gender == 'M' || gender == 'm') ? 1 : 0;
        return random.nextInt(500) * 10 + start;
    }

    private char calculateChecksum(String peselWithoutChecksum) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;

        for (int i = 0; i < peselWithoutChecksum.length(); i++) {
            sum += Character.getNumericValue(peselWithoutChecksum.charAt(i)) * weights[i];
        }

        int remainder = sum % 10;
        int checksum = (10 - remainder) % 10;

        return (char) (checksum + '0');
    }

    public String generateRandomBankAccount() {
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < 24; i++) {
            accountNumber.append(random.nextInt(10));
        }

        String checkDigits = calculateIbanChecksum("PL00" + accountNumber);

        return checkDigits + accountNumber;
    }

    private String calculateIbanChecksum(String ibanWithoutChecksum) {
        String rearranged = ibanWithoutChecksum.substring(4) + "252100";

        BigInteger ibanAsNumber = new BigInteger(rearranged);

        BigInteger remainder = ibanAsNumber.mod(BigInteger.valueOf(97));
        BigInteger checksum = BigInteger.valueOf(98).subtract(remainder);

        return String.format("%02d", checksum);
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
