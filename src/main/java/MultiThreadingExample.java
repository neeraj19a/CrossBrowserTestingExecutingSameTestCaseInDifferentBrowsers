
import java.io.File;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.Assert;

import org.testng.annotations.AfterClass;

import org.testng.annotations.Test;


public class MultiThreadingExample extends Thread {

    private WebDriver driver;

    private String Url;

    private String browsertype;

    public MultiThreadingExample(String name, String browsertype) {

        super(name);

        this.browsertype = browsertype;

    }

    @Override
    public void run() {

        System.out.println("Thread- Started" + Thread.currentThread().getName());

        String threadname = Thread.currentThread().getName();

        System.out.println(threadname);

        try {
            Thread.sleep(1000);
            setUp(this.browsertype);
            Thread.sleep(1000);
            testGoogleSearch();
        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            tearDown();
        }
        System.out.println("Thread- END " + Thread.currentThread().getName());
    }

    // main method to create the thread and run multiple threads
    public static void main(String[] args) {
        Thread t1 = new MultiThreadingExample("Thread Chrome", "Chrome");
        Thread t2 = new MultiThreadingExample("Thread IE", "IE");
        Thread t3 = new MultiThreadingExample("Thread Firefox", "Firefox");
        System.out.println("Starting MyThreads");
        t1.start();
        t2.start();
        t3.start();
        System.out.println("Thread has been started");
    }

    // set up the method to initialize driver object
    public void setUp(String browsertype) throws Exception {
        if (browsertype.contains("IE")) {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            System.setProperty("webdriver.ie.driver", "C:\\Users\\T460\\Downloads\\Drivers\\IEDriverServer.exe");
            driver = new InternetExplorerDriver(capabilities);

        } else if (browsertype.contains("Firefox")) {

            System.setProperty("webdriver.gecko.driver", "C:\\Users\\T460\\Downloads\\Drivers\\geckodriver.exe");

            driver = new FirefoxDriver();
        } else if (browsertype.contains("Chrome")) {

            System.setProperty("webdriver.chrome.driver", "C:\\Users\\T460\\Downloads\\Drivers\\chromedriver.exe");

            driver = new ChromeDriver();

        }

        Url = "https://www.google.co.in/";

        driver.get(Url);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.manage().window().maximize();

    }

    // test scripts

    public void testGoogleSearch() throws Exception {

        String actualtitle = driver.getTitle();

        String expectedTitle = "Google";

        Assert.assertEquals(actualtitle, expectedTitle, "The expected title matched");

        System.out.println("Cross Browser Testing Example");

        System.out.println("################Actual:" + actualtitle + "Expected:" + expectedTitle + "#################");
    }

    // tear down function to close browser
    public void tearDown() {

        driver.close();
        driver.quit();

    }

}