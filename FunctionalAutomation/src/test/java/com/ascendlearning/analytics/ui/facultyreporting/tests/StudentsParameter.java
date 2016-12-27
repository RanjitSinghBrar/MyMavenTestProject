package com.ascendlearning.analytics.ui.facultyreporting.tests;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ascendlearning.analytics.ui.facultyreporting.pageobjects.AdmissionsPage;
import com.ascendlearning.automation.ui.assertions.VerificationHandler;
import com.ascendlearning.automation.ui.config.PropertiesRepository;
import com.ascendlearning.automation.ui.exceptions.DriverException;
import com.ascendlearning.automation.ui.test.BaseTest;

public class StudentsParameter extends BaseTest {

    Logger logger = LogManager.getLogger(this.getClass());
    AdmissionsPage admissionPage = null;

    @Test(groups = { "Sanity", "StudentsParameter", "FR-144", "ATIFR-1140" }, enabled = true, priority = 1)
    public void VerifyStudentParameterisDropdown() throws DriverException {
        logger.info("Verify that the Student(s) parameter is changed to drop down.");
        boolean flag = false;
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username"),
                PropertiesRepository.getString("password"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName"));

        flag = admissionPage.VerifyStudentDropdownTag();
        VerificationHandler.verifyTrue(flag, "The Student(s) field failed to show up as dropdown");
    }
}
