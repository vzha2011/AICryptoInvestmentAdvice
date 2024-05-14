package com.project.AICryptoInvestmentAdvice.automationtest.TestNG;

import com.project.AICryptoInvestmentAdvice.qa.pages.HomePage;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;

public class UserProfileTest {

    HomePage homePage;
    WebDriver driver;
    WebElement name;
    WebElement password;
    WebElement msg;
    WebElement logInButton;
    WebElement signInButton;
    WebDriverWait webDriverWait;

    @BeforeMethod
    public void setUp() throws AWTException {
        homePage = new HomePage("chrome");
        //homePage = new HomePage("firefox");
        //homePage = new HomePage("microsoftedge");
        driver = homePage.getHomePageWebDriver();
        Actions actions = new Actions(driver);
        Robot robot = new Robot();
        robot.mouseMove(1000,18);
        actions.click().build().perform();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(6));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]")));
        logInButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]"));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click();", logInButton);
        logInButton.click();
        name = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]"));
        password = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]"));
        msg = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/div[3]"));
        signInButton = driver.findElement(By.id("signInButton"));
        name.sendKeys("user565656");
        password.sendKeys("123456");
        signInButton.click();
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(2));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("userProfile")));
        driver.findElement(By.id("userProfile")).click();
    }

    @AfterMethod
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Test(priority = 0)
    public void profileMenuTest(){
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Assert.assertTrue(driver.findElement(By.linkText("History")).isDisplayed());
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Assert.assertTrue(driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/ul[1]/li[1]")).isDisplayed());
    }

    @Test(priority = 1)
    public void clickUserAdviceHistoryTest() throws AWTException {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.linkText("History")));
        driver.findElement(By.linkText("History")).click();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.linkText("log out")));
        driver.findElement(By.linkText("log out")).click();
        Actions actions = new Actions(driver);
        Robot robot = new Robot();
        robot.mouseMove(901,18);
        actions.click().build().perform();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(6));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]")));
        logInButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]"));
        org.testng.Assert.assertTrue(logInButton.isDisplayed());
    }

    @Test(priority = 2)
    public void clickLogOutTest() throws AWTException {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[2]/div[3]/ul[1]/li[1]")));
        driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/ul[1]/li[1]")).click();
        Actions actions = new Actions(driver);
        Robot robot = new Robot();
        robot.mouseMove(901,18);
        actions.click().build().perform();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(6));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]")));
        logInButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]"));
        org.testng.Assert.assertTrue(logInButton.isDisplayed());
    }

}
