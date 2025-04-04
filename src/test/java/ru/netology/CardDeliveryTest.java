package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "http://localhost:9999";
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        open("/");

        // Формируем дату на 3 дня вперёд
        String futureDate = LocalDate.now().plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        // Заполняем поля формы
        $("[data-test-id='city'] input").shouldBe(visible).setValue("Москва");

        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.shouldBe(visible).doubleClick().sendKeys(Keys.BACK_SPACE);
        dateInput.setValue(futureDate);

        $("[data-test-id='name'] input").shouldBe(visible).setValue("Иван Иванов");
        $("[data-test-id='phone'] input").shouldBe(visible).setValue("+79991234567");

        $("[data-test-id='agreement']").shouldBe(visible).click();
        $("[name='agreement']").shouldBe(selected);

        // Отправляем форму
        $$("button").findBy(text("Забронировать"))
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        // Проверка результата
        $("[data-test-id='notification']")
                .shouldBe(visible, Duration.ofSeconds(20))
                .shouldHave(text("Встреча успешно запланирована на " + futureDate));
    }
}
