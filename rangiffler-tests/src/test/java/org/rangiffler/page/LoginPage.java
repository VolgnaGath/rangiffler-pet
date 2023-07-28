package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.rangiffler.config.Config;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class LoginPage extends BasePage<LoginPage>{
    public static final String Login_PAGE_URL = Config.getConfig().getAuthUrl() + "/login";
    private final SelenideElement usernameInput = $(".form__input[name='username']");
    private final SelenideElement passwordInput = $(".form__input[name='password']");
    private final SelenideElement signInBtn = $(".form__submit[type='submit']");
    private final SelenideElement registrationBtn = $("a[href='/register']");
    private final SelenideElement formErrorMessage = $(".form__error");


    @Override
    public LoginPage checkThatPageLoaded() {
        usernameInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
        signInBtn.shouldBe(visible);
        return this;
    }

    public YourTravels doLogin(String username, String password) {
        usernameInput.val(username);
        passwordInput.val(password);
        signInBtn.click();
        return new YourTravels();
    }
    public LoginPage doLoginWithError(String username, String password) {
        usernameInput.val(username);
        passwordInput.val(password);
        signInBtn.click();
        return new LoginPage();
    }
    public LoginPage checkErrorMessage(String expectedMessage) {
        formErrorMessage.shouldHave(text(expectedMessage));
        return new LoginPage();
    }
    public RegistrationPage goToRegistrationPage() {
        registrationBtn.click();
        return new RegistrationPage();
    }
}
