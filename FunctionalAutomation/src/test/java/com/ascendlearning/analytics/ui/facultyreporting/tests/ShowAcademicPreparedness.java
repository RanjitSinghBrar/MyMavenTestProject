package com.ascendlearning.analytics.ui.facultyreporting.tests;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ascendlearning.analytics.ui.facultyreporting.pageobjects.AdmissionsPage;
import com.ascendlearning.automation.ui.assertions.VerificationHandler;
import com.ascendlearning.automation.ui.config.PropertiesRepository;
import com.ascendlearning.automation.ui.exceptions.DriverException;
import com.ascendlearning.automation.ui.test.BaseTest;

public class ShowAcademicPreparedness extends BaseTest {
    Logger logger = LogManager.getLogger(this.getClass());
    AdmissionsPage admissionPage = null;

    /**
     * This test case is to verify the filter when the selected Institution's
     * Program type is ATI TEAS Allied Health.
     * 
     * @throws DriverException
     */
    @Test(groups = { "Regression", "Show_Academic_Preparedness", "FR-198",
            "ATIFR-797", }, description = "academicPreparednessDisplay", enabled = true, priority = 1)
    public void programTypeIsATITEASAlliedHealth() throws DriverException {
        logger.info("Verify that if the selected institution's ProgramType is ATI TEAS Allied Health remove.");
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("ATITeasAlliedHealthProgramTypeUser"),
                PropertiesRepository.getString("password"));
        admissionPage.chooseFromDateRange(PropertiesRepository.getString("fromDate"));
        admissionPage.selectingAllAndHideGraduatedStudents(false);
        admissionPage.clickOnSubmitButton();
        admissionPage.waitForGridLoad();
        VerificationHandler.verifyFalse(admissionPage.verifyShowAcademicPreparednessSectionDisplay());
    }

    /**
     * This test case is to verify the filter when the selected Product is
     * Practice TEAS.
     * 
     * @throws DriverException
     */
    @Test(groups = { "Regression", "Show_Academic_Preparedness", "FR-199",
            "ATIFR-797", }, description = "academicPreparednessDisplay", enabled = true, priority = 2)
    public void programTypeIsPracticeTEAS() throws DriverException {
        logger.info("Verify that if the selected product is Practice TEAS then remove filter and hide");
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("PracticeTeasProgramTypeUser"),
                PropertiesRepository.getString("password"));
        admissionPage.selectingInstitution("ATI");
        admissionPage.selectingProduct("Practice TEAS");
        admissionPage.selectingAllAndHideGraduatedStudents(false);
        admissionPage.chooseFromDateRange(PropertiesRepository.getString("fromDate"));
        admissionPage.selectingPraticeTeasForms("Discover");
        admissionPage.clickOnSubmitButton();
        admissionPage.waitForGridLoad();
        VerificationHandler.verifyFalse(admissionPage.verifyShowAcademicPreparednessSectionDisplay());
    }

    /**
     * This test case is to verify the grid is changed based upon the selection
     * made in the "Show Academic Preparedness".
     * 
     * @throws DriverException
     */
    @Test(groups = { "Sanity", "Show_Academic_Preparedness", "FR-198",
            "ATIFR-797", }, description = "academicPreparednessCheckboxes", enabled = true, priority = 3)
    public void gridChangedUponCheckOrUncheckOfTheCheckboxes() throws DriverException {
        logger.info("Verify that grid is changed upon check/uncheck of the checkboxes.");
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username"),
                PropertiesRepository.getString("password"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName"));
        admissionPage.chooseFromDateRange(PropertiesRepository.getString("fromDate"));
        admissionPage.chooseToDateRange("12/16/2015");
        admissionPage.selectingAllAndHideGraduatedStudents(false);
        admissionPage.clickOnSubmitButton();
        admissionPage.waitForGridLoad();
        admissionPage.expandShowAcademicPreparednessTab();
        VerificationHandler.verifyTrue(admissionPage.VerifyGridExemplaryIsUnChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyGridAdvancedIsUnChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyGridProficientIsUnChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyGridBasicIsUnChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyGridDevelopmentalIsUnChecked());
    }

    /**
     * This test case is to verify the Academic Preparedness level checkbox when
     * there is no data for any of the Academic Preparedness level.
     * 
     * @throws DriverException
     */

    @Test(groups = { "Regression", "Show_Academic_Preparedness", "FR-202",
            "ATIFR-797", }, description = "academicPreparednessCheckboxes", enabled = true, priority = 4)
    public void noDataForAcademiPreparednesslevelcheckboxFunctionality() throws DriverException {
        logger.info(
                "Verify that if there are no data for any of the academic Preparedness level then also the checkbox is checked and enabled.");
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username"),
                PropertiesRepository.getString("password"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName"));
        admissionPage.chooseFromDateRange("10/10/2015");
        admissionPage.chooseToDateRange("10/10/2015");
        admissionPage.clickOnSubmitButton();
        boolean flag = admissionPage.verifyStudentSoreTableDisplay();
        VerificationHandler.verifyFalse(flag);
        admissionPage.expandShowAcademicPreparednessTab();
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabExemplaryIsChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabAdvancedIsChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabProficientIsChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabBasicIsChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabDevelopmentalIsChecked());
    }

    /**
     * This test case is to verify the filters are reset to default values when
     * the user change the institution and any of the above parameters and click
     * on Submit button.
     * 
     * @throws DriverException
     */

    @Test(groups = { "Smoke", "Show_Academic_Preparedness", "FR-201",
            "ATIFR-797", }, description = "academicPreparednessCheckboxes", enabled = true, priority = 5)
    public void academicPreparednessCheckboxesChangeUponInstitutionSelection() throws DriverException {
        logger.info(
                "Verify that the filters are reset to default values when the institution or any of the above parameters are changed and submit button is clicked.");
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username"),
                PropertiesRepository.getString("password"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName"));
        admissionPage.chooseFromDateRange(PropertiesRepository.getString("fromDate"));
        admissionPage.chooseToDateRange("12/16/2015");
        admissionPage.selectingAllAndHideGraduatedStudents(false);
        admissionPage.clickOnSubmitButton();
        admissionPage.waitForGridLoad();
        admissionPage.expandShowAcademicPreparednessTab();
        VerificationHandler.verifyTrue(admissionPage.VerifyGridProficientIsUnChecked());
        admissionPage.selectingInstitution("Ivy Tech Indy ASN");
        admissionPage.clickOnSubmitButton();
        admissionPage.waitForGridLoad();
        admissionPage.expandShowAcademicPreparednessTab();
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabExemplaryIsChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabAdvancedIsChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabProficientIsChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabBasicIsChecked());
        VerificationHandler.verifyTrue(admissionPage.VerifyShowAcademicPreparednessTabDevelopmentalIsChecked());
    }

}
