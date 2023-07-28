package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.rangiffler.config.Config;

import static com.codeborne.selenide.Selenide.$;

@Getter
public class StartPage extends BasePage<StartPage>{
    public static final String WELCOME_PAGE_URL = Config.getConfig().getFrontUrl();
    private final SelenideElement loginBtn = $("a[href='/redirect']");
    private final SelenideElement registrationBtn = $("a[href='http://127.0.0.1:9000/register']");
    private final SelenideElement rangifflerLogo = $("img");

    @Override
    public StartPage checkThatPageLoaded() {
        rangifflerLogo.shouldBe(Condition.visible);
        return new StartPage();
    }

    public RegistrationPage goToRegistrationPage() {
        registrationBtn.click();
        return new RegistrationPage();
    }

    public YourTravels goToLoginWithToken() {
        loginBtn.click();
        return new YourTravels();
    }
    public LoginPage goToLoginWithoutToken() {
        loginBtn.click();
        return new LoginPage();
    }
}
