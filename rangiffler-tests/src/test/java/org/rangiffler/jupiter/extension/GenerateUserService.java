package org.rangiffler.jupiter.extension;

import grpc.rangiffler.grpc.*;
import org.rangiffler.api.AuthRestClient;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.test.grpc.BaseGrpcTest;
import org.rangiffler.utils.DataUtils;
import com.google.common.base.Stopwatch;

import javax.annotation.Nonnull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


public class GenerateUserService {

    private static final AuthRestClient authClient = new AuthRestClient();

    private static final BaseGrpcTest grpcTest = new BaseGrpcTest();


    public UserJson generateUser(@Nonnull GenerateUser annotation) {
        UserJson user = createRandomUser();

        addFriendsIfPresent(user, annotation.friends());
        addOutcomeInvitationsIfPresent(user, annotation.outcomeInvitations());
        addIncomeInvitationsIfPresent(user, annotation.incomeInvitations());
        addPhotoIfPresent(user, annotation.photo());
        return user;
    }

    private void addPhotoIfPresent(UserJson targetUser, Photo[] photos) {
        if (isNotEmpty(photos)) {
            for (Photo photo : photos) {
                PhotoJson photoJson = new PhotoJson();
                photoJson.setUsername(targetUser.getUsername());
                photoJson.setDescription(photo.description());
                photoJson.setCountryCode(photo.countryCode());
                photoJson.setPhoto(photo.value());
                grpcTest.getPhotoStub().addPhoto(AddPhotoRequest.newBuilder()
                        .setPhoto(grpc.rangiffler.grpc.Photo.newBuilder()
                                .setUsername(photoJson.getUsername())
                                .setCountry(Country.newBuilder().setCode(photoJson.getCountryCode()).build())
                                .setDescription(photoJson.getDescription())
                                .setImage(photoJson.getPhoto())).build());
            }
        }
    }


    private void addFriendsIfPresent(UserJson targetUser, Friend[] friends) {
        if (isNotEmpty(friends)) {
            for (Friend friend : friends) {
                UserJson friendJson = createRandomUser();
                grpcTest.getUserDataStub().addFriend(AddFriendsRequest.newBuilder().setUsername(targetUser.getUsername())
                        .setFriend(grpc.rangiffler.grpc.Friend.newBuilder().setUsername(friendJson.getUsername()).build())
                        .build());
                grpcTest.getUserDataStub().acceptInvitation(AcceptInvitationRequest.newBuilder()
                        .setUsername(friendJson.getUsername())
                        .setInvitation(grpc.rangiffler.grpc.Friend.newBuilder().setUsername(targetUser.getUsername()).build()).build());
                friendJson.getFriends().add(targetUser);
                targetUser.getFriends().add(friendJson);
            }
        }
    }

    private void addOutcomeInvitationsIfPresent(UserJson targetUser, Friend[] outcomeInvitations) {
        if (isNotEmpty(outcomeInvitations)) {
            for (Friend oi : outcomeInvitations) {
                UserJson friendJson = createRandomUser();
                grpcTest.getUserDataStub().addFriend(AddFriendsRequest.newBuilder().setUsername(targetUser.getUsername())
                        .setFriend(grpc.rangiffler.grpc.Friend.newBuilder().setUsername(friendJson.getUsername()).build()).build());
                targetUser.getOutcomeInvitations().add(friendJson);
            }
        }
    }

    private void addIncomeInvitationsIfPresent(UserJson targetUser, Friend[] incomeInvitations) {
        if (isNotEmpty(incomeInvitations)) {
            for (Friend ii : incomeInvitations) {
                UserJson friendJson = createRandomUser();
                grpcTest.getUserDataStub().addFriend(AddFriendsRequest.newBuilder().setUsername(friendJson.getUsername())
                        .setFriend(grpc.rangiffler.grpc.Friend.newBuilder().setUsername(targetUser.getUsername()).build()).build());
                targetUser.getIncomeInvitations().add(friendJson);
            }
        }
    }

    private UserJson createRandomUser() {
        final String username = DataUtils.generateRandomUsername();
        final String password = DataUtils.generateRandomPassword();
        authClient.register(username, password);
        UserJson user = waitWhileUserToBeConsumed(username, 5000L);
        user.setPassword(password);
        return user;
    }

    private UserJson waitWhileUserToBeConsumed(String username, long maxWaitTime) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        User user = grpcTest.getUserDataStub().currentUser(CurrentUserRequest.newBuilder()
                .setUsername(username).build()).getCurrentUser();

        UserJson userJson = new UserJson();
        userJson.setId(UUID.fromString(user.getId()));
        userJson.setUsername(user.getUsername());
        userJson.setFirstName(user.getFirstname());
        userJson.setSurname(user.getSurname());
        return userJson;
    }
}
