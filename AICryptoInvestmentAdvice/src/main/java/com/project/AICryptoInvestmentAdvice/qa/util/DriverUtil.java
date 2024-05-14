package com.project.AICryptoInvestmentAdvice.qa.util;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URL;
import java.util.Properties;
@Service
public class DriverUtil {

    private static Properties properties;

    private static Properties urls;


    public static WebDriver getDriver(String browser, String url) throws IOException {
        properties = new Properties();
        properties.load(new FileBufferedReaders().getDriverProperties());
        WebDriver driver = null;
        if(browser.equals("chrome")){
            System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);
        }
        if(browser.equals("firefox")){
            System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));
            FirefoxOptions options = new FirefoxOptions();
            //options.addArguments("--headless");
            driver = new FirefoxDriver(options);

        }
        if(browser.equals("microsoftedge")){
            System.setProperty("webdriver.edge.driver", properties.getProperty("webdriver.edge.driver"));
            driver = new EdgeDriver();
        }
        driver.get(url);
        driver.manage().window().maximize();
        return driver;
    }

    public static RemoteWebDriver getRemoteDriver(String browser, String url) throws IOException {
        urls = new Properties();
        urls.load(new FileBufferedReaders().getQaProperties());
        String seleniumHubUrl = urls.getProperty("seleniumHubUrl");
        System.out.println(seleniumHubUrl);
        RemoteWebDriver remoteWebDriver = null;
        if(browser.equals("chrome")){
            ChromeOptions chromeOptions = new ChromeOptions();
            remoteWebDriver = new RemoteWebDriver(new URL(seleniumHubUrl), chromeOptions);
        }
        if(browser.equals("firefox")){
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            //firefoxOptions.addArguments("--headless");
            remoteWebDriver = new RemoteWebDriver(new URL(seleniumHubUrl), firefoxOptions);
        }
        if(browser.equals("microsoftedge")){
            EdgeOptions edgeOptions = new EdgeOptions();
            //edgeOptions.addArguments("--headless");
            remoteWebDriver = new RemoteWebDriver(new URL(seleniumHubUrl), edgeOptions);
        }
        remoteWebDriver.get(url);
        remoteWebDriver.manage().window().maximize();
        return remoteWebDriver;
    }


}
