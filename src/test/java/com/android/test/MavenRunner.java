package com.android.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MavenRunner {
    private String path;

    public MavenRunner() {
        this.path = System.getProperty("user.dir") + "/../CRMOperations";
    }

    public void run() {
       try {
           ProcessBuilder processBuilder = new ProcessBuilder("mvn", "test", "-q", "-DsuiteXmlFile=testng.xml");
           processBuilder.directory(new java.io.File(path));

           Process process = processBuilder.start();

           BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
           String line;
           while ((line = reader.readLine()) != null) {
               System.out.println(line);
           }

           int exitCode = process.waitFor();
           System.out.println("Maven zakończył działanie z kodem: " + exitCode);
       } catch (IOException | InterruptedException e) {
           e.printStackTrace();
       }
    }
}
