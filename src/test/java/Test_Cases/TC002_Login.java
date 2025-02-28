package Test_Cases;

import Page_Objects.Base;
import Page_Objects.Login_Page;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class TC002_Login extends Base {

    @Test(groups = {"Regression"})
    public void TC002_Login_Perform() throws InterruptedException {
        logger.info("**** STARTING TC002_Login ****");
        Login_Page.getInstance().performLogin();
        Login_Page.getInstance().performLoginAssertion();

    }
}
