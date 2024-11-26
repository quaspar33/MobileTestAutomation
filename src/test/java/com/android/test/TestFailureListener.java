package com.android.test;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestFailureListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        throw new RuntimeException("Test zakończony niepowodzeniem.");
    }
}
