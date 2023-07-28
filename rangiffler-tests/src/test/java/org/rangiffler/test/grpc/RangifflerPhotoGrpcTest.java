package org.rangiffler.test.grpc;

import grpc.rangiffler.grpc.*;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.db.dao.RangifflerPhotoDAO;
import org.rangiffler.db.dao.RangifflerPhotoDAOHibernate;
import org.rangiffler.db.entity.PhotoEntity;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class RangifflerPhotoGrpcTest extends BaseGrpcTest{
    private final RangifflerPhotoDAO photoDAO = new RangifflerPhotoDAOHibernate();



    @GenerateUser()
    @Photo()
    @AllureId("113")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис rangiffler-photo должен добавить фото")
    void addPhotoTest(UserJson userJson, PhotoJson photoJson) {
        photoJson.setUsername(userJson.getUsername());
        AddPhotoResponse expected = AddPhotoResponse.newBuilder().setPhoto(grpc.rangiffler.grpc.Photo.newBuilder()
                .setUsername(photoJson.getUsername())
                .setCountry(Country.newBuilder().setCode(photoJson.getCountryCode()).build())
                .setDescription(photoJson.getDescription())
                .setImage(photoJson.getPhoto()).build()).build();
        AddPhotoRequest addPhotoRequest = AddPhotoRequest.newBuilder().setPhoto(grpc.rangiffler.grpc.Photo.newBuilder()
                .setUsername(photoJson.getUsername())
                .setCountry(Country.newBuilder().setCode(photoJson.getCountryCode()).build())
                .setDescription(photoJson.getDescription())
                .setImage(photoJson.getPhoto()).build()).build();
        AddPhotoResponse addPhotoResponse = photoStub.addPhoto(addPhotoRequest);
        assertThat(addPhotoResponse).usingRecursiveComparison().ignoringFields("photo_.id_",
                        "photo_.memoizedHashCode", "photo_.memoizedIsInitialized", "memoizedHashCode", "photo_.country_.memoizedIsInitialized")
                .isEqualTo(expected);
    }

    @GenerateUser(
            photo = @Photo
    )
    @AllureId("114")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис rangiffler-photo мои фото")
    void allUserPhotosTest(UserJson userJson) {
        GetAllUserPhotosRequest getAllUserPhotosRequest = GetAllUserPhotosRequest.newBuilder()
                .setUsername(userJson.getUsername()).build();
        GetAllUserPhotosResponse getAllUserPhotosResponse = photoStub.getAllUserPhotos(getAllUserPhotosRequest);
        step("Check that response contains photo", () ->
                assertThat(getAllUserPhotosResponse.getPhotoList().size()).isGreaterThan(0)
        );

        step("Check that response contains generated Photo CountryCode", () ->
                assertThat(getAllUserPhotosResponse.getPhotoList().get(0).getCountry().getCode()).isEqualTo("RU")
        );

        step("Check that response contains generated Photo description", () ->
                assertThat(getAllUserPhotosResponse.getPhotoList().get(0).getDescription()).isEmpty()
        );
    }

    @GenerateUser(
            photo = @Photo
    )
    @Photo()
    @AllureId("115")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис rangiffler-photo должен обновить информацию о фото")
    void editUserPhotoTest(UserJson userJson, PhotoJson photoJson) {
        PhotoEntity photoEntity = photoDAO.findAllByUsername(userJson.getUsername()).stream()
                .filter(ph -> ph.getUsername().equals(userJson.getUsername())).findFirst().orElseThrow();
        photoJson.setUsername(userJson.getUsername());
        AddPhotoResponse expected = AddPhotoResponse.newBuilder().setPhoto(grpc.rangiffler.grpc.Photo.newBuilder()
                .setId(photoEntity.getId().toString())
                .setUsername(photoJson.getUsername())
                .setCountry(Country.newBuilder().setCode(photoJson.getCountryCode()).build())
                .setDescription(photoJson.getDescription())
                .build()).build();
        EditUserPhotoRequest editUserPhotoRequest = EditUserPhotoRequest.newBuilder()
                .setPhoto(grpc.rangiffler.grpc.Photo.newBuilder()
                        .setId(photoEntity.getId().toString())
                        .setUsername(photoJson.getUsername())
                        .setCountry(Country.newBuilder().setCode(photoJson.getCountryCode()).build())
                        .setDescription(photoJson.getDescription())
                        .setImage(photoJson.getPhoto())
                        .build()).build();
        EditUserPhotoResponse editUserPhotoResponse = photoStub.editUserPhoto(editUserPhotoRequest);
        assertThat(editUserPhotoResponse).usingRecursiveComparison().ignoringFields("photo_.id_",
                        "photo_.memoizedHashCode", "photo_.memoizedIsInitialized", "memoizedHashCode",
                        "photo_.country_.memoizedIsInitialized", "photo_.image_")
                .isEqualTo(expected);
    }

    @GenerateUser(
            friends = @Friend()
    )
    @Photo
    @AllureId("116")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис rangiffler-photo вернуть все фото друзей")
    void getAllFriendsPhotosTest(UserJson userJson, PhotoJson photoJson) {
        UserJson friend = userJson.getFriends().get(0);
        photoJson.setUsername(friend.getUsername());
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setId(photoJson.getId());
        photoEntity.setUsername(photoJson.getUsername());
        photoEntity.setDescription(photoJson.getDescription());
        photoEntity.setCountryCode(photoJson.getCountryCode());
        photoEntity.setImage(photoJson.getPhoto());
        photoDAO.addPhoto(photoEntity);
        GetAllFriendsPhotosResponse expected = GetAllFriendsPhotosResponse.newBuilder()
                .addPhoto(grpc.rangiffler.grpc.Photo.newBuilder()
                        .setUsername(photoJson.getUsername())
                        .setCountry(Country.newBuilder().setCode(photoJson.getCountryCode()).build())
                        .setDescription(photoJson.getDescription()).build()).build();

        GetAllFriendsPhotosRequest getAllFriendsPhotosRequest = GetAllFriendsPhotosRequest.newBuilder()
                .setUser(User.newBuilder().setId(userJson.getId().toString())
                        .setUsername(userJson.getUsername())
                        .setFirstname(userJson.getFirstName())
                        .setSurname(userJson.getSurname()).build()).build();
        GetAllFriendsPhotosResponse getAllFriendsPhotosResponse = photoStub.getAllFriendsPhotos(getAllFriendsPhotosRequest);
        assertThat(getAllFriendsPhotosResponse).usingRecursiveComparison().ignoringFields("photo_.id_",
                        "photo_.memoizedHashCode", "photo_.memoizedIsInitialized", "memoizedHashCode",
                        "photo_.country_.memoizedIsInitialized", "photo_.image_", "photo_.country_.memoizedIsInitialized")
                .isEqualTo(expected);
    }

    @GenerateUser(
            photo = @Photo
    )
    @AllureId("117")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис rangiffler-photo должен удалить фото")
    void deletePhotoTest(UserJson userJson) {
        PhotoEntity photoEntity = photoDAO.findAllByUsername(userJson.getUsername()).stream().findFirst().get();
        DeletePhotoRequest deletePhotoRequest = DeletePhotoRequest.newBuilder().setUsername(userJson.getUsername())
                .setPhotoId(photoEntity.getId().toString()).build();
        photoStub.deletePhoto(deletePhotoRequest);
        List<PhotoEntity> list = photoDAO.findAllByUsername(userJson.getUsername());
        assertThat(list).isEmpty();
    }

}
