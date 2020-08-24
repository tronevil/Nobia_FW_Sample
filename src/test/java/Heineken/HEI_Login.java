package Heineken;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import page_objects.EWEPageObject;
import smokeTest_desktop.BaseTestDesktop;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

//import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Set test run order with Name Ascending order
public class HEI_Login extends BaseTestDesktop {
    //region Variables declaration
    //Get page object and helpers
    private final EWEPageObject ewePageObject = new EWEPageObject();
  //  private final static EWEHelper eweHelper = new EWEHelper(driver);
  //  final static Logger logger = Logger.getLogger(HEI_Login.class);
    private static final String brand = "EWE";
    //Get all test data
    private static String testEnvironment;
    private static String username;
    private static String password;



    @BeforeClass
    public static void GetAllTestData()
    {
        testEnvironment = commonMethods.getTestData(brand,commonMethods.getProperties("environment"));
        username = commonMethods.getTestData(brand,commonMethods.getProperties("username"));
        password = commonMethods.getTestData(brand,commonMethods.getProperties("password"));

    }


    public void Scenario1_logInAsCustomer() {

        ArrayList<String> errorList = new ArrayList<>();
        generalMethods.printTestStep("Step1: User go to storefront");
        generalMethods.openURL(testEnvironment, 60);
        generalMethods.printTestStep("Step2: User input username and password to log in");
        generalMethods.Login(ewePageObject.inputUsername(),ewePageObject.inputPassword(),ewePageObject.clickLogin(),username,password);
        generalMethods.printTestStep("Step3: Verify result");
        generalMethods.waitForElementToBeHidden(ewePageObject.waitLoaderCompleted());
        if(generalMethods.compareResult(driver.getCurrentUrl().equalsIgnoreCase("https://hei-id-public-qa.azurewebsites.net/"),"Log in successfully", "Log in failed"))
        //If FALSE, add step name to the error list
            errorList.add("Step2 - image");

    }
    @Test
    public void Scenario2_SavedOrder()
    {
        generalMethods.printTestStep("SCENARIO2");
        Scenario1_logInAsCustomer();
        generalMethods.waitForElementToBeHidden(ewePageObject.waitLoaderCompleted());
        generalMethods.printTestStep("Step2: Select brands in Brands page");
        generalMethods.saveOrder(ewePageObject.selectMenuNewOrder(),ewePageObject.click_logoHeineken(),ewePageObject.click_addProduct(),ewePageObject.get_cart_list(),ewePageObject.clickSaveCart(),
        ewePageObject.selectOrderTemplates(),ewePageObject.clickFirstRowInSaveCartList(),ewePageObject.getSaveCartDetailList());

    }



}
