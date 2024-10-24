package com.android.test;

import java.math.BigInteger;
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
        int year = birthDate.getYear();
        int month = birthDate.getMonthValue();

        if (year >= 2000) {
            month += 20;
        }

        pesel.append(String.format("%02d%02d%02d", year % 100, month, birthDate.getDayOfMonth()));

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

        for (int i = 0; i < 10; i++) {
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
}
