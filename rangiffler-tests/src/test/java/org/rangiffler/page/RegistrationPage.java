package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.apache.kafka.common.security.auth.Login;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class RegistrationPage extends BasePage<RegistrationPage>{
    public static final String URL = "/register";
    private final SelenideElement header = $(".form__paragraph");
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement passwordSubmitInput = $("#passwordSubmit");
    private final SelenideElement signUpBtn = $("button[type='submit']");
    private final SelenideElement formError = $(".form__error");
    private final SelenideElement signInBtn = $("a[href='http://127.0.0.1:3001/redirect']");


    @Override
    public RegistrationPage checkThatPageLoaded() {
        header.shouldHave(text("Already have an account? "));
        return this;
    }
    public RegistrationPage fillRegistrationForm(String username, String password, String passwordSubmit) {
        usernameInput.val(username);
        passwordInput.val(password);
        passwordSubmitInput.val(passwordSubmit);
        signUpBtn.click();
        return this;
    }

    public RegistrationPage checkErrorMessage(String expectedMessage) {
        formError.shouldHave(text(expectedMessage));
        return this;
    }
    public LoginPage signInAfterRegistrationBtn() {
        signInBtn.click();
        return new LoginPage();
    }
}
