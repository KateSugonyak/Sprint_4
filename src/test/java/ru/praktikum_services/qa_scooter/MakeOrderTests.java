package ru.praktikum_services.qa_scooter;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import ru.praktikum_services.qa_scooter.pom.MainPage;
import ru.praktikum_services.qa_scooter.pom.OrderPage;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(Parameterized.class)

public class MakeOrderTests {
    private WebDriver driver;

    //данные для заполнения
    private final String firstName, lastName, address, station, phone, date, rentPeriod, color, comment;
    private final boolean useOrderButtonHeader;
    //сообщение при успешном заказе
    private final String successOrderMessage = "Заказ оформлен";

    public MakeOrderTests(String firstName, String lastName, String address, String station, String phone, String date, String rentPeriod, String color, String comment, boolean useOrderButtonHeader) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.rentPeriod = rentPeriod;
        this.color = color;
        this.comment = comment;
        this.useOrderButtonHeader = useOrderButtonHeader;
    }

    //паматеры для заказа с выбором кнопки Заказать
    @Parameterized.Parameters(name = "Заказ самоката. Заказчик: {0} {1}. Верхняя кнопка заказа: {9} (true - в header, false - в body")
    public static Object[][] setPositiveOrderData() {
        return new Object[][] {
                {"Саша", "Иванова", "Москва, Ленинградский пр., д. 10", "Белорусская", "11111111111", "23.02.25", "двое суток", "чёрный жемчуг", "не звонить", true},
                {"Петя ", "Васечкин", "Москва, Ленинградский пр., д. 60", "Аэропорт", "22222222222", "24.02.2025", "трое суток", "серая безысходность", "не писать", true},
                {"Валя", "Сидорова", "Москва, Ленинградский пр., д. 33к5", "Динамо", "33333333333", "25.02.25", "сутки", "чёрный жемчуг", "комментарий", false},
                {"Константин ", "Константинопольский", "Москва, Ленинградский пр., д. 74к1", "Сокол", "44444444444", "26.02.2025", "семеро суток", "серая безысходность", "без комментариев", false}
        };
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver(); // т.к. для хрома ошибка, тест до конца не пройдет
        //driver = new SafariDriver(); // в сафари проходит
        driver.manage().window().maximize();

    }

    @Test
    public void makeOrderPositive(){
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);
        mainPage.open()
                .clickCookieButton();

    if (useOrderButtonHeader) {
        mainPage.clickOrderButtonHeader();
    } else {
        mainPage.clickOrderButtonBody();
    }
    orderPage.waitForLoadOrderForm();

    orderPage.setFirstName(firstName);
    orderPage.setLastName(lastName);
    orderPage.setAddress(address);
    orderPage.setStation(station);
    orderPage.setPhone(phone);

    orderPage.clickNextButton();
    orderPage.waitForLoadOrderForm();

    orderPage.setDate(date);
    orderPage.setRentPeriod(rentPeriod);
    orderPage.setColor(color);
    orderPage.setComment(comment);

    orderPage.clickOrderButton();
    orderPage.waitForLoadPopup();
    orderPage.clickConfirmOrderButton();
    orderPage.waitForLoadPopup();

    MatcherAssert.assertThat("Не удалось оформить заказ",
            orderPage.getOrderSuccessMessage(), containsString(this.successOrderMessage));

    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
