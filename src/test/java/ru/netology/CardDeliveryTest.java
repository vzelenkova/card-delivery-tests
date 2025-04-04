package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "http://localhost:9999";
        Configuration.holdBrowserOpen = false;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true"));
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        open("/");
        SelenideElement form = $("[data-test-id='form']");

        String futureDate = LocalDate.now().plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys("\b\b\b\b\b\b\b\b\b\b" + futureDate);
        form.$("[data-test-id='name'] input").setValue("Иван Иванов");
        form.$("[data-test-id='phone'] input").setValue("+79991234567");
        form.$("[data-test-id='agreement']").click();
        form.$("button[type='button']").click();

        $(" [data-test-id='notification'] ").should(appear, Duration.ofSeconds(15));
    }
}