import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/*
1) Зайти на страницу help.salesforce.com
2) В инпут на странице написать outloook for salesforce
3) Нажать на иконку лупы
4) На следующей странице в блоке Content Type выбрать Quick Starts
5) Кликнуть на первый результат на странице
6) На следующей странице проверить, что блок Related Resources присутствует
7) Кликнуть на первый результат в этом блоке
8) Проверить, что странице не показывает ошибку 404

Написать на java + selenium.*/

public class Automation {
    public static ChromeDriver driver;
    public static WebDriverWait wait;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

//1) Зайти на страницу help.salesforce.com
        driver.get("https://help.salesforce.com");

//2) В инпут на странице написать outloook for salesforce  // в слове "outloook" опечатка, поэтому не удавалось сразу найти Quick Starts
        findElement(By.xpath("//input[@placeholder='Search Knowledge articles, best practices, and more...']")).sendKeys("outlook for salesforce");

//3) Нажать на иконку лупы
        findElement(By.cssSelector(".tds-search-input__icon")).click();
        waitingForElement(By.xpath("//a[contains(text(),'Help us Improve your results in 1 minute or less.')]"));

//4) На следующей странице в блоке Content Type выбрать Quick Starts
        findElement(By.xpath("//div[@aria-label='Expand Content Type facet']")).click();
        waitingForElement(By.xpath("//span[contains(text(),'Quick Starts')]"));
        findElement(By.xpath("//span[contains(text(),'Quick Starts')]")).click();

//5) Кликнуть на первый результат на странице
        waitingForElement(By.xpath("//a[contains(text(),'Getting Started with Sales Cloud Einstein')]"));
        findElement(By.xpath("//a[@class='CoveoResultLink'][1]")).click();

//6) На следующей странице проверить, что блок Related Resources присутствует
        waitingForElement(By.cssSelector(".tdx-button-brand.contact-us-button.hide-in-console"));
//        Thread.sleep(2000);
        if (findElement(By.xpath("//div[@id='relatedresources_articleview']")).isDisplayed()) {
            System.out.println("Related Resources is displayed");
        } else {
            System.out.println("Related Resources is not displayed");
        }

//7) Кликнуть на первый результат в этом блоке
        findElement(By.linkText("Salesforce for Outlook")).click();

//8) Проверить, что странице не показывает ошибку 404
//        driver.get("https://help.salesforce.com/articleView?id=outldsadasookcrm_sfo_about.htm&type=5"); // для вызова заглушки 404
        List<WebElement> dynamicElements = driver.findElements(By.xpath("//div/error-block"));
        if (dynamicElements.size() != 0) {
            String errors = findElement(By.xpath("//div/error-block")).getAttribute("error-code");
            System.out.println("The page shows error: " + errors);
        } else {
            System.out.println("The page doesn't show Error 404");
        }

        driver.quit();
    }

    public static WebElement findElement(By element) {
        return driver.findElement(element);
    }

    public static WebElement waitingForElement(By element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
