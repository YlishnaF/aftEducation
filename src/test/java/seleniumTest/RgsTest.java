package seleniumTest;


import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class RgsTest extends BaseClass{

    @Test
    @DisplayName("test insurance request form")
    @RepeatedTest(name="{displayName} : repetition {currentRepetition} of {totalRepetitions}", value = 3)
    public void test() throws InterruptedException {
        System.out.println(this.getClass().getName());

        WebElement cookie = webDriver.findElement(By.xpath("//button[@class= \"btn--text\"]"));
        cookie.click();

        WebElement company = webDriver.findElement(By.xpath("//a[@href=\"/for-companies\"]"));
        company.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class = 'item text--basic current']")));
        Thread.sleep(2000);

        WebElement health = webDriver.findElement(By.xpath("//span[@class=\"padding\" and contains(text(), \"Здоровье\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(health));
        health.click();

        WebElement insurance = webDriver.findElement(By.xpath("//a[contains(text(), 'Добровольное медицинское страхование')]"));
        wait.until(ExpectedConditions.elementToBeClickable(insurance));
        insurance.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class = \"title word-breaking title--h2\"]")));

        WebElement titleInsurance = webDriver.findElement(By.xpath("//h1[@class = \"title word-breaking title--h2\"]"));
        Assertions.assertEquals(titleInsurance.getText(), "Добровольное медицинское страхование");

        WebElement sendBtn = webDriver.findElement(By.xpath("//button/span[contains(text(), \"Отправить заявку\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(sendBtn))
                .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"form-submit\"]")));

        WebElement reCall = webDriver.findElement(By.xpath("//h2[@class=\"section-basic__title title--h2 word-breaking title--h2\" and (../div[@id=\"callback\"])]"));

        Assertions.assertEquals(reCall.getText(), "Оперативно перезвоним\nдля оформления полиса");
        scrollToElement(webDriver.findElement(By.xpath("//div[@class=\"form-submit\"]")));

        WebElement name = webDriver.findElement(By.xpath("//input[@name=\"userName\"]"));
        fillInputField(name, "Петров Петр Петрович");
        Assertions.assertEquals(name.getAttribute("value"), "Петров Петр Петрович");

        WebElement phoneNumber = webDriver.findElement(By.xpath("//input[@name=\"userTel\"]"));
        fillInputField(phoneNumber, "8485684353");
        Assertions.assertEquals(phoneNumber.getAttribute("value"), "+7 (848) 568-4353");

        WebElement address = webDriver.findElement(By.xpath("//input[@placeholder=\"Введите\"]"));
        fillInputField(address, "London");
        Assertions.assertEquals(address.getAttribute("value"), "London");

        WebElement userEmail = webDriver.findElement(By.xpath("//input[@name=\"userEmail\"]"));
        fillInputField(userEmail, "qwertyqwerty");
        Assertions.assertEquals(userEmail.getAttribute("value"), "qwertyqwerty");

        Actions actions = new Actions(webDriver);
        WebElement checkbox = webDriver.findElement(By.xpath("//div[@ class=\"checkbox-body form__checkbox\" ]/input"));
        scrollToElement(webDriver.findElement(By.xpath("//div[@class=\"form-submit\"]")));
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"form-submit\"]")));
        actions.moveToElement(checkbox).click(checkbox).build().perform();

        WebElement sendForm = webDriver.findElement(By.xpath("//button[@type=\"submit\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(sendForm));
        sendForm.click();

        WebElement element = webDriver.findElement(By.xpath("//span[@class=\"input__error text--small\" and(../label[contains(text(), \"почта\")])]"));
        Assertions.assertEquals(element.getText(),
                "Введите корректный адрес электронной почты");

        Assertions.assertAll("message",
                ()-> Assertions.assertEquals(userEmail.getAttribute("value"), "qwertyqwerty"),
                ()->Assertions.assertEquals(address.getAttribute("value"), "London"),
                ()->Assertions.assertEquals(phoneNumber.getAttribute("value"), "+7 (848) 568-4353"));

    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView();", element);
    }

    public void fillInputField(WebElement element, String value) {
        scrollToElement(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        Actions actions = new Actions(webDriver);
        actions.click(element);
//        element.click();
//        element.submit();
        element.clear();
        element.sendKeys(value);
    }

}