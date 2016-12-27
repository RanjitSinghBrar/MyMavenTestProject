package com.ascendlearning.analytics.ui.facultyreporting.pageobjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ascendlearning.automation.ui.assertions.VerificationHandler;
import com.ascendlearning.automation.ui.config.PropertiesRepository;
import com.ascendlearning.automation.ui.exceptions.DriverException;
import com.ascendlearning.automation.ui.handlers.BaseHandler;
import com.ascendlearning.automation.ui.handlers.ButtonHandler;
import com.ascendlearning.automation.ui.handlers.CheckboxHandler;
import com.ascendlearning.automation.ui.handlers.DropDownHandler;
import com.ascendlearning.automation.ui.handlers.TextHandler;
import com.ascendlearning.automation.ui.handlers.WindowHandler;
import com.ascendlearning.automation.ui.page.BasePage;

public class AdmissionsPage extends BasePage {
    Logger logger = LogManager.getLogger(this.getClass());

    private TextHandler textHandler;
    private ButtonHandler buttonhandler;
    private WindowHandler windowsHandler;
    private DropDownHandler dropDownHandler;
    private CheckboxHandler checkBoxHandler;
    private JavascriptExecutor javaScriptActions;
    private BaseHandler baseHandler;
    List<WebElement> checkboxs;
    String monthToSelect = "";
    String dateToSelect = "";
    String yearToSelect = "";
    String minYearRange = "";
    WebDriverWait driverWait;

    public AdmissionsPage(WebDriver webDriver) {
        super(webDriver);

        textHandler = new TextHandler(driver);
        buttonhandler = new ButtonHandler(driver);
        windowsHandler = new WindowHandler(driver);
        dropDownHandler = new DropDownHandler(driver);
        checkBoxHandler = new CheckboxHandler(driver);
        javaScriptActions = (JavascriptExecutor) driver;
        baseHandler = new BaseHandler(driver);
    }

    /**
     * This method Invoke the browser and Login to ATI
     * 
     * @throws
     **/
    public AdmissionsPage loginToFacultyReporting(String username, String password) {
        loginToFacultyReportingPage(username, password);
        return new AdmissionsPage(driver);
    }

    /**
     * This method is to login to application with user name and password
     * 
     * @param userName
     *            ,password @throws void
     **/
    private boolean loginToFacultyReportingPage(String userName, String password) {
        boolean verified = false;
        logger.info("Method : goToAdmissionsPage :::::::: START");
        try {

            // Navigate to Faculty Reporting page and enter the user credentials
            driver.get(PropertiesRepository.getString("mainpage.url"));
            setDriverWait(PropertiesRepository.getString("faculty.reporting.login.username.field"));
            textHandler.writeText(PropertiesRepository.getString("faculty.reporting.login.username.field"), userName);

            WebElement pwdField = driver.findElement(
                    By.cssSelector(PropertiesRepository.getString("faculty.reporting.login.password.field")));
            if (pwdField != null) {

                textHandler.writeText(PropertiesRepository.getString("faculty.reporting.login.password.field"),
                        password);

            } else {
                logger.info("Password Field is Fetched as null");
            }

            buttonhandler.clickButton(PropertiesRepository.getString("faculty.reporting.login.ok.button"));

            // To verify the user navigated to Faculty Reporting Home page.
            verified = verifyNavigationToFacultyReportingHomePage();

            logger.info("Method : goToDashBoardPage :::::::: END");
        } catch (DriverException e) {
            logger.info("Element is not Display in UI " + e);
        }

        return verified;

    }

    /**
     * This method is for Verifying whether the user is navigated to Faculty
     * Reporting home page.
     **/
    private boolean verifyNavigationToFacultyReportingHomePage() {
        boolean flag = false;
        try {
            setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.header"));
            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            if (textHandler.getText(PropertiesRepository.getString("faculty.reporting.admissions.tab.header"))
                    .equals("TEAS Score Report Generation")) {
                flag = true;
            }

        } catch (DriverException e) {
            logger.info("Faculty Reporting page is not displaying");

        }
        return flag;

    }

    /**
     * This method is for selecting institution from institution drop down.
     **/
    public void selectingInstitution(String institutionName) {
        try {
            logger.info("selectingInstitutionAndMakeDefaultInstitution :::::::: Start ");
            javaScriptActions.executeScript("window.scrollBy(0,-250);");
            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            Select sel = dropDownHandler.getDropDown(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.institution.drop.down"));

            sel.selectByVisibleText(institutionName);
            // javaScriptActions.executeScript("window.scrollBy(0,-250);");

            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            logger.info("selectingInstitutionAndMakeDefaultInstitution :::::::: End ");

        } catch (DriverException e) {
            logger.info("Element is not Present " + e);
        }
    }

    /**
     * This method is for selecting All from drop down and Selecting hide
     * garduated students
     * 
     * @throws DriverException
     **/
    public boolean selectingAllAndHideGraduatedStudents(boolean checkBoxSelection) throws DriverException {
        boolean checked = false;
        logger.info("selectingAllTabAndHideGraduatedStudents :::::::: Start ");

        Select studentDropdown = dropDownHandler
                .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.students.dropdown"));
        studentDropdown.selectByVisibleText("All");

        if (!checkBoxSelection) {
            try {
                checkBoxHandler.selectCheckbox(PropertiesRepository
                        .getString("faculty.reporting.admissions.tab.checkbox.hide.graduated.students"));
                checked = true;
            } catch (DriverException e) {
                e.printStackTrace();
            }
        }

        logger.info("selectingAllTabAndHideGraduatedStudents :::::::: END");
        return checked;
    }

    /**
     * This method is for checking the default value of Student(s) drop down
     * 
     **/
    public String getDefaultValueOfStudentsDropDown() {
        String defaultDropdownValue = "";
        Select studentDropdown;
        logger.info("verifyDefaultValueOfStudentsDropDown :::::::: Start ");
        try {
            studentDropdown = dropDownHandler
                    .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.students.dropdown"));
            defaultDropdownValue = studentDropdown.getFirstSelectedOption().getText();
        } catch (DriverException e) {
            System.out.println("The student(s) parameter drop down is not visible");
            e.printStackTrace();
        }
        logger.info("verifyDefaultValueOfStudentsDropDown :::::::: End ");
        return defaultDropdownValue;
    }

    /**
     * This method is for verifying the contents of Student(s) drop down
     * 
     * @return boolean
     **/
    public boolean verifyContentsOfStudentsDropDown() {
        boolean flag = true;
        List<WebElement> dropdownValues = new ArrayList<WebElement>();
        List<String> expectedDropValues = new ArrayList<String>();
        Select studentDropdown;
        logger.info("verifyContentsOfStudentsDropDown :::::::: Start ");
        expectedDropValues.add("All");
        expectedDropValues.add("Cohort");
        expectedDropValues.add("Individual");

        try {
            studentDropdown = dropDownHandler
                    .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.students.dropdown"));
            dropdownValues = studentDropdown.getOptions();
            for (WebElement option : dropdownValues) {
                String value = option.getText();
                if (expectedDropValues.contains(value)) {
                } else {
                    flag = false;
                }
            }
        } catch (DriverException e) {
            logger.info("The student(s) parameter drop down is not visible");
            e.printStackTrace();
        }
        logger.info("verifyContentsOfStudentsDropDown :::::::: End ");
        return flag;
    }

    /**
     * This method is to verify that the check box and hide Graduated Students
     * is displayed for All selection in Student(s) drop down
     **/
    public boolean verifyCheckboxAndHideGraduatedStudentsForAllStudentsDropDownSelection() {
        boolean flag = true;
        Select studentDropdown;
        WebElement checkbox;
        String labelFromUI = "";
        String labelFromProp = "";
        logger.info("verifyCheckboxAndHideGraduatedStudentsForAllStudentsDropDownSelection :::::::: Start ");
        try {
            studentDropdown = dropDownHandler
                    .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.students.dropdown"));
            studentDropdown.selectByVisibleText("All");
            checkbox = checkBoxHandler.getCheckbox(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.checkbox.hide.graduated.students"));
            labelFromUI = textHandler.getText(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.hide.graduated.students"));
            labelFromProp = PropertiesRepository.getString("label.hide.graduated.students");
            if (checkbox.isDisplayed()) {
                System.out.println("Chechbox is displayed");
                if (labelFromUI.equalsIgnoreCase(labelFromProp)) {
                    logger.info("Hide Graduated Students text is matching");
                }
            } else {
                flag = false;
            }

        } catch (DriverException e) {
            logger.info("Chechbox and Label is not displayed.");
            e.printStackTrace();
        }
        logger.info("verifyCheckboxAndHideGraduatedStudentsForAllStudentsDropDownSelection :::::::: End ");
        return flag;
    }

    /**
     * This method is for selecting Cohort Tab and Selecting cohort from drop
     * down and checking hide graduated cohorts
     **/
    public Boolean selectCohortAndCohortNameAndHideGraduatedCohorts(String cohortName, Boolean checkBoxSelection) {
        Boolean checked = false;
        logger.info("selectingCohortfromStudentsParameterAndSelectingCohortAndHideGraduatedCohorts :::::::: Start ");
        javaScriptActions.executeScript("window.scrollBy(0,-250);");
        try {

            Select selCohort = dropDownHandler
                    .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.students.dropdown"));

            selCohort.selectByVisibleText("Cohort");

            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            WebElement drop = driver.findElement(By.cssSelector(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.cohortselection.enabled")));

            boolean cohortSelectionDropdownEnabled = drop.isDisplayed();
            if (!cohortSelectionDropdownEnabled) {
                Thread.sleep(2000);
                checked = selectHideGraduatedCohorts(false);
            } else if (cohortSelectionDropdownEnabled && !checkBoxSelection) {
                checked = selectHideGraduatedCohorts(checkBoxSelection);
            }
            Select sel = dropDownHandler.getDropDown(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.Cohort.select.drop.down"));

            sel.selectByVisibleText(cohortName);

            // Get the selected cohort name

            VerificationHandler.verifyEquals(sel.getFirstSelectedOption().getText(), cohortName);

            javaScriptActions.executeScript("window.scrollBy(0,-250);");

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("selectingCohortTabAndSelectingCohortAndHideGraduatedCohorts :::::::: END");
        return checked;
    }

    /**
     * This method is for verifying the Cohort selection drop down and Hide
     * Graduated Cohorts are shown on selection of cohort from student(s)
     * parameter
     **/
    public boolean VerifyCohortsDropdownAndHideGraduatedCohortsIsDisplayedOnCohortSelection() {
        boolean checked = true;
        logger.info("VerifyCohortsDropdownAndHideGraduatedCohortsIsDisplayedOnCohortSelection :::::::: Start ");
        try {

            Select selCohort = dropDownHandler
                    .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.students.dropdown"));

            selCohort.selectByVisibleText("Cohort");

            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));

            WebElement drop = driver.findElement(By.cssSelector(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.cohortselection.enabled")));
            WebElement check = checkBoxHandler.getCheckbox(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.checkbox.hide.graduated.cohorts"));
            if (drop.isDisplayed()) {
                if (check.isDisplayed()) {
                    checked = true;
                }
            } else {
                checked = false;
            }

        } catch (Exception e) {
            logger.info("The Cohort drop down is not displayed");
            e.printStackTrace();
        }
        return checked;
    }

    /**
     * This method is for selecting Date Taken/sent from date Taken/Sent drop
     * down.
     **/
    public void selectingDateFilterTakenOrSent(String filterSelection) {
        try {
            logger.info("selectingDateFilterTakenOrSent :::::::: Start ");
            javaScriptActions.executeScript("window.scrollBy(0,-250);");

            Select sel = dropDownHandler.getDropDown(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.taken.sent.dropdown"));

            sel.selectByVisibleText(filterSelection);
            javaScriptActions.executeScript("window.scrollBy(0,-250);");

        } catch (DriverException e) {
            logger.info("Element is not Present " + e);
        }
        logger.info("selectingDateFilterTakenOrSent :::::::: End ");
    }

    /**
     * This method is for choosing the date from date picker
     * 
     * @param pass
     *            the date values from date
     * @return
     * @throws DriverException
     */

    public void chooseFromDateRange(String fromDate) throws DriverException {

        setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.from.date.picker.open"));

        buttonhandler
                .clickButton(PropertiesRepository.getString("faculty.reporting.admissions.tab.from.date.picker.open"));

        setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.date.picker.header"));

        buttonhandler
                .clickButton(PropertiesRepository.getString("faculty.reporting.admissions.tab.date.picker.header"));

        setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.month.picker.header"));

        buttonhandler
                .clickButton(PropertiesRepository.getString("faculty.reporting.admissions.tab.month.picker.header"));
        System.out.println(fromDate);
        selectionLogicForDateSelection(fromDate);

    }

    /**
     * This method is for choosing the date from date picker
     * 
     * @param pass
     *            the date values To date
     * @return void
     * @throws DriverException
     */
    public void chooseToDateRange(String toDate) {
        try {

            setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.to.date.picker.open"));

            buttonhandler.clickButton(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.to.date.picker.open"));

            setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.date.picker.header"));

            buttonhandler
                    .clickButton(PropertiesRepository.getString("faculty.reporting.admissions.tab.date.picker.header"));

            setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.month.picker.header"));

            buttonhandler.clickButton(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.month.picker.header"));

            selectionLogicForDateSelection(toDate);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    static Map<String, String> monthselection = new HashMap<String, String>();

    {
        monthselection.put("01", "January");
        monthselection.put("02", "February");
        monthselection.put("03", "March");
        monthselection.put("04", "April");
        monthselection.put("05", "May");
        monthselection.put("06", "June");
        monthselection.put("07", "July");
        monthselection.put("08", "August");
        monthselection.put("09", "September");
        monthselection.put("10", "October");
        monthselection.put("11", "November");
        monthselection.put("12", "December");

    }

    /**
     * This method is to select the Individual tab in the admissions Page.
     * 
     * @param studentName
     * @return string
     * @throws DriverException
     */
    public String selectingIndividualStudentFromAdmissionsTab(String studentName) throws DriverException {
        String popUpHeader = "";
        String studentNameFromUI = "";
        logger.info("selectingIndividualStudentFromAdmissionsTab :::::::: Start ");
        baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
        try {
            Select sel = dropDownHandler
                    .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.students.dropdown"));

            sel.selectByVisibleText("Individual");
            javaScriptActions.executeScript("window.scrollBy(0,-250);");

            /*
             * buttonhandler.clickButton(PropertiesRepository
             * .getString("faculty.reporting.admissions.tab.individual.button"
             * ));
             */
            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            setDriverWait(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.individual.student.name.text.box"));

            // Entering student name in the student search textbox

            textHandler.writeText(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.individual.student.name.text.box"),
                    studentName);
            setDriverWait(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.individual.magnifying.glass.button"));

            // Clicking on magnifying glass button

            buttonhandler.clickButton(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.individual.magnifying.glass.button"));

            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.individual.popup.span"));

            // Verifying student search popup is opened or not
            popUpHeader = textHandler.getText(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.individual.popup.header"));

            VerificationHandler.verifyEquals(popUpHeader, PropertiesRepository.getString("individual.popUpHeader"));

            // Selecting the student from popup and verifying the student name
            List<WebElement> studentInfo = textHandler.findElements(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.individual.popup.content"));
            for (WebElement popUpCell : studentInfo) {
                String studentInfoFromUI = popUpCell.getText();
                if (String.valueOf(studentName).equalsIgnoreCase(studentInfoFromUI)) {
                    popUpCell.click();
                    break;
                }
            }

            setDriverWait(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.individual.student.name.text.box"));

            studentNameFromUI = driver
                    .findElement(By.cssSelector(PropertiesRepository
                            .getString("faculty.reporting.admissions.tab.individual.student.name.text.box")))
                    .getAttribute("value");
        } catch (DriverException e) {

            e.printStackTrace();
        }

        return studentNameFromUI;
    }

    /**
     * This method is for selecting display for cut score criteria from the drop
     * down
     * 
     * @param cutScoreCriteria
     * @return boolean
     */

    public boolean selectingDisplayForCutScoreCriteriaFromDropDown(String cutScoreCriteria) {
        boolean selected = false;
        logger.info("selectingDisplayForCutScoreCriteriaFromDropDown :::::::: Start ");

        try {
            Select sel = dropDownHandler.getDropDown(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.display.for.cut.score.drop.down"));

            sel.selectByVisibleText(cutScoreCriteria);

            selected = true;
        } catch (DriverException e) {
            e.printStackTrace();
        }
        logger.info("selectingDisplayForCutScoreCriteriaFromDropDown :::::::: END");
        return selected;
    }

    /**
     * This method is for selecting filter Prepardness Categories checkbox
     * 
     **/
    /*
     * public Boolean selectingFilterPrepardnessCategories( Boolean
     * Exemplary,Boolean Advanced,Boolean Proficient,Boolean Basic,Boolean
     * Developmental) { Boolean checked = false; logger.info(
     * "selectingFilterPrepardnessCategories :::::::: Start ");
     * 
     * checkboxs = driver.findElements(By .cssSelector(
     * "div[class='filterprof']>div>span>div[class='checkbox']>label>input"));
     * 
     * if (checkboxs == null || checkboxs.size() == 0) return null;
     * 
     * for (int i = index; i < checkboxs.size();) {
     * 
     * if (!radioButtons.get(i).isSelected()) { radioButtons.get(i).click();
     * count++; if (count >= Integer.parseInt(numberOfStudents)) { break; }
     * 
     * if (!checkBoxSelection) { try { int i=1; checkBoxHandler .selectCheckbox(
     * "div[class='filterprof']>div>span>div[class='checkbox']>label[for='i']>input"
     * ); checked = true; } catch (DriverException e) { // TODO Auto-generated
     * catch block e.printStackTrace(); } } logger.info(
     * "selectingFilterPrepardnessCategories :::::::: END"); return checked; }
     */

    /**
     * This method is used to retrieve the students data from the grid.
     * 
     * @return Map
     */

    public Map<String, List<String>> verifyTheResultGrid() {
        int navigationsButtons = 2;
        Map<String, List<String>> studentInfoScores = new HashMap<String, List<String>>();
        StringBuilder sb = new StringBuilder();

        int paginationButtons = driver.findElements(By.cssSelector(
                PropertiesRepository.getString("faculty.reporting.admissions.tab.student.grid.pagination.buttons")))
                .size();
        paginationButtons = paginationButtons - navigationsButtons;
        for (int i = 0; i < paginationButtons; i++) {
            List<WebElement> studentInfo = driver
                    .findElements(By.cssSelector("div[class='table-responsive'] tbody tr"));
            for (WebElement row : studentInfo) {
                List<WebElement> columns = row.findElements(By.tagName("td"));
                int count = 0;
                String studentName = "";

                if (sb.toString().length() > 0)
                    sb.delete(0, sb.length());

                for (WebElement ele1 : columns) {
                    if (count == 0) {

                        studentName = ele1.getText();
                    }
                    if (count > 0) {
                        sb.append(ele1.getAttribute("data-title") + "=" + ele1.getText() + ";");
                    }
                    count++;
                }

                if (!studentInfoScores.containsKey(studentName)) {
                    List<String> listOfRecords = new ArrayList<String>();
                    listOfRecords.add(sb.toString());
                    studentInfoScores.put(studentName, listOfRecords);
                } else {
                    studentInfoScores.get(studentName).add(sb.toString());
                }
            }
            clickOnNextPageButton();

        }
        return studentInfoScores;
    }

    /**
     * This method is used to click on the next page button of the students grid
     * 
     * @return void
     */
    public void clickOnNextPageButton() {
        setDriverWait(PropertiesRepository.getString("faculty.reporting.admissions.tab.student.grid.pagination"));
        try {
            buttonhandler.clickButton(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.student.grid.pagination.nextpage.button"));
        } catch (Exception e) {
            System.out.println("Next Page button is not available");
        }
    }

    /**
     * This is the method is having the logic for selecting from and to Date
     * from the calender control.
     *
     * @return void
     */

    private void selectionLogicForDateSelection(String dateToSelect) throws DriverException {

        String sysMonth = "";
        String sysDate = "";
        String sysYear = "";

        // This is to get the system Date in DD/MM/YYYY format

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();
        df.format(dateobj);

        String[] split = df.format(dateobj).split("/");
        if (split != null && split.length == 3) {
            sysDate = split[0];
            sysMonth = split[1];
            sysYear = split[2];
        }

        // This method is to split the date which user wants to select

        String[] dateSpliter = dateToSelect.split("/");
        if (split != null && split.length == 3) {
            monthToSelect = dateSpliter[0];
            dateToSelect = dateSpliter[1];
            yearToSelect = dateSpliter[2];
        }

        if (Integer.parseInt(yearToSelect) > Integer.parseInt(sysYear)) {
            System.out.println("The year to be selected is more than current year");
        } else if (Integer.parseInt(yearToSelect) < Integer.parseInt(sysYear)) {

            minYearRange = textHandler
                    .getText(PropertiesRepository.getString("faculty.reporting.admissions.tab.year.picker.min.range"));
            while (Integer.parseInt(yearToSelect) < Integer.parseInt(minYearRange)) {
                minYearRange = textHandler.getText(
                        PropertiesRepository.getString("faculty.reporting.admissions.tab.year.picker.min.range"));
                if (Integer.parseInt(yearToSelect) < Integer.parseInt(minYearRange)) {
                    buttonhandler.clickButton(PropertiesRepository
                            .getString("faculty.reporting.admissions.tab.year.picker.previous.button"));
                }

            }
            if (Integer.parseInt(yearToSelect) > Integer.parseInt(minYearRange)) {
                minYearRange = textHandler.getText(
                        PropertiesRepository.getString("faculty.reporting.admissions.tab.year.picker.min.range"));

                List<WebElement> yearCells = textHandler.findElements(
                        PropertiesRepository.getString("faculty.reporting.admissions.tab.year.picker.list"));
                for (WebElement calCell : yearCells) {
                    String yearFromUI = calCell.getText();
                    if (String.valueOf(yearToSelect).equalsIgnoreCase(yearFromUI)) {
                        calCell.click();
                        break;
                    }
                }
            }
        }

        if (Integer.parseInt(monthToSelect) > Integer.parseInt(sysMonth)) {
            System.out.println("The month to be selected is more than the present month");
        } else {
            String month = monthselection.get(monthToSelect);
            List<WebElement> monthCells = textHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.month.picker.list"));
            for (WebElement calCell : monthCells) {
                String monthFromUI = calCell.getText();
                if (String.valueOf(month).equalsIgnoreCase(monthFromUI)) {
                    calCell.click();
                    break;
                }
            }
        }
        if (monthToSelect.equalsIgnoreCase(sysMonth)) {
            if (Integer.parseInt(dateToSelect) > Integer.parseInt(sysDate)) {
                System.out.println("The date to be selected is more than the present date");
            }
        } else {
            List<WebElement> dateCells = textHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.date.picker.list"));
            for (WebElement calCell : dateCells) {
                String dateFromUI = calCell.getText();
                if (String.valueOf(dateToSelect).equalsIgnoreCase(dateFromUI)) {
                    calCell.click();
                    break;
                }
            }

        }

    }

    private boolean selectHideGraduatedCohorts(boolean checkBoxSelection) throws Exception {
        boolean checked = false;
        try {
            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));

            if (!checkBoxSelection) {
                checkBoxHandler.selectCheckbox(PropertiesRepository
                        .getString("faculty.reporting.admissions.tab.checkbox.hide.graduated.cohorts"));
                checked = true;

                baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checked;

    }

    // This method is to select the Individual tab in the admissions Page.
    public void selectIndividualFromStudentsParameter() {

        logger.info("selectingIndividualStudentFromStudentsParameter");

        baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
        try {
            Select sel = dropDownHandler
                    .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.students.dropdown"));

            sel.selectByVisibleText("Individual");

        } catch (DriverException e) {
            e.printStackTrace();
        }
    }

    public boolean isMagnifyingGlassSearchButtonIsDisplayed() {
        boolean isDisplayed = false;
        try {
            isDisplayed = buttonhandler.isDisplayed(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.individual.magnifying.glass.button"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDisplayed;
    }

    private void expandEvaluateScoresFilter() {
        String dropArrowValue = "";
        try {
            dropArrowValue = baseHandler
                    .findElement(PropertiesRepository
                            .getString("faculty.reporting.admissions.tab.evaluateTotalScore.dropArrow"))
                    .getAttribute("class");

            if (dropArrowValue.contains("glyphicon-chevron-down")) {
                buttonhandler.clickButton(PropertiesRepository
                        .getString("faculty.reporting.admissions.tab.evaluateTotalScore.dropArrow"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getDefaultCutScoreValue() {
        int deafultScore = -1;
        try {
            expandEvaluateScoresFilter();
            deafultScore = Integer.parseInt(textHandler.getTextFromValueAttr(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.evaluateTotalScore.entrybox")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deafultScore;
    }

    public boolean isGridHeadercolumnPresent(String columnHeader) {
        boolean flag = false;
        try {
            List<WebElement> gridHeaders = baseHandler.findElements(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.results.grid.headers"));

            for (WebElement column : gridHeaders) {
                if (column.getText().trim().equalsIgnoreCase(columnHeader))
                    flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isEvaluateTotalScoresFilterSectionDispalyed() {
        try {
            baseHandler.findElement(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.evaluateTotalScore.filtersection"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickOnSubmitButton() {
        try {
            buttonhandler.clickButton(PropertiesRepository.getString("faculty.reporting.admissions.tab.submit.button"));
            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void enterCutScoreValueAndThenClickEnter(String cutScore) {
        try {
            expandEvaluateScoresFilter();
            baseHandler.waitToBeDisplayed(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.evaluateTotalScore.entrybox"));
            textHandler.writeText(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.evaluateTotalScore.entrybox"),
                    cutScore);
            baseHandler
                    .findElement(PropertiesRepository
                            .getString("faculty.reporting.admissions.tab.evaluateTotalScore.entrybox"))
                    .sendKeys(Keys.ENTER);
            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStudentCountDispalyedAboveTheGrid() {
        boolean flag = false;
        try {
            flag = baseHandler.isDisplayed(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.cutscore.studentcount"));

        } catch (Exception e) {
            return flag;
        }
        return flag;
    }

    public boolean verifyTotalScoreColumnColorCodeIsRemoved() {
        boolean flag = false;
        try {
            List<WebElement> list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.totalscore.color"));
            for (int i = 0; i < list.size(); i++) {

                flag = list.get(i).getAttribute("class").contains("admission-white");
                if (flag == false)
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean verifyTotalScoreColumnColorCoded() {
        boolean flag = false;
        try {
            List<WebElement> list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.totalscore.color"));
            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).getText().trim().equals("N/A")) {

                    flag = list.get(i).getAttribute("class").contains("admission-white");

                    if (!flag)
                        break;
                } else
                    flag = (list.get(i).getAttribute("class").contains("admission-red")
                            || list.get(i).getAttribute("class").contains("admission-green"))
                            && (!(list.get(i).getAttribute("class").contains("admission-white")));

                if (!flag)
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean verifyShowAcademicPreparednessIsCollapsed() {
        boolean flag = false;
        try {
            WebElement button = baseHandler.findElement(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.collapse.button"));

            flag = button.getAttribute("class").contains("glyphicon-chevron-down");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean expandShowAcademicPreparednessTab() {
        boolean flag = false;
        try {
            WebElement button = baseHandler.findElement(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.collapse.button"));

            flag = button.getAttribute("class").contains("glyphicon-chevron-down");
            if (flag == true) {
                button.click();
                baseHandler.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean VerifyTheCountandColorForAboveTEASCutScore() {
        boolean flag = false;
        float scoreValue, intScoreValue;
        int studentCount = 0;

        try {
            WebElement totalScoreValue = baseHandler.findElement(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.Students.Score.AtorAbove.cut.Score"));

            String studentsCountGrid = totalScoreValue.getText();
            List<WebElement> list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.totalscore.color"));

            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).getText().split("%")[0].equals("N/A")) {
                } else {
                    intScoreValue = Float.parseFloat(list.get(i).getText().split("%")[0]);
                    scoreValue = Integer.parseInt(PropertiesRepository.getString("cutscore.value"));
                    // convertRgbaColorToHex(list.get(i).getCssValue("background-color");

                    String colorcode = list.get(i).getCssValue("background-color").replace(",", "");
                    if (colorcode.equals(PropertiesRepository.getString("hexcolor.green.code"))
                            && (intScoreValue >= scoreValue)) {
                        studentCount++;
                    }
                }
            }

            String studentCountOnScreen = String.valueOf(studentCount);
            if (studentsCountGrid.equalsIgnoreCase(studentCountOnScreen)) {
                flag = true;
            }

        } catch (Exception e) {
            return flag;
        }
        return flag;
    }

    public boolean VerifycountAndColorForBelowTEASCutScore() {
        boolean flag = false;
        int studentCount = 0;
        float scoreValue, intScoreValue;
        try {
            WebElement totalScoreValue = baseHandler.findElement(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.Students.Score.Below.cut.Score"));

            String studentsCountGrid = totalScoreValue.getText();

            List<WebElement> list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.totalscore.color"));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getText().split("%")[0].equals("N/A")) {
                } else {
                    intScoreValue = Float.parseFloat(list.get(i).getText().split("%")[0]);
                    scoreValue = Integer.parseInt(PropertiesRepository.getString("cutscore.value"));
                    String colorcode = list.get(i).getCssValue("background-color").replace(",", "");
                    if (colorcode.equals(PropertiesRepository.getString("hexcolor.vermillion.code"))
                            && (intScoreValue < scoreValue)) {
                        studentCount++;
                    }
                }
            }
            String studentCountOnScreen = String.valueOf(studentCount);
            if (studentsCountGrid.equalsIgnoreCase(studentCountOnScreen)) {
                flag = true;
            }

        } catch (Exception e) {
            return flag;
        }
        return flag;
    }

    // This method is to check all the sub-test columns are present or not
    public boolean verifyAllSubtestsColumnsArePresent() {
        boolean allColumnsPresent = false;
        boolean readingColumnPresent = false;
        boolean mathColumnPresent = false;
        boolean scienceColumnPresent = false;
        boolean englishColumnPresent = false;

        readingColumnPresent = isGridHeadercolumnPresent("Reading");
        mathColumnPresent = isGridHeadercolumnPresent("Math");
        scienceColumnPresent = isGridHeadercolumnPresent("Science");
        englishColumnPresent = isGridHeadercolumnPresent("English");

        if (readingColumnPresent && mathColumnPresent && scienceColumnPresent && englishColumnPresent) {
            allColumnsPresent = true;
        }

        return allColumnsPresent;
    }

    // This method is to verify that the result grid records are having Partial
    // TEAS records.
    public boolean verifyPartialTEASRecordsForColumn(String columnName) {
        boolean flag = false;
        List<WebElement> list = null;
        switch (columnName) {
        case "Total Score":
            list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.totalscore.color"));

            break;

        case "Reading":
            list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.reading.column"));

            break;

        case "Math":
            list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.math.column"));

            break;

        case "English":
            list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.english.column"));

            break;

        case "Science":
            list = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.science.column"));

            break;

        }
        for (WebElement score : list) {
            String studentScore = score.getText();
            if (studentScore.equalsIgnoreCase("N/A")) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    // This method is for verifying that the Academic Preparedness- Exemplary is
    // checked or not
    public boolean VerifyShowAcademicPreparednessTabExemplaryIsChecked() {
        boolean flag = false;
        try {
            WebElement list = baseHandler.findElement(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Exemplary.checkbox.Status"));

            flag = list.getAttribute("aria-checked").contains("true");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // This method is for verifying that the Academic Preparedness- Advanced is
    // checked or not
    public boolean VerifyShowAcademicPreparednessTabAdvancedIsChecked() {
        boolean flag = false;
        try {
            WebElement list = baseHandler.findElement(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Advanced.checkbox.Status"));

            flag = list.getAttribute("aria-checked").contains("true");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // This method is for verifying that the Academic Preparedness- Proficient
    // is checked or not
    public boolean VerifyShowAcademicPreparednessTabProficientIsChecked() {
        boolean flag = false;
        try {
            WebElement list = baseHandler.findElement(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Proficient.checkbox.Status"));

            flag = list.getAttribute("aria-checked").contains("true");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // This method is for verifying that the Academic Preparedness- Basic is
    // checked or not
    public boolean VerifyShowAcademicPreparednessTabBasicIsChecked() {
        boolean flag = false;
        try {
            WebElement list = baseHandler.findElement(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Basic.checkbox.Status"));

            flag = list.getAttribute("aria-checked").contains("true");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // This method is for verifying that the Academic Preparedness-
    // Developmental is checked or not
    public boolean VerifyShowAcademicPreparednessTabDevelopmentalIsChecked() {
        boolean flag = false;
        try {
            WebElement list = baseHandler.findElement(PropertiesRepository.getString(
                    "faculty.reporting.admissions.tab.ShowAcademicPreparedness.Developmental.checkbox.Status"));

            flag = list.getAttribute("aria-checked").contains("true");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // This method is for getting the count of records in the admission grid
    public int getAdmissionGridRecordsCount() {
        int count = 0;
        try {
            List<WebElement> scores = baseHandler.findElements(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.student.totalscore.count"));
            if (scores.size() > 0) {
                count = scores.size();
            }
        } catch (Exception e) {
            return count;
        }
        return count;
    }

    // This method is for getting the count of N/A records in the admissions
    // grid
    public int getNARecordsCount() {
        int count = 0;
        try {
            List<WebElement> scores = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.totalscore.color"));
            if (scores.size() > 0) {
                for (WebElement score : scores) {
                    String studentScoreFromUI = score.getText();
                    if (studentScoreFromUI.equalsIgnoreCase("N/A")) {
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            return count;
        }
        return count;
    }

    // This method is for getting the count of records in result grid when
    // ShowAcademicPreparedness all check box are unchecked
    public int verifyUncheckShowAcademicPreparednessShowBlankGrid() {
        int count = 0;
        boolean checkstatusExemplary, checkstatusAdvanced, checkstatusProficient, checkstatusBasic, checkstatusDev,
                uncheckstatus;
        try {
            count = getAdmissionGridRecordsCount();
            expandShowAcademicPreparednessTab();

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            checkstatusExemplary = VerifyShowAcademicPreparednessTabExemplaryIsChecked();
            checkstatusAdvanced = VerifyShowAcademicPreparednessTabAdvancedIsChecked();
            checkstatusProficient = VerifyShowAcademicPreparednessTabProficientIsChecked();
            checkstatusBasic = VerifyShowAcademicPreparednessTabBasicIsChecked();
            checkstatusDev = VerifyShowAcademicPreparednessTabDevelopmentalIsChecked();

            if (checkstatusExemplary == true) {

                checkBoxHandler.selectCheckbox(PropertiesRepository.getString(
                        "faculty.reporting.admissions.tab.ShowAcademicPreparedness.Exemplary.checkbox.Action"));

                baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));

                uncheckstatus = true;
            }

            if (checkstatusAdvanced == true) {

                checkBoxHandler.selectCheckbox(PropertiesRepository.getString(
                        "faculty.reporting.admissions.tab.ShowAcademicPreparedness.Advanced.checkbox.Action"));

                baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));

                uncheckstatus = true;
            }

            if (checkstatusProficient == true) {

                checkBoxHandler.selectCheckbox(PropertiesRepository.getString(
                        "faculty.reporting.admissions.tab.ShowAcademicPreparedness.Proficient.checkbox.Action"));
                baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));

                uncheckstatus = true;
            }

            if (checkstatusBasic == true) {

                checkBoxHandler.selectCheckbox(PropertiesRepository
                        .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Basic.checkbox.Action"));

                baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
                uncheckstatus = true;
            }
            if (checkstatusDev == true) {

                checkBoxHandler.selectCheckbox(PropertiesRepository.getString(
                        "faculty.reporting.admissions.tab.ShowAcademicPreparedness.Developmental.checkbox.Action"));

                baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
                uncheckstatus = true;
            }

            count = getAdmissionGridRecordsCount();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // This method is to get the academicPreparedness label text
    public String getAcademicPreparednessLabel() {
        String label = "";
        String text = baseHandler.findElement(
                PropertiesRepository.getString("faculty.reporting.admissions.tab.show.academic.preparedness.label"))
                .getText();
        label = text.replace("\n", "").replace(" ", "");
        return label;
    }

    // This method is to verify that the default sorting for the admissions grid
    // is date taken
    public boolean verifyDefaultSortingForGrid() {
        boolean descending = false;
        try {
            List<WebElement> list = baseHandler.findElements(PropertiesRepository
                    .getString("faculty.reporting.admissions.tab.studentscore.Grid.DateTaken.Column"));

            for (int i = 0; i < list.size(); i++) {
                String[] firstDate = list.get(i).getText().split("/");
                int month = Integer.parseInt(firstDate[0]);
                int date = Integer.parseInt(firstDate[1]);
                int year = Integer.parseInt(firstDate[2]);

                for (int j = 1; j < list.size(); j++) {
                    String[] secondDate = list.get(j).getText().split("/");
                    int secondmonth = Integer.parseInt(firstDate[0]);
                    int seconddate = Integer.parseInt(firstDate[1]);
                    int secondyear = Integer.parseInt(firstDate[2]);

                    if (((month >= secondmonth) || (date >= seconddate)) && (year >= secondyear)) {
                        descending = true;
                    } else {
                        System.out.println("The following dates failed to show up as Most recent First order:"
                                + firstDate + secondDate);
                        descending = false;
                        break;
                    }
                }

            }
        } catch (Exception e) {
            return descending;
        }
        return descending;
    }

    /**
     * This method is used to verify count of students are not displayed next to
     * the AcademicPreparedness level
     * 
     * @return boolean
     */
    public boolean countOfStudentsNotDisplayed() {
        boolean flag = false;
        int i = 0;
        String[] values = { "Exemplary", "Advanced", "Proficient", "Basic", "Developmental" };
        List<WebElement> lst = baseHandler.findElements(
                PropertiesRepository.getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Alltext"));
        if (lst.size() == 5) {
            Iterator<WebElement> itr = lst.iterator();
            while (itr.hasNext()) {
                if (itr.next().getText().trim().equals(values[i]))
                    flag = true;
                else {
                    flag = false;
                    break;
                }
                i++;
            }
        }
        return flag;
    }

    /**
     * This method is used to uncheck all academicpreparedness check boxes
     * 
     * @return boolean
     */
    public boolean unCheckAllAcademicPreparednessCheckBoxes() {
        boolean flag = false;
        List<WebElement> list = baseHandler.findElements(PropertiesRepository
                .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.AllCheckBoxes"));
        List<WebElement> list1 = baseHandler.findElements(PropertiesRepository
                .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.AllCheckBoxes.Status"));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).click();
            baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
            flag = list1.get(i).getAttribute("aria-checked").contains("false");
            if (!flag)
                break;
        }
        return flag;

    }

    /**
     * This method is used to click on Hide Graduate check box
     */
    public void ClickHideGraduatedStudentsCheckBox() {
        baseHandler.findElement(
                PropertiesRepository.getString("faculty.reporting.admissions.tab.checkbox.hide.graduated.students"))
                .click();
    }

    /**
     * This method is used to verify the student grid is display
     * 
     * @return boolean
     */
    public boolean verifyStudentSoreTableDisplay() {
        baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
        boolean flag = false;
        int size;
        try {
            size = baseHandler
                    .findElements(PropertiesRepository.getString("faculty.reporting.admissions.tab.StudentScore.Table"))
                    .size();
            if (size > 0) {
                flag = true;
            }
        } catch (Exception e) {
            size = 0;
        }

        return flag;
    }

    /**
     * This method is used to verify Academic preparedness value Proficient
     * checkbox is checked or not
     * 
     * @return boolean
     */
    public boolean VerifyGridProficientIsUnChecked() {
        boolean status = false;
        String tableXapth = "//tbody/tr[*]/td[4]";
        baseHandler
                .findElement(PropertiesRepository.getString(
                        "faculty.reporting.admissions.tab.ShowAcademicPreparedness.Proficient.checkbox.Action"))
                .click();
        waitForGridLoad();
        List<WebElement> lst1 = driver.findElements(By.xpath(tableXapth));
        for (int i = 0; i <= lst1.size() - 1; i++) {
            String text = lst1.get(i).getText();
            if (text.equalsIgnoreCase("Proficient")) {
                status = false;
                break;
            } else {
                status = true;
            }
        }
        return status;
    }

    /**
     * This method is used to verify Academic preparedness value Exemplary
     * checkbox is checked or not
     * 
     * @return boolean
     */
    public boolean VerifyGridExemplaryIsUnChecked() {
        boolean status = false;
        String tableXapth = "//tbody/tr[*]/td[4]";
        baseHandler.findElement(PropertiesRepository
                .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Exemplary.checkbox.Action"))
                .click();
        waitForGridLoad();
        List<WebElement> lst1 = driver.findElements(By.xpath(tableXapth));
        for (int i = 0; i <= lst1.size() - 1; i++) {
            String text = lst1.get(i).getText();
            if (text.equalsIgnoreCase("Exemplary")) {
                status = false;
                break;
            } else {
                status = true;
            }
        }
        return status;
    }

    /**
     * This method is used to verify Academic preparedness value Advanced
     * checkbox is checked or not
     * 
     * @return boolean
     */
    public boolean VerifyGridAdvancedIsUnChecked() {
        boolean status = false;
        String tableXapth = "//tbody/tr[*]/td[4]";
        baseHandler.findElement(PropertiesRepository
                .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Advanced.checkbox.Action"))
                .click();
        waitForGridLoad();
        List<WebElement> lst1 = driver.findElements(By.xpath(tableXapth));
        for (int i = 0; i <= lst1.size() - 1; i++) {
            String text = lst1.get(i).getText();
            if (text.equalsIgnoreCase("Advanced")) {
                status = false;
                break;
            } else {
                status = true;
            }
        }
        return status;
    }

    /**
     * This method is used to verify Academic preparedness value Basic checkbox
     * is checked or not
     * 
     * @return boolean
     */
    public boolean VerifyGridBasicIsUnChecked() {
        boolean status = false;
        String tableXapth = "//tbody/tr[*]/td[4]";
        baseHandler
                .findElement(PropertiesRepository
                        .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.Basic.checkbox.Action"))
                .click();
        waitForGridLoad();
        List<WebElement> lst1 = driver.findElements(By.xpath(tableXapth));
        for (int i = 0; i <= lst1.size() - 1; i++) {
            String text = lst1.get(i).getText();
            if (text.equalsIgnoreCase("Basic")) {
                status = false;
                break;
            } else {
                status = true;
            }
        }
        return status;
    }

    /**
     * This method is used to verify Academic preparedness value Developmental
     * checkbox is checked or not
     * 
     * @return boolean
     */
    public boolean VerifyGridDevelopmentalIsUnChecked() {
        boolean status = true;
        String tableXapth = "//tbody/tr[*]/td[4]";
        baseHandler
                .findElement(PropertiesRepository.getString(
                        "faculty.reporting.admissions.tab.ShowAcademicPreparedness.Developmental.checkbox.Action"))
                .click();
        waitForGridLoad();
        List<WebElement> lst1 = driver.findElements(By.xpath(tableXapth));
        for (int i = 0; i <= lst1.size() - 1; i++) {
            String text = lst1.get(i).getText();
            if (text.equalsIgnoreCase("Developmental")) {
                status = false;
                break;
            }
        }
        return status;
    }

    /**
     * This method is used to wait for student grid load
     */
    public void waitForGridLoad() {
        baseHandler.waitForInvisibilityOfElementLocated(PropertiesRepository.getString("teas.loading.symbol"));
    }

    /**
     * This method is used to verify showacademic preparedness section is
     * displayed or not
     * 
     * @return boolean
     */
    public boolean verifyShowAcademicPreparednessSectionDisplay() {
        boolean status = false;
        try {
            status = baseHandler
                    .findElement(PropertiesRepository
                            .getString("faculty.reporting.admissions.tab.ShowAcademicPreparedness.collapse.button"))
                    .isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * This method is used to select the product
     * 
     * @param productName
     */
    public void selectingProduct(String productName) {
        Select sel = null;
        try {
            sel = dropDownHandler
                    .getDropDown(PropertiesRepository.getString("faculty.reporting.admissions.tab.product.drop.down"));
        } catch (DriverException e) {
            e.printStackTrace();
        }
        sel.selectByVisibleText(productName);
    }

    /**
     * This method is used to select the PraticeTeas forms
     * 
     * @param form
     */
    public void selectingPraticeTeasForms(String form) {

        Select sel = null;
        try {
            sel = dropDownHandler.getDropDown(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.praticeteas.drop.down"));
        } catch (DriverException e) {
            e.printStackTrace();
        }
        sel.selectByVisibleText(form);
    }

    /**
     * This method is used to verify product version display
     * 
     * @param selectProductDropdown
     * @return
     */
    public boolean ProductVersionisDisplayed(String selectProductDropdown) {
        boolean flag;
        try {
            selectingProduct(selectProductDropdown);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            WebElement productVersion = baseHandler.findElement(
                    PropertiesRepository.getString("faculty.reporting.admissions.tab.Product.Version.Dropdown"));

            flag = productVersion.isDisplayed();

        } catch (NoSuchElementException ne) {
            flag = false;
        }
        return flag;
    }

    /**
     * This method is used to verify the student slection is a drop-down or not
     * 
     * @return boolean
     */
    public boolean VerifyStudentDropdownTag() {
        boolean flag = false;
        try {

            WebElement studentTag = baseHandler
                    .findElement(PropertiesRepository.getString("faculty.reporting.admissions.tab.Student.dropdown"));

            String tag = studentTag.getTagName();
            flag = tag.contains("select");

        } catch (Exception e) {
            return flag;
        }
        return flag;
    }

}
