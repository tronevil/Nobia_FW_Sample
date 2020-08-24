package smokeTest_desktop;

import general_methods.GeneralMethods;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * BaseTest class is created to be the base of all other test classes.
 * It contains the common variables, setup() function and tearDown() function.
 * All test classes must inherit from this class in order to be able to start and close any session successfully.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Set test run order with Name Ascending order
public class BaseTestDesktop {

    protected static WebDriver driver;
    protected GeneralMethods generalMethods;
    protected static GeneralMethods commonMethods = new GeneralMethods();
    final static Logger logger = Logger.getLogger(BaseTestDesktop.class);
    protected final String folderName = "desktop/" + this.getClass().getSimpleName();
    private static boolean initialized = false;
    private static int errorNo = 0;


    /**
     * cleanRefImages() method is used to clean all reference images based value of "cleanRefImages" in Config.properties file.
     */
    @Before
    public void cleanImages() {
        if (!initialized) {

            boolean cleanRefImages = Boolean.parseBoolean(commonMethods.getProperties("cleanImages"));
            try {
                File imageFolder = new File(System.getProperty("user.dir") + "/images/" + commonMethods.getProperties("browser") + "/" + folderName);
                if (cleanRefImages) {
                    System.out.print("Option to clean all reference images is set to TRUE. Deleting all images in " + imageFolder);
                    FileUtils.cleanDirectory(imageFolder);
                    logger.info("----> Done.");
                } else logger.info("Option to clean all reference images is set to FALSE. Continue to test run.");
            } catch (IOException e) {
                logger.info("This is the first run.");
            } catch (IllegalArgumentException i) {
                logger.info("This is the first run");
            }
            cleanUpImages();
            initialized = true;
        }
    }

    @Rule
    public TestRule testWatcher = new TestWatcher() {

        @Override
        public void starting(Description desc) {
            try {
                setup();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void finished(Description desc) {
            logger.info("Quitting driver...");
           // driver.quit();
        }
        @Override
        public void failed(Throwable e, Description d) {
            String error = e.getMessage();
            if (!error.contains("Errors happened during test run.")) {
                logger.debug("Exception is thrown. Creating screenshot...");
                errorNo++;
                Screenshot debug = generalMethods.takeScreenshot(folderName, null);
                String debugPath = System.getProperty("user.dir") + "/images/" + commonMethods.getProperties("browser") + "/" + folderName + "/debug/debug_";
                File debugFolder = new File(System.getProperty("user.dir") + "/images/" + commonMethods.getProperties("browser") + "/" + folderName + "/debug");
                generalMethods.folderCheck(debugFolder);
                if (folderName.contains("desktop")) {
                    debugPath = debugPath + errorNo + "_1440x892.jpg";
                } else if (folderName.contains("android")) {
                    debugPath = debugPath + errorNo + "_360x640.jpg";
                } else if (folderName.contains("iPhone")) {
                    debugPath = debugPath + errorNo + "_375x667.jpg";
                }
                try {
                    ImageIO.write(debug.getImage(), "PNG", new File(debugPath));
                    logger.info("Debug image for this error is: " + debugPath);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    };

    /**
     * setup() method is used to initialize webdriver based value of "browser" in Config.properties file.
     * There are 3 types of browser which are supported here:
     * - Chrome
     * - Safari
     * - Selenium Grid
     *
     * @throws MalformedURLException throw exception if URL is not in correct format
     */
    private void setup() throws MalformedURLException {

        //Set up webdriver with the browser specified in config file
        String driverType = commonMethods.getProperties("browser").toLowerCase();

        //Use Switch Case to switch between driver types
        switch (driverType) {
            case "chrome": {
                //WebDriverManager is used to download and setup ChromeDriver
                WebDriverManager.chromedriver().setup();

                //Set custom options for Chrome
                ChromeOptions chromeOptions = new ChromeOptions();

                //Headless mode value is True or False. Value is get from config file
                chromeOptions.setHeadless(Boolean.parseBoolean(commonMethods.getProperties("headlessMode")));
                //Setup window size. Value Width and Height are taken from config file
                //Check for running mode (Head/Headless)
                if (Boolean.parseBoolean(commonMethods.getProperties("headlessMode")))
                    chromeOptions.addArguments("--window-size=" + commonMethods.getProperties("windowWidth") + "," + commonMethods.getProperties("windowHeight"));
                else
                    chromeOptions.addArguments("--window-size=" + (Integer.parseInt(commonMethods.getProperties("windowWidth")) + 16) + "," + (Integer.parseInt(commonMethods.getProperties("windowHeight")) + 132));
                //Disable unexpected notification during test run
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-browser-side-navigation");
                chromeOptions.addArguments("enable-automation");
                chromeOptions.addArguments("--no-sandbox");
                System.setProperty("webdriver.chrome.silentOutput", "true"); //THIS will surpress all logs expect INFO

                java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
                //Set custom UA
                chromeOptions.addArguments("--user-agent=" + commonMethods.getProperties("userAgentDesktop"));
                //Set scrollbar in headless mode to the same with head mode
                chromeOptions.addArguments("--enable-features=OverlayScrollbar");
                chromeOptions.addArguments("--enable-prefer-compositing-to-lcd-text");                //Initialize ChromeDriver
                driver = new ChromeDriver(chromeOptions);
                //Set size for window
                long innerWidth = (long) ((JavascriptExecutor) driver).executeScript("return window.innerWidth");
                long innerHeight = (long) ((JavascriptExecutor) driver).executeScript("return window.innerHeight");
                long outerWidth = (long) ((JavascriptExecutor) driver).executeScript("return window.outerWidth");
                long outerHeight = (long) ((JavascriptExecutor) driver).executeScript("return window.outerHeight");
                long borderWidth = outerWidth - innerWidth;
                long borderHeight = outerHeight - innerHeight;
                driver.manage().window().setSize(new Dimension(Integer.parseInt(commonMethods.getProperties("windowWidth")) + Math.toIntExact(borderWidth),
                        (Integer.parseInt(commonMethods.getProperties("windowHeight")) + Math.toIntExact(borderHeight))));

                //Set Location for browser
                ((JavascriptExecutor) driver).executeScript("window.navigator.geolocation.getCurrentPosition=function(success){" +
                        "var position = {\"coords\" : {\"latitude\": \"59.334591\",\"longitude\": \"18.063240\"}};" +
                        "success(position);}");
                break;
            }
            case "safari": {
                //Set custom options for Safari
                SafariOptions safariOptions = new SafariOptions();
                //setUseTechnologyPreview value is True or False. If true, the Safari Technology Preview version will be used.
                safariOptions.setUseTechnologyPreview(Boolean.parseBoolean(commonMethods.getProperties("setUseTechnologyPreview")));
                //Initialize Safari driver
                driver = new SafariDriver(safariOptions);
                //Since there is no custom command to run Safari in specific window size, maximize the window.
                driver.manage().window().maximize();
                break;
            }
            case "grid": {
                //WebDriverManager is used to download and setup ChromeDriver
                //WebDriverManager.chromedriver().setup();
                //Set custom options for Chrome
                ChromeOptions chromeOptions = new ChromeOptions();
                //Headless mode value is True or False. Value is get from config file
                chromeOptions.setHeadless(Boolean.parseBoolean(commonMethods.getProperties("headlessMode")));
                //Setup window size. Value Width and Height are taken from config file
                //Check for running mode (Head/Headless)
                if (Boolean.parseBoolean(commonMethods.getProperties("headlessMode")))
                    chromeOptions.addArguments("--window-size=" + commonMethods.getProperties("windowWidth") + "," + commonMethods.getProperties("windowHeight"));
                else
                    chromeOptions.addArguments("--window-size=" + (Integer.parseInt(commonMethods.getProperties("windowWidth")) + 16) + "," + (Integer.parseInt(commonMethods.getProperties("windowHeight")) + 132));
                //Disable unexpected notification during test run
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-browser-side-navigation");
                chromeOptions.addArguments("enable-automation");
                chromeOptions.addArguments("--no-sandbox");
                System.setProperty("webdriver.chrome.silentOutput", "true"); //THIS will surpress all logs expect INFO

                java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
                //Set custom UA
                chromeOptions.addArguments("--user-agent=" + commonMethods.getProperties("userAgentDesktop"));
                //Set scrollbar in headless mode to the same with head mode
                chromeOptions.addArguments("--enable-features=OverlayScrollbar");
                chromeOptions.addArguments("--enable-prefer-compositing-to-lcd-text");
                driver = new RemoteWebDriver(new URL(commonMethods.getProperties("NodeURL")), chromeOptions);
                //Set size for window
                long innerWidth = (long) ((JavascriptExecutor) driver).executeScript("return window.innerWidth");
                long innerHeight = (long) ((JavascriptExecutor) driver).executeScript("return window.innerHeight");
                long outerWidth = (long) ((JavascriptExecutor) driver).executeScript("return window.outerWidth");
                long outerHeight = (long) ((JavascriptExecutor) driver).executeScript("return window.outerHeight");
                long borderWidth = outerWidth - innerWidth;
                long borderHeight = outerHeight - innerHeight;
                driver.manage().window().setSize(new Dimension(Integer.parseInt(commonMethods.getProperties("windowWidth")) + Math.toIntExact(borderWidth),
                        (Integer.parseInt(commonMethods.getProperties("windowHeight")) + Math.toIntExact(borderHeight))));

                break;
            }
        }

        //Set Implicitly wait time
        driver.manage().timeouts().implicitlyWait(Long.parseLong(commonMethods.getProperties("implicitlyWait")), TimeUnit.SECONDS);
        //Set Page Load time out
        driver.manage().timeouts().pageLoadTimeout(Long.parseLong(commonMethods.getProperties("pageLoadTimeout")), TimeUnit.SECONDS);

        //Initiate generalMethods
        generalMethods = new GeneralMethods(driver);
    }

    private void cleanUpImages() {
        File imageFolder = new File(System.getProperty("user.dir") + "/images/" + commonMethods.getProperties("browser") + "/" + folderName + "/new");
        if(imageFolder.isDirectory() && imageFolder.exists()) {
            if(Objects.requireNonNull(imageFolder.list()).length != 0) {
                logger.info("Cleaning New images folder...");
                try {
                    FileUtils.cleanDirectory(imageFolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("Done");
            }
        }
    }
}
