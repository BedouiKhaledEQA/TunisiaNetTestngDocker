package Page_Objects;

import com.github.javafaker.Faker;
import org.openqa.selenium.support.PageFactory;

public class User_Page extends Base {
    private String Firstname;
    private String Lastname;
    private String Password;
    private String Email;
    private String Birthday;

    public User_Page(String firstname, String lastname, String password, String email, String birthday) {
        Firstname = firstname;
        Lastname = lastname;
        Password = password;
        Email = email;
        Birthday = birthday;
    }
    public User_Page(){
        Faker faker =new Faker();
        String Firstname =faker.name().firstName();
        String Lastname =faker.name().lastName();
        String Email =faker.internet().safeEmailAddress();
        String Password=faker.internet().password();
        String Birthday=faker.date().birthday().toString();

        this.Firstname=Firstname;
        this.Lastname=Lastname;
        this.Email=Email;
        this.Password=Password;
        this.Birthday=Birthday;

    }


    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
}
