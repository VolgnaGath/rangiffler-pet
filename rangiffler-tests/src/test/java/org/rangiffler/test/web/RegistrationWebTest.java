package org.rangiffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.StartPage;

public class RegistrationWebTest extends BaseWebTest {
    @Test
    @AllureId("118")
    @Tag("webTest")
    @DisplayName("RegistrationForm: Появилось сообщение об ошибке при вводе неверных данных в поля регистрации")
    public void errorMessageShouldBeVisibleInCaseThatPasswordsAreDifferent() {
        Selenide.open(StartPage.WELCOME_PAGE_URL, StartPage.class)
                .goToRegistrationPage()
                .fillRegistrationForm("wdfsdasfs", "123", "12345")
                .checkErrorMessage("Passwords should be equal");
    }

    @GenerateUser()
    @Test
    @AllureId("119")
    @Tag("webTest")
    @DisplayName("RegistrationForm: Появилось сообщение об ошибке при вводе данных существующего пользователя")
    public void errorMessageShouldBeVisibleInCaseThatUsernameNotUniq(UserJson userJson) {

        Selenide.open(StartPage.WELCOME_PAGE_URL, StartPage.class)
                .goToRegistrationPage()
                .fillRegistrationForm(userJson.getUsername(), "12345", "12345")
                .checkErrorMessage("Username `" + userJson.getUsername() + "` already exists");
    }

    @Test
    @AllureId("120")
    @Tag("webTest")
    @DisplayName("RegistrationForm: Появилось сообщение об ошибке при вводе недопустимых значений в поля password")
    public void errorMessageShouldBeVisibleInCaseThatPasswordsLessThan3Symbols() {
        Selenide.open(StartPage.WELCOME_PAGE_URL, StartPage.class)
                .goToRegistrationPage()
                .checkThatPageLoaded()
                .fillRegistrationForm("wdfsdadfdaasfs", "1", "1")
                .checkErrorMessage("Allowed password length should be from 3 to 12 characters");
    }

    @Test
    @AllureId("121")
    @Tag("webTest")
    @DisplayName("RegistrationForm: Появилось сообщение об ошибке при вводе недопустимого значения в поле username")
    public void errorMessageShouldBeVisibleInCaseThatUsernameLessThan3Symbols() {
        Selenide.open(StartPage.WELCOME_PAGE_URL, StartPage.class)
                .goToRegistrationPage()
                .fillRegistrationForm("g", "12345", "12345")
                .checkErrorMessage("Allowed username length should be from 3 to 50 characters");
    }
}
