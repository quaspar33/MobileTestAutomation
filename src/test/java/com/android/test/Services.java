package com.android.test;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Services {

    private SecureRandom random = new SecureRandom();

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        pesel.append(birthDate.format(formatter));

        int serialNumber = generateSerialNumber(gender);
        pesel.append(String.format("%03d", serialNumber));

        pesel.append(calculateChecksum(pesel.toString()));

        return pesel.toString();
    }

    private int generateSerialNumber(char gender) {
        int start = (gender == 'M' || gender == 'm') ? 0 : 500;
        int end = start + 499;
        return random.nextInt(end - start + 1) + start;
    }

    private char calculateChecksum(String peselWithoutChecksum) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1};
        int sum = 0;

        for (int i = 0; i < peselWithoutChecksum.length(); i++) {
            sum += Character.getNumericValue(peselWithoutChecksum.charAt(i)) * weights[i];
        }

        int remainder = sum % 10;
        return (char) ((10 - remainder) % 10 + '0');
    }
}
