package com.android.test;

import org.testng.annotations.Test;

public class LaunchAppTest extends BaseTest {

    @Test
    public void testAppLaunch() {
        System.out.println("App is launched successfully!");
        assert driver != null : "Driver was not initialized";
    }
}
