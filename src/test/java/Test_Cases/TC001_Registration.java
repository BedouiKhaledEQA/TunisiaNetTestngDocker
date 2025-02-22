package Test_Cases;

import Page_Objects.Base;
import Page_Objects.Registration_Page;
import org.testng.annotations.Test;

public class TC001_Registration extends Base {



    @Test
    public void RegistrationPage() throws InterruptedException {
        Registration_Page.getInstance().perform_registration();
    }




}

