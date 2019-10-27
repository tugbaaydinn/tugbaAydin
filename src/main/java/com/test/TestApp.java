package com.test;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestApp {
    protected WebDriver driver;
    public String price;
    public String basketPrice;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver.exe");
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        this.login("tugbaaydinn96@gmail.com", "tugbatugba1996");
    }


    @Test
    public void login(String id, String pw) throws Exception {
        driver.get("https://www.n11.com");
        driver.findElement(By.className("btnSignIn")).click();
        driver.findElement(By.id("email")).sendKeys(id);
        driver.findElement(By.id("password")).sendKeys(pw);
        driver.findElement(By.id("loginButton")).click();
        this.searchConsole("Bilgisayar");
    }

    @Test
    public void searchConsole(String keyword) throws InterruptedException {
        System.out.println("Finded Keyword : " + keyword);
        driver.findElement(By.id("searchData")).sendKeys(keyword);
        driver.findElement(By.className("searchBtn")).click();
        Thread.sleep(5000);
        this.findPage(2);
    }

    public void findPage(int n) {
        System.out.println("I am in " + n + ". page");
        driver.findElement(By.cssSelector("#contentListing > div > div > div.productArea > div.pagination > a:nth-child(" + n + ")"));
        this.getRandomElementWithClassName("plink");
    }

    public void getRandomElementWithClassName(String className) {
        System.out.println("Class Name is " + className);
        Random rand = new Random();
        List<WebElement> links = driver.findElements(By.className(className));
        int randomNum = rand.nextInt((links.size() - 1) + 1) + 1;
        String pLink = links.get(randomNum).getAttribute("href");
        this.goPageAndAddBasket(pLink);
    }

    public void goPageAndAddBasket(String link) {
        System.out.println("Product Link :" + link);
        driver.get(link);
        this.price = driver.findElement(By.className("newPrice")).getText();
        this.addBasket();
    }

    public void addBasket() {
        driver.findElement(By.className("btnAddBasket")).click();
        driver.findElement(By.className("myBasket")).click();
        driver.findElement(By.className("spinnerUp ")).click();
        driver.findElement(By.className("removeProd ")).click();

        this.basketPrice = driver.findElement(By.className("priceArea")).getText();
        this.price = this.price.replaceAll("(?<=(L))(?s)(.*$)", " ");
        this.basketPrice = this.basketPrice + "  ";
        if (this.price.equals(this.basketPrice)) {
            System.out.print("Prices are equal : " + this.price + " == " + this.basketPrice);
        } else {
            System.out.println("Prices are not equal : " + this.price + " != " + this.basketPrice);
        }

    }


}
