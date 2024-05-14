package com.project.AICryptoInvestmentAdvice.automationtest.TestNG;

import com.project.AICryptoInvestmentAdvice.automationtest.testdata.DPs;
import com.project.AICryptoInvestmentAdvice.qa.pages.HomePage;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.awt.*;
import java.time.Duration;
public class SignUpAndLogInTest {

    HomePage homePage;
    WebDriver driver;
    WebElement name;
    WebElement password;
    WebElement msg;
    WebElement logInButton;
    WebElement signInButton;


    @BeforeMethod
    public void setUp() throws AWTException, InterruptedException {
        Thread.sleep(100000);
        homePage = new HomePage("chrome");
        //homePage = new HomePage("firefox");
        //homePage = new HomePage("microsoftedge");
        driver = homePage.getHomePageWebDriver();
        Actions actions = new Actions(driver);
        Robot robot = new Robot();
        robot.mouseMove(1000,18);
        actions.click().build().perform();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]")));
        logInButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/button[1]"));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click();", logInButton);
        name = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]"));
        password = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]"));
        msg = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/div[3]"));
        signInButton = driver.findElement(By.id("signInButton"));
    }

    @AfterMethod
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }


    @Test(priority = 0)
    public void signUpAndLogInTest(){
        name.sendKeys("user111888");
        password.sendKeys("123456");
        driver.findElement(By.id("signUpButton")).click();
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> msg.isDisplayed());
        Assert.assertTrue(msg.getText().equals("You've successfully created the account!"));
        driver.findElement(By.id("signUpButton")).click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> msg.isDisplayed());
        Assert.assertTrue(msg.getText().equals("user111888 was registered. Please use other username."));
        name.clear();
        password.clear();
        name.sendKeys("user111888");
        password.sendKeys("123456");
        signInButton.click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(d -> driver.findElement(By.id("userProfile")).isDisplayed());
    }

    @Test(priority = 1, dataProvider = "userData", dataProviderClass = DPs.class)
    public void logInTestCorrectPassword(String userName, String pw) {
        name.sendKeys(userName);
        password.sendKeys(pw);
        signInButton.click();
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(1));
        wait.until(d -> driver.findElement(By.id("userProfile")).isDisplayed());
        driver.findElement(By.id("userProfile")).click();
        driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[3]/ul[1]/li[1]")).click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> driver.findElement(By.id("loginButton")).isDisplayed());
    }

    @Test(priority = 2)
    public void logInTestWrongPassword() {
        name.sendKeys("user111888");
        password.sendKeys("1234567");
        signInButton.click();
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(d -> msg.isDisplayed());
        Assert.assertTrue(msg.getText().equals("Incorrect username/password"));
    }



}
