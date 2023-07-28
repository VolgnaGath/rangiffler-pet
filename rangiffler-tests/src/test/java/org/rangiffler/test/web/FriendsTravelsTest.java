package org.rangiffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.db.dao.RangifflerPhotoDAO;
import org.rangiffler.db.dao.RangifflerPhotoDAOHibernate;
import org.rangiffler.db.entity.PhotoEntity;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.YourTravels;
import org.rangiffler.utils.DataUtils;

public class FriendsTravelsTest extends BaseWebTest{
    private final RangifflerPhotoDAO photoDAO = new RangifflerPhotoDAOHibernate();
    @ApiLogin(user = @GenerateUser(
            friends = @Friend)
    )
    @Photo()
    @Test
    @AllureId("130")
    @Tag("webTest")
    @DisplayName("FriendTravelsPage: пользователь имеет добавленное фото")
    void checkThatFriendHasPhoto(UserJson userJson, PhotoJson photoJson) {
        UserJson friend = userJson.getFriends().get(0);
        photoJson.setUsername(friend.getUsername());
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setId(photoJson.getId());
        photoEntity.setUsername(photoJson.getUsername());
        photoEntity.setDescription(photoJson.getDescription());
        photoEntity.setCountryCode(photoJson.getCountryCode());
        photoEntity.setImage(photoJson.getPhoto());
        photoDAO.addPhoto(photoEntity);
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToFriendsTravels()
                .checkThatPageLoaded()
                .checkThatFriendHasPhoto();
    }

    @ApiLogin(user = @GenerateUser()
    )
    @Test
    @AllureId("131")
    @Tag("webTest")
    @DisplayName("FriendsTravelsPage: контроллеры для использования WorldMap кликабельны")
    void checkThatControllersCanBeUsed() {
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToFriendsTravels()
                .checkThatControllersCanBeUsed();
    }

    @ApiLogin(user = @GenerateUser(
            friends = @Friend)
    )
    @Photo()
    @Test
    @AllureId("132")
    @Tag("webTest")
    @DisplayName("FriendsTravelsPage: фото кликабельно и при закрытии возвращает на страницу стран")
    void checkThatPhotoOpenedAndClose(UserJson userJson, PhotoJson photoJson) {
        UserJson friend = userJson.getFriends().get(0);
        photoJson.setUsername(friend.getUsername());
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setId(photoJson.getId());
        photoEntity.setUsername(photoJson.getUsername());
        photoEntity.setDescription(photoJson.getDescription());
        photoEntity.setCountryCode(photoJson.getCountryCode());
        photoEntity.setImage(photoJson.getPhoto());
        photoDAO.addPhoto(photoEntity);
        Selenide.open(YourTravels.MAIN_PAGE_URL, YourTravels.class)
                .getHeader()
                .goToFriendsTravels()
                .checkThatFriendHasPhotoThenOpenAndClose()
                .checkThatPageLoaded();
    }
}
