package org.rangiffler.controller;

import java.util.List;
import org.rangiffler.model.UserJson;
import org.rangiffler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public List<UserJson> getAllUsers(@AuthenticationPrincipal Jwt principal) {
    String username = principal.getClaim("sub");
    return userService.getAllUsers(username);
  }

  @GetMapping("/currentUser")
  public UserJson getCurrentUser(@AuthenticationPrincipal Jwt principal) {
    String username = principal.getClaim("sub");
    return userService.getCurrentUser(username);
  }

  @PatchMapping("/currentUser")
  public UserJson updateCurrentUser(@AuthenticationPrincipal Jwt principal,
          @RequestBody UserJson user) {
    String username = principal.getClaim("sub");
    user.setUsername(username);
    return userService.updateCurrentUser(user);
  }

  @GetMapping("/friends")
  public List<UserJson> getFriendsByUsername(@AuthenticationPrincipal Jwt principal) {
    String username = principal.getClaim("sub");
    return userService.getFriends(username);
  }

  @GetMapping("invitations")
  public List<UserJson> getInvitations(@AuthenticationPrincipal Jwt principal) {
    String username = principal.getClaim("sub");
    return userService.getInvitations(username);
  }

  @PostMapping("users/invite/")
  public UserJson sendInvitation(@AuthenticationPrincipal Jwt principal,
          @RequestBody UserJson friend) {
    String username = principal.getClaim("sub");
    return userService.sendInvitation(username, friend);
  }

  @PostMapping("friends/remove")
  public UserJson removeFriendFromUser(@AuthenticationPrincipal Jwt principal,
                                       @RequestBody UserJson friend) {
    String username = principal.getClaim("sub");
    return userService.removeUserFromFriends(username, friend);
  }

  @PostMapping("friends/submit")
  public UserJson submitFriend(@AuthenticationPrincipal Jwt principal,
          @RequestBody UserJson friend) {
    String username = principal.getClaim("sub");
    return userService.acceptInvitation(username, friend);
  }

  @PostMapping("friends/decline")
  public UserJson declineFriend(@AuthenticationPrincipal Jwt principal,
          @RequestBody UserJson friend) {
    String username = principal.getClaim("sub");
    return userService.declineInvitation(username, friend);
  }

}
