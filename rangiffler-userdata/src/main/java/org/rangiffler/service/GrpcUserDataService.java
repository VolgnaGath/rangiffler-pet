package org.rangiffler.service;


import com.google.protobuf.Empty;
import grpc.rangiffler.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.rangiffler.data.FriendsEntity;
import org.rangiffler.data.UserEntity;
import org.rangiffler.data.repository.UserRepository;
import org.rangiffler.model.UserJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@GrpcService
public class GrpcUserDataService extends RangifflerUserServiceGrpc.RangifflerUserServiceImplBase {
    private static final Logger LOG = LoggerFactory.getLogger(GrpcUserDataService.class);

    String avatar = "data:image/jpeg;base64,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";

    private final UserRepository userRepository;

    @Autowired
    public GrpcUserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "users", groupId = "userdata")
    public void listener(@Payload UserJson user, ConsumerRecord<String, UserJson> cr) {
        LOG.info("### Kafka topic [users] received message: " + user.getUsername());
        LOG.info("### Kafka consumer record: " + cr.toString());
        UserEntity userDataEntity = new UserEntity();
        userDataEntity.setUsername(user.getUsername());
        userDataEntity.setFirstname("No firstname");
        userDataEntity.setSurname("No surname");
        userDataEntity.setAvatar(avatar);
        UserEntity userEntity = userRepository.save(userDataEntity);
        LOG.info(String.format(
                "### User '%s' successfully saved to database with id: %s",
                user.getUsername(),
                userEntity.getId()
        ));
    }

    @Override
    public void getUpdateUserInfo(UpdateUserInfoRequest request, StreamObserver<UpdateUserInfoResponse> responseObserver) {
        UserEntity userEntity = userRepository.findByUsername(request.getUpdateUser().getUsername());
        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "   " + request.getUpdateUser().getUsername());
        }
        if (!request.getUpdateUser().getUsername().isEmpty()){
            userEntity.setUsername(request.getUpdateUser().getUsername());
        }
        if (!request.getUpdateUser().getFirstname().isEmpty()) {
            userEntity.setFirstname(request.getUpdateUser().getFirstname());
        }
        if (!request.getUpdateUser().getSurname().isEmpty()){
            userEntity.setSurname(request.getUpdateUser().getSurname());
        }
        if (!request.getUpdateUser().getAvatar().isEmpty()) {
            userEntity.setAvatar(request.getUpdateUser().getAvatar());
        }
        UserEntity user = userRepository.save(userEntity);
        UpdateUserInfoResponse updateUserInfoResponse = UpdateUserInfoResponse.newBuilder()
                .setUpdatedUser(User.newBuilder()
                        .setId(user.getId().toString())
                        .setUsername(user.getUsername())
                        .setFirstname(user.getFirstname())
                        .setSurname(user.getSurname())
                        .setAvatar(user.getAvatar())
                        .build())
                .build();
        responseObserver.onNext(updateUserInfoResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void currentUser(CurrentUserRequest request, StreamObserver<CurrentUserResponse> responseObserver) {
        UserEntity userDataEntity = userRepository.findByUsername(request.getUsername());
        assert userDataEntity != null;
        CurrentUserResponse currentUserResponse = CurrentUserResponse.newBuilder()
                .setCurrentUser(User.newBuilder()
                        .setId(userDataEntity.getId().toString())
                        .setUsername(userDataEntity.getUsername())
                        .setFirstname(userDataEntity.getFirstname())
                        .setSurname(userDataEntity.getFirstname())
                        .setAvatar(userDataEntity.getAvatar())
                        .build())
                .build();
        responseObserver.onNext(currentUserResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void allUsers(AllUsersRequest request, StreamObserver<AllUsersResponse> responseObserver) {
        Map<UUID, User> result = new HashMap<>();
        List<UserEntity> userEntities = userRepository.findByUsernameNot(request.getUsername());
        for (UserEntity userEntity : userEntities) {
            List<FriendsEntity> sendInvites = userEntity.getFriends();
            List<FriendsEntity> receivedInvites = userEntity.getInvites();

            if (!sendInvites.isEmpty() || !receivedInvites.isEmpty()) {
                Optional<FriendsEntity> inviteToMe = sendInvites.stream()
                        .filter(i -> i.getFriend().getUsername().equals(request.getUsername()))
                        .findFirst();

                Optional<FriendsEntity> inviteFromMe = receivedInvites.stream()
                        .filter(i -> i.getUser().getUsername().equals(request.getUsername()))
                        .findFirst();

                if (inviteToMe.isPresent()) {
                    FriendsEntity invite = inviteToMe.get();
                    result.put(userEntity.getId(), User.newBuilder()
                            .setUsername(userEntity.getUsername())
                            .setFirstname(userEntity.getFirstname())
                            .setSurname(userEntity.getSurname())
                            .setId(userEntity.getId().toString())
                            .setFriendState(invite.isPending() ? FriendState.INVITATION_RECEIVED
                                    : FriendState.FRIEND)
                            .setAvatar(userEntity.getAvatar())
                            .build());
                }
                if (inviteFromMe.isPresent()) {
                    FriendsEntity invite = inviteFromMe.get();
                    result.put(userEntity.getId(), User.newBuilder()
                            .setUsername(userEntity.getUsername())
                            .setFirstname(userEntity.getFirstname())
                            .setSurname(userEntity.getSurname())
                            .setId(userEntity.getId().toString())
                            .setFriendState(invite.isPending() ? FriendState.INVITATION_SENT
                                    : FriendState.FRIEND)
                            .setAvatar(userEntity.getAvatar())
                            .build());
                }
            }
            if (!result.containsKey(userEntity.getId())) {
                result.put(userEntity.getId(), User.newBuilder()
                        .setUsername(userEntity.getUsername())
                        .setFirstname(userEntity.getFirstname())
                        .setSurname(userEntity.getSurname())
                        .setId(userEntity.getId().toString())
                        .setAvatar(userEntity.getAvatar())
                        .build());
            }
        }
        List<User> userList = result.values().stream().toList();
        AllUsersResponse allUsersResponse = AllUsersResponse.newBuilder()
                .addAllAllUsers(userList.stream()
                        .map(e -> User.newBuilder()
                                .setId(e.getId())
                                .setUsername(e.getUsername())
                                .setFirstname(e.getFirstname())
                                .setSurname(e.getSurname())
                                .setFriendState(e.getFriendState())
                                .setAvatar(e.getAvatar())
                                .build())
                        .toList())
                .build();
        responseObserver.onNext(allUsersResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void friends(UserFriendsRequest request, StreamObserver<UserFriendsResponse> responseObserver) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getUsername());
        }
        List<User> usersList = userEntity
                .getFriends()
                .stream()
                .filter(fe -> request.getIncludePending() || !fe.isPending())
                .map(e -> User.newBuilder()
                        .setId(e.getFriend().getId().toString())
                        .setUsername(e.getFriend().getUsername())
                        .setFirstname(e.getFriend().getFirstname())
                        .setSurname(e.getFriend().getSurname())
                        .setFriendState(e.isPending()
                                ? FriendState.INVITATION_SENT
                                : FriendState.FRIEND)
                        .setAvatar(e.getFriend().getAvatar())
                        .build())
                .toList();
        UserFriendsResponse response = UserFriendsResponse.newBuilder()
                .addAllUser(usersList.stream()
                        .map(e -> User.newBuilder()
                                .setId(e.getId())
                                .setUsername(e.getUsername())
                                .setFirstname(e.getFirstname())
                                .setSurname(e.getSurname())
                                .setFriendState(e.getFriendState())
                                .setAvatar(e.getAvatar())
                                .build())
                        .toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void invitations(UserInvitationsRequest request, StreamObserver<UsersInvitationResponse> responseObserver) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getUsername());
        }
        List<User> usersList = userEntity
                .getInvites().stream().map(e -> User.newBuilder()
                        .setId(e.getUser().getId().toString())
                        .setUsername(e.getUser().getUsername())
                        .setFirstname(e.getUser().getFirstname())
                        .setSurname(e.getUser().getSurname())
                        .setFriendState(FriendState.INVITATION_RECEIVED)
                        .setAvatar(e.getUser().getAvatar())
                        .build()).toList();

        UsersInvitationResponse response = UsersInvitationResponse.newBuilder()
                .addAllUser(usersList)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addFriend(AddFriendsRequest request, StreamObserver<AddFriendsResponse> responseObserver) {
        UserEntity currentUser = userRepository.findByUsername(request.getUsername());
        UserEntity friendEntity = userRepository.findByUsername(request.getFriend().getUsername());
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getUsername());
        }
        if (friendEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find friend by username: " + request.getFriend().getUsername());
        }
        currentUser.addFriends(true, friendEntity);
        userRepository.save(currentUser);
        AddFriendsResponse response = AddFriendsResponse.newBuilder()
                .setUser(User.newBuilder()
                        .setId(friendEntity.getId().toString())
                        .setUsername(friendEntity.getUsername())
                        .setFirstname(friendEntity.getFirstname())
                        .setSurname(friendEntity.getSurname())
                        .setFriendState(FriendState.INVITATION_SENT)
                        .setAvatar(friendEntity.getAvatar())
                        .build())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void acceptInvitation(AcceptInvitationRequest request, StreamObserver<AcceptInvitationResponse> responseObserver) {
        UserEntity currentUser = userRepository.findByUsername(request.getUsername());
        UserEntity inviteUser = userRepository.findByUsername(request.getInvitation().getUsername());
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getUsername());
        }
        if (inviteUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getInvitation().getUsername());
        }
        FriendsEntity invite = currentUser.getInvites()
                .stream()
                .filter(fe -> fe.getUser().getUsername().equals(inviteUser.getUsername()))
                .findFirst()
                .orElseThrow();

        invite.setPending(false);
        currentUser.addFriends(false, inviteUser);
        userRepository.save(currentUser);
        AcceptInvitationResponse response = AcceptInvitationResponse.newBuilder().setUser(User.newBuilder()
                        .setId(inviteUser.getId().toString())
                        .setUsername(inviteUser.getUsername())
                        .setFirstname(inviteUser.getFirstname())
                        .setSurname(inviteUser.getSurname())
                        .setAvatar(inviteUser.getAvatar())
                        .setFriendState(FriendState.FRIEND).build()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void declineInvitation(DeclineInvitationRequest request, StreamObserver<DeclineInvitationResponse> responseObserver) {
        UserEntity currentUser = userRepository.findByUsername(request.getUsername());
        UserEntity friendToDecline = userRepository.findByUsername(request.getInvitation().getUsername());
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getUsername());
        }
        if (friendToDecline == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getInvitation().getUsername());
        }
        currentUser.removeInvites(friendToDecline);
        friendToDecline.removeFriends(currentUser);

        userRepository.save(currentUser);
        userRepository.save(friendToDecline);
        DeclineInvitationResponse response = DeclineInvitationResponse.newBuilder().setUser(User.newBuilder()
                        .setId(friendToDecline.getId().toString())
                        .setUsername(friendToDecline.getUsername())
                        .setFirstname(friendToDecline.getFirstname())
                        .setSurname(friendToDecline.getSurname())
                        .setAvatar(friendToDecline.getAvatar())
                        .setFriendState(FriendState.NOT_FRIEND).build()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void removeUsersFriend(RemoveFriendRequest request, StreamObserver<Empty> responseObserver) {
        UserEntity currentUser = userRepository.findByUsername(request.getUsername());
        UserEntity friendToRemove = userRepository.findByUsername(request.getFriendForRemove().getUsername());
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getUsername());
        }
        if (friendToRemove == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Can`t find user by username: " + request.getFriendForRemove().getUsername());
        }

        currentUser.removeFriends(friendToRemove);
        currentUser.removeInvites(friendToRemove);
        friendToRemove.removeInvites(currentUser);
        userRepository.save(currentUser);

        Empty response = Empty.getDefaultInstance();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}
