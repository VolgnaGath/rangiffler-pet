package org.rangiffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.*;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.YourTravels;


public class PeopleAroundTest extends BaseWebTest {


    @ApiLogin(user = @GenerateUser(
            friends = @Friend)
    )
    @Test
    @AllureId("135")
    @Tag("webTest")
    @DisplayName("AllPeople: на странице отображаются другие пользователи")
    void checkThatAllPeopleHasContent(UserJson userJson) {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToPeopleAround()
                .checkThatContentSectionHaveUsers();
    }
    @ApiLogin(user = @GenerateUser(
            friends = @Friend)
    )
    @Test
    @AllureId("136")
    @Tag("webTest")
    @DisplayName("AllPeople: удаление из друзей")
    void checkThatUserCanRemoveFriend(UserJson userJson) {
        final UserJson friend = userJson.getFriends().get(0);
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToPeopleAround()
                .deleteFriend(friend);

    }
    @ApiLogin(user = @GenerateUser(
            friends = @Friend)
    )
    @Test
    @AllureId("137")
    @Tag("webTest")
    @DisplayName("AllPeople: найти друга")
    void checkThatUserHasFriend(UserJson userJson) {
        final UserJson friend = userJson.getFriends().get(0);
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToPeopleAround()
                .checkThatUserHasFriend(friend);
    }
    @ApiLogin(user = @GenerateUser(
            incomeInvitations = @Friend)
    )
    @Test
    @AllureId("138")
    @Tag("webTest")
    @DisplayName("AllPeople: одобрение заявки в друзья")
    void checkThatUserAcceptInvitation(UserJson userJson) {
        final UserJson friend = userJson.getIncomeInvitations().get(0);
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToPeopleAround()
                .acceptInvitation(friend);
    }
    @ApiLogin(user = @GenerateUser(
            incomeInvitations = @Friend)
    )
    @Test
    @AllureId("139")
    @Tag("webTest")
    @DisplayName("AllPeople: отказать в дружбе")
    void checkThatUserDeclineInvitation(UserJson userJson) {
        final UserJson friend = userJson.getIncomeInvitations().get(0);
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToPeopleAround()
                .declineInvitation(friend);
    }


    @ApiLogin(user = @GenerateUser())
    @Test
    @AllureId("140")
    @Tag("webTest")
    @DisplayName("AllPeople: отправить заявку в друзья, заявка на добавление в друзья отправлена")
    void checkThatInvitationSent(UserJson currentUser) {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToPeopleAround()
                .sendInvitation();
    }
}
