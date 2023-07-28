package org.rangiffler.service;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import grpc.rangiffler.grpc.*;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.rangiffler.model.FriendStatus;
import org.rangiffler.model.UserJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @GrpcClient("grpcUserDataClient")
    private RangifflerUserServiceGrpc.RangifflerUserServiceBlockingStub rangifflerUserServiceBlockingStub;



    public List<UserJson> getAllUsers(@Nonnull String username) {
        try {
            List<User> usersList = rangifflerUserServiceBlockingStub.allUsers(AllUsersRequest.newBuilder()
                    .setUsername(username).build()).getAllUsersList();
            List<UserJson> userJsons = new ArrayList<>();
            for (User user : usersList) {
                userJsons.add(UserJson.fromGrpcMessage(user));
            }
            return userJsons;
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public UserJson getCurrentUser(@Nonnull String username) {
        User currentUser = rangifflerUserServiceBlockingStub.currentUser(CurrentUserRequest.newBuilder().setUsername(username).build()).getCurrentUser();
        try {
            return UserJson.fromGrpcMessage(currentUser);
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public List<UserJson> getFriends(@Nonnull String username) {
        try {
            List<User> usersList = new ArrayList<>();
            usersList.addAll(rangifflerUserServiceBlockingStub.friends(UserFriendsRequest.newBuilder().setUsername(username).build()).getUserList());
            List<UserJson> userJsons = new ArrayList<>();
            for (User user : usersList) {
                userJsons.add(UserJson.fromGrpcMessage(user));
            }
            return userJsons;
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public UserJson sendInvitation(@Nonnull String username, @Nonnull UserJson friend) {
        try {
            User userRpc = rangifflerUserServiceBlockingStub.addFriend(AddFriendsRequest.newBuilder().setUsername(username)
                    .setFriend(Friend.newBuilder().setUsername(friend.getUsername()).build()).build()).getUser();
            return UserJson.fromGrpcMessage(userRpc);
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public UserJson acceptInvitation(@Nonnull String username,
                                     @Nonnull UserJson friend) {
        try {
            User user = rangifflerUserServiceBlockingStub.acceptInvitation(AcceptInvitationRequest.newBuilder()
                            .setUsername(username)
                            .setInvitation(Friend.newBuilder().setUsername(friend.getUsername()).build())
                            .build()).getUser();
            return UserJson.fromGrpcMessage(user);
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public UserJson declineInvitation(@Nonnull String username,
                                      @Nonnull UserJson friend) {
        try {
            User user = rangifflerUserServiceBlockingStub.declineInvitation(DeclineInvitationRequest.newBuilder()
                            .setUsername(username)
                            .setInvitation(Friend.newBuilder().setUsername(friend.getUsername()).build())
                            .build()).getUser();

            return UserJson.fromGrpcMessage(user);
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public UserJson removeUserFromFriends(@Nonnull String username,
                                          @Nonnull UserJson friend) {
        try {
            rangifflerUserServiceBlockingStub.removeUsersFriend(RemoveFriendRequest.newBuilder()
                            .setUsername(username)
                            .setFriendForRemove(Friend.newBuilder().setUsername(friend.getUsername()).build()).build());
            friend.setFriendStatus(FriendStatus.NOT_FRIEND);
            return friend;
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public UserJson updateCurrentUser(@Nonnull UserJson user) {
        try {
            User userRpc = rangifflerUserServiceBlockingStub.getUpdateUserInfo(UpdateUserInfoRequest.newBuilder()
                    .setUpdateUser(User.newBuilder()
                            .setUsername(user.getUsername())
                            .setFirstname(user.getFirstName())
                            .setSurname(user.getLastLame())
                            .setAvatar(user.getAvatar())
                            .build()).build()).getUpdatedUser();
            return UserJson.fromGrpcMessage(userRpc);
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public List<UserJson> getInvitations(@Nonnull String username) {
        try {
            List<User> usersList = rangifflerUserServiceBlockingStub.invitations(UserInvitationsRequest.newBuilder()
                    .setUsername(username).build()).getUserList();
            List<UserJson> userJsons = new ArrayList<>();
            for (User user : usersList) {
                userJsons.add(UserJson.fromGrpcMessage(user));
            }
            return userJsons;
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }
}
