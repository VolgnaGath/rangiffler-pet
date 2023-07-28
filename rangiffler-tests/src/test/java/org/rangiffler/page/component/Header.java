package org.rangiffler.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.rangiffler.page.*;

import static com.codeborne.selenide.Selenide.$;

@Getter
public class Header extends BaseComponent<Header> {

    public Header() {
        super($(".MuiPaper-root"));
    }
    private final SelenideElement addPhotoBtn = $("button[tabindex='0'] span svg");
    private final SelenideElement userInfoButton = $("img[class]");
    private final SelenideElement visitedCountriesArea = $("Your visited countries");
    private final SelenideElement yourPhotosArea = $("Your photos");
    private final SelenideElement yourFriendsArea = $("Your friends");
    private final SelenideElement logoutBtn = $("svg[data-testid='LogoutIcon']");
    private final SelenideElement rangifflerLogoImage = $("img[alt='Rangiffler logo']");


    private final SelenideElement yourTravelsBtn = $("button[id$='main']");
    private final SelenideElement friendsTravelsBtn = $("button[id$='friends']");
    private final SelenideElement peopleAroundBtn = $("button[id$='all']");

    @Override
    public Header checkThatComponentDisplayed() {
        rangifflerLogoImage.shouldBe(Condition.visible);
        return this;
    }

    public void logout() {
        logoutBtn.click();
    }

    public AddPhotoForm goToAddPhoto() {
        addPhotoBtn.click();
        return new AddPhotoForm();
    }

    public UpdateProfileForm goToUpdateProfile() {
        userInfoButton.click();
        return new UpdateProfileForm();
    }
    public FriendsTravels goToFriendsTravels() {
        friendsTravelsBtn.click();
        return new FriendsTravels();
    }
    public PeopleAround goToPeopleAround() {
        peopleAroundBtn.click();
        return new PeopleAround();
    }
    public YourTravels goToYourTravels() {
        yourTravelsBtn.click();
        return new YourTravels();
    }
    public StartPage logoutFromAcc() {
        logoutBtn.click();
        return new StartPage();
    }
}
