package org.rangiffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.db.entity.PhotoEntity;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.YourTravels;
import org.rangiffler.utils.DataUtils;

public class AddPhotoTest extends BaseWebTest{
    @ApiLogin(user = @GenerateUser()
    )
    @Test
    @AllureId("133")
    @Tag("webTest")
    @DisplayName("AddPhotoComponent: пользователь добавляет фото")
    void checkThatUserCanAddPhoto() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToAddPhoto()
                .checkThatComponentDisplayed()
                .addPhoto("RU", DataUtils.generateNewDescription())
                .checkThatUserHasPhoto();
    }

}
