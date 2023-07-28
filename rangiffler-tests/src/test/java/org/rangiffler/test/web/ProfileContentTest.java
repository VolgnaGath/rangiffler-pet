package org.rangiffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.page.YourTravels;
import org.rangiffler.utils.DataUtils;

public class ProfileContentTest extends BaseWebTest{
    @ApiLogin(user = @GenerateUser()
    )
    @Test
    @AllureId("133")
    @Tag("webTest")
    @DisplayName("UpdateProfileForm: пользователь редактирует профиль, обновляет аватар")
    void checkThatUserCanUpdateProfile() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToUpdateProfile()
                .checkThatComponentDisplayed()
                .updateProfile(DataUtils.generateRandomName(), DataUtils.generateRandomName())
                .checkThatPageLoaded();
    }

    @ApiLogin(user = @GenerateUser()
    )
    @Test
    @AllureId("134")
    @Tag("webTest")
    @DisplayName("Header: пользователь выходит из профиля")
    void checkThatUserCanLogout() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .checkThatComponentDisplayed()
                .logoutFromAcc()
                .checkThatPageLoaded();
    }

}
