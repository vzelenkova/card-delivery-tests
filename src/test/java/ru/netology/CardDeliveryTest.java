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

$("[data-test-id='city'] input").setValue("Москва");

$("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
$("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
$("[data-test-id='date'] input").setValue(getFutureDate(4)); 

$("[data-test-id='name'] input").setValue("Иван Иванов");
$("[data-test-id='phone'] input").setValue("+79998887766");
$("[data-test-id='agreement']").click();
$("button.button").click();

$("[data-test-id='notification']").should(appear, Duration.ofSeconds(15));
    }
}
