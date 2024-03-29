package org.rangiffler.data;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String surname;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendsEntity> friends = new ArrayList<>();

    @OneToMany(mappedBy = "friend", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendsEntity> invites = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public @Nonnull List<FriendsEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsEntity> friends) {
        this.friends = friends;
    }

    public @Nonnull List<FriendsEntity> getInvites() {
        return invites;
    }

    public void setInvites(List<FriendsEntity> invites) {
        this.invites = invites;
    }

    public void addFriends(boolean pending, UserEntity... friends) {
        List<FriendsEntity> friendsEntities = Stream.of(friends)
                .map(f -> {
                    FriendsEntity fe = new FriendsEntity();
                    fe.setUser(this);
                    fe.setFriend(f);
                    fe.setPending(pending);
                    return fe;
                }).toList();

        this.friends.addAll(friendsEntities);
    }

    public void removeFriends(UserEntity... friends) {
        for (UserEntity friend : friends) {
            getFriends().removeIf(f -> f.getFriend().getId().equals(friend.getId()));
        }
    }

    public void removeInvites(UserEntity... invitations) {
        for (UserEntity invite : invitations) {
            getInvites().removeIf(i -> i.getUser().getId().equals(invite.getId()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(firstname, that.firstname) && Objects.equals(surname, that.surname) && Objects.equals(friends, that.friends) && Objects.equals(invites, that.invites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstname, surname, friends, invites);
    }
}
