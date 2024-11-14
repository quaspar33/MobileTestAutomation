package com.android.test.tests;

import com.android.test.AbstractTest;
import com.android.test.pages.AcceptCommissionPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AcceptCommissionTest extends AbstractTest {
    private AcceptCommissionPage acceptCommissionPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        acceptCommissionPage = new AcceptCommissionPage(driver);
    }

    @Test(groups = "acceptCommission", dependsOnGroups = "verifyPartner")
    public void acceptCommissionTest() {
        acceptCommissionPage.clickCommission();
        acceptCommissionPage.acceptCommission();
        acceptCommissionPage.setAgreements();
    }
}
