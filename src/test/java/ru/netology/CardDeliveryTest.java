package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import org.openqa.selenium.By;

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

    $("form").shouldBe(visible);

    String futureDate = LocalDate.now().plusDays(3)
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    $("[data-test-id='city'] input").shouldBe(visible).setValue("Москва"); 
    $("[data-test-id='date'] input").shouldBe(visible).setValue(futureDate); 
    $("[data-test-id='name'] input").shouldBe(visible).setValue("Иван Иванов"); 
    $("[data-test-id='phone'] input").shouldBe(visible).setValue("+79991234567"); 

    $("[data-test-id='agreement']").shouldBe(visible).click(); 
    $("[name='agreement']").shouldBe(selected); 

    $$("button").findBy(text("Забронировать"))
            .shouldBe(visible, Duration.ofSeconds(10)) 
            .click();

    $("div[data-test-id='notification']")
            .shouldBe(visible, Duration.ofSeconds(20));
}
}
