package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

class AuthTest {
    @BeforeEach
    void setup() {
         open("http://localhost:9999/");
    }

    @Test
    void successTest() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(exactText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void notRegisteredUserTest() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test
    void blocedUserTest() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(exactText("Ошибка! Пользователь заблокирован"))
                .shouldBe(visible);
    }

    @Test
    void wrongLoginTest() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test
    void wrongPasswordTest() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(visible);
    }
}