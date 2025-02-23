package ru.praktikum_services.qa_scooter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import ru.praktikum_services.qa_scooter.pom.MainPage;


@RunWith(Parameterized.class)
public class MainPageFAQTests {
    private WebDriver driver;

    //порядковый номер вопроса
    private final int indexOfQuestion;

    //ожидаемый текст вопроса
    private final String expectedQuestionText;

    // ожидаемый текст ответа
    private final String expectedAnswerText;

    //конструктор класса
    public MainPageFAQTests(int indexOfQuestion, String expectedQuestionText, String expectedAnswerText) {
        this.indexOfQuestion = indexOfQuestion;
        this.expectedQuestionText = expectedQuestionText;
        this.expectedAnswerText = expectedAnswerText;
    }

    //параметры для проверки вопросов и ответов
    @Parameterized.Parameters(name = "Проверка FAQ, Вопрос {1}")
    public static Object[][] setFAQData() {
        return new Object[][] {
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области." }
        }; //в последнем вопросе на сайте опечатка в слове "живу", на нем тест будет фейлиться
    }
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        //driver = new SafariDriver();
        driver.manage().window().maximize();
    }


    @Test
    public void checkFAQCorrect(){
        MainPage mainPage = new MainPage(driver);

        mainPage.open()
                .clickCookieButton();
        mainPage.clickOnQuestion(indexOfQuestion);
        mainPage.waitForLoadAnswer(indexOfQuestion);
        Assert.assertEquals("Вопрос " + indexOfQuestion + " изменен", expectedQuestionText, mainPage.getQuestionText(indexOfQuestion));
        Assert.assertEquals("В вопросе " + indexOfQuestion + " текст ответа не совпал", expectedAnswerText, mainPage.getAnswerText(indexOfQuestion));
}
    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
