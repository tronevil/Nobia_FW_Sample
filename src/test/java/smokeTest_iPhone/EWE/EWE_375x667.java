package smokeTest_iPhone.EWE;

import helpers.EWEHelper;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import page_objects.EWEPageObject;
import smokeTest_iPhone.BaseTest_375x667;

import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Set test run order with Name Ascending order
public class EWE_375x667 extends BaseTest_375x667 {
    //region Variables declaration
    //Get page object and helpers
    private final EWEPageObject ewePageObject = new EWEPageObject();
    private final static EWEHelper eweHelper = new EWEHelper(driver);
    final static Logger logger = Logger.getLogger(EWE_375x667.class);
    private static final String brand = "EWE";
    //Get all test data
    private static String testEnvironment;
    private static String postcode;
    private static String searchKeyword;
    private static String findStorePage;
    private static String configuratorPage;
    private static String kitchenalityPage;
    private static String displaySales;
    private static String orderCatalogPage;
    private static String magazinePage;
    private static String flagshipPartnerPage;
    private static String galleryPage;

    @BeforeClass
    public static void getAllTestData() {
        testEnvironment = commonMethods.getTestData(brand, commonMethods.getProperties("environment"));
        postcode = commonMethods.getTestData(brand, "Postcode");
        searchKeyword = commonMethods.getTestData(brand, "SearchKeyword");
        findStorePage = commonMethods.getTestData(brand, "FindStorePage");
        configuratorPage = commonMethods.getTestData(brand, "ConfiguratorPage");
        kitchenalityPage = commonMethods.getTestData(brand, "KitchenalityPage");
        displaySales = commonMethods.getTestData(brand, "DisplaySalesPage");
        orderCatalogPage = commonMethods.getTestData(brand, "OrderCatalogPage");
        magazinePage = commonMethods.getTestData(brand, "MagazinePage");
        flagshipPartnerPage = commonMethods.getTestData(brand, "FlagshipPartnerPage");
        galleryPage = commonMethods.getTestData(brand, "GalleryPage");
    }
    //endregion

    @Test
    public void Step1_Search() throws Exception {
        generalMethods.printTestName("======Start testing Step 1: Search Page======");
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();

        generalMethods.printTestStep("1.User opens the start page then click on the Search icon at top right. Do validation-1");
        //try to open the page with 60s timeout to let it fully loaded.
        generalMethods.openURL(testEnvironment, 60);
        generalMethods.handlingCookies();
        //Click on Search icon
        generalMethods.clickOnElement(ewePageObject.getHamburgerMenu());
        generalMethods.clickOnElement(ewePageObject.getSearchIcon());
        //The search popup displays. Take a screenshot and do the comparing of the page
        //Check if Search Dropdown is displayed.
        generalMethods.waitForElementToBeVisible(ewePageObject.getSearchPopup(), 30);
        if (!generalMethods.compareResult(driver.findElement(ewePageObject.getSearchPopup()).isDisplayed(),
                "Search popup should be displayed.",
                "Search popup should be displayed."))
            //If FALSE, add step name to the error list
            errorList.add("Step 1");
        //Hide Video and Image in Homepage to avoid error in image comparison
        if (!driver.findElements(ewePageObject.getVideo()).isEmpty())
            generalMethods.hideElements(ewePageObject.getVideo());
        //Take screenshot of the Search field and Dropdown and compare
        if (!generalMethods.compareImages(folderName, "Step1.1", ewePageObject.getSearchMask()))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - image");

        generalMethods.printTestStep("2. Input a keyword, example \"Kök\" then press Enter to perform the search. Do validation-2");
        //Enter "Kök" into search field
        generalMethods.typeIn(ewePageObject.getSearchField(), searchKeyword);
        generalMethods.waitForElementToBeVisible(By.xpath("//div[@class='js-c-yellow-header__search-results c-yellow-header__search-results']"), 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return window.stop");
        //Press ENTER key
        try {
            driver.findElement(ewePageObject.getSearchField()).sendKeys(Keys.ENTER);
        } catch (TimeoutException e){
            driver.findElement(ewePageObject.getSearchField()).sendKeys(Keys.ENTER);
        }
        //The search result displays with filter tabs at left and detail info at right. Take a screenshot and do the comparing of the page.
        //Check if Search Filter is visible
        generalMethods.waitForElementToBeVisible(ewePageObject.getSearchFilter(), 5);
        if (!generalMethods.compareResult(driver.findElement(ewePageObject.getSearchFilter()).isDisplayed(),
                "Search filter should be displayed.",
                "Search filter should be displayed."))
            //If FALSE, add step name to the error list
            errorList.add("Step 2");
        //Check if Search Result is visible
        generalMethods.waitForElementToBeVisible(ewePageObject.getSearchResult(), 5);
        if (!generalMethods.compareResult(driver.findElement(ewePageObject.getSearchResult()).isDisplayed(),
                "Search result should be displayed.",
                "Search result should be displayed."))
            //If FALSE, add step name to the error list
            errorList.add("Step 2");
        generalMethods.removeElement(ewePageObject.getSearchArticles());
        //Take screenshot of the entire page and compare
        if (!generalMethods.compareImages(folderName, "Step1.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - image");

        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }

    /**
     * Find Store test:
     * The scenario will be as following:
     * 1. User open the start page and click the icon of "find a store".
     * 2. User landing into find a store page. User do the filtering by enter postcode.
     * 3. Click "Book meeting" from the store who got closest hit. (Should be Marbodal Stockholm)
     * Expected result:
     * - User landing in BADA page
     * - Take a screenshot and do the comparing of the page,
     */
    @Test
    public void Step2_FindStore() throws Exception {
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();

        generalMethods.printTestName("======Start testing Step 2: Find Store======");

        generalMethods.printTestStep("1. User open the start page and click the icon of \"find a store\".");
        generalMethods.openURL(testEnvironment, 10);
        generalMethods.handlingCookies();
        //Click on Find Store button
        generalMethods.clickOnElement(ewePageObject.getHamburgerMenu());
        generalMethods.clickOnElement(ewePageObject.getFindStoreButton());

        generalMethods.printTestStep("2. User landing into find a store page. User do the filtering by enter postcode - Do validation 1");
        //Find a store page opens.
        generalMethods.waitForElementToBeVisible(ewePageObject.getSearchBox(), 30);
        if (!generalMethods.compareResult(driver.getCurrentUrl().endsWith(findStorePage),
                "Find Store page is displayed",
                "Find Store page is not displayed." + driver.getCurrentUrl()))
            errorList.add("Step 2");
        //Enter postcode into searchbox
        generalMethods.typeIn(ewePageObject.getSearchBox(), postcode);
        //Click on Search button
        generalMethods.waitInSeconds(2);
        generalMethods.clickOnElement(ewePageObject.getSearchButton());
        generalMethods.waitInSeconds(3);
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step2.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step2 - image");

        generalMethods.printTestStep("3. Click button \"Get direction\" - Do Validation 3");
        //Enter postcode into searchbox
        generalMethods.waitForElementToBeVisible(ewePageObject.getSearchBox(), 30);
        generalMethods.typeIn(ewePageObject.getSearchBox(), postcode);
        //Click on Search button
        generalMethods.waitInSeconds(2);
        generalMethods.clickOnElement(ewePageObject.getSearchButton());
        generalMethods.waitInSeconds(3);
        //Click on Get Direction button
        generalMethods.scrollToElement(ewePageObject.getGetDirectionLink());
        generalMethods.clickOnElement(ewePageObject.getGetDirectionLink());
        //Switch to the new tab
        generalMethods.switchToTab(1);
        //Check if it's Google map
        generalMethods.waitInSeconds(3);
        if (!generalMethods.compareResult(driver.getCurrentUrl().startsWith("https://www.google.com/maps"),
                "Google Map is displayed",
                "Google Map is not displayed." + driver.getCurrentUrl()))
            errorList.add("Step 3");

        generalMethods.printTestStep("4. Click button \"More Info\" from the store that get the closest hit - Do validation 2");
        generalMethods.switchToTab(0);
        //Click on More Info button
        String storeName = driver.findElement(ewePageObject.getStoreName()).getText().toUpperCase();
        generalMethods.clickOnOneOfElements(ewePageObject.getMoreInfoButton(), 0);
        //Store detail info page opens. Take a screenshot and do the comparing of the page
        if (!generalMethods.compareResult(storeName.contains(driver.findElement(ewePageObject.getStoreDetailHeading()).getText().toUpperCase()),
                storeName + " page is displayed",
                storeName + " page is not displayed." + driver.findElement(ewePageObject.getStoreDetailHeading()).getText()))
            errorList.add("Step 4");
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step2.4", null))
            //If FALSE, add step name to the error list
            errorList.add("Step4 - image");

        generalMethods.printTestStep("5. Switch to Display Sales tab");
        generalMethods.clickOnElement(ewePageObject.getDisplaySalesTab());
        generalMethods.waitForElementToBeVisible(driver.findElements(ewePageObject.getDisplaySalesItems()).get(0), 30);
        String url = driver.getCurrentUrl();
        if (!generalMethods.compareResult(url.contains(displaySales),
                "Display Sales page is displayed",
                "Display Sales page is not displayed." + url))
            errorList.add("Step 5 - link");
        url = driver.findElements(ewePageObject.getDisplaySalesItems()).get(0).getAttribute("href");
        generalMethods.clickOnOneOfElements(ewePageObject.getDisplaySalesItems(), 0);
        generalMethods.waitForElementToBeVisible(ewePageObject.getDisplaySaleHeading(), 30);
        if (!generalMethods.compareResult(url.equals(driver.getCurrentUrl()),
                "Display Sales page is displayed",
                "Display Sales page is not displayed." + url))
            errorList.add("Step 5 - link item");
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step2.5", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 5 - image");

        generalMethods.printTestStep("6. Open flagship partner page");
        driver.navigate().back();
        driver.navigate().back();
        generalMethods.clickOnOneOfElements(ewePageObject.getBackToMagazineBtn(), 0);
        generalMethods.waitInSeconds(3);
        url = driver.getCurrentUrl();
        if (!generalMethods.compareResult(url.contains(flagshipPartnerPage),
                "Flagship Partner page is displayed",
                "Flagship Partner page is not displayed." + url))
            errorList.add("Step 6 - link");
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step2.6", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 6 - image");


        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }

    /**
     * Order catalogue test:
     * The scenario will be as following:
     * 1. User opens the start page and open the main menu "Kök" then select sub menu "Beställ katalog". Do validation-1
     * 2. User clicks on first catalogue image. Do validation-2
     * 3. Back to Order catalogue page: check on first "Beställ" checkbox then click button "Beställ katalog". Do validation-3
     * Expected result:
     * 1 - User lands in Order catalogue page. Take a screenshot and do the comparing of the page
     * 2 - The catalogue is opened in new tab, user can view it only. Take a screenshot and do the comparing of the page.
     * 3 - The "Beställ kataloger" page displays with user input info form. Take a screenshot and do the comparing of the page.
     */
    @Test
    public void Step3_OrderCatalogue() throws Exception {
        generalMethods.printTestName("======Start testing Step 6: Order Catalogue======");
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();

        generalMethods.printTestStep("1. User opens the start page and open the main menu \"Kök\" then select sub menu \"Beställ katalog\". Do validation-1");
        generalMethods.openURL(testEnvironment, 10);
        generalMethods.handlingCookies();
        //Hover on the main menu "Kök"
        generalMethods.clickOnElement(ewePageObject.getHamburgerMenu());
        generalMethods.clickOnElement(ewePageObject.getOrderCatalog());
        generalMethods.waitForElementToBeVisible(driver.findElements(ewePageObject.getCatalogThumb()).get(0), 30);
        //User lands in Order catalogue page. Take a screenshot and do the comparing of the page
        //Check if user lands on Order catalogue page by checking the URL
        String url = driver.getCurrentUrl();
        if (!generalMethods.compareResult(url.contains(orderCatalogPage),
                "Order catalogue page should be displayed",
                "Order catalogue page should be displayed" + url))
            //If FALSE, add step name to the error list
            errorList.add("Step 1");
        //Take screenshot of the entire page and compare
        if (!generalMethods.compareImages(folderName, "Step3.1", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - image");

        generalMethods.printTestStep("2. User clicks on first catalogue image. Do validation-2");
        url = driver.findElements(ewePageObject.getCatalogThumb()).get(0).getAttribute("href");
        //Click on the first catalogue
        generalMethods.clickOnOneOfElements(ewePageObject.getCatalogThumb(), 0);
        //The catalogue is opened in new tab, user can view it only. Take a screenshot and do the comparing of the page.
        //Take screenshot of the entire page and compare
        generalMethods.switchToTab(1);
        generalMethods.waitInSeconds(5);
        if (!generalMethods.compareResult(driver.getCurrentUrl().contains(url),
                "Correct catalogue page is displayed",
                "Incorrect catalogue page is displayed"))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - Link");
        if (!generalMethods.compareImages(folderName, "Step3.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - image");

        generalMethods.printTestStep("3. Back to Order catalogue page: check on first \"Beställ\" checkbox then click button \"Beställ katalog\". Do validation-3");
        generalMethods.switchToTab(0);
        //Click on the Order catalogue button
        generalMethods.clickOnOneOfElements(ewePageObject.getOrderCatalogBtn(), 0);
        //The "Beställ kataloger" page displays with user input info form. Take a screenshot and do the comparing of the page.
        //Check if the input form is displayed.
        try {
            generalMethods.waitForElementToBeVisible(ewePageObject.getOrderCatalogueForm(), 30);
        } catch (TimeoutException e) {
            if (!generalMethods.compareResult(driver.findElement(ewePageObject.getOrderCatalogueForm()).isDisplayed(),
                    "Order Catalog form is displayed",
                    "Order Catalog form is not displayed"))
                //If FALSE, add step name to the error list
                errorList.add("Step 3 - Form");
        }
        //Take screenshot of the entire page and compare
        if (!generalMethods.compareImages(folderName, "Step3.3", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 3 - image");

        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }

    /**
     * Product overview test:
     * The scenario will be as following:
     * +Product overview page+
     * 1. User opens the start page and open the main menu "Kök" then select sub menu "Alla modeller och färger". Do validation-1
     * 2. User clicks on first product "Fagerö". Do validation-2
     * Expected result:
     * 1 - User lands in Product overview page with filter options at left, heading, text and products which display in 2 columns. Take a screenshot and do the comparing of the page
     * 2 - User lands in product page. Take a screenshot and do the comparing of the page
     */
    @Test
    public void Step4_ProductOverview() throws Exception {
        generalMethods.printTestName("======Start testing Step 4: Product Overview & Magazine Start page======");
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();

        //Product overview page
        generalMethods.printTestStep("1.User opens the start page and open the main menu \"Kök\" then select sub menu \"Alla modeller och färger\". Do validation-1");
        generalMethods.openURL(testEnvironment + "kitchen/models/", 60);
        generalMethods.handlingCookies();

        //User lands in Product overview page with filter options at left, heading, text and products which display in 2 columns. Take a screenshot and do the comparing of the page
        //Check if Product List is displayed
        if (!generalMethods.compareResult(driver.findElements(ewePageObject.getProductItems()).size() > 0,
                "Product list should be displayed",
                "Product list should be displayed"))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - Product list");
        //Check if Page Description is displayed.
        if (!generalMethods.compareResult(driver.findElements(ewePageObject.getProductItems()).size() > 0,
                "Page description should be displayed",
                "Page description should be displayed"))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - Description");
        //Take screenshot of the entire page and compare
        if (!generalMethods.compareImages(folderName, "Step4.1", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - image");

        generalMethods.printTestStep("2. User clicks on first product. Do validation-2");
        String url = driver.findElements(ewePageObject.getProductItems()).get(1).getAttribute("href");
        //Click on first product "Fagerö"
        generalMethods.clickOnOneOfElements(ewePageObject.getProductItems(), 1);
        generalMethods.waitForElementToBeVisible(ewePageObject.getColorVariants(), 30);
        //User lands in product page. Take a screenshot and do the comparing of the page
        //Check if the correct product page is displayed by getting the product name on the page and compare
        if (!generalMethods.compareResult(driver.getCurrentUrl().equals(url),
                "correct page displayed",
                "Incorrect page displayed" + driver.getCurrentUrl() + url))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - Link");
        //Hide Product Slideshow to avoid error when comparing images
        //Take screenshot of the entire page and compare
        if (!generalMethods.compareImages(folderName, "Step4.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - image");

        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }

    @Ignore //Not available for small screen
    @Test
    public void Step5_KitchenConfigurator() throws Exception {
        generalMethods.printTestName("======Start testing Step 4: Product Overview & Magazine Start page======");
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();

        //Product overview page
        generalMethods.printTestStep("1.User opens the start page and open the main menu Inspiration then select sub menu Configurator. Do validation-1");
        generalMethods.openURL(testEnvironment, 10);
        generalMethods.handlingCookies();
        //Hover on the main menu "Kök"
        generalMethods.clickOnElement(ewePageObject.getHamburgerMenu());
        generalMethods.clickOnOneOfElements(ewePageObject.getHeaderItems(), 1);
        generalMethods.clickOnOneOfElements(ewePageObject.getHeaderMenuLinksMobile(), 2);
        //User lands on Configurator page
        generalMethods.waitForElementToBeVisible(driver.findElements(ewePageObject.getKitchenItems()).get(0), 30);
        String url = driver.getCurrentUrl();
        if (!generalMethods.compareResult(url.contains(configuratorPage),
                "correct page displayed",
                "Incorrect page displayed" + url))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - Link");
        //Take screenshot of the entire page and compare
        if (!generalMethods.compareImages(folderName, "Step5.1", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - image");

        generalMethods.printTestStep("2. User clicks on the 1st item and lands on the correct page");
        url = driver.findElements(ewePageObject.getKitchenItems()).get(0).getAttribute("data-href");
        generalMethods.clickOnOneOfElements(ewePageObject.getKitchenItems(), 0);
        generalMethods.switchFrame(ewePageObject.getConfiguratorFrame());
        generalMethods.waitForElementToBeVisible(ewePageObject.getSceneButtons(), 30);
        if (!generalMethods.compareResult(driver.getCurrentUrl().contains(url),
                "correct page displayed",
                "Incorrect page displayed" + driver.getCurrentUrl() + url))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - Link");
        //Take screenshot of the entire page and compare
        if (!generalMethods.compareImages(folderName, "Step5.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - image");

        generalMethods.printTestStep("3. Change material and compare screenshot");
        int items = generalMethods.countElement(ewePageObject.getHotspotPoints());
        for (int i = 0; i < items ; i++) {
            logger.info(i);
            try {
                driver.findElements(ewePageObject.getHotspotPoints()).get(i).click();
            } catch (ElementNotInteractableException e) {
                generalMethods.hoverOnElement(By.xpath("//div[contains(@class, 'actionwidget')]"));
                generalMethods.waitInSeconds(3);
                driver.findElements(ewePageObject.getHotspotPoints()).get(i).click();
            }
            generalMethods.waitForElementToBeVisible(By.xpath("//div[@class='overlay material-browser enable-viewmodes show']"), 30);
            driver.findElements(ewePageObject.getMaterialSelects()).get(0).click();
            generalMethods.waitForElementToBeHidden(ewePageObject.getLoader());
        }
        if (!generalMethods.compareImages(folderName, "Step5.3", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 3 - image");

        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }

    /**
     * Kitchenality test:
     * The scenario will be as following:
     * 1.Login with customer account
     * 2. User open start page, Click the first menu. The menu is opened. Click option: Tag køkkentesten on menu - Do validation -1
     * 3. Complete all step 1 to step 5 - Do validation - 2
     * 4. Input the email, check the email content - Do validation-3
     * Expected result:
     * 1 - Open kitchenality test screen. Take a screenshot and do the comparing of the page
     * 2. Each step, Take a screenshot and do the comparing of the page
     * 3. Email has been sent, user is able to click to view the result quiz from email properly. Take a screenshot and do the comparing of the page
     */
    @Test
    public void Step6_Kitchenality() throws Exception {
        generalMethods.printTestName("======Start testing Step 6: Kitchenality======");
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();
        //Initialize a variable to check if cookie consent is already displayed or not
        boolean popupDisplayed = false;

        generalMethods.printTestStep("Go to disposable email and copy the temporary email address.");
        String tempEmail = generalMethods.gettempmail();

        generalMethods.printTestStep("1. Go to Kitchenality page ");
        generalMethods.openURL(testEnvironment + kitchenalityPage, 10);
        //Check if Pop-up is displayed. If yes, close it and set popupDisplayed to True so the next check of pop-up will not execute the unnecessary action
        popupDisplayed = generalMethods.checkDisplayed(popupDisplayed, ewePageObject.getKitchenalityPopupClose());

        generalMethods.printTestStep("2. Click Start quiz");
        generalMethods.clickOnElement(ewePageObject.getStartQuizButton());
        generalMethods.handlingCookies();
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step6.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - image");

        generalMethods.printTestStep("3. Complete all step 1 to step 5 - Do validation - 2");
        //Step 1
        generalMethods.clickOnQuizItem();
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step6.3.1", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 3.1 - image");
        generalMethods.clickOnElement(ewePageObject.getQuizNextButton());
        //Step 2
        generalMethods.clickOnQuizItem();
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step6.3.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 3.2 - image");
        generalMethods.clickOnElement(ewePageObject.getQuizNextButton());
        //Step 3
        generalMethods.clickOnQuizItem();
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step6.3.3", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 3.34 - image");
        generalMethods.clickOnElement(ewePageObject.getQuizNextButton());
        //Step 4
        generalMethods.clickOnQuizItem();
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step6.3.4", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 3.4 - image");
        generalMethods.clickOnElement(ewePageObject.getQuizNextButton());
        //Step 5
        generalMethods.clickOnQuizItem();
        //Take screenshot of the entire page and do the comparison
        if (!generalMethods.compareImages(folderName, "Step6.3.5", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 3.5 - image");
        generalMethods.clickOnElement(ewePageObject.getQuizNextButton());

        generalMethods.printTestStep("4. Input the email, check the email content - Do validation-3");
        //Enter test email into the Email field
        generalMethods.typeIn(ewePageObject.getEmailBox(), tempEmail);
        //CLick on Save button
        generalMethods.clickOnElement(ewePageObject.getSaveKitchenalityButton());
        generalMethods.checkEmail(folderName, "Step6.4", errorList);

        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }

    @Test
    public void Step7_Magazine() throws Exception {
        generalMethods.printTestName("======Start testing Step 7: Magazine======");
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();
        //Initialize a variable to check if cookie consent is already displayed or not
        boolean popupDisplayed = false;

        generalMethods.printTestStep("1. Go to Magazine Start page ");
        generalMethods.openURL(testEnvironment + magazinePage, 60);
        generalMethods.handlingCookies();
        //Check if Pop-up is displayed. If yes, close it and set popupDisplayed to True so the next check of pop-up will not execute the unnecessary action
        popupDisplayed = generalMethods.checkDisplayed(popupDisplayed, ewePageObject.getKitchenalityPopupClose());
        if (!generalMethods.compareImages(folderName, "Step7.1", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - image");

        generalMethods.printTestStep("2. Click on the 1st magazine ");
        int articles = driver.findElements(ewePageObject.getReadMagazineBtn()).size();
        if (articles > 0) {
            String url = driver.findElements(ewePageObject.getReadMagazineBtn()).get(1).getAttribute("href");
            generalMethods.clickOnOneOfElements(ewePageObject.getReadMagazineBtn(), 1);
            generalMethods.waitForElementToBeVisible(ewePageObject.getBackToMagazineBtn(), 30);
            if (!generalMethods.compareResult(driver.getCurrentUrl().contains(url),
                    "correct page displayed",
                    "Incorrect page displayed" + driver.getCurrentUrl() + url))
                //If FALSE, add step name to the error list
                errorList.add("Step 2 - Link");
            if (!generalMethods.compareImages(folderName, "Step7.2", null))
                //If FALSE, add step name to the error list
                errorList.add("Step 2 - image");
        } else {
            generalMethods.printErrorMsg("There is no magazine");
            errorList.add("Step 2 - No Magazine");
        }


        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }

    @Test
    public void Step8_Gallery() throws Exception {
        generalMethods.printTestName("======Start testing Step 8: Gallery======");
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();
        //Initialize a variable to check if cookie consent is already displayed or not
        boolean popupDisplayed = false;

        generalMethods.printTestStep("1. Go to Gallery page ");
        generalMethods.openURL(testEnvironment, 10);
        generalMethods.handlingCookies();
        generalMethods.clickOnElement(ewePageObject.getHamburgerMenu());
        generalMethods.clickOnOneOfElements(ewePageObject.getHeaderItems(), 1);
        generalMethods.clickOnOneOfElements(ewePageObject.getHeaderMenuLinksMobile(), 0);
        generalMethods.waitForElementToBeVisible(ewePageObject.getLoadMoreImages(), 30);
        String url = driver.getCurrentUrl();
        if (!generalMethods.compareResult(url.contains(galleryPage),
                "Gallery page is displayed",
                "Gallery page is not displayed" + driver.getCurrentUrl() + url))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - Link");
        if (!generalMethods.compareImages(folderName, "Step8.1", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - image");

        generalMethods.printTestStep("2. Click on the Load more button ");
        int imagesNum = driver.findElements(ewePageObject.getGalleryItem()).size();
        generalMethods.clickOnElement(ewePageObject.getLoadMoreImages());
        generalMethods.waitInSeconds(5);
        if (!generalMethods.compareResult(driver.findElements(ewePageObject.getGalleryItem()).size() > imagesNum,
                "Load more successfully",
                "Load more unsuccessfully" + driver.findElements(ewePageObject.getGalleryItem()).size() + "/" + imagesNum))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - Load more");
        if (!generalMethods.compareImages(folderName, "Step8.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - image");


        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }

    @Test
    public void Step9_ChangeLanguage() throws Exception {
        generalMethods.printTestName("======Start testing Step 9: Change Language======");
        //Initialize a List to store the step which has error during test run
        ArrayList<String> errorList = new ArrayList<>();
        //Initialize a variable to check if cookie consent is already displayed or not
        boolean popupDisplayed = false;

        generalMethods.printTestStep("1. Go to Home page, change to DE ");
        generalMethods.openURL(testEnvironment, 10);
        generalMethods.handlingCookies();
        generalMethods.selectFromDropDown(ewePageObject.getChangeLanguageMobile(), "de");
        generalMethods.waitInSeconds(3);
        String url = driver.getCurrentUrl();
        if (!generalMethods.compareResult(url.contains("/de/"),
                "Changed to DE",
                "Failed to change to DE" + driver.getCurrentUrl() + url))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - Link");
        generalMethods.hideElements(ewePageObject.getSlideshow());
        if (!generalMethods.compareImages(folderName, "Step9.1", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 1 - image");

        generalMethods.printTestStep("2. Change to FR ");
        generalMethods.selectFromDropDown(ewePageObject.getChangeLanguageMobile(), "fr");
        generalMethods.waitInSeconds(3);
        url = driver.getCurrentUrl();
        if (!generalMethods.compareResult(url.contains("/fr/"),
                "Changed to FR",
                "Failed to change to FR" + driver.getCurrentUrl() + url))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - Link");
        generalMethods.hideElements(ewePageObject.getSlideshow());
        if (!generalMethods.compareImages(folderName, "Step9.2", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 2 - image");

        generalMethods.printTestStep("2. Change to EN ");
        generalMethods.selectFromDropDown(ewePageObject.getChangeLanguageMobile(), "en");
        generalMethods.waitInSeconds(3);
        url = driver.getCurrentUrl();
        if (!generalMethods.compareResult(url.contains("/en/"),
                "Changed to EN",
                "Failed to change to EN" + driver.getCurrentUrl() + url))
            //If FALSE, add step name to the error list
            errorList.add("Step 3 - Link");
        generalMethods.hideElements(ewePageObject.getSlideshow());
        if (!generalMethods.compareImages(folderName, "Step9.3", null))
            //If FALSE, add step name to the error list
            errorList.add("Step 3 - image");

        //Get number of error and do assertion
        Assert.assertEquals("Errors happened during test run. The following steps have error: " + errorList, 0, errorList.size());
        generalMethods.printSuccessMsg("Result of " + new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName() + " is: PASSED");
    }
}
