package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;

public class AcceptCommissionPage extends AbstractPage {
    private JsonHandler jsonHandler;
    private int commissionDay;

    public AcceptCommissionPage(AndroidDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("accept_commission");
        commissionDay = currentDay + 2;
        apiHandler.POST(
                jsonHandler.getStrFromJson("uri"),
                String.format("{\"employees_per_day\":1,\"hours\":8,\"start_time\":\"08:00\",\"dates\":[\"%d-%02d-%02d\"],\"region\":\"4809\",\"location\":\"460\",\"commission\":\"1463\"}", currentYear, currentMonth, commissionDay),
                jsonHandler.getStrFromJson("auth"));
    }


}
