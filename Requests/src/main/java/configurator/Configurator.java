package configurator;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Класс конфигуратор WebDriver
 */
public final class Configurator {
    private static WebDriver driver;
    private static final int TIME_DEFAULT = 5;

    private Configurator(){}

    /**
     * Установка WebDriverManager, масштаба окна, задержки по времени
     */
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIME_DEFAULT, TimeUnit.SECONDS);
    }

    /**
     * Метод изменения времени задержки
     * @param time время
     * @param timeUnit единицы измерения
     */
    public static void setTimeoutTime(final int time, final TimeUnit timeUnit) {
        getDriver().manage().timeouts().implicitlyWait(time, timeUnit);
    }

    /**
     * @return driver
     */
    public static WebDriver getDriver() {
        return driver;
    }
}
