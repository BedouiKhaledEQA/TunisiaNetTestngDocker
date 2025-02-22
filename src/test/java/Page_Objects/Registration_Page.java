package Page_Objects;


import Locators.Registration_Locators;
import org.openqa.selenium.support.ui.Select;

public class Registration_Page extends Registration_Locators {
    public static Registration_Page instance;

    public Registration_Page() {
        super(driver);
    }

    public static Registration_Page getInstance(){
        if(instance==null){
            instance=new Registration_Page();
        }return instance;

    }

    public void perform_registration() throws InterruptedException {
        registrationButton.click();
        Thread.sleep(10000);
        Username.sendKeys();
        FirstName.sendKeys();
        LastName.sendKeys();
        email.sendKeys();
        Select select=new Select(Country);
        select.selectByIndex(1);
        password.sendKeys();
        clockButton.click();
        submitButton.click();

    }





}
