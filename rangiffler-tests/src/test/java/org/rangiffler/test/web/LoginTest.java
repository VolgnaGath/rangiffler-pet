package org.rangiffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.StartPage;
import org.rangiffler.utils.DataUtils;

import javax.xml.crypto.Data;

public class LoginTest extends BaseWebTest{

    @GenerateUser()
    @Test
    @AllureId("122")
    @Tag("webTest")
    @DisplayName("LoginPage: успешный вход в аккаунт")
    void doLogin(UserJson userJson) {
        Selenide.open(StartPage.WELCOME_PAGE_URL, StartPage.class)
                .goToLoginWithoutToken()
                .doLogin(userJson.getUsername(), userJson.getPassword())
                .checkThatPageLoaded();
    }
    @GenerateUser()
    @Test
    @AllureId("123")
    @Tag("webTest")
    @DisplayName("LoginPage: появилось сообщение о неверных данных пользователя при некорректном значении password")
    void checkErrorMessageWithIncorrectPass(UserJson userJson) {
        Selenide.open(StartPage.WELCOME_PAGE_URL, StartPage.class)
                .goToLoginWithoutToken()
                .doLoginWithError(userJson.getUsername(), "1")
                .checkErrorMessage("Неверные учетные данные пользователя");

    }

    @Test
    @AllureId("124")
    @Tag("webTest")
    @DisplayName("LoginPage: появилось сообщение о неверных данных пользователя при неверном заполнении поля username")
    void checkErrorMessageWithIncorrectUsername() {
        Selenide.open(StartPage.WELCOME_PAGE_URL, StartPage.class)
                .goToLoginWithoutToken()
                .doLoginWithError("q", "12345")
                .checkErrorMessage("Неверные учетные данные пользователя");
    }
    @Test
    @AllureId("125")
    @Tag("webTest")
    @DisplayName("LoginPage: успешная регистрации и логин")
    void createUserAndLogin() {
        String username = DataUtils.generateRandomUsername();
        String pass = DataUtils.generateRandomPassword();
        Selenide.open(StartPage.WELCOME_PAGE_URL, StartPage.class)
                .goToRegistrationPage()
                .fillRegistrationForm(username, pass, pass)
                .signInAfterRegistrationBtn()
                .doLogin(username, pass)
                .checkThatPageLoaded();
    }
}
