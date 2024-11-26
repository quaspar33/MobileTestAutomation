package com.android.test;

public class PasswordFromSmsParser {
    public String parse(String input) {
        String[] lines = input.split("\n");
        for (String line : lines) {
            if (line.trim().startsWith("<#> code ")) {
                return line.trim().substring(9);
            }
        }
        return "";
    }
}
