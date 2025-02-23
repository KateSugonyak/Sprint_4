package ru.praktikum_services.qa_scooter.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    private WebDriver driver;
    //фома заказа
    private final By orderForm = By.xpath(".//div[starts-with(@class, 'Order_Form')]");
    //поле имя
    private final By firstNameInput = By.xpath(".//input[contains(@placeholder, 'Имя')]");
    //поле фамилия
    private final By lastNameInput = By.xpath(".//input[contains(@placeholder, 'Фамилия')]");
    //поле адрес
    private final By addressInput = By.xpath(".//input[contains(@placeholder, 'Адрес')]");

    //поле стация метро
    private final By stationInput = By.className("select-search__input");
    //список станций
    private final By stationList = By.className("select-search__select");
    //элементы списка станций
    private final By stationValues = By.xpath(".//ul[@class='select-search__options']//div[contains(@class,'Order_Text')]");
    //телефона
    private final By phoneInput = By.xpath("//input[contains(@placeholder, 'Телефон')]");
    //кнопка Далее
    private final By nextButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button[starts-with(@class, 'Button_Button')]");
    //дата заказа
    private final By dateInput = By.xpath("//input[contains(@placeholder, 'Когда привезти')]");
    //дата в календаре
    private final By dateInCalendar = By.className("react-datepicker__day--selected");

    //сроки аренды
    private final By rentPeriodDropdown = By.className("Dropdown-control");
    //элементы даты аренды
    private final By rentPeriodItem = By.className("Dropdown-option");
    //доступные цвета
    private final By colorLabels = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]//label");

    //комментарий
    private final By commentInput = By.xpath("//input[contains(@placeholder, 'Комментарий')]");
    //кнопки Заказать
    private final By orderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[text() = 'Заказать']");
    //popup подтверждения заказа
    private final By orderPopup = By.xpath(".//div[starts-with(@class, 'Order_Modal_')]");
    //заголовок popup
    private final By orderPopupTitle = By.xpath(".//div[starts-with(@class, 'Order_Modal')]/div[starts-with(@class, 'Order_ModalHeader')]");
    //кнопки Да
    private final By confirmOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//button[text() = 'Да']");
    //сообщение в popup успешного заказа
  //  private final By orderSuccessMessage = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//div[(starts-with(@class,'Order_Text'))]");


    //ошибка имени
    private final By firstNameError = By.xpath(".//div[text()='Введите корректное имя']");


    //ошибка фамилии
    private final By lastNameError = By.xpath(".//div[text()='Введите корректную фамилию']");

    //ошибка адреса
    private final By addressError = By.xpath(".//div[text()='Введите корректный адрес']");

    //ошибка станции
    private final By stationError = By.xpath(".//div[starts-with(@class, 'Order_MetroError')]");

    //ошибка телефона
    private final By phoneError = By.xpath(".//div[text()='Введите корректный номер']");


    // конструктор класса
    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }
    // ожидание загрузки формы заказа
    public void waitForLoadOrderForm() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(driver.findElement(orderForm)));
    }

    //ожидание загрузки попапа
    public void waitForLoadPopup() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(driver.findElement(orderPopup)));
    }
    //ожидание загрузки элемента
    private void waitForElementLoad(By element) {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(driver.findElement(element)));
    }


    //выбор элемента в выпадающем списке
    private void selectItemFromDropdown(By dropdownItems, String selectingItem) {
        List<WebElement> itemsFiltered = driver.findElements(dropdownItems);
        for (WebElement element : itemsFiltered) {
            if (element.getText().equals(selectingItem)) {
                element.click();
                break;
            }
        }
    }

    //ввод имени
    public void setFirstName (String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    //ввод фамилии
    public void setLastName (String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }
    //ввод адреса
    public void setAddress (String address) {
        driver.findElement(addressInput).sendKeys(address);
    }
    //выбор станции метро
    public void setStation (String station){
        driver.findElement(stationInput).sendKeys(station);
        this.waitForElementLoad(stationList);
        this.selectItemFromDropdown(stationValues, station);
    }
    //ввод телефона
    public void setPhone (String phoneNumber) {
        driver.findElement(phoneInput).sendKeys(phoneNumber);
    }
    //клик по кнопке Далее
    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    //выбор даты в календаре
    public void clickDateInCalendar() {
        driver.findElement(dateInCalendar).click();
    }
    //ввод даты
    public void setDate(String date) {
        driver.findElement(dateInput).sendKeys(date);
        this.waitForElementLoad(dateInCalendar);
        this.clickDateInCalendar();
    }

    //выбор срока аренды
    public void setRentPeriod(String rentPeriod) {
        driver.findElement(rentPeriodDropdown).click();
        this.selectItemFromDropdown(rentPeriodItem, rentPeriod);
    }

    //выбор цвета
    public void setColor(String color) {
        this.selectItemFromDropdown(colorLabels, color);
    }

    //ввод комментария
    public void setComment(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    //клик по кнопке Заказать для подтверждения заказа
    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    //подтверждение заказа
    public void clickConfirmOrderButton() {
        driver.findElement(confirmOrderButton).click();
    }

    //получение подтверждающего сообщения
    public String getOrderSuccessMessage() {
        return driver.findElement(orderPopupTitle).getText();
    }

    //проверка ошибки имени
    public boolean checkFirstNameError() {
        return driver.findElement(firstNameError).isDisplayed();
    }

    //проверка ошибки имени
    public boolean isFirstNameErrorVisible() {
        return driver.findElement(firstNameError).isDisplayed();
    }

    //проверка ошибки фамилии
    public boolean isLastNameErrorVisible() {
        return driver.findElement(lastNameError).isDisplayed();
    }

    //проверка ошибки адреса
    public boolean isAddressErrorVisible() {
        return driver.findElement(addressError).isDisplayed();
    }

    //проверка ошибки станции метро
    public boolean isStationErrorVisible() {
        return driver.findElement(stationError).isDisplayed();
    }

    //проверка ошибки телефона
    public boolean isPhoneErrorVisible() {
        return driver.findElement(phoneError).isDisplayed();
    }
}
