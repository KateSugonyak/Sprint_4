package ru.praktikum_services.qa_scooter.pom;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    //адрес страницы
    private static final String URL = "https://qa-scooter.praktikum-services.ru/";

    //кнопка принятие куки
    private final By cookieButton = By.className("App_CookieButton__3cvqF");

    //кнопка заказа в хэдере
    private final By orderButtonHeader = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]");

    // кнопка заказа на странице
    private final By orderButtonBody = By.xpath(".//div[starts-with(@class, 'Home_FinishButton')]//button[starts-with(@class, 'Button_Button')]");

    // лого Самокат
    private  final  By scooterLogo = By.xpath(".//a[starts-with(@class, 'Header_LogoScooter')]");

    // лого Яндекс
    private  final  By yandexLogo = By.xpath(".//a[starts-with(@class, 'Header_LogoYandex')]");

    // кнопка статус заказа
    private By orderStatusButton = By.xpath(".//button[starts-with(@class, 'Header_Link')]");

    // проле ввода номера заказа
    private By orderInput = By.xpath(".//div[starts-with(@class, 'Header_SearchInput')]//input");

    //кнопка проверки заказа
    private By checkOrderButton = By.xpath(".//div[starts-with(@class, 'Header_SearchInput')]//button");






    //конструктор класса

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // открытие страницы
    public MainPage open() {
        driver.get(URL);
        return this;
    }

    // согласие на куки
    public void clickCookieButton(){
        driver.findElement(cookieButton).click();
    }
    // получение заголовка вопроса о важном по индексу
    public WebElement getQuestionByIndex(int index) {
        String locator = "accordion__heading-" + index;
        return driver.findElement(By.id(locator));
    }

    // получение ответа на вопрос о важном по индексу
    public WebElement getAnswerByIndex(int index) {
        String locator = ".//div[@id = 'accordion__panel-" + index + "']/p";
        return driver.findElement(By.xpath(locator));
    }


    //клик на вопрос о важном
    public void clickOnQuestion(int index){
        getQuestionByIndex(index).click();
    }

    //получение текста вопроса
    public String getQuestionText(int index){
        return getQuestionByIndex(index).getText();
    }

    //ожидание загрузки ответа на вопрос о важном
    public void waitForLoadAnswer(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(getAnswerByIndex(index)));
    }

    //проверка открытия блока с ответом
    public boolean isAnswerDisplayed(int index){
        return getAnswerByIndex(index).isDisplayed();
    }

    //получение текста ответа
    public String getAnswerText(int index){
        return getAnswerByIndex(index).getText();
    }

    //клик по кнопке Заказать в хэдере
    public void clickOrderButtonHeader() {
        driver.findElement(orderButtonHeader).click();
    }

    //клик по кнопке Заказать на странице
    public void clickOrderButtonBody() {
        driver.findElement(orderButtonBody).click();
    }

    //клик по лого Самокат
    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
    }

    //клик по лого Яндекс
    public void clickYandexLogo() {
        driver.findElement(yandexLogo).click();
    }

    //клик пл Статус заказа
    public void clickOrderStatusButton() {
        driver.findElement(orderStatusButton).click();
    }

    //ввод номера заказа
    public void enterOrderNumber(String orderNumber) {
        driver.findElement(orderInput).sendKeys(orderNumber);
    }

    //клик по кнопке проверки заказа
    public void clickCheckOrderButton() {
        driver.findElement(checkOrderButton).click();
    }

    //проверка что отобразилась ошибка поиска заказа

}
