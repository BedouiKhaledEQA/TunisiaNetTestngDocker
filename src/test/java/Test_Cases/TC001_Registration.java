package Test_Cases;

import Page_Objects.Base;
import Page_Objects.Registration_Page;
import org.testng.annotations.Test;

public class TC001_Registration extends Base {



    @Test(groups = {"Sanity"})
    public void RegistrationPage() throws InterruptedException {
        logger.info("**** STARTING TC001_Registration **** ");
        Registration_Page.getInstance().performRegister();
        Registration_Page.getInstance().performRegisterAssertion();
    }




}

