package Test_Cases;

import Page_Objects.Base;
import Page_Objects.Login_Page;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class TC002_Login extends Base {
    public TC002_Login(){
        PageFactory.initElements(driver,this);
    }

    @Test
    public void TC002_Login_Perform() throws InterruptedException {
        Login_Page.getInstance().performLogin();
        Login_Page.getInstance().performLoginAssertion();

    }
}
