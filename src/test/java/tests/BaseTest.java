package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.TestListener;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import util.Config;
import util.Constants;
import java.net.MalformedURLException;
import java.net.URL;

@Listeners({ TestListener.class })
public abstract class BaseTest {
    protected WebDriver driver;
    public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void setupConfig() { Config.initialise(); }

    @BeforeTest
    public void setDriver(ITestContext context) throws MalformedURLException {
        this.driver = (Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)))
                ? getRemoteDriver()
                : getLocalDriver();
        context.setAttribute(Constants.DRIVER, this.driver);
    }

    private WebDriver getRemoteDriver() throws MalformedURLException {
        String url = String.format(
                Config.get(Constants.GRID_URL_FORMAT), Config.get(Constants.GRID_HUB_HOST));

        Capabilities capabilities = switch(Config.get(Constants.BROWSER).toLowerCase()) {
            case Constants.FIREFOX -> new FirefoxOptions();
            case Constants.EDGE -> new EdgeOptions();
            case Constants.SAFARI -> new SafariOptions();
            default -> new ChromeOptions();
        };
        logger.info("Grid URL: {}", url);

        return new RemoteWebDriver(new URL(url), capabilities);
    }

    private WebDriver getLocalDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    @AfterTest
    public void tearDown(){
        this.driver.quit();
    }

}
