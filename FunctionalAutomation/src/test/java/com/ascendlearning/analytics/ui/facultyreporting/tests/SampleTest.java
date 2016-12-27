package com.ascendlearning.analytics.ui.facultyreporting.tests;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ascendlearning.analytics.ui.facultyreporting.pageobjects.AdmissionsPage;
import com.ascendlearning.automation.ui.assertions.VerificationHandler;
import com.ascendlearning.automation.ui.config.PropertiesRepository;
import com.ascendlearning.automation.ui.exceptions.DriverException;
import com.ascendlearning.automation.ui.test.BaseTest;

public class SampleTest extends BaseTest {
	Logger logger = LogManager.getLogger(this.getClass());
	AdmissionsPage admissionPage = new AdmissionsPage(driver);

	/**
	 * This test case is to validate the user is navigated to Faculty reporting
	 * Home Page and select institution
	 * 
	 * @throws DriverException
	 */
	@Test(description = "InstitutionSelection", enabled = false, priority = 1)
	public void VerifyNavigationToFacultyReportingPageAndInstitutionSelection()
			throws DriverException {
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
	}

	/**
	 * This test case is to validate the user is navigated to Faculty reporting
	 * Home Page and select All Tab
	 * 
	 * @throws DriverException
	 */
	@Test(description = "AllSelection", enabled = false, priority = 1)
	public void VerifyNavigationToFacultyReportingPageAndSelectStudentParameterAll()
			throws DriverException {
		admissionPage.loginToFacultyReporting("rcrossp", "123");
		admissionPage.selectingInstitution("Ivy Tech Indy ASN");
		boolean flag = admissionPage
				.selectingAllAndHideGraduatedStudents(false);
		VerificationHandler.verifyTrue(flag);
	}

	/**
	 * This test case is to validate the user is navigated to Faculty reporting
	 * Home Page and select Cohort Tab
	 * 
	 * @throws DriverException
	 */
	@Test(description = "CohortSelection", enabled = false, priority = 1)
	public void VerifyNavigationToFacultyReportingPageAndSelectCohort()
			throws DriverException {
		admissionPage.loginToFacultyReporting("daveb", "123");
		admissionPage.selectingInstitution("ATI");
		boolean flag = admissionPage
				.selectCohortAndCohortNameAndHideGraduatedCohorts("1216", true);
		VerificationHandler.verifyFalse(flag);
	}

	/**
	 * This test case is to validate the user is navigated to Faculty reporting
	 * Home Page and select custom Group Tab
	 * 
	 * @throws DriverException
	 */
	/*
	 * @Test(description = "CustomGroupTabSelection", enabled = false, priority
	 * = 1) public void
	 * VerifyNavigationToFacultyReportingPageAndSelectCustomGroup() throws
	 * DriverException { admissionPage=new AdmissionsPage(driver);
	 * admissionPage.loginToFacultyReporting("admin","admin");
	 * admissionPage.selectingInstitutionAndMakeDefaultInstitution
	 * ("Alcorn State U ADN");
	 * admissionPage.selectingCustomGroupTabAndSelectingGroupNameFromDropDown
	 * ("Groupname3"); }
	 */
	/**
	 * This test case is to validate the user is navigated to Faculty reporting
	 * Home Page and select Individual Tab
	 * 
	 * @throws DriverException
	 */
	@Test(description = "IndividualStudentSelection", enabled = false, priority = 1)
	public void VerifyNavigationToFacultyReportingPageAndSelectIndividualStudentName()
			throws DriverException {
		admissionPage.loginToFacultyReporting("rcrossp", "123");
		admissionPage.selectingInstitution("Ivy Tech Gary ASN");
		String studentNameFromUI = admissionPage
				.selectingIndividualStudentFromAdmissionsTab("stephanie hernandez");
		VerificationHandler.verifyEquals("stephanie hernandez",
				studentNameFromUI);
	}

	/**
	 * This test case is to validate the user is navigated to Faculty reporting
	 * Home Page and select Date Range
	 * 
	 * @throws DriverException
	 */
	@Test(description = "DateRangeSelection", enabled = true, priority = 1)
	public void VerifyNavigationToFacultyReportingPageAndSelectDateRange()
			throws DriverException {
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting("rcrossp", "123");
		admissionPage.selectingInstitution("Ivy Tech Gary ASN");
		/* admissionPage.selectingDateFilterTakenOrSent("Sent"); */

		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.chooseToDateRange(PropertiesRepository
				.getString("toDate"));
	}

	/**
	 * This test case is to validate the user is navigated to Faculty reporting
	 * Home Page and select Cohort Tab
	 * 
	 * @throws DriverException
	 */
	@Test(description = "CohortSelection", enabled = false, priority = 1)
	public void VerifyNavigationToFacultyReportingPageAndSelectDisabledCohort()
			throws DriverException {
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		boolean flag = admissionPage
				.selectCohortAndCohortNameAndHideGraduatedCohorts("TEAS", false);
		VerificationHandler.verifyTrue(flag);
	}

	/**
	 * This test case is to verify the default value of student(s) drop down
	 * 
	 * @throws DriverException
	 */
	@Test(description = "AllSelection", enabled = false, priority = 1)
	public void VerifyDefaultValueOfStudentsDropdown() throws DriverException {
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		String DefaultValue = admissionPage.getDefaultValueOfStudentsDropDown();
		VerificationHandler.verifyEquals(DefaultValue, "All",
				"The default value for student(s) drop down is not matching");
	}

	/**
	 * This test case is to verify the contains of student(s) drop down
	 * 
	 * @throws DriverException
	 */
	@Test(description = "AllSelection", enabled = false, priority = 1)
	public void VerifyContentsOfStudentsDropdown() throws DriverException {

		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		Boolean verified = admissionPage.verifyContentsOfStudentsDropDown();
		VerificationHandler.verifyTrue(verified);
	}

	/**
	 * This test case is to verify that checkbox and HideGraduatedStudents label
	 * is displayed on selecting All from student(s) drop down
	 * 
	 * @throws DriverException
	 */
	@Test(description = "AllSelection", enabled = false, priority = 1)
	public void VerifyHideGraduatedStudentsCheckboxAndLabelOnSelectingAllFromStudentsDropdown()
			throws DriverException {

		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		Boolean verified = admissionPage
				.verifyCheckboxAndHideGraduatedStudentsForAllStudentsDropDownSelection();
		VerificationHandler.verifyTrue(verified);
	}

	/**
	 * This test case is to verify the cohort drop down and hide graduated
	 * cohort is displayed when cohort is selected from students drop down.
	 * 
	 * @throws DriverException
	 */
	@Test(description = "CohortSelection", enabled = false, priority = 1)
	public void VerifyCohortDropdownAndHideGraduatedCohortsIsDisplayedOnSelectingCohortFromStudentsDropDown()
			throws DriverException {

		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		boolean flag = admissionPage
				.VerifyCohortsDropdownAndHideGraduatedCohortsIsDisplayedOnCohortSelection();
		VerificationHandler.verifyTrue(flag);
	}

	/**
	 * This test case is to verify that Magnifying Glass Search icon is
	 * displayed when individual is selected from students dropdown
	 * 
	 * @throws DriverException
	 */
	@Test(description = "IndividualSelection", enabled = false, priority = 1)
	public void VerifyMagnifyingGlassSearchButtonIsDisplayedForIndividual()
			throws DriverException {

		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		admissionPage.selectIndividualFromStudentsParameter();
		boolean flag = admissionPage.isMagnifyingGlassSearchButtonIsDisplayed();
		VerificationHandler.verifyTrue(flag);
	}

	/**
	 * This test case is to verify that default cut score value is zero or not
	 * 
	 * @throws DriverException
	 */
	@Test(description = "EvaluateTotalScores", enabled = false, priority = 1)
	public void VerifyDefaultCutScoreValue() throws DriverException {
		int expectedScore = 0;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));

		int defaultScore = admissionPage.getDefaultCutScoreValue();

		VerificationHandler.verifyTrue((defaultScore == expectedScore),
				"Default cut score value is not an zero");
	}

	/**
	 * This test case is to verify EvaluateTotalScores Filter When TotalScore
	 * Column is Hidden
	 * 
	 * @throws DriverException
	 */
	@Test(description = "EvaluateTotalScores", enabled = false, priority = 1)
	public void VerifyEvaluateTotalScoresFilterWhenTotalScoreColumnIsHidden()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("partialTest.username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		flag = admissionPage.isGridHeadercolumnPresent("Total Score");
		VerificationHandler.verifyFalse(flag,
				"Grid column Total score column is not hidden");

		flag = admissionPage.isEvaluateTotalScoresFilterSectionDispalyed();
		VerificationHandler.verifyFalse(flag,
				"Evaluate Total Score filter is not hidden");

	}

	/**
	 * This test case is to verify count of students are shown above the grid
	 * 
	 * @throws DriverException
	 */
	@Test(description = "EvaluateTotalScores", enabled = false, priority = 1)
	public void VerifyCountOfStudentsAreShownAboveTheGrid()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username.one"),
				PropertiesRepository.getString("password.one"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName.one"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		admissionPage.enterCutScoreValueAndThenClickEnter(PropertiesRepository
				.getString("cutscore.value"));
		flag = admissionPage.isStudentCountDispalyedAboveTheGrid();
		VerificationHandler.verifyTrue(flag,
				"Students count not shown above the grid");
	}

	/**
	 * This test case is to verify count of students are shown above the grid
	 * 
	 * @throws DriverException
	 */
	@Test(description = "EvaluateTotalScores", enabled = false, priority = 1)
	public void VerifyColorCodeIsRemovedForTotalScoreColumnWhenCutScoreIsZero()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username.one"),
				PropertiesRepository.getString("password.one"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName.one"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		admissionPage.enterCutScoreValueAndThenClickEnter(PropertiesRepository
				.getString("cutscore.value.zero"));
		flag = admissionPage.isStudentCountDispalyedAboveTheGrid();
		VerificationHandler.verifyFalse(flag,
				"Students count shown above the grid");
		flag = admissionPage.verifyTotalScoreColumnColorCodeIsRemoved();
		VerificationHandler.verifyTrue(flag,
				"Color code is not removed when cutscore value is zero");
	}

	/**
	 * This test case is to verify count of students are shown above the grid
	 * 
	 * @throws DriverException
	 */
	@Test(description = "EvaluateTotalScores", enabled = false, priority = 1)
	public void VerifyColorCodeForTotalScoreColumnWhenCutScoreIsNotZero()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username.one"),
				PropertiesRepository.getString("password.one"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName.one"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		admissionPage.enterCutScoreValueAndThenClickEnter(PropertiesRepository
				.getString("cutscore.value"));
		flag = admissionPage.isStudentCountDispalyedAboveTheGrid();
		VerificationHandler.verifyTrue(flag,
				"Students count not shown above the grid");
		flag = admissionPage.verifyTotalScoreColumnColorCoded();
		VerificationHandler.verifyTrue(flag,
				"Color code is not removed when cutscore value is zero");
	}

	

	

	// TODO

	/**
	 * This test case is to verify the color of total score if the total score
	 * is more than or equal the cut score.
	 * 
	 * @throws DriverException
	 */

	@Test(description = "VerifyTheColorofTotalScoreWithCutScore", enabled = true, priority = 1)
	public void VerifyTheColorofTotalScoreForAtOrAboveCutScore()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username.one"),
				PropertiesRepository.getString("password.one"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName.one"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		admissionPage.enterCutScoreValueAndThenClickEnter(PropertiesRepository
				.getString("cutscore.value.max"));
		flag = admissionPage.isStudentCountDispalyedAboveTheGrid();
		VerificationHandler.verifyTrue(flag,
				"Student Count is not Dispalyed Above the Grid");

		flag = admissionPage.VerifyTheCountandColorForAboveTEASCutScore();
		VerificationHandler
				.verifyTrue(
						flag,
						"Color legend of total score do not match with, more than or equal the cut score");

	}

	/**
	 * This test case is to verify the color of total score if the total score
	 * is more than or equal the cut score.
	 * 
	 * @throws DriverException
	 */

	@Test(description = "VerifyTheColorofTotalScoreWithCutScore", enabled = true, priority = 1)
	public void VerifyTheColorofTotalScoreForBelowCutScore()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username.one"),
				PropertiesRepository.getString("password.one"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName.one"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		admissionPage.enterCutScoreValueAndThenClickEnter(PropertiesRepository
				.getString("cutscore.value.min"));
		flag = admissionPage.isStudentCountDispalyedAboveTheGrid();
		VerificationHandler.verifyTrue(flag,
				"Student Count is not Dispalyed Above the Grid");

		flag = admissionPage.VerifycountAndColorForBelowTEASCutScore();
		VerificationHandler
				.verifyTrue(flag,
						"Color legend of total score do not match with,less than the cut score");

	}

	/**
	 * This test case is to verify that if there is no count above or below the
	 * cut score then it should be shown as 0.
	 * 
	 * @throws DriverException
	 */

	@Test(description = "VerifyZeroCutScoreValue", enabled = true, priority = 1)
	public void VerifyZeroCutScoreValue() throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		admissionPage.enterCutScoreValueAndThenClickEnter(PropertiesRepository
				.getString("cutscore.value.max"));
		flag = admissionPage.VerifyTheCountandColorForAboveTEASCutScore();
		VerificationHandler
				.verifyTrue(flag,
						"Students score At or above TEAS Cut Score value fail to match with grid count");

		admissionPage.enterCutScoreValueAndThenClickEnter(PropertiesRepository
				.getString("cutscore.value.min"));
		flag = admissionPage.VerifycountAndColorForBelowTEASCutScore();
		VerificationHandler
				.verifyTrue(flag,
						"Students score At or below TEAS Cut Score value fail to match with grid count");

	}

	/**
	 * This test case is to verify total score column is shown only when all
	 * sub-test columns are present
	 * 
	 * @throws DriverException
	 */
	@Test(description = "EvaluateTotalScores", enabled = true, priority = 1)
	public void VerifyTotalScoreColumnWhenAllSubtestArePresent()
			throws DriverException {
		boolean subTestColumnPresent = false;
		boolean totalScoreColumn = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username.one"),
				PropertiesRepository.getString("password.one"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName.one"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		subTestColumnPresent = admissionPage
				.verifyAllSubtestsColumnsArePresent();
		totalScoreColumn = admissionPage
				.isGridHeadercolumnPresent("Total Score");
		VerificationHandler.verifyTrue(subTestColumnPresent,
				"All columns are not present");
		VerificationHandler.verifyTrue(totalScoreColumn,
				"Total score column is not present");
	}

	/**
	 * This test case is to verify that if subtest column is hidden the total
	 * score column is also hidden.
	 * 
	 * @throws DriverException
	 */
	@Test(description = "EvaluateTotalScores", enabled = true, priority = 1)
	public void VerifyTotalScoreColumnWhenAllSubtestAreNotPresent()
			throws DriverException {
		boolean subTestColumnPresent = false;
		boolean totalScoreColumn = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("partialTest.username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		subTestColumnPresent = admissionPage
				.verifyAllSubtestsColumnsArePresent();
		totalScoreColumn = admissionPage
				.isGridHeadercolumnPresent("Total Score");
		VerificationHandler.verifyFalse(subTestColumnPresent,
				"All columns are present");
		VerificationHandler.verifyFalse(totalScoreColumn,
				"Total score column is present");
	}

	/**
	 * This test case is to verify that if total score or sub-tests column is
	 * not hidden if it contains mixed data set
	 * 
	 * @throws DriverException
	 */
	@Test(description = "EvaluateTotalScores", enabled = true, priority = 1)
	public void VerifyTotalScoreAndSubTestColumnsWhenItContainsMixedDataSet()
			throws DriverException {
		boolean subTestColumnPresent = false;
		boolean totalScoreColumn = false;
		boolean partialTEAS = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username.one"),
				PropertiesRepository.getString("password.one"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName.one"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		subTestColumnPresent = admissionPage
				.verifyAllSubtestsColumnsArePresent();
		VerificationHandler.verifyTrue(subTestColumnPresent,
				"All columns are present");

		totalScoreColumn = admissionPage
				.isGridHeadercolumnPresent("Total Score");
		VerificationHandler.verifyTrue(totalScoreColumn,
				"Total score column is present");

		partialTEAS = admissionPage
				.verifyPartialTEASRecordsForColumn("Reading");
		VerificationHandler.verifyTrue(partialTEAS,
				"Reading column does not have partial TEAS record");

		partialTEAS = admissionPage
				.verifyPartialTEASRecordsForColumn("Total Score");
		VerificationHandler.verifyTrue(partialTEAS,
				"Total Score column does not have partial TEAS record");

		partialTEAS = admissionPage.verifyPartialTEASRecordsForColumn("Math");
		VerificationHandler.verifyTrue(partialTEAS,
				"Math column does not have partial TEAS record");

		partialTEAS = admissionPage
				.verifyPartialTEASRecordsForColumn("Science");
		VerificationHandler.verifyTrue(partialTEAS,
				"Science column does not have partial TEAS record");

		partialTEAS = admissionPage
				.verifyPartialTEASRecordsForColumn("English");
		VerificationHandler.verifyTrue(partialTEAS,
				"English column does not have partial TEAS record");

	}
	

	/**
	 * This test case is to Verify show academic Preparedness Label text
	 * 
	 * @throws DriverException
	 */
	@Test(description = "ShowAcademicPreparedness", enabled = true, priority = 1)
	public void VerifyShowAcademicPreparednessLabel()
			throws DriverException {
	    String label="";	
	
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();
		
		label=admissionPage.getAcademicPreparednessLabel();
        VerificationHandler.verifyEquals(label, PropertiesRepository.getString("show.academic.preparedness.label"), "The show academic preparedness label does not match");
	}
	
	/**
	 * This test case is to verify Show Academic Preparedness tab appears as
	 * collapsed on page load
	 * 
	 * @throws DriverException
	 */

	@Test(description = "ShowAcademicPreparedness", enabled = true, priority = 1)
	public void VerifyShowAcademicPreparednessIsCollapsedByDefault()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		flag = admissionPage.verifyShowAcademicPreparednessIsCollapsed();
		VerificationHandler.verifyTrue(flag,
				"Show Academic Preparedness tab is not collapsed on page load");

	}
	
	/**
	 * This test case is to Verify that the count of students is not shown for each academic Preparedness level
	 * 
	 * @throws DriverException
	 */
	@Test(description = "Show Academic Preparedness", enabled = true, priority = 1)
    public void countOfStudentsNotDisplayed() throws DriverException {
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username"),
                PropertiesRepository.getString("password"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName"));
        admissionPage.selectingAllAndHideGraduatedStudents(false);
        admissionPage.chooseFromDateRange(PropertiesRepository.getString("fromDate"));
        admissionPage.clickOnSubmitButton();
        admissionPage.expandShowAcademicPreparednessTab();
        boolean flag = admissionPage.countOfStudentsNotDisplayed();
        VerificationHandler.verifyTrue(flag);

    }

	/**
	 * This test case is to Verify that all or any check boxes can be selected from show academic Preparedness
	 * 
	 * @throws DriverException
	 */
    @Test(description = "Academic Preparedness checkboxes selection", enabled = true, priority = 1)
    public void singleAndAllCheckBoxesSelection() throws DriverException {
        admissionPage = new AdmissionsPage(driver);
        admissionPage.loginToFacultyReporting(PropertiesRepository.getString("username"),
                PropertiesRepository.getString("password"));
        admissionPage.selectingInstitution(PropertiesRepository.getString("institutionName"));
        admissionPage.selectingAllAndHideGraduatedStudents(false);
        admissionPage.chooseFromDateRange(PropertiesRepository.getString("fromDate"));
        admissionPage.clickOnSubmitButton();
        admissionPage.expandShowAcademicPreparednessTab();
        boolean flag = admissionPage.unCheckAllAcademicPreparednessCheckBoxes();
        VerificationHandler.verifyTrue(flag);

    }
    
    /**
	 * This test case is to verify Show Academic Preparedness tab should expand
	 * on button click and verify all the checkbox are checked
	 * 
	 * @throws DriverException
	 */
	@Test(description = "ShowAcademicPreparedness", enabled = true, priority = 1)
	public void ExpandShowAcademicPreparednessTabAndVerifyAllCheckboxChecked()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		flag = admissionPage.expandShowAcademicPreparednessTab();
		VerificationHandler.verifyTrue(flag,
				"Show Academic Preparedness tab is not collapsed on page load");

		flag = admissionPage
				.VerifyShowAcademicPreparednessTabProficientIsChecked();
		VerificationHandler.verifyTrue(flag,
				"Show Academic Preparedness tab, Proficient is not checked");

		flag = admissionPage
				.VerifyShowAcademicPreparednessTabAdvancedIsChecked();
		VerificationHandler.verifyTrue(flag,
				"Show Academic Preparedness tab, Advanced is not checked");

		flag = admissionPage.VerifyShowAcademicPreparednessTabBasicIsChecked();
		VerificationHandler.verifyTrue(flag,
				"Show Academic Preparedness tab, Basic is not checked");

		flag = admissionPage
				.VerifyShowAcademicPreparednessTabDevelopmentalIsChecked();
		VerificationHandler.verifyTrue(flag,
				"Show Academic Preparedness tab, Developmental is not checked");

		flag = admissionPage
				.VerifyShowAcademicPreparednessTabExemplaryIsChecked();
		VerificationHandler.verifyTrue(flag,
				"Show Academic Preparedness tab, Exemplary is not checked");

	}
	
	/**
	 * This test case is to Verify that the deselection of all the checkboxes, from show academic preparedness tab
	 * to show up an empty grid.
	 * 
	 * @throws DriverException
	 */

	@Test(description = "ShowAcademicPreparedness", enabled = true, priority = 1)
	public void VerifyDeselectShowAcademicPreparednessCheckboxesShowEmptyGrid()
			throws DriverException {
		int count = 0;
		int naCount=0;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		count = admissionPage.verifyUncheckShowAcademicPreparednessShowBlankGrid();
		if (count!=0){
			naCount=admissionPage.getNARecordsCount();
			VerificationHandler.verifyEquals(Integer.toString(count),Integer.toString(naCount),"The count of NA records is not matching");
		}
		else
			VerificationHandler.verifyEquals(Integer.toString(count),Integer.toString(naCount),"The count of records is not matching");
		
	}
	
	/**
	 * This test case is to Verify that the grid is sorted by default by Date taken:Most Recent.
	 * 
	 * @throws DriverException
	 */

	@Test(description = "Grid is sorted by default by Date:Most Recent", enabled = true, priority = 1)
	public void VerifyGridDefaultDateRecent()
			throws DriverException {
		boolean flag = false;
		admissionPage = new AdmissionsPage(driver);
		admissionPage.loginToFacultyReporting(
				PropertiesRepository.getString("username"),
				PropertiesRepository.getString("password"));
		admissionPage.selectingInstitution(PropertiesRepository
				.getString("institutionName"));
		admissionPage.selectingAllAndHideGraduatedStudents(false);
		admissionPage.chooseFromDateRange(PropertiesRepository
				.getString("fromDate"));
		admissionPage.clickOnSubmitButton();

		flag = admissionPage.verifyDefaultSortingForGrid();
		VerificationHandler.verifyTrue(flag,"The Date Taken column in the grid by default is not sorted as Most recent");

	}
	
	
}