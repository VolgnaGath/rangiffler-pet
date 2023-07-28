package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.rangiffler.page.component.AddPhotoForm;

import static com.codeborne.selenide.Selenide.$;

@Getter
public class FriendsTravels extends BasePage<FriendsTravels> {

    private final SelenideElement friendsTravelsMainContentSection = $("div[id$='friends']");

    private final SelenideElement closePhotoBtn = $("svg[data-testid='CloseIcon']");
    private final SelenideElement zoomInBtn = $("button[aria-label='Zoom in']");
    private final SelenideElement zoomOutBtn = $("button[aria-label='Zoom out']");
    private final SelenideElement backToWorldMapBtn = $("button[aria-label='Back to whole world']");
    private final SelenideElement photoContentSection = $("div ul");
    private final SelenideElement worldMapSection = $(".worldmap__figure-container");


    @Override
    public FriendsTravels checkThatPageLoaded() {
        friendsTravelsMainContentSection.shouldBe(Condition.visible);
        return this;
    }

    public AddPhotoForm openFirstPhoto() {
        photoContentSection.$$("li").first().click();
        return new AddPhotoForm();
    }
    public FriendsTravels checkThatFriendHasPhoto() {
        photoContentSection.$$("li").first().shouldBe(Condition.visible);
        return this;
    }
    public FriendsTravels checkThatFriendHasPhotoThenOpenAndClose() {
        photoContentSection.$$("li").first().shouldBe(Condition.visible).click();
        closePhotoBtn.click();
        return this;
    }
    public FriendsTravels checkThatControllersCanBeUsed() {
        worldMapSection.$$("path").get(6).click();
        backToWorldMapBtn.click();
        zoomInBtn.click();
        zoomOutBtn.click();
        return this;
    }
}
