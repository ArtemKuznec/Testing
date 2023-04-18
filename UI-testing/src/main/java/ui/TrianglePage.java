package ui;

import configurator.Configurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * PageObject для Triangle страницы
 */
public class TrianglePage {

    /**
     * Конструктор
     */
    public TrianglePage() {
        Configurator.getDriver().get("https://playground.learnqa.ru/puzzle/triangle");
        PageFactory.initElements(Configurator.getDriver(), this);
    }

    /**
     * Определение кнопки "Я сдаюсь"
     */
    @FindBy(xpath = "//button[@id='show_answ']")
    private WebElement showAnswersButton;

    /**
     * Определение ссылки на ответы
     */
    @FindBy(xpath = "//a[text()='Ссылка на ответы']")
    private WebElement answersLink;

    /**
     * Определение кнопки "Спрятать ответы"
     */
    @FindBy(xpath = "//button[@id='hide_answ']")
    private WebElement hideAnswersButton;

    /**
     * Геттер кнопки "Я сдаюсь"
     */
    public WebElement getShowAnswersButton() {
        return showAnswersButton;
    }

    /**
     * Геттер ссылки с ответами
     */
    public WebElement getAnswersLink() {
        return answersLink;
    }

    /**
     * Геттер кнопки "Спрятать ответы"
     */
    public WebElement getHideAnswersButton() {
        return hideAnswersButton;
    }

    /**
     * Проверка на существование кнопки по указанному xpath пути
     * @return true - в случае обнаружения элемента;
     *         false - в случае отсутсвия элемента ;
     */
    public boolean isShowAnswersButtonExist() {
        try {
            By by = new By.ByXPath("//button[@id='show_answ']");
        } catch (org.openqa.selenium.WebDriverException ignored) {
            return false;
        }
        return true;
    }
}
