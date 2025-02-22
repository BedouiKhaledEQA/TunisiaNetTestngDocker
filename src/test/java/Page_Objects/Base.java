package Page_Objects;

import org.openqa.selenium.Platform;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class Base {

    public static WebDriver driver;
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

    @BeforeClass
    public static WebDriver launcher() throws MalformedURLException {
        // Récupère le navigateur : prioritaire sur la propriété système sinon depuis le fichier de config
        String browser = System.getProperty("browser", props.getProperty("browser", "chrome"));
        String environment = System.getProperty("env", "local");

        if ("prod".equalsIgnoreCase(environment)) {
            driver = launchRemoteDriver(browser);
        } else {
            driver = launchLocalDriver(browser);
        }
        configureDriver();
        driver.get(props.getProperty("url"));
        return driver;
    }

    // Méthode pour lancer le driver en mode Remote (Selenium Grid)
    private static WebDriver launchRemoteDriver(String browser) throws MalformedURLException {
        DesiredCapabilities des = new DesiredCapabilities();
        des.setPlatform(Platform.WIN10);
        // Récupère la propriété dynamique : "chromeHeadless" ou "firefoxHeadless" etc.
        boolean isHeadless = Boolean.parseBoolean(props.getProperty(browser + "Headless", "false"));

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
        boolean isHeadless = Boolean.parseBoolean(props.getProperty(browser + "Headless", "false"));

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless");
                }
                return new ChromeDriver(chromeOptions);
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless");
                }
                return new EdgeDriver(edgeOptions);
            default:
                throw new RuntimeException("Navigateur non supporté : " + browser);
        }
    }

    // Configuration commune du driver
    private static void configureDriver() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
