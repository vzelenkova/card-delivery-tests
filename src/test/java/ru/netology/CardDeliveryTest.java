package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
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
        $("#city").setValue("Москва"); // Поле города
        $("#date").setValue(futureDate); // Поле даты
        $("#name").setValue("Иван Иванов"); // Поле имени
        $("#phone").setValue("+79991234567"); // Поле телефона

        // Отметить чекбокс согласия
        $("[name='agreement']").click();

        // Нажать кнопку "Забронировать"
        $("button[type='button']").filter(text("Забронировать")).click();

        // Проверка успешной отправки формы
        $("div[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(20));
    }
}
