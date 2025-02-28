package Page_Objects;

import Locators.Registration_Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Registration_Page extends Registration_Locators {
    private WebDriver driver;
    private static Registration_Page instance;

    public Registration_Page() {
        this.driver = Base.getDriver();  // Assure que l'instance du WebDriver est bien récupérée
    }

    public static Registration_Page getInstance() {
        if (instance == null) {
            instance = new Registration_Page();
        }
        return instance;
    }

    User_Page user = new User_Page();

    public void performRegister() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Utilisation du driver initialisé
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("page-preloader")));
        connexionButtonIcon.click();
        ConnexionButton.click();
        createAccountButton.click();
        genderRadioButton.click();
        firstname.sendKeys(user.getFirstname());
        lastname.sendKeys(user.getLastname());
        email.sendKeys(user.getEmail());
        password.sendKeys(user.getPassword());
        birthdayInput.sendKeys(user.getBirthday());
        saveButton.click();
        Thread.sleep(3000);  // Remplacer par une attente explicite si possible
    }

    public void performRegisterAssertion() {
        connexionButtonIcon.click();
        Assert.assertTrue(connectedUserButton.isDisplayed());
    }
}
