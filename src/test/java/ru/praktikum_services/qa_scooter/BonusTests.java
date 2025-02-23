package ru.praktikum_services.qa_scooter;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum_services.qa_scooter.pom.MainPage;
import ru.praktikum_services.qa_scooter.pom.OrderPage;
import ru.praktikum_services.qa_scooter.pom.TrackPage;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertTrue;

public class BonusTests {
    private WebDriver driver;

    private final String yaUrl = "https://ya.ru/";
    private final String scooterUrl = "https://qa-scooter.praktikum-services.ru/";
    private static final String NOT_EXISTING_ORDER = UUID.randomUUID().toString();


    @Before
    public void setUp() {
        driver = new ChromeDriver();
        //driver = new SafariDriver();
        driver.manage().window().maximize();
    }

        @Test
        public void checkScooterLogoUrl(){
            MainPage mainPage = new MainPage(driver);
            mainPage.open()
                    .clickCookieButton();
            mainPage.clickScooterLogo();
            Assert.assertEquals("Лого Самокат завело не туда",
                    scooterUrl, driver.getCurrentUrl());
        }

        @Test
        public void checkYandexLogoUrl() {
            MainPage mainPage = new MainPage(driver);
            mainPage.open()
                    .clickCookieButton();
            String originalWindow = driver.getWindowHandle();
            // переходим по ссылке на лого Яндекс
            mainPage.clickYandexLogo();
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.numberOfWindowsToBe(2));

            // это решение с переключением на открывшийся таб работает с safari, но не работает с chrome
            //не успела разобраться почему
            //но тест все равно не проходит, тк главная Яндекса сейчас ya.ru,
            // а не yandex.ru, перенаправляющий на dzen.ru
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            // проеряем url в новом окне
            Assert.assertEquals("Лого Яндекс ведет не на главную Яндекса",
                    yaUrl, driver.getCurrentUrl());
        }

        @Test
        public void checkOrderFormErrors() {
            MainPage mainPage = new MainPage(driver);
            OrderPage orderPage = new OrderPage(driver);
            mainPage.open()
                    .clickCookieButton();
            mainPage.clickOrderButtonHeader();
            orderPage.waitForLoadOrderForm();
            orderPage.clickNextButton();
            List<Boolean> values = Arrays.asList(orderPage.isFirstNameErrorVisible(),
                                                orderPage.isLastNameErrorVisible(),
                                                orderPage.isAddressErrorVisible(), //на этом поле баг, тест не пройдет
                                                orderPage.isStationErrorVisible(),
                                                orderPage.isPhoneErrorVisible());
            MatcherAssert.assertThat("Не все ошибки видны", values, everyItem(is(true)));
        }

        @Test
        public void checkBadOrderNumberStatus() {
            MainPage mainPage = new MainPage(driver);
            TrackPage trackPage = new TrackPage(driver);

            mainPage.open();
            mainPage.clickCookieButton();

            mainPage.clickOrderStatusButton();
            mainPage.enterOrderNumber(NOT_EXISTING_ORDER);
            mainPage.clickCheckOrderButton();

            assertTrue(trackPage.checkNotFoundExists());
        }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    }

