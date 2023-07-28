package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.component.Header;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class PeopleAround extends BasePage<PeopleAround> {
    public final String RESULT_MESSAGE = "Invitation sent";

    private final Header header = new Header();
    private final SelenideElement allPeopleContentSection = $("table[aria-label] tbody");
    private final SelenideElement addFriendBtn = $("button[aria-label='Add friend']");
    private final SelenideElement removeFriendBtn = $("button[aria-label='Remove friend']");
    private final SelenideElement acceptInvitationBtn = $("button[aria-label='Accept invitation']");
    private final SelenideElement declineInvitationBtn = $("button[aria-label='Decline invitation']");


    public Header getHeader() {
        return header;
    }

    @Override
    public PeopleAround checkThatPageLoaded() {
        allPeopleContentSection.shouldBe(Condition.visible);
        return this;
    }
    public PeopleAround checkThatContentSectionHaveUsers() {
        allPeopleContentSection.$$("tr").stream().findFirst().get().shouldBe(Condition.visible);
        return new PeopleAround();
    }
    public PeopleAround deleteFriend(UserJson friend) {
        SelenideElement friendRow = getRowByUsername(friend.getUsername());
        SelenideElement actionsCell = getActionsCell(friendRow);
        actionsCell.$("button[aria-label='Remove friend']")
                .click();
        return new PeopleAround();
    }
    public PeopleAround acceptInvitation(UserJson friend) {
        SelenideElement friendRow = getRowByUsername(friend.getUsername());
        SelenideElement actionsCell = getActionsCell(friendRow);
        actionsCell.$("button[aria-label='Accept invitation']")
                .click();
        return new PeopleAround();
    }
    public PeopleAround declineInvitation(UserJson friend) {
        SelenideElement friendRow = getRowByUsername(friend.getUsername());
        SelenideElement actionsCell = getActionsCell(friendRow);
        actionsCell.$("button[aria-label='Decline invitation']")
                .click();
        return new PeopleAround();
    }
    public void sendInvitation() {
        SelenideElement friendRow = allPeopleContentSection.$$("tr").get(0);
        SelenideElement actionsCell = getActionsCell(friendRow);
        actionsCell.$("button[aria-label='Add friend']")
                .click();
        actionsCell.shouldHave(text(RESULT_MESSAGE));
        new PeopleAround();
    }
    public PeopleAround checkThatUserHasFriend(UserJson friend) {
        allPeopleContentSection.$$("tr").find(text(friend.getUsername()));

        return new PeopleAround();
    }
    public SelenideElement getRowByUsername(String username) {
        ElementsCollection allRows = allPeopleContentSection.$$("tr");
        return allRows.find(text(username));
    }

    public SelenideElement getUsernameCell(SelenideElement row) {
        return row.$$("td").get(1);
    }

    public SelenideElement getActionsCell(SelenideElement row) {
        return row.$$("td").get(3);
    }
}
