package org.rangiffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.page.StartPage;
import org.rangiffler.page.YourTravels;
import org.rangiffler.utils.DataUtils;

public class YourTravelsTest extends BaseWebTest{
    @ApiLogin(user = @GenerateUser(
            photo = @Photo)
    )
    @Test
    @AllureId("126")
    @Tag("webTest")
    @DisplayName("YourTravelsPage: пользователь имеет добавленное фото")
    void checkThatUserHasPhoto() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .checkThatPageLoaded()
                .checkThatUserHasPhoto();
    }
    @ApiLogin(user = @GenerateUser(
            photo = @Photo)
    )
    @Test
    @AllureId("127")
    @Tag("webTest")
    @DisplayName("YourTravelsPage: успешное редактирование существующей записи")
    void editUserPhotoDescription() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .checkThatPageLoaded()
                .checkThatUserHasPhoto()
                .openFirstPhotoForEdit()
                .editFirstPhoto("CA", DataUtils.generateNewDescription());
    }
    @ApiLogin(user = @GenerateUser()
    )
    @Test
    @AllureId("128")
    @Tag("webTest")
    @DisplayName("YourTravelsPage: контроллеры для использования WorldMap кликабельны")
    void checkThatControllersCanBeUsed() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .checkThatControllersCanBeUsed();
    }

    @ApiLogin(user = @GenerateUser(
            photo = @Photo)
    )
    @Test
    @AllureId("129")
    @Tag("webTest")
    @DisplayName("YourTravelsPage: фото кликабельно и при закрытии возвращает на страницу стран")
    void checkThatPhotoOpenedAndClose() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .checkThatUserHasPhotoThenOpenAndClose()
                .checkThatPageLoaded();
    }
    @ApiLogin(user = @GenerateUser(
            photo = @Photo)
    )
    @Test
    @AllureId("129")
    @Tag("webTest")
    @DisplayName("YourTravelsPage: фото кликабельно и при закрытии возвращает на страницу стран")
    void checkThatPhotoDelete() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .checkThatUserHasPhotoThenDeletePhoto()
                .checkThatPageLoaded();
    }
}
