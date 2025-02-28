package Page_Objects;

import Locators.Login_Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Login_Page extends Login_Locators {
    private WebDriver driver;
    public  static  Login_Page instance ;

    public Login_Page() {
        this.driver = Base.getDriver();  // Assure que l'instance du WebDriver est bien récupérée
    }

    public static Login_Page getInstance(){
        if(instance == null){
            instance= new Login_Page();
        }return instance ;
    }

    public void performLogin() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("page-preloader")));
        connexionButtonIcon.click();
        ConnexionButton.click();
        email.sendKeys(Base.props.getProperty("mail"));
        password.sendKeys(Base.props.getProperty("password"));
        submit.click();
        Thread.sleep(3000);


    }

    public void performLoginAssertion() throws InterruptedException {
        connexionButtonIcon.click();
        Thread.sleep(2000);
        Assert.assertTrue((logoutButton).isDisplayed());
    }
}
