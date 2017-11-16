import org.junit.*;


import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Liz Mahoney
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.JVM)
public class UIIndependentNetworkTest {

    public static final String CHROMEDRIVER = "chromedriver";
    public static final String homePage = "https://independent-work.herokuapp.com/#/";
    private static ChromeDriverService service;
    private static WebDriver driver;


    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File (CHROMEDRIVER))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterClass
    public static void createAndStopService() {
        service.stop();
    }

    @Before
    public void createDriver() {
        driver = new RemoteWebDriver (service.getUrl(),
                DesiredCapabilities.chrome());
    }


    @After
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void testToIndependentNetworkHomepage() {
        driver.get(homePage);

        assertEquals ("Independent Work", driver.getTitle ());
    }

    @Test
    public void testClickSignupButton() throws InterruptedException {
        driver.get(homePage);
        Thread.sleep(7000);
        WebElement signUpButton= driver.findElement(By.xpath ("//*[@id=\"subscribe\"]/a"));

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", signUpButton);

        assertEquals ("https://independent-work.herokuapp.com/#/signup", driver.getCurrentUrl ());
    }

    @Test
    public void testClickEmployeeSignupButton() throws InterruptedException {
        driver.get ("https://independent-work.herokuapp.com/#/signup");
        Thread.sleep(7000);
        WebElement element= driver.findElement(By.xpath ("//*[@id=\"app-container\"]/div[2]/div/div/div/div/div/div/div/div/div[1]/a"));

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);

        assertEquals ("https://independent-work.herokuapp.com/#/signup/employer/step-one", driver.getCurrentUrl ());
    }

    @Test
    public void testMultiFormInputCreateAccount() throws InterruptedException{
        driver.get("https://independent-work.herokuapp.com/#/signup/employer/step-one");
        Thread.sleep(5000);
        WebElement inputEmail = driver.findElement (By.name("email"));
        WebElement inputPassword = driver.findElement (By.name("password"));
        WebElement inputConfirmPassword = driver.findElement (By.name("confirm_password"));
        inputEmail.sendKeys ("test10@yahoo.com");
        inputPassword.sendKeys ("Password!01");
        inputConfirmPassword.sendKeys ("Password!01");
        Thread.sleep (2000);

        WebElement submitButton= driver.findElement(By.xpath
                ("//*[@id=\"app-container\"]/div[2]/div/div/div/div/div/div/div/div/form/div[3]/div/button"));

        JavascriptExecutor submitExecutor = (JavascriptExecutor)driver;
        submitExecutor.executeScript("arguments[0].click();", submitButton);

        Thread.sleep (5000);

        assertEquals ("https://independent-work.herokuapp.com/#/signup/employer/step-two", driver.getCurrentUrl ());

        Thread.sleep(3000);
        WebElement inputFirstName = driver.findElement (By.name("first_name"));
        WebElement inputLastName = driver.findElement (By.name("last_name"));
        WebElement inputCity = driver.findElement (By.name("city"));
        WebElement mySelectElement = driver.findElement(By.name("state"));
        Select dropdown= new Select(mySelectElement);

        inputFirstName.sendKeys ("Test3");
        inputLastName.sendKeys ("Test3Last");
        inputCity.sendKeys ("Auburn");
        dropdown.selectByVisibleText("WA");

        Thread.sleep (4000);

        WebElement goToDashboardButton= driver.findElement(By.xpath
                ("//*[@id=\"app-container\"]/div[2]/div/div/div/div/div/div/div/div/form/div[4]/div/button"));

        JavascriptExecutor goToDashboardExecutor = (JavascriptExecutor)driver;
        goToDashboardExecutor.executeScript("arguments[0].click();", goToDashboardButton);

        Thread.sleep (5000);

        assertEquals ("https://independent-work.herokuapp.com/#/dashboard", driver.getCurrentUrl ());

    }

    @Test
    public void testSignOutAccount() throws InterruptedException{
        driver.get("https://independent-work.herokuapp.com/#/dashboard");

    }







}
