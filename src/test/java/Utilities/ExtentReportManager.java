package Utilities;

import Page_Objects.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "TestReport-" + timeStamp + ".html";
        String reportPath = "./reports/" + repName;

        // Vérifier et créer le dossier "reports"
        File reportsDir = new File("./reports/");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("TunisiaNet Automation Report");
        sparkReporter.config().setReportName("TunisiaNet Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "TunisiaNet");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        // Ajout des informations depuis testng.xml
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includeGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includeGroups.toString());
        }

        System.out.println("Initialisation du rapport ExtentReport dans " + reportPath);
    }


    public void onTestSuccess(ITestResult result){
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS,result.getName()+"got successfully executed");



    }

    public void onTestFailure(ITestResult result){
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL,result.getName()+"got Failed");
        test.log(Status.INFO,result.getThrowable().getMessage());

        try {

            String imgPath = new Base().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        }catch (IOException e1){
            e1.printStackTrace();
        }
    }

        public void onTestSkipped(ITestResult result){
            test=extent.createTest(result.getTestClass().getName());
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.SKIP,result.getName()+"got Skipped");
            test.log(Status.INFO,result.getThrowable().getMessage());

            try {


                String imgPath = new Base().captureScreen(result.getName());
                test.addScreenCaptureFromPath(imgPath);

            }catch (IOException e3){
                e3.printStackTrace();
            }
        }

    public void onFinish(ITestContext context) {
      extent.flush();

        // Vérification de la création du fichier de rapport
        String reportPath = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(reportPath);

            try {
                Desktop.getDesktop().browse(extentReport.toURI());
            } catch (IOException e) {
                System.err.println("❌ Impossible d'ouvrir le rapport : " + e.getMessage());
            }

            // Envoi du rapport par email
            try {
                URL reportURL = new URL("file:///"+System.getProperty("user.dir"+"\\reports\\"+repName));
                ImageHtmlEmail email = new ImageHtmlEmail();
                email.setDataSourceResolver(new DataSourceUrlResolver(reportURL));
                email.setHostName("smtp.googlemail.com");
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("bedouikhaled1@gmail.com", "password"));
                email.setSSLOnConnect(true);
                email.setFrom("bedouikhaled1@gmail.com");
                email.setSubject("Test Results");
                email.setMsg("Veuillez trouver le rapport en pièce jointe.");
                email.addTo("bedouikhaled090@gmail.com");
                email.attach(reportURL, "Extent Report", "Veuillez vérifier le rapport");
                email.send();

            } catch (IOException | EmailException e) {
                System.err.println("❌ Échec de l'envoi de l'email : " + e.getMessage());
            }

    }










}
