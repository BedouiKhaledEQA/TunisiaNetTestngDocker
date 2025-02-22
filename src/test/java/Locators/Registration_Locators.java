package Locators;
import Page_Objects.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Registration_Locators extends Base {
    public Registration_Locators(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//a[@class='btn btn-black navbar-btn']")
    protected WebElement registrationButton;
    @FindBy(xpath = "//input[@id='input-username']")
    protected WebElement Username;
    @FindBy(xpath = "//input[@id='input-firstname']")
    protected WebElement FirstName;
    @FindBy(xpath = "//input[@id='input-lastname']")
    protected WebElement LastName;
    @FindBy(xpath = "//input[@id='input-email']")
    protected WebElement email;
    @FindBy(xpath = "//select[@id='input-country']")
    protected WebElement Country;
    @FindBy(xpath = "//input[@id='input-password']")
    protected WebElement password;
    @FindBy(xpath = "//div[@id='account-register']//li[1]//a[1]//img[1]")
    protected WebElement clockButton;
    @FindBy(xpath = "//button[@type='submit']")
    protected WebElement submitButton;



}
