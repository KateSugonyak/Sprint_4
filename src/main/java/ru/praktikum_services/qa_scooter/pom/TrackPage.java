package ru.praktikum_services.qa_scooter.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TrackPage {

    private WebDriver driver;


    //картинка "такого заказа нет"
    private By notFoundMessage = By.xpath(".//img[@alt='Not found']");

    public TrackPage(WebDriver driver) {
        this.driver = driver;
    }

    //проверка что картинка ошибки поиска отобразилась
    public boolean checkNotFoundExists() {
        return !driver.findElements(notFoundMessage).isEmpty();
    }

}
