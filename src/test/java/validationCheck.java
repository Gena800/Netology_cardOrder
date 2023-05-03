import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class validationCheck {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {

        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void positiveResult() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    void negativeResultInName() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Vitalya");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__sub")).getText().trim();
        assertEquals(expected, actual);

    }
    @Test
    void negativeResultInPhone() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("79876543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        assertEquals(expected, actual);

    }
    @Test
    void negativeResultInCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79876543210");

        driver.findElement(By.className("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__text")).getText().trim();
        assertEquals(expected, actual);

    }
}
