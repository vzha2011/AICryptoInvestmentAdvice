package com.project.AICryptoInvestmentAdvice.automationtest.TestNG;

import com.project.AICryptoInvestmentAdvice.automationtest.testdata.DPs;
import com.project.AICryptoInvestmentAdvice.qa.pages.HomePage;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;

public class GenerateAdviceTest {

    HomePage homePage;
    WebDriver driver;
    WebElement name;
    WebElement password;
    WebElement msg;
    WebElement logInButton;
    WebElement signInButton;
    WebElement currentPrice;
    WebElement targetPrice;
    WebElement purchasePrice;
    WebElement yearOfPurchase;
    WebElement targetYear;
    WebElement quantityPurchased;
    WebDriverWait webDriverWait;

    @BeforeMethod
    public void setUp() throws AWTException, InterruptedException {
        Thread.sleep(300000);
        homePage = new HomePage("firefox");
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
        name.sendKeys("user123");
        password.sendKeys("123456");
        signInButton.click();
        wait = new WebDriverWait(driver, Duration.ofMinutes(20));
        wait.until(d -> driver.findElement(By.id("userProfile")).isDisplayed());
    }

    @AfterMethod
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }
    @Test(priority = 0, dataProvider = "historicaldata2", dataProviderClass = DPs.class)
    public void generateAdvice(String name, String exchange, String quantity, String pricePurchased, String purchaseYear,
                               String targetPriceInTargetYear, String yearOfTarget) {
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/select[1]"), By.tagName("option")));
        Select cryptoNameDropDown = new Select(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/select[1]")));
        cryptoNameDropDown.selectByVisibleText(name);
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/select[1]"), By.tagName("option")));
        Select exchangeDropDown = new Select(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/select[1]")));
        exchangeDropDown.selectByVisibleText(exchange);
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[7]/input[1]")));
        currentPrice = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[7]/input[1]"));
        //target price
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[9]/input[1]")));
        targetPrice = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[9]/input[1]"));
        targetPrice.clear();
        targetPrice.sendKeys(targetPriceInTargetYear);
        //purchase price
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[3]/input[1]")));
        purchasePrice = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[3]/input[1]"));
        purchasePrice.clear();
        purchasePrice.sendKeys(pricePurchased);
        //year of purchase
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[6]/input[1]")));
        yearOfPurchase = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[6]/input[1]"));
        yearOfPurchase.clear();
        yearOfPurchase.sendKeys(purchaseYear);
        //target Year
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[10]/input[1]")));
        targetYear = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[10]/input[1]"));
        targetYear.clear();
        targetYear.sendKeys(yearOfTarget);
        //quantity purchased
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[4]/input[1]")));
        quantityPurchased = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[4]/input[1]"));
        quantityPurchased.clear();
        quantityPurchased.sendKeys(quantity);
        //generateadviceButton
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("generateAdviceButton")));
        driver.findElement(By.id("generateAdviceButton")).click();
        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(20));
        webDriverWait.until(d -> !driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[12]/div[1]")).getText().equals(""));
        Assert.assertTrue(!driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[12]/div[1]")).getText().equals(""));
    }


//    @Test(priority = 0, dataProvider = "cryptoInfoData", dataProviderClass = DPs.class)
//    public void generateAdvice(String name, String exchange, String quantity, String pricePurchased, String purchaseYear,
//            String targetPriceInTargetYear, String yearOfTarget) {
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/select[1]"), By.tagName("option")));
//        Select cryptoNameDropDown = new Select(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/select[1]")));
//        cryptoNameDropDown.selectByVisibleText(name);
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/select[1]"), By.tagName("option")));
//        Select exchangeDropDown = new Select(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/select[1]")));
//        exchangeDropDown.selectByVisibleText(exchange);
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[7]/input[1]")));
//        currentPrice = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[7]/input[1]"));
//        //target price
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[9]/input[1]")));
//        targetPrice = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[9]/input[1]"));
//        targetPrice.clear();
//        targetPrice.sendKeys(targetPriceInTargetYear);
//        //javascriptExecutor.executeScript("key.value=100000", targetPrice);
//        //purchase price
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[3]/input[1]")));
//        purchasePrice = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[3]/input[1]"));
//        purchasePrice.clear();
//        purchasePrice.sendKeys(pricePurchased);
//        //javascriptExecutor.executeScript("key.value=40000", purchasePrice);
//        //year of purchase
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[6]/input[1]")));
//        yearOfPurchase = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[6]/input[1]"));
//        yearOfPurchase.clear();
//        yearOfPurchase.sendKeys(purchaseYear);
//        //target Year
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[10]/input[1]")));
//        targetYear = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[10]/input[1]"));
//        targetYear.clear();
//        targetYear.sendKeys(yearOfTarget);
//        //quantity purchased
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[4]/input[1]")));
//        quantityPurchased = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[4]/input[1]"));
//        quantityPurchased.clear();
//        quantityPurchased.sendKeys(quantity);
//        //generateadviceButton
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("generateAdviceButton")));
//        driver.findElement(By.id("generateAdviceButton")).click();
//        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
//        webDriverWait.until(d -> !driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[12]/div[1]")).getText().equals(""));
//        Assert.assertTrue(!driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[12]/div[1]")).getText().equals(""));
//    }

}
