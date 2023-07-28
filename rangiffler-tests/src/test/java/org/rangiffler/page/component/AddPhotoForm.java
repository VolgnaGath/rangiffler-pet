package org.rangiffler.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.rangiffler.page.YourTravels;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

@Getter
public class AddPhotoForm extends BaseComponent<AddPhotoForm> {

    public AddPhotoForm() {
        super($("main form"));
    }

    private final SelenideElement addPhotoBtn = $("input[id]");
    private final SelenideElement countrySelector = $("form div[tabindex='0']");
    private final SelenideElement uploadPhoto = $("input[id='file']");
    SelenideElement countryMenu;
    private final SelenideElement descriptionInput = $("form textarea[aria-invalid=false]");
    private final SelenideElement savePhotoBtn = $("form button[type='submit']");

    public SelenideElement getCountryMenu(String countryCode) {
        countryMenu = $("div ul li[data-value='" + countryCode + "']");
        return countryMenu;
    }

    public YourTravels addPhoto(String countryCode, String description) {
        executeJavaScript("document.getElementById(\"file\").classList.remove(\"visually-hidden\")");
        uploadPhoto.uploadFile(new File("/Users/ilyalogutov/IdeaProjects/rangiffler/rangiffler-tests/src/test/resources/testdata/cat.png"));
        countrySelector.click();
        getCountryMenu(countryCode).click();
        descriptionInput.val(description);
        savePhotoBtn.click();
        return new YourTravels();
    }

    public YourTravels editFirstPhoto(String countryCode, String description) {
        countrySelector.click();
        getCountryMenu(countryCode).click();
        descriptionInput.val(description);
        savePhotoBtn.click();
        return new YourTravels();
    }


    @Override
    public AddPhotoForm checkThatComponentDisplayed() {
        addPhotoBtn.shouldBe(Condition.visible);
        return this;
    }
}
