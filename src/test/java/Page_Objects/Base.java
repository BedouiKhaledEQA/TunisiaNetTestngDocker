package Page_Objects;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class Base {

    public static Logger logger;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static Properties props = new Properties();

    // Chargement des propriétés (configurations) une seule fois, dès le démarrage
    static {
        String environment = System.getProperty("env", "local");
        String filePath = "src/test/resources/" + environment + "__config.Properties";
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de la configuration pour l'environnement : " + environment, e);
        }
    }

    @BeforeClass(groups = {"Regression"})
    @Parameters({"os", "browser", "env"})
    public void launcher(String os, String browser, String env) throws MalformedURLException {
        logger = LogManager.getLogger(this.getClass());
        String environment = System.getProperty("env", "local");

        if ("prod".equalsIgnoreCase(environment)) {
            setDriver(launchRemoteDriver(browser));
        } else {
            setDriver(launchLocalDriver(browser));
        }
        configureDriver();
    }

    // Méthode pour définir le WebDriver pour le thread courant
    private static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    // Méthode pour obtenir le WebDriver du thread courant
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Méthode pour supprimer le WebDriver du thread courant
    private static void removeDriver() {
        driver.remove();
    }

    // Méthode pour lancer le driver en mode Remote (Selenium Grid)
    private static WebDriver launchRemoteDriver(String browser) throws MalformedURLException {
        DesiredCapabilities des = new DesiredCapabilities();
        des.setPlatform(Platform.WIN10);
        boolean isHeadless = Boolean.parseBoolean(props.getProperty(browser + "Headless", "true"));

        switch (browser.toLowerCase()) {
            case "chrome":
                des.setBrowserName("chrome");
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless");
                }
                des.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                break;
            case "firefox":
                des.setBrowserName("firefox");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                des.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                break;
            case "edge":
                des.setBrowserName("edge");
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless");
                }
                des.setCapability(EdgeOptions.CAPABILITY, edgeOptions);
                break;
            default:
                throw new RuntimeException("Navigateur non supporté : " + browser);
        }
        return new RemoteWebDriver(new URL("http://localhost:4444"), des);
    }

    // Méthode pour lancer le driver en mode Local
    private static WebDriver launchLocalDriver(String browser) {
        boolean isHeadless = Boolean.parseBoolean(props.getProperty(browser + "Headless", "true"));
        WebDriver driverInstance;

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless");
                }
                driverInstance = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                driverInstance = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless");
                }
                driverInstance = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new RuntimeException("Navigateur non supporté : " + browser);
        }
        return driverInstance;
    }

    // Configuration commune du driver
    private static void configureDriver() {
        WebDriver currentDriver = getDriver();
        currentDriver.manage().window().maximize();
        currentDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        currentDriver.get(props.getProperty("url"));
    }

    @AfterClass(groups = {"Regression"})
    public void tearDown() {
        WebDriver currentDriver = getDriver();
        if (currentDriver != null) {
            logger.info("Closing the browser...");
            currentDriver.quit();
            removeDriver();
        }
    }

    public String captureScreen(String tname) throws  IOException{
        String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot)getDriver();
        File sourceFile =takesScreenshot.getScreenshotAs(OutputType.FILE);
        String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp +".png";
        File targetFile=new File(targetFilePath);
        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return targetFilePath;


    }
}
