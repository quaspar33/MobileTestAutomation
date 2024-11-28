package com.android.test;

import browserstack.shaded.org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestFailureListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        WebDriver driver = BaseTest.driver;
        final JavascriptExecutor jse = (JavascriptExecutor) driver;
        JSONObject executorObject = new JSONObject();
        JSONObject argumentsObject = new JSONObject();
        argumentsObject.put("status", "failed");
        argumentsObject.put("reason", result.getThrowable().getMessage());
        executorObject.put("action", "setSessionStatus");
        executorObject.put("arguments", argumentsObject);
        jse.executeScript(String.format("browserstack_executor: %s", executorObject));

    }
}
