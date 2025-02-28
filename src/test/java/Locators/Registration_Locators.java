package Locators;
import Page_Objects.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Registration_Locators extends Base {
    public Registration_Locators(){
        PageFactory.initElements(getDriver(),this);
    }

    @FindBy(xpath = "//div[@class='user-info']")
    protected WebElement connexionButtonIcon;
    @FindBy(xpath = "//span[normalize-space()='Connexion']")
    protected WebElement ConnexionButton;
    @FindBy(css = "a[href='https://www.tunisianet.com.tn/connexion?create_account=1']")
    protected WebElement createAccountButton;
    @FindBy(css = "input[value='2']")
    protected WebElement genderRadioButton;
    @FindBy(xpath = "//input[@name='firstname']")
    protected WebElement firstname;
    @FindBy(xpath = "//input[@name='lastname']")
    protected WebElement lastname;
    @FindBy(xpath = "//input[@name='email']")
    protected WebElement email;
    @FindBy(xpath = "//input[@name='password']")
    protected WebElement password;
    @FindBy(xpath = "//input[@name='birthday']")
    protected WebElement birthdayInput;
    @FindBy(xpath = "//button[@type='submit']")
    protected WebElement saveButton;
    @FindBy(css = "a[title='Voir mon compte client']")
    protected WebElement connectedUserButton;




}
