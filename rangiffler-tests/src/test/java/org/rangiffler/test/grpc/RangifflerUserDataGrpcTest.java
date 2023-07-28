package org.rangiffler.test.grpc;

import com.google.protobuf.Empty;
import grpc.rangiffler.grpc.*;
import grpc.rangiffler.grpc.User;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.db.dao.RangifflerPhotoDAO;
import org.rangiffler.db.dao.RangifflerPhotoDAOHibernate;
import org.rangiffler.db.dao.RangifflerUserDataDAO;
import org.rangiffler.db.dao.RangifflerUserDataDAOHibernate;
import org.rangiffler.db.entity.FriendsEntity;
import org.rangiffler.db.entity.UserEntity;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.model.UserJson;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


public class RangifflerUserDataGrpcTest extends BaseGrpcTest {
    private final RangifflerUserDataDAO userDataDAO = new RangifflerUserDataDAOHibernate();


    @AllureId("101")
    @Test
    @GenerateUser()
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data обновление юзера")
    void updateUserInfoTest(UserJson user) {
        UpdateUserInfoRequest request = UpdateUserInfoRequest.newBuilder().setUpdateUser(User.newBuilder()
                .setId(user.getId().toString())
                .setUsername(user.getUsername())
                .setSurname("testSurname")
                .setFirstname("testFirstname")
                .build()).build();

        UpdateUserInfoResponse expected = UpdateUserInfoResponse.newBuilder().setUpdatedUser(User.newBuilder()
                .setId(user.getId().toString())
                .setUsername(user.getUsername())
                .setSurname("testSurname")
                .setFirstname("testFirstname")
                .build()).build();

        UpdateUserInfoResponse response = userDataStub.getUpdateUserInfo(request);
        assertThat(response).usingRecursiveComparison().ignoringFields("updatedUser_.id_",
                "updatedUser_.avatar_", "friendState_", "memoizedHashCode", "updatedUser_.memoizedHashCode", "updatedUser_.memoizedIsInitialized")
                .isEqualTo(expected);

    }
    @AllureId("102")
    @GenerateUser()
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data должен вернуть юзера")
    void currentUserTest(UserJson user) {
        CurrentUserRequest request = CurrentUserRequest.newBuilder().setUsername(user.getUsername()).build();
        CurrentUserResponse response = userDataStub.currentUser(request);
        assertThat(response).isNotNull();
        assertThat(response.getCurrentUser().getUsername()).isEqualTo(request.getUsername());
    }


    @GenerateUser(
            friends = @Friend
    )
    @AllureId("103")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data должен вернуть всех юзеров")
    void allUsersTest(UserJson user) {
        AllUsersRequest request = AllUsersRequest.newBuilder().setUsername(user.getUsername()).build();
        AllUsersResponse response = userDataStub.allUsers(request);

        final List<User> userList = response.getAllUsersList();
        step("Check that response contains 2 users", () ->
                assertThat(userList.size()).isGreaterThan(1)
        );
    }

    @GenerateUser(
            incomeInvitations = @Friend
    )
    @AllureId("104")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data должен показать всех друзей")
    void userFriendsTest(UserJson user) {
        final UserJson friendInv = user.getIncomeInvitations().get(0);
        AddFriendsRequest request = AddFriendsRequest.newBuilder().setUsername(user.getUsername())
                .setFriend(grpc.rangiffler.grpc.Friend.newBuilder().setUsername(friendInv.getUsername()).build()).build();

        AddFriendsResponse response = userDataStub.addFriend(request);
        step("Check response - friend username", () ->
                assertThat(response.getUser().getUsername()).isEqualTo(friendInv.getUsername())
        );
    }

    @GenerateUser(
            incomeInvitations = @Friend
    )
    @AllureId("105")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data должен показать всех приглашения в друзья")
    void invitationsTest(UserJson user) {
        final UserJson friendInv = user.getIncomeInvitations().get(0);
        UserInvitationsRequest request = UserInvitationsRequest.newBuilder().setUsername(user.getUsername()).build();
        UsersInvitationResponse response = userDataStub.invitations(request);
        List<User> userList = response.getUserList();
        step("Check that response contains users", () ->
                assertThat(userList.size()).isGreaterThan(0)
        );
        step("Check response - invitations for user", () ->
                assertThat(userList.get(0).getUsername()).isEqualTo(friendInv.getUsername())
        );
    }

    @GenerateUser(
            incomeInvitations = @Friend
    )
    @AllureId("106")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data должен отправить запрос на дружбу")
    void addFriendTest(UserJson user) {
        final UserJson friendInv = user.getIncomeInvitations().get(0);
        AddFriendsRequest request = AddFriendsRequest.newBuilder().setUsername(user.getUsername())
                .setFriend(grpc.rangiffler.grpc.Friend.newBuilder()
                        .setUsername(friendInv.getUsername())
                        .build()).build();
        AddFriendsResponse response = userDataStub.addFriend(request);
        step("Check that add friend request is successful", () ->
                assertThat(response.getUser().getUsername()).isEqualTo(friendInv.getUsername())
        );
    }

    @GenerateUser(
            incomeInvitations = @Friend
    )
    @AllureId("107")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data должен установить Friend state")
    void acceptInvitationTest(UserJson user) {
        final UserJson friendInv = user.getIncomeInvitations().get(0);
        System.out.println(friendInv.getUsername());
        AcceptInvitationRequest request = AcceptInvitationRequest.newBuilder().setUsername(user.getUsername())
                .setInvitation(grpc.rangiffler.grpc.Friend.newBuilder().setUsername(friendInv.getUsername()).build())
                .build();
        AcceptInvitationResponse response = userDataStub.acceptInvitation(request);
        System.out.println(response.getUser().getUsername());
        step("Check that accept Invitation request is successful", () ->
                assertThat(response.getUser().getUsername()).isEqualTo(friendInv.getUsername())
        );
    }
    @GenerateUser(
            incomeInvitations = @Friend
    )
    @AllureId("108")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data должен вернуть отклоненного пользователя")
    void declineInvitationTest(UserJson user) {
        final UserJson friendInv = user.getIncomeInvitations().get(0);
        DeclineInvitationRequest request = DeclineInvitationRequest.newBuilder().setUsername(user.getUsername())
                .setInvitation(grpc.rangiffler.grpc.Friend.newBuilder().setUsername(friendInv.getUsername())
                        .build()).build();
        DeclineInvitationResponse response = userDataStub.declineInvitation(request);
        step("Check that decline invitation request is successful", () ->
                assertThat(response.getUser().getUsername()).isEqualTo(friendInv.getUsername())
        );
    }

    @GenerateUser(
            friends = @Friend
    )
    @AllureId("109")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис user-data должен удалить юзера")
    void removeFriendTest(UserJson user) {
        final UserJson friendInv = user.getFriends().stream().findFirst().get();

        RemoveFriendRequest removeFriendRequest = RemoveFriendRequest.newBuilder().setUsername(user.getUsername())
                .setFriendForRemove(grpc.rangiffler.grpc.Friend.newBuilder().setUsername(friendInv.getUsername()).build()).build();
        Empty emptyResponse = userDataStub.removeUsersFriend(removeFriendRequest);
        step("Check that remove friend request is successful", () ->
                assertThat(emptyResponse).isNotNull()
        );
    }
}
