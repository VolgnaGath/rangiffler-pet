package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.rangiffler.config.Config;
import org.rangiffler.page.component.AddPhotoForm;
import org.rangiffler.page.component.Header;

import static com.codeborne.selenide.Selenide.$;

@Getter
public class YourTravels extends BasePage<YourTravels> {
    public static final String MAIN_PAGE_URL = Config.getConfig().getFrontUrl();
    private final Header header = new Header();

    public Header getHeader() {
        return header;
    }

    private final SelenideElement yourTravelsMainContentSection = $("div[id$='main']");
    private final SelenideElement zoomInBtn = $("button[aria-label='Zoom in']");
    private final SelenideElement zoomOutBtn = $("button[aria-label='Zoom out']");
    private final SelenideElement backToWorldMapBtn = $("button[aria-label='Back to whole world']");
    private final SelenideElement photoContentSection = $("div ul");
    private final SelenideElement editPhotoBtn = $("svg[data-testid='EditIcon']");
    private final SelenideElement deletePhotoBtn = $("svg[data-testid='DeleteOutlineIcon']");
    private final SelenideElement closePhotoBtn = $("svg[data-testid='CloseIcon']");
    private final SelenideElement worldMapSection = $(".worldmap__figure-container");


    @Override
    public YourTravels checkThatPageLoaded() {
        yourTravelsMainContentSection.shouldBe(Condition.visible);
        return this;
    }
    

    public AddPhotoForm openFirstPhotoForEdit() {
        photoContentSection.$$("li").first().click();
        editPhotoBtn.click();
        return new AddPhotoForm();
    }
    public YourTravels checkThatUserHasPhoto() {
        photoContentSection.$$("li").first().shouldBe(Condition.visible);
        return this;
    }
    public YourTravels checkThatUserHasPhotoThenOpenAndClose() {
        photoContentSection.$$("li").first().shouldBe(Condition.visible).click();
        editPhotoBtn.click();
        closePhotoBtn.click();
        return this;
    }
    public YourTravels checkThatUserHasPhotoThenDeletePhoto() {
        photoContentSection.$$("li").first().shouldBe(Condition.visible).click();
        editPhotoBtn.click();
        deletePhotoBtn.click();
        return this;
    }
    public YourTravels checkThatControllersCanBeUsed() {
        worldMapSection.$$("path").get(10).click();
        backToWorldMapBtn.click();
        zoomInBtn.click();
        zoomOutBtn.click();
        return this;
    }

}
