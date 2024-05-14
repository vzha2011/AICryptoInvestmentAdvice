package com.project.AICryptoInvestmentAdvice.automationtest.TestNG;

import com.project.AICryptoInvestmentAdvice.automationtest.testdata.DPs;
import com.project.AICryptoInvestmentAdvice.qa.pages.HomePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;


@Slf4j
public class HomePageTest {

    WebDriver driver;


    @AfterMethod
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }

    @Test(dataProvider = "browserTypes", dataProviderClass = DPs.class)
    public void clickTest(String browser) throws AWTException {
        HomePage homePage = new HomePage(browser);
        driver = homePage.getHomePageWebDriver();
        Actions actions = new Actions(driver);
        Robot robot = new Robot();
        robot.mouseMove(1000,18);
        actions.click().build().perform();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]")));

        WebElement webElement = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]"));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click();", webElement);
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logInPanel")));
    }
}
