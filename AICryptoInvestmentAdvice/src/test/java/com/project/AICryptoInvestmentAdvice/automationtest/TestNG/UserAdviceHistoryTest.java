package com.project.AICryptoInvestmentAdvice.automationtest.TestNG;

import com.project.AICryptoInvestmentAdvice.automationtest.testdata.DPs;
import com.project.AICryptoInvestmentAdvice.qa.util.FilePaths;
import com.project.AICryptoInvestmentAdvice.qa.pages.HomePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;

public class UserAdviceHistoryTest {

    HomePage homePage;
    WebDriver driver;
    WebElement name;
    WebElement password;
    WebElement msg;
    WebElement logInButton;
    WebElement signInButton;
    WebDriverWait webDriverWait;

    @BeforeMethod
    public void setUp() throws AWTException, InterruptedException {
        //Thread.sleep(100000);
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
        name.sendKeys("user565656");
        password.sendKeys("123456");
        signInButton.click();
        wait = new WebDriverWait(driver, Duration.ofMinutes(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("userProfile")));
        driver.findElement(By.id("userProfile")).click();
    }

    @AfterMethod
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }


    @Test(priority = 0, dataProvider = "adviceRecord", dataProviderClass = DPs.class)
    public void recordContentWithoutNewTest(String[] items) {
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(2));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.linkText("History")));
        driver.findElement(By.linkText("History")).click();
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(2));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//div[@col-id='Record ID'][contains(text(), %s)]/..", items[1]))));
        WebElement parentElement = driver.findElement(By.xpath(String.format("//div[@col-id='Record ID'][contains(text(), %s)]/..", items[1])));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(2));
        webDriverWait.until(d -> parentElement.findElements(By.xpath("*")));
        ArrayList<WebElement> childrenElements = (ArrayList<WebElement>) parentElement.findElements(By.xpath("*"));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(0).getText().equals(items[1]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(1).getText().equals(items[2]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(2).getText().equals(items[3]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(3).getText().equals(items[4]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(4).getText().equals(items[5]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(5).getText().equals(items[6]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(6).getText().equals(items[7]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(7).getText().equals(items[8]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(8).getText().equals(items[9]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(9).getText().equals(items[10]));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(10).getText().equals("download"));
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
        webDriverWait.until(d -> childrenElements.get(11).getText().equals("download"));
    }

    @Test(priority = 1)
    public void downloadNewsTitlesTest() throws IOException {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("History")));
        driver.findElement(By.linkText("History")).click();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/div[1]/div[11]/button[1]")));
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/div[1]/div[11]/button[1]")).click();
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/div[1]/div[11]/button[1]")).click();
        Path fileName = Path.of(FilePaths.downloadedText);
        String str = Files.readString(fileName);
        Assert.assertTrue(str.length()>0);
    }


    @Test(priority = 2)
    public void downloadAdviceTest() throws IOException {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.linkText("History")));
        driver.findElement(By.linkText("History")).click();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/div[1]/div[12]/button[1]")));
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/div[1]/div[12]/button[1]")).click();
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/div[1]/div[12]/button[1]")).click();
        Path fileName = Path.of(FilePaths.downloadedText);
        String str = Files.readString(fileName);
        Assert.assertTrue(str.length()>0);
    }


}
