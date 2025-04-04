package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "http://localhost:9999";
        Configuration.holdBrowserOpen = false;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true"));
    }

   @Test
void shouldSubmitFormSuccessfully() {
    // Открыть страницу
    open("/");

    // Ожидание загрузки формы
    $("form").shouldBe(visible);

    // Генерация будущей даты
    String futureDate = LocalDate.now().plusDays(3)
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    // Заполнение полей формы
    $("[data-test-id='city'] input").shouldBe(visible).setValue("Москва"); // Поле города
    $("[data-test-id='date'] input").shouldBe(visible).setValue(futureDate); // Поле даты
    $("[data-test-id='name'] input").shouldBe(visible).setValue("Иван Иванов"); // Поле имени
    $("[data-test-id='phone'] input").shouldBe(visible).setValue("+79991234567"); // Поле телефона

    // Отметить чекбокс согласия
    $("[data-test-id='agreement']").shouldBe(visible).click(); // Клик по label
    $("[name='agreement']").shouldBe(selected); // Проверка, что чекбокс отмечен

    // Нажать кнопку "Забронировать"
    $("[class*='button_text']:contains('Забронировать')").parent()
            .shouldBe(visible) // Явное ожидание видимости
            .click();

    // Проверка успешной отправки формы
    $("div[data-test-id='notification']")
            .shouldBe(visible, Duration.ofSeconds(20));
}
}
