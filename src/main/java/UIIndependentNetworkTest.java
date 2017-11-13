import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Liz Mahoney
 * @version 1.0
 */
public class UIIndependentNetworkTest {

    private static ChromeDriverService service;
    private WebDriver driver;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File ("chromedriver"))
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
    public void testToIndependentWorkHomepage() {

        driver.get("https://independent-work.herokuapp.com/#/");

        assertEquals ("Independent Work", driver.getTitle ());
    }


}
