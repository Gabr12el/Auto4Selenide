package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrderCardTest {
    @BeforeEach
    public void openForm() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldValid() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);//"17.06.2022"
        $("[data-test-id='name'] input").val("Упрямый Габрик");
        $("[data-test-id='phone'] input").val("+79123456789");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));//задержка
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldInValidCity() {
        $x("//input[@placeholder=\"Город\"]").val("Мухосранск");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Упрямый Габрик");
        $("[data-test-id='phone'] input").val("+79123456789");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=city] .input__sub").should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNoName() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").val("+79123456789");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInValidName1() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Gabrik Upryamiy");
        $("[data-test-id='phone'] input").val("+79123456789");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldInValidName2() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Габрик");
        $("[data-test-id='phone'] input").val("+79123456789");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldInValidName3() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Ёжиков Габрик");
        $("[data-test-id='phone'] input").val("+79123456789");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));//задержка
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldInvalidDate() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Габрик Упрямый");
        $("[data-test-id='phone'] input").val("+79123456789");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=date] .input__sub").should(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldInvalidPhone1() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Габрик Упрямый");
        $("[data-test-id='phone'] input").val("+791234567");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldInvalidPhone2() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Габрик Упрямый");
        $("[data-test-id='phone'] input").val("+791234567891");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNoPhone() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Габрик Упрямый");
        $("[data-test-id='phone'] input").val("");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNoAgre() {
        $x("//input[@placeholder=\"Город\"]").val("Самара");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Габрик Упрямый");
        $("[data-test-id='phone'] input").val("+79123456789");
        $x("//*[contains(text(),'Забронировать')]").click();
        //  $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=agreement] .checkbox__text").should(exactText("Я соглашаюсь с условиями обработки и использования  моих персональных данных"));
    }
}