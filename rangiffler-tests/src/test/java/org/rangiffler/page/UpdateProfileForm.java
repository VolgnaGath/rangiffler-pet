package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.rangiffler.page.component.BaseComponent;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

@Getter
public class UpdateProfileForm extends BaseComponent<UpdateProfileForm> {
    public UpdateProfileForm() {
        super($("main form"));
    }
    private final SelenideElement saveChangesBtn = $("main form button[tabindex='0']");
    private final SelenideElement firstNameInput = $("main form input[name='firstName']");
    private final SelenideElement lastNameInput = $("main form input[name='lastName']");
    private final SelenideElement uploadPhoto = $("input[id='file']");


    @Override
    public UpdateProfileForm checkThatComponentDisplayed() {
        saveChangesBtn.shouldBe(Condition.visible);
        return this;
    }
    public YourTravels updateProfile(String firstname, String lastname) {
        executeJavaScript("document.getElementById(\"file\").classList.remove(\"visually-hidden\")");
        uploadPhoto.uploadFile(new File("/Users/ilyalogutov/IdeaProjects/rangiffler/rangiffler-tests/src/test/resources/testdata/cat.png"));
        firstNameInput.val(firstname);
        lastNameInput.val(lastname);
        saveChangesBtn.click();
        return new YourTravels();
    }
}
