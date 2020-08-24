package general_methods;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import page_objects.BasePage;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.yandex.qatools.ashot.shooting.ShootingStrategies.scaling;

/**
 * This Class contains all methods that can be used across all the sites
 */
public class GeneralMethods {
    private static WebDriver driver;
   // NgWebDriver ngWebDriver = new NgWebDriver(driver);
    //ngWebDriver.waitForAngularRequestsToFinish();

    JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
   // NgWebDriver ngDriver= new NgWebDriver(jsDriver);
    //ngDriver.waitForAngularRequestsToFinish();



    public GeneralMethods(WebDriver driver) {
        GeneralMethods.driver = driver;
    }

    public GeneralMethods() {
    }

    final static Logger logger = Logger.getLogger(GeneralMethods.class);

    private final static BasePage basePage = new BasePage();

    //region Read Data methods
    /**
    * HEINEKEN DEMO METHODS
    */
    public void Login(By element1, By element2, By element3, String username, String password)
    {
        System.out.println("Try to log in as customer");
        //Wait to element to be visible before interacting
        waitForElementToBeVisible(element1, 10);
        logger.info("Input \"" + username + "\" into " + element1);
        waitForElementToBeVisible(element2, 10);
        logger.info("Input \"" + password + "\" into " + element2);
        waitForElementToBeVisible(element3, 10);
        logger.info("Select Login button");
        //Send text to the username field
        driver.findElement(element1).sendKeys(username);
        //Send text to the password field
        driver.findElement(element2).sendKeys(password);
        //Select Log in button
        driver.findElement(element3).click();

    }

    public void saveOrder(By element1,By element2, By element3, By element4, By element5, By element6, By element7, By element8) {

        waitForElementToBeVisible(element1, 10);
        System.out.println("Step1: Select New Order menu");
        driver.findElement(element1).click();
        System.out.println("Step2: Select a brand in Brands page");
        driver.findElement(element2).click();
        System.out.println("Step3: Print list of product");
        //Add item in list to cart
        List<WebElement> product_list = driver.findElements(element3);
        for(int i=1 ; i < product_list.size(); ++i) {
            WebElement checkbox = product_list.get(i).findElement(By.xpath("//*[@elem='list']/div/hot-product["+i+"]/div/div/div/div[2]/div[2]/div/hot-quantity-input/div/div[1]"));
            if (i==1 || i == 3)
                checkbox.click();

        }

        // Save items in cart to verify later
        List<WebElement> cart_list = driver.findElements(element4);
        System.out.println("Cart list:" + cart_list.size());
        String b = driver.findElement(element4).getText();
        System.out.println("Product in cart :" + b);
        System.out.println("Click Save Cart button");
        driver.findElement(element5).click();

        // Go to Saved Order
        System.out.println("Go to Order Template");
        waitForElementToBeVisible(element6, 10);
        WebElement t = driver.findElement(element6);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()",t);
        System.out.println("Select First Row in SavedOrder List");
        waitForElementToBeVisible(element7, 10);
        driver.findElement(element7).click();

        //Get Saved Cart Detail List
        waitForElementToBeVisible(element8, 10);
        List<WebElement> saveCartList = driver.findElements(element8);
        String c = driver.findElement(element8).getText();
        System.out.println("Product in SavedCart :" + c);
        //Compare Products in Cart and Products in Saved Cart
        System.out.println("Compare Products in Cart and Products in Saved Cart");
        if(b.equalsIgnoreCase(c))
            printSuccessMsg("PASSED: ");
        else
            printSuccessMsg("FAILED: ");

    }




    /**
     * getProperties() method is used to read data from Config.properties file.
     *
     * @param key is the keyword defined in Config file.
     * @return value associated to the keyword provided.
     */
    private String loadProperties(String filePath, String key) {
        String value = "";
        try (InputStream input = new FileInputStream(filePath)) {
            Properties prop = new Properties();
            prop.load(input);
            value = prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * getProperties() method is used to read data from Config.properties file.
     *
     * @param key is the keyword defined in Config file.
     * @return value associated to the keyword provided.
     */
    public String getProperties(String key) {
        //Try to read Config.properties file
       // String path = System.getProperty("user.dir") + "/src/test/java/Config.properties";
        String path = System.getProperty("user.dir") + "/src/test/java/HeinekenConfig.properties";
        return loadProperties(path, key);
    }

    /**
     * getTestData() is method to get data from [Brand]TestData.properties file.
     *
     * @param brand is name of the site.
     * @param key   is the keyword defined in Config file.
     * @return value associated to the keyword provided.
     */
    public String getTestData( String brand,String key) {
       //String path = System.getProperty("user.dir") + "/src/test/java/test_data/" + brand + "TestData.properties";
        String path = System.getProperty("user.dir") + "/src/test/java/test_data/HeinekenData.properties";
        return loadProperties(path, key);
    }


    //endregion

    //region Wait methods

    public void waitForImagesToLoad() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
// Getting DOM status
        Object result = jse.executeScript("return document.readyState;");
        System.out.println("=> The status is : " + result.toString());
// Checking DOM loading is completed or not?
        if (result.equals("complete")) {
            // Fetching images count
            result = jse.executeScript("return document.images.length");
            int imagesCount = Integer.parseInt(result.toString());
            boolean allLoaded = false;
            // Checking and waiting until all the images are getting loaded
            while (!allLoaded) {
                int count = 0;
                for (int i = 0; i < imagesCount; i++) {
                    result = jse.executeScript("return document.images[" + i + "].complete && typeof document.images[" + i + "].naturalWidth != \"undefined\" && document.images[" + i + "].naturalWidth > 0");
                    boolean loaded = (Boolean) result;
                    if (loaded) count++;
                }
                // Breaking the while loop if all the images loading completes
                if (count == imagesCount) {
                    System.out.println("=> All the Images are loaded...");
                    break;
                } else {
                    System.out.println("=> Not yet loaded...");
                }
                waitInSeconds(1);
            }
        }
    }

    /**
     * waitInSeconds() is the method to pause the thread for a specified amount of time
     *
     * @param sec used to define amount of time the you need the thread to sleep. Time Unit: Second.
     */
    public void waitInSeconds(int sec) {
        try {
            logger.info("Pausing for " + sec + " seconds");
            SECONDS.sleep(sec);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * waitForElementToBeVisible() method is used to wait for an element to be visible within time out.
     *
     * @param element is the element that need to be visible
     */
    public void waitForElementToBeVisible(By element, int timeout) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeout, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        logger.info("Waiting for  " + element + " to be visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    /**
     * waitForElementToBeVisible() method is used to wait for an element to be visible within time out.
     *
     * @param element is the element that need to be visible
     */
    public void waitForElementToBeVisible(WebElement element, int timeout) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeout, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        logger.info("Waiting for  " + element + " to be visible");
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * waitForElementToBeHidden() method is used to wait for an element to be hidden within time out.
     *
     * @param element is the element that need to be hidden
     */
    public void waitForElementToBeHidden(By element) {
        //Set Implicitly wait time
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        try {
            logger.info("Waiting for  " + element + " to be hidden");
            int i = 0;
            while (driver.findElement(element).isDisplayed() && i < 100) {
                MILLISECONDS.sleep(100);
                i++;
            }
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            logger.info("Element is already hidden");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        //Set Implicitly wait time
        driver.manage().timeouts().implicitlyWait(Long.parseLong(getProperties("implicitlyWait")), TimeUnit.SECONDS);
    }

    /**
     * waitForElementToBeClickable() method is used to wait for an element to be clickable within time out.
     *
     * @param element is the element that need to be clickable
     */
    public void waitForElementToBeClickable(By element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        logger.info("Waiting for  " + element + " to be clickable");
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * waitForElementToBeClickable() method is used to wait for an element to be clickable within time out.
     *
     * @param element is the element that need to be clickable
     */
    public void waitForElementToBeClickable(WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        logger.info("Waiting for  " + element + " to be clickable");
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * waitForPageToLoad() method is used to wait for page to load successfully. It uses Javascript Executor to get
     * 'readyState' of the DOM. If it's 'complete', it means the page has loaded successfully. If not, print a '.' until page is loaded.
     */
    public void waitForPageToLoad(String oldURL) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        logger.info("Waiting for page to load");
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains(oldURL)));
    }

    //endregions

    //region Common methods

    public Screenshot takeScreenshot(String device, @Nullable By elementToBeTaken) {
        waitInSeconds(3);
        List<WebElement> images = driver.findElements(By.xpath("//div[@id='main']//img"));
        if (!images.isEmpty()) {
            for (WebElement image : images) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", image);
            }
        }
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0)");
        //Create a new Screenshot object to save the new image.
        Screenshot screenshot = null;
        //Check if an element or the entire page will be taken.
        if (elementToBeTaken == null) {
            logger.info("Taking screenshot of the entire page to compare...");
            //Using 'ashot' lib to take screenshot of the entire page
            if (device.contains("desktop"))
                screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
            else if (device.contains("android"))
                screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(scaling(3), 100)).takeScreenshot(driver);
            else if (device.contains("iPhone"))
                screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(scaling(2), 100)).takeScreenshot(driver);
        } else {
            logger.info("Taking screenshot of element:" + elementToBeTaken + " to compare...");
            WebElement el = driver.findElement(elementToBeTaken);
            //Using 'ashot' lib to take screenshot of the element.
            if (device.contains("desktop"))
                screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, el);
            else if (device.contains("android"))
                screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(scaling(3), 100)).coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, el);
            else if (device.contains("iPhone"))
                screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(scaling(2), 100)).coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, el);
        }
        return screenshot;
    }

    /**
     * compareImages() method is used to take screenshot of an element or the entire page and compare with reference image if existed
     * If reference image does not exist, take a new screenshot and place it in Reference folder
     * Result of 'compareImages' function will be TRUE or FALSE:
     * TRUE means reference is updated or new screenshot matches reference image.
     * FALSE means new screenshot does not match reference image.
     *
     * @param folderName       is the name of folder that images will be stored
     * @param screenName       is the unique name of the image of the element or page
     * @param elementToBeTaken is the element that will be taken screenshot.
     * @return True or False based on result of pixel-by-pixel comparison between 2 images.
     * @throws Exception show any exception during the run.
     */
    public Boolean compareImages(String folderName, String screenName, @Nullable By elementToBeTaken) throws Exception {
        //initialize a variable to store comparison result.
        boolean result = false;
        String refPath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/" + screenName + "_ref_";
        if (folderName.contains("desktop")) {
            refPath = refPath + "1440x892.jpg";
        } else if (folderName.contains("android")) {
            refPath = refPath + "360x640.jpg";
        } else if (folderName.contains("iPhone")) {
            refPath = refPath + "375x667.jpg";
        }
        String newPath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/" + screenName + "_new_";
        if (folderName.contains("desktop")) {
            newPath = newPath + "1440x892.jpg";
        } else if (folderName.contains("android")) {
            newPath = newPath + "360x640.jpg";
        } else if (folderName.contains("iPhone")) {
            newPath = newPath + "375x667.jpg";
        }
        String diffPath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/" + screenName + "_diff_";
        if (folderName.contains("desktop")) {
            diffPath = diffPath + "1440x892.jpg";
        } else if (folderName.contains("android")) {
            diffPath = diffPath + "360x640.jpg";
        } else if (folderName.contains("iPhone")) {
            diffPath = diffPath + "375x667.jpg";
        }

        //Create folders to store images if not exist
        logger.info("Creating folder...");
        File refFolder = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references");
        File newFolder = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new");
        File diffFolder = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff");
        File diffHighlightedImage = new File(diffPath);
        File newImage = new File(newPath);

        //Check if the images folder is existed or not.
        folderCheck(refFolder);
        folderCheck(newFolder);
        folderCheck(diffFolder);
        if (diffHighlightedImage.exists()) {
            FileUtils.forceDelete(diffHighlightedImage);
        }
        if (newImage.exists()) {
            FileUtils.forceDelete(newImage);
        }

        //Check if reference image existed
        File ref = new File(refPath);
        //If it exists
        if (ref.exists()) {
            logger.info("References image existed!");
            //Setup a path for the new image

            logger.info("Taking new screenshot to compare...");
            //Take screenshot
            Screenshot actual = takeScreenshot(folderName, elementToBeTaken);
            //Render the image and save to New folder
            ImageIO.write(actual.getImage(), "PNG", new File(newPath));

            //Create a new File object to get the new image.
            File actualImage = new File(newPath);

            //ImageDiff object will make a comparison between new and ref images and highlight any unmatched pixel.
            ImageDiff diff = new ImageDiffer().makeDiff(ImageIO.read(ref), ImageIO.read(actualImage));
            BufferedImage diffImage = diff.getMarkedImage(); // comparison result with marked differences
            //Get the proportion of the different pixels
            double diffProportion = (double) diff.getDiffSize() / ((double) diffImage.getHeight() * (double) diffImage.getWidth());
            //Set up a threshold for differences.
            double threshold = Double.parseDouble(getProperties("imageComparisonThreshold")) / 100;
            //If the proportion of different pixel is over the threshold, images are different.
            logger.info("Comparing images...");
            if (diffProportion > threshold) {
                //Render a new image with highlighted different and placed in Diff folder
                ImageIO.write(diffImage, "png", diffHighlightedImage);
                printErrorMsg("FAILED: Images " + screenName + " are different. Different percentage: " + String.format("%.2f", diffProportion * 100) + "% while threshold is " + threshold * 100 + "%");
                if (ImageIO.read(actualImage).getWidth() != ImageIO.read(ref).getWidth() | ImageIO.read(actualImage).getHeight() != ImageIO.read(ref).getHeight()) {
                    printErrorMsg("Images do not have the same size. Actual is: "
                            + ImageIO.read(actualImage).getWidth()
                            + "x"
                            + ImageIO.read(actualImage).getHeight()
                            + " while Ref is: "
                            + ImageIO.read(ref).getWidth()
                            + "x"
                            + ImageIO.read(ref).getHeight()
                    );
                }
            }
            //If the proportion of different pixel is under the threshold, images are the same.
            else {
                //Delete the image in New folder.
                FileUtils.forceDelete(actualImage);
                //Set result to true
                result = true;
                printSuccessMsg("PASSED: Images " + screenName + " are matched");
            }
        }
        //If reference image does not exist. Take a new screenshot and save to Reference folder
        else {
            //Create a new Screenshot object to store new screenshot.
            Screenshot r = takeScreenshot(folderName, elementToBeTaken);
            //Render image and placed in Reference folder
            ImageIO.write(r.getImage(), "PNG", new File(refPath));
            //Set result to true
            result = true;
            printSuccessMsg("This script is running for the first time, taking new screenshot as reference");
        }
        return result;
    }

    public boolean compareImages2(String folderName, String screenName, @Nullable By elementToBeTaken) throws IOException {
        //initialize a variable to store comparison result.
        boolean result = false;
        waitInSeconds(5);
        //Create folders to store images if not exist
        logger.info("Creating folder...");
        File refFolder = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references");
        File newFolder = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new");
        File diffFolder = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff");
        File diffHighlightedImage = null;
        if (folderName.contains("desktop")) {
            diffHighlightedImage = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/" + screenName + "_diff_1440x892.png");
        } else if (folderName.contains("android")) {
            diffHighlightedImage = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/" + screenName + "_diff_360x640.png");
        } else if (folderName.contains("iPhone")) {
            diffHighlightedImage = new File(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/" + screenName + "_diff_375x667.png");
        }

        //Check if the Reference folder is existed or not.
        folderCheck(refFolder);
        folderCheck(newFolder);
        folderCheck(diffFolder);
        if (diffHighlightedImage.exists()) {
            FileUtils.forceDelete(diffHighlightedImage);
        }

        //Check if reference image existed
        String refPath = null;
        if (folderName.contains("desktop")) {
            refPath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/" + screenName + "_ref_1440x892.png";
        } else if (folderName.contains("android")) {
            refPath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/" + screenName + "_ref_360x640.png";
        } else if (folderName.contains("iPhone")) {
            refPath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/" + screenName + "_ref_375x667.png";
        }
        File ref = new File(refPath);
        //If it exists
        if (ref.exists()) {
            logger.info("References image existed!");
            //Setup a path for the new image
            String filePath = null;
            if (folderName.contains("desktop")) {
                filePath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/" + screenName + "_new_1440x892.png";
            } else if (folderName.contains("android")) {
                filePath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/" + screenName + "_new_360x640.png";
            } else if (folderName.contains("iPhone")) {
                filePath = System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/" + screenName + "_new_375x667.png";
            }
            logger.info("Taking new screenshot to compare...");
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, 0)");
            //Check if an element or the entire page will be taken.
            if (elementToBeTaken == null) {
                logger.info("Taking screenshot of the entire page to compare...");
                //Using 'ashot' lib to take screenshot of the entire page
                if (folderName.contains("desktop"))
                    Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE)
                            .withName(screenName + "_new_1440x892")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/");
                else if (folderName.contains("android"))
                    Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE, 500, true)
                            .withName(screenName + "_new_360x640")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/");
                else if (folderName.contains("iPhone"))
                    Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE, 500, true)
                            .withName(screenName + "_new_375x667")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/");
            } else {
                logger.info("Taking screenshot of element:" + elementToBeTaken + " to compare...");
                WebElement el = driver.findElement(elementToBeTaken);
                scrollToElement(el);
                //Using 'ashot' lib to take screenshot of the element.
                if (folderName.contains("desktop"))
                    Shutterbug.shootElement(driver, el)
                            .withName(screenName + "_new_1440x892")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/");
                else if (folderName.contains("android"))
                    Shutterbug.shootElement(driver, el, false)
                            .withName(screenName + "_new_360x640")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/");
                else if (folderName.contains("iPhone"))
                    Shutterbug.shootElement(driver, el, false)
                            .withName(screenName + "_new_375x667")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/new/");
            }

            //Create a new File object to get the new image.
            File actualImage = new File(filePath);

            //ImageDiff object will make a comparison between new and ref images and highlight any unmatched pixel.
            ImageDiff diff = new ImageDiffer().makeDiff(ImageIO.read(ref), ImageIO.read(actualImage));
            BufferedImage diffImage = diff.getMarkedImage(); // comparison result with marked differences
            //Get the proportion of the different pixels
            double diffProportion = (double) diff.getDiffSize() / ((double) diffImage.getHeight() * (double) diffImage.getWidth());
            //Set up a threshold for differences.
            double threshold = Double.parseDouble(getProperties("imageComparisonThreshold")) / 100;
            //If the proportion of different pixel is over the threshold, images are different.
            logger.info("Comparing images...");
            if (diffProportion > threshold) {
                //Render a new image with highlighted different and placed in Diff folder
                ImageIO.write(diffImage, "png", diffHighlightedImage);
                printErrorMsg("===FAILED: Images " + screenName + " are different. Different percentage: " + String.format("%.2f", diffProportion * 100) + "% while threshold is " + threshold * 100 + "%");
                if (ImageIO.read(actualImage).getWidth() != ImageIO.read(ref).getWidth() | ImageIO.read(actualImage).getHeight() != ImageIO.read(ref).getHeight()) {
                    printErrorMsg("Images do not have the same size. Actual is: "
                            + ImageIO.read(actualImage).getWidth()
                            + "x"
                            + ImageIO.read(actualImage).getHeight()
                            + " while Ref is: "
                            + ImageIO.read(ref).getWidth()
                            + "x"
                            + ImageIO.read(ref).getHeight()
                    );
                }
            }
            //If the proportion of different pixel is under the threshold, images are the same.
            else {
                //Delete the image in New folder.
                FileUtils.forceDelete(actualImage);
                //Set result to true
                result = true;
                printSuccessMsg("PASSED: Images " + screenName + " matched");
            }
        }
        //If reference image does not exist. Take a new screenshot and save to Reference folder
        else {
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, 0)");
            //Check if an element or the entire page will be taken.
            if (elementToBeTaken == null) {
                logger.info("Taking screenshot of the entire page to save...");
                //Using 'ashot' lib to take screenshot of the entire page
                if (folderName.contains("desktop"))
                    Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE)
                            .withName(screenName + "_ref_1440x892")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/")
//                            .equalsWithDiff(ImageIO.read(ref), System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/")
                            ;
                else if (folderName.contains("android"))
                    Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE)
                            .withName(screenName + "_ref_360x640")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/")
//                            .equalsWithDiff(ImageIO.read(ref), System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/")
                            ;
                else if (folderName.contains("iPhone"))
                    Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE)
                            .withName(screenName + "_ref_375x667")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/")
//                            .equalsWithDiff(ImageIO.read(ref), System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/")
                            ;
            } else {
                logger.info("Taking screenshot of element:" + elementToBeTaken + " to save...");
                WebElement el = driver.findElement(elementToBeTaken);
                //Using 'ashot' lib to take screenshot of the element.
                if (folderName.contains("desktop"))
                    Shutterbug.shootElement(driver, el)
                            .withName(screenName + "_ref_1440x892")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/")
//                            .equalsWithDiff(ImageIO.read(ref), System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/")
                            ;
                else if (folderName.contains("android"))
                    Shutterbug.shootElement(driver, el)
                            .withName(screenName + "_ref_360x640")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/")
//                            .equalsWithDiff(ImageIO.read(ref), System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/")
                            ;
                else if (folderName.contains("iPhone"))
                    Shutterbug.shootElement(driver, el)
                            .withName(screenName + "_ref_375x667")
                            .save(System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/references/")
//                            .equalsWithDiff(ImageIO.read(ref), System.getProperty("user.dir") + "/images/" + getProperties("browser") + "/" + folderName + "/diff/")
                            ;
            }
            //Render image and placed in Reference folder
            //Set result to true
            result = true;
            printSuccessMsg("===This script is running for the first time, taking new screenshot as reference");
        }
        return result;
    }

    public void folderCheck(File name) {
        if (!name.exists()) {
            //if not, create it
            if (name.mkdirs()) {
                logger.info("Directory " + name + " is created!");
            }
            //if there is an error, print a message.
            else {
                logger.info("Failed to create " + name + " directory!");
            }
        }
    }

    /**
     * hideElements() method is used to hide a specified element(s) from displaying.
     *
     * @param elements is the element(s) that need to be hidden
     */
    public void hideElements(By elements) {
        //Find all matched elements and put them in a list.
        List<WebElement> el = driver.findElements(elements);
        //IF list is not empty, hide all of the element in the list using Javascript Executor.
        if (!el.isEmpty()) {
            for (WebElement webElement : el) {
                logger.info("Hiding  " + webElement);
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='hidden'", webElement);
            }
        }
        //If the list is empty, print a message.
        else logger.info("There is no matched element to hide.");
    }

    /**
     * showElements() method is used to make a specified element(s) visible.
     *
     * @param elements is the element(s) that need to be hidden
     */
    public void showElements(By elements) {
        //Find all matched elements and put it in a list.
        List<WebElement> el = driver.findElements(elements);
        //IF list is not empty, hide all of the element in the list using Javascript Executor.
        if (!el.isEmpty()) {
            for (WebElement webElement : el) {
                logger.info("Showing  " + webElement);
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='visible'", webElement);
            }
        }
        //If the list is empty, print a message.
        else logger.info("There is no matched element to show.");
    }

    /**
     * clickOnElement() method is used to click on a specified element.
     * Javascript Executor is used to click on Safari while normal method is used to click on Chrome
     *
     * @param element is the element(s) that need to be hidden
     */
    public void clickOnElement(By element) {
        try {
            //Wait for element to be visible and clickable
            waitForElementToBeVisible(element, 30);
            waitForElementToBeClickable(element);
            scrollToElement(element);

            logger.info("Clicking on  " + element);
            //If browser is Safari, use JS Executor
            if (getProperties("browser").equals("Safari")) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", driver.findElement(element));
                logger.info("Clicked on  " + element);
                waitInSeconds(5);
                //waitForPageToLoad(driver);
            }
            //If browser is Chrome or other, use normal method.
            else {
                driver.findElement(element).click();
                logger.info("Clicked on  " + element);
            }
        }
        //If element is overlapped by other element, throw an error message.
        catch (ElementClickInterceptedException e) {
            logger.info("Unable to click on " + element);
            throw e;
        }
    }

    public void clickOnElementAction(WebElement element) {
        try {
            //Wait for element to be visible and clickable
            waitForElementToBeVisible(element, 10);
            waitForElementToBeClickable(element);

            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().build().perform();
            logger.info("Clicked on " + element);
        }
        //If element is overlapped by other element, throw an error message.
        catch (ElementClickInterceptedException e) {
            logger.info("Unable to click on " + element);
            throw e;
        }
    }

    public void clickOnElementAction(By element) {
        try {
            //Wait for element to be visible and clickable
            waitForElementToBeVisible(element, 30);
            waitForElementToBeClickable(element);

            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(element)).click().build().perform();
            logger.info("Clicked on " + element);
        }
        //If element is overlapped by other element, throw an error message.
        catch (ElementClickInterceptedException e) {
            logger.info("Unable to click on " + element);
            throw e;
        }
    }

    public void clickOnOneOfElements(By elements, int index) {
        try {
            WebElement element = driver.findElements(elements).get(index);
            //Wait for element to be visible and clickable
            waitForElementToBeVisible(element, 10);
            waitForElementToBeClickable(element);
            scrollToElement(element);

            logger.info("Clicking on  " + element);
            //If browser is Safari, use JS Executor
            if (getProperties("browser").equals("Safari")) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", element);
                logger.info("Clicked on  " + element);
                waitInSeconds(5);
                //waitForPageToLoad(driver);
            }
            //If browser is Chrome or other, use normal method.
            else {
                element.click();
                logger.info("Clicked on  " + element);
            }
        }
        //If element is overlapped by other element, throw an error message.
        catch (ElementClickInterceptedException e) {
            logger.info("Unable to click on " + elements);
            throw e;
        } catch (IndexOutOfBoundsException ex) {
            logger.info("There is no matched element");
            throw ex;
        }
    }

    public void clickOnMultipleElements(By elements) {
        try {
            List<WebElement> elementList = driver.findElements(elements);
            for (WebElement element : elementList) {
                scrollToElement(element);
                //If browser is Safari, use JS Executor
                if (getProperties("browser").equals("Safari")) {
                    JavascriptExecutor jse = (JavascriptExecutor) driver;
                    jse.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", element);
                    //waitForPageToLoad(driver);
                }
                //If browser is Chrome or other, use normal method.
                else {
                    element.click();
                }
                logger.info("Clicked on  " + element);
            }
        }
        //If element is overlapped by other element, throw an error message.
        catch (ElementClickInterceptedException e) {
            logger.info("Unable to click on " + elements);
            throw e;
        } catch (IndexOutOfBoundsException ex) {
            logger.info("There is no matched element");
        }
    }


    /**
     * typeIn() method is used to send text to a specified element.
     *
     * @param element is the element(s) that need to be hidden
     */
    public void typeIn(By element, String text) {
        //Wait to element to be visible before interacting
        waitForElementToBeVisible(element, 10);
        logger.info("Typing \"" + text + "\" into " + element);
        //clear the field
        driver.findElement(element).clear();
        //Send text to the element.
        driver.findElement(element).sendKeys(text);
    }

    /**
     * scrollToElement() method is used to scroll a specified element into view.
     *
     * @param element is the element(s) that need to be hidden
     */
    public void scrollToElement(By element) {
        waitForElementToBeVisible(element, 10);
        WebElement el = driver.findElement(element);
        Point p = el.getLocation();
        long innerHeight = (long) ((JavascriptExecutor) driver).executeScript("return window.innerHeight");
        logger.info("Scrolling to " + element);
        ((JavascriptExecutor) driver).executeScript("window.scroll(" + p.getX() + "," + (p.getY() - innerHeight / 2) + ");", el);
    }

    /**
     * scrollToElement() method is used to scroll a specified element into view.
     *
     * @param element is the element(s) that need to be hidden
     */
    public void scrollToElement(WebElement element) {
        waitForElementToBeVisible(element, 10);
        Point p = element.getLocation();
        long innerHeight = (long) ((JavascriptExecutor) driver).executeScript("return window.innerHeight");
        logger.info("Scrolling to " + element);
        ((JavascriptExecutor) driver).executeScript("window.scroll(" + p.getX() + "," + (p.getY() - innerHeight / 2) + ");", element);
    }

    /**
     * scrollToElement() method is used to scroll a specified element into view.
     *
     * @param element is the element(s) that need to be hidden
     */
    public void scrollToElementHorizontal(By element) {
        waitForElementToBeVisible(element, 10);
        WebElement el = driver.findElement(element);
        logger.info("Scrolling to " + element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", el);
    }

    /**
     * scrollToElement() method is used to scroll a specified element into view.
     *
     * @param element is the element(s) that need to be hidden
     */
    public void scrollToElementHorizontal(WebElement element) {
        waitForElementToBeVisible(element, 10);
        logger.info("Scrolling to " + element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", element);
    }

    /**
     * openURL() method is used to open a URl with a restricted timeout of 5 seconds.
     * If timeout is reached, stop all loading requests and start interacting with the page.
     * Javascript Executor is used.
     *
     * @param url is the URL that need to be open
     */
    public void openURL(String url, int timeout) {
        String browser = getProperties("browser");
        driver.manage().timeouts().pageLoadTimeout(timeout, SECONDS);
        try {
            logger.info("Opening website at: " + url + getProperties("queryString") + " on " + browser);
            driver.get(url );
        } catch (TimeoutException e) {
            logger.info("Page Load Timeout reaches. Stopping all loading requests.");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("return window.stop");
        }
        logger.info("Opened website at: " + driver.getCurrentUrl() + " on " + browser);
        driver.manage().timeouts().pageLoadTimeout(Long.parseLong(getProperties("pageLoadTimeout")), SECONDS);
        //driver.manage().addCookie(new Cookie(getProperties("cookieName"), getProperties("cookieValue")));
       // logger.info("Automation Test Cookie has been set: " + driver.manage().getCookieNamed("QA_Nobia"));
        logger.info("Custom User Agent has been set: " + ((JavascriptExecutor) driver).executeScript("return navigator.userAgent"));
    }

    /**
     * hoverOnElement() method is used to hover on a specified element.
     * If browser is Safari, use JS Executor to action. If browser is Chrome or other, user Actions.
     *
     * @param element is the element(s) that need to be hovered on.
     */
    public void hoverOnElement(By element) {
        waitForElementToBeVisible(element, 30);
        WebElement el = driver.findElement(element);
        //Check if browser is Safari
        if (getProperties("browser").equals("Safari")) {
            String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                    "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                    "arguments[0].dispatchEvent(evObj);";
            logger.info("Hovering on " + element);
            ((JavascriptExecutor) driver).executeScript(javaScript, el);
        }
        //If browser is Chrome or other.
        else {
            Actions actions = new Actions(driver);
            logger.info("Hovering on " + element);
            actions.moveToElement(el).perform();
        }
    }

    /**
     * hoverOnElement() method is used to hover on a specified element.
     * If browser is Safari, use JS Executor to action. If browser is Chrome or other, user Actions.
     *
     * @param element is the element(s) that need to be hovered on.
     */
    public void hoverOnElement(WebElement element) {
        //Check if browser is Safari
        if (getProperties("browser").equals("Safari")) {
            String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                    "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                    "arguments[0].dispatchEvent(evObj);";
            logger.info("Hovering on " + element);
            ((JavascriptExecutor) driver).executeScript(javaScript, element);
        }
        //If browser is Chrome or other.
        else {
            Actions actions = new Actions(driver);
            waitForElementToBeVisible(element, 30);
            logger.info("Hovering on " + element);
            actions.moveToElement(element).perform();
        }
    }

    /**
     * hoverOnElement() method is used to hover on a specified element.
     * If browser is Safari, use JS Executor to action. If browser is Chrome or other, user Actions.
     *
     * @param elements is the element(s) that need to be hovered on.
     */
    public void hoverOnOneOfElement(By elements, int index) {
        WebElement webElement = driver.findElements(elements).get(index);
        waitForElementToBeVisible(webElement, 30);
        //Check if browser is Safari
        if (getProperties("browser").equals("Safari")) {
            String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                    "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                    "arguments[0].dispatchEvent(evObj);";
            logger.info("Hovering on " + webElement);
            ((JavascriptExecutor) driver).executeScript(javaScript, webElement);
        }
        //If browser is Chrome or other.
        else {
            Actions actions = new Actions(driver);
            logger.info("Hovering on " + webElement);
            actions.moveToElement(webElement).perform();
        }
    }

    /**
     * switchToTab() method is used to switch to other opened tab.
     * This action only run on Chrome. Safari will be handle differently.
     *
     * @param tabNumber is the tab that need to be switched to, start from 0.
     */
    public void switchToTab(int tabNumber) {
        //Get list of tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        try {
            //Switch to tab number.
            logger.info("Switching to tab number " + (tabNumber + 1));
            driver.switchTo().window(tabs.get(tabNumber));
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("There is only 1 tab. Cannot switch to other tab");
            throw e;
        }
    }

    /**
     * printErrorMsg() method is used to print error message with red text color.
     *
     * @param errorMsg is the message that need to be red.
     */
    public void printErrorMsg(String errorMsg) {
        printMessage("\033[0;31m", "===" + errorMsg);
    }

    /**
     * printSuccessMsg() method is used to print success message with green text color.
     *
     * @param successMsg is the message that need to be green.
     */
    public void printSuccessMsg(String successMsg) {
        printMessage("\033[0;32m", "===" + successMsg);
    }

    /**
     * printTestName() method is used to print Step name with teal text color.
     *
     * @param testName is the message that need to be teal.
     */
    public void printTestName(String testName) {
        printMessage("\033[0;36m", testName);
    }

    /**
     * printTestName() method is used to print Step name with teal text color.
     *
     * @param testStep is the message that need to be teal.
     */
    public void printTestStep(String testStep) {
        printMessage("\033[0;33m", testStep);
    }

    public void printMessage(String color, String message) {
        String path = System.getProperty("user.dir") + "/src/test/resources/log4j.properties";
        if (loadProperties(path, "log4j.rootLogger").contains("console")) {
            logger.info(color + message + "\033[0m");
        } else {
            logger.info(message);
            System.out.println(color + message + "\033[0m");
        }
    }

    public Boolean compareResult(Boolean condition, String successMsg, String errorMsg) {
        if (condition) {
            printSuccessMsg("PASSED: " + successMsg);
            return true;
        } else {
            printErrorMsg("FAILED: " + errorMsg);
            return false;
        }
    }

    public void clickOnQuizItem() {
        List<WebElement> quizItems = driver.findElements(basePage.getQuizItems());
        String number = driver.findElement(basePage.getNumberOfItems()).getText();
        int numberOfItems = Integer.parseInt(number.substring(number.length() - 1));
        for (int i = 0; i < numberOfItems; i++) {
            waitForElementToBeVisible(quizItems.get(i), 10);
            waitForElementToBeClickable(quizItems.get(i));
            scrollToElement(quizItems.get(i));
            quizItems.get(i).click();
        }
    }

    public void removeElement(By element) {
        List<WebElement> elements = driver.findElements(element);
        if (!elements.isEmpty()) {
            for (WebElement webElement : elements) {
                logger.info("Removing element: " + webElement);
                ((JavascriptExecutor) driver).executeScript("arguments[0].parentNode.removeChild(arguments[0])", webElement);
            }
        } else logger.warn("There is no element to remove");
    }

    public void selectFromDropDown(By element, String option) {
        scrollToElement(element);
        logger.info("Selecting " + option + " from dropdown " + element);
        Select dropDown = new Select(driver.findElement(element));
        try {
            dropDown.selectByVisibleText(option);
        } catch (NoSuchElementException e) {
            dropDown.selectByValue(option);
        }
    }

    public void selectFromDropDown(By element, int index) {
        scrollToElement(element);
        logger.info("Selecting " + index + " from dropdown " + element);
        Select dropDown = new Select(driver.findElement(element));
        dropDown.selectByIndex(index);
    }

    public void selectFromDropDownAction(By element) {
        WebElement e = driver.findElement(element);
        Actions builder = new Actions(driver);
        builder.moveToElement(e).build().perform();
        builder .click()
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER)
                .build()
                .perform();
    }

    /**
     * 'checkDisplayed()' method is used to check if the element is already clicked or not
     * It will use a variable that declare in the test method to track the display state of a element.
     * If the element is already displayed and clicked, it will print a message. If not, it will perform click action on the element.
     * This method will return result of True or False that can be used to set to the declared variable.
     *
     * @param displayedItem is the variable declared in the test method
     * @param element       is the element that need to be checked
     * @return the result of the click action.
     */
    public boolean checkDisplayed(boolean displayedItem, By element) {
        logger.info("Handling pop-up " + element + "...");
        if (!displayedItem) {
            try {
                waitInSeconds(3);
                clickOnElement(element);
                return true;
            } catch (TimeoutException e) {
                logger.info(element + " is not visible");
                return false;
            }
        } else {
            logger.info(element + " is already handled.");
            return true;
        }
    }

    public String getDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        return formatter.format(date);
    }

    public void switchFrame(String id) {
        logger.info("Switching to " + id + " frame");
        if (!id.equals("defaultContent"))
            driver.switchTo().frame(id);
        else driver.switchTo().defaultContent();
    }

    public int countElement(By element) {
        List<WebElement> elements = driver.findElements(element);
        return elements.size();
    }
    //endregion

    public String gettempmail() {
        String temporaryEmail = getProperties("TemporaryEmailAddress");
        try {
            driver.get(temporaryEmail);
            logger.info("Opened website at: " + driver.getCurrentUrl());
        } catch (TimeoutException e) {
            logger.info("Page Load Timeout reaches. Stopping all loading requests.");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("return window.stop");
        }
        String tempEmail = driver.findElement(basePage.getTempEmailAddress()).getAttribute("value");
        while (tempEmail.isEmpty())
            tempEmail = driver.findElement(basePage.getTempEmailAddress()).getAttribute("value");
        logger.info(tempEmail);
        return tempEmail;
    }

    public void checkEmail(String device, String screenName, ArrayList<String> errorList) throws Exception{
        String temporaryEmail = getProperties("TemporaryEmailAddress");
        try {
            driver.get(temporaryEmail);
            logger.info("Opening website at: " + driver.getCurrentUrl());
        } catch (TimeoutException e) {
            logger.info("Page Load Timeout reaches. Stopping all loading requests.");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("return window.stop");
        }

        try {
            String tempEmail = driver.findElement(basePage.getTempEmailAddress()).getAttribute("value");
            logger.info(tempEmail);
            clickOnElement(basePage.getRefreshButton());
            waitForElementToBeVisible(basePage.getEmailEntry(), 480);
            if (device.contains("desktop")) {
                clickOnElement(basePage.getEmailEntry());
                removeElement(basePage.getTopContent());
                removeElement(basePage.getBottomContent());
                removeElement(By.xpath("//iframe[contains(@id, 'ads')]"));
                if (!compareImages(device, screenName, basePage.getEmailContent())) {
                    errorList.add("Image - Wrong email");
                }
            } else {
                if (!compareResult(driver.findElement(basePage.getEmailEntry()).isDisplayed(),
                        "User received Kitchenality email",
                        "User does not received Kitchenality email"))
                    errorList.add("No email");
            }
        } catch (TimeoutException | IndexOutOfBoundsException | IOException e) {
            printErrorMsg("===FAILED: There is no email");
            errorList.add("No email");
        }
    }

    public void handlingCookies() {
        List<WebElement> cookies = driver.findElements(basePage.getCookieNotification());
        if (cookies.size() > 0) clickOnElement(basePage.getCookieNotification());
    }
}
