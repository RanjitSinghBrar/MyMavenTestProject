package com.ascendlearning.analytics.ui.facultyreporting.tests;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ascendlearning.analytics.ui.facultyreporting.pageobjects.AdmissionsPage;
import com.ascendlearning.automation.ui.assertions.VerificationHandler;
import com.ascendlearning.automation.ui.config.PropertiesRepository;
import com.ascendlearning.automation.ui.exceptions.DriverException;
import com.ascendlearning.automation.ui.test.BaseTest;

public class ProductDropDown extends BaseTest {
    Logger logger = LogManager.getLogger(this.getClass());
    AdmissionsPage admissionPage = null;

    /**
     * This test case is to Verify that on selecting "Discover" from the product
     * drop down then the versions should not show up
     * 
     * @throws DriverException
     */

    @Test(groups = { "Sanity", "ProductDropDown", "FR-131", "ATIFR-1139", }, enabled = true, priority = 1)
    public void VerifyDiscoverProductVersionsisDisplayed() throws DriverException {
        logger.info(
                "Verify that on selecting Discover from the product drop down then the versions should not show up");
        boolean flag = true;
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username.one"),
                PropertiesRepository.getString("password.one"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName.one"));

        flag = admissionPage.ProductVersionisDisplayed(PropertiesRepository.getString("select.Product.Discover"));
        VerificationHandler.verifyFalse(flag, "The versions failed to disappear for Discover from product dropdown");

    }

    /**
     * This test case is to Verify that on selecting "TEAS V Allied Health" from
     * the product drop down then the versions should not show up
     * 
     * @throws DriverException
     */

    @Test(groups = { "Sanity", "ProductDropDown", "FR-140", "ATIFR-801", }, enabled = true, priority = 2)
    public void VerifyTEASVAlliedHealthProductVersionsisDisplayed() throws DriverException {
        logger.info("Verify that on selecting TEAS V Allied Health from the product drop down then the version");
        boolean flag = true;
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username.one"),
                PropertiesRepository.getString("password.one"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName.one"));

        flag = admissionPage
                .ProductVersionisDisplayed(PropertiesRepository.getString("select.Product.TeasAlliedHealth"));
        VerificationHandler.verifyFalse(flag,
                "The versions failed to disappear for TEAS V Allied Health from product dropdown");

    }

    /**
     * This test case is to Verify that on selecting "Practice TEAS" from the
     * product drop down then the versions should show up
     * 
     * @throws DriverException
     */

    @Test(groups = { "Sanity", "ProductDropDown", "FR-141", "ATIFR-801", }, enabled = true, priority = 3)
    public void VerifyPracticeTEASProductVersionsisDisplayed() throws DriverException {
        boolean flag = false;
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username.one"),
                PropertiesRepository.getString("password.one"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName.one"));

        flag = admissionPage.ProductVersionisDisplayed(PropertiesRepository.getString("select.Product.PracticeTEAS"));
        VerificationHandler.verifyTrue(flag, "The versions failed to show up for Practice TEAS from product dropdown");

    }

}
