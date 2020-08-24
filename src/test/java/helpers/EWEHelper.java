package helpers;

import general_methods.GeneralMethods;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import page_objects.EWEPageObject;

public class EWEHelper {
    private static WebDriver driver;

    public EWEHelper(WebDriver driver) {
        EWEHelper.driver = driver;
    }

    private final static GeneralMethods generalMethods = new GeneralMethods(driver);
    private final static EWEPageObject ewePageObject = new EWEPageObject();
    final static Logger logger = Logger.getLogger(EWEHelper.class);
}
