package org.rangiffler.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import grpc.rangiffler.grpc.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("username")
  private String username;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastLame;

  @JsonProperty("avatar")
  private String avatar;


  @JsonProperty("friendStatus")
  private FriendStatus friendStatus;


  public static UserJson fromGrpcMessage(User userMessage) {
    return new UserJson(UUID.fromString(userMessage.getId()), userMessage.getUsername(),
            userMessage.getFirstname(), userMessage.getSurname(), userMessage.getAvatar(),
            FriendStatus.valueOf(userMessage.getFriendState().name()));
  }
}

