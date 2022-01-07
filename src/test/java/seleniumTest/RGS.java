package seleniumTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class RGS {
    WebDriver webDriver;
    WebDriverWait wait;

    @BeforeTest
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(webDriver, 10);
        webDriver.get("https://www.rgs.ru/");
    }

    @Test
    public void test() throws InterruptedException {
        WebElement cookie = webDriver.findElement(By.xpath("//button[@class= \"btn--text\"]"));
        cookie.click();

        WebElement company = webDriver.findElement(By.xpath("//a[@href=\"/for-companies\"]"));
        company.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class = 'item text--basic current']")));

        WebElement health = webDriver.findElement(By.xpath("//span[@class=\"padding\" and contains(text(), \"Здоровье\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(health));
        health.click();

        WebElement insurance = webDriver.findElement(By.xpath("//a[contains(text(), 'Добровольное медицинское страхование')]"));
        insurance.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class = \"title word-breaking title--h2\"]")));

        WebElement titleInsurance = webDriver.findElement(By.xpath("//h1[@class = \"title word-breaking title--h2\"]"));
        Assert.assertEquals(titleInsurance.getText(), "Добровольное медицинское страхование");

        WebElement sendBtn = webDriver.findElement(By.xpath("//button/span[contains(text(), \"Отправить заявку\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(sendBtn));
        sendBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"form-submit\"]")));

        WebElement reCall = webDriver.findElement(By.xpath("//h2[@class=\"section-basic__title title--h2 word-breaking title--h2\" and (../div[@id=\"callback\"])]"));

        Assert.assertEquals(reCall.getText(), "Оперативно перезвоним\nдля оформления полиса");
        scrollToElement(webDriver.findElement(By.xpath("//div[@class=\"form-submit\"]")));

        WebElement name = webDriver.findElement(By.xpath("//input[@name=\"userName\"]"));
        fillInputField(name, "Петров Петр Петрович");
        Assert.assertEquals(name.getAttribute("value"), "Петров Петр Петрович");

        WebElement phoneNumber = webDriver.findElement(By.xpath("//input[@name=\"userTel\"]"));
        fillInputField(phoneNumber, "8485684353");
        Assert.assertEquals(phoneNumber.getAttribute("value"), "+7 (848) 568-4353");

        WebElement address = webDriver.findElement(By.xpath("//input[@placeholder=\"Введите\"]"));
        fillInputField(address, "London");
        Assert.assertEquals(address.getAttribute("value"), "London");

        WebElement userEmail = webDriver.findElement(By.xpath("//input[@name=\"userEmail\"]"));
        fillInputField(userEmail, "qwertyqwerty");
        Assert.assertEquals(userEmail.getAttribute("value"), "qwertyqwerty");

        Actions actions = new Actions(webDriver);
        WebElement checkbox = webDriver.findElement(By.xpath("//div[@ class=\"checkbox-body form__checkbox\" ]/input"));
        scrollToElement(webDriver.findElement(By.xpath("//div[@class=\"form-submit\"]")));
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"form-submit\"]")));
        actions.moveToElement(checkbox).click(checkbox).build().perform();

        WebElement element = webDriver.findElement(By.xpath("//span[@class=\"input__error text--small\" and(../label[contains(text(), \"почта\")])]"));
        Assert.assertEquals(element.getText(),
                "Введите корректный адрес электронной почты");

        //Кнопка иногда неактивна

//        WebElement sendForm = webDriver.findElement(By.xpath("//button[@type=\"submit\"]"));
//        wait.until(ExpectedConditions.elementToBeClickable(sendForm));
//        sendForm.click();


    }

    @AfterTest
    public void afterTest() {
        webDriver.quit();
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView();", element);
    }

    public void fillInputField(WebElement element, String value) {
        scrollToElement(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
//        element.click();
        element.submit();
        element.clear();
        element.sendKeys(value);
    }
}