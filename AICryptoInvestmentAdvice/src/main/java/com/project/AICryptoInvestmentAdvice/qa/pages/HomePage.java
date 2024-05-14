package com.project.AICryptoInvestmentAdvice.qa.pages;

import com.project.AICryptoInvestmentAdvice.qa.util.DriverUtil;
import com.project.AICryptoInvestmentAdvice.qa.util.FileBufferedReaders;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HomePage {
    protected WebDriver driver;
    protected String browser;

    private static Properties properties;


    public HomePage(String browser){
        try{
            properties = new Properties();
            properties.load(new FileBufferedReaders().getQaProperties());
            this.browser = browser;
            driver = DriverUtil.getDriver(browser, properties.getProperty("homePageUrl"));
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
        }
        catch(Exception e){
            log.error(e.getMessage());
        }
    }

//    public HomePage(String browser){
//        try{
//            properties = new Properties();
//            properties.load(new FileBufferedReaders().getQaProperties());
//            this.browser = browser;
//            driver = DriverUtil.getRemoteDriver(browser, properties.getProperty("homePageUrl"));
//            //driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
//        }
//        catch(Exception e){
//            log.error(e.getMessage());
//        }
//    }


    public WebDriver getHomePageWebDriver(){
        return driver;
    }



}
