package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserJson {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstName")
    private String firstName;

    private transient String password;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("friendState")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FriendState friendState;

    private transient List<UserJson> friends = new ArrayList<>();
    private transient List<UserJson> outcomeInvitations = new ArrayList<>();
    private transient List<UserJson> incomeInvitations = new ArrayList<>();


    public UserJson() {
    }


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public FriendState getFriendState() {
        return friendState;
    }

    public void setFriendState(FriendState friendState) {
        this.friendState = friendState;
    }

    public List<UserJson> getFriends() {
        return friends;
    }

    public void setFriends(List<UserJson> friends) {
        this.friends = friends;
    }

    public List<UserJson> getOutcomeInvitations() {
        return outcomeInvitations;
    }

    public void setOutcomeInvitations(List<UserJson> outcomeInvitations) {
        this.outcomeInvitations = outcomeInvitations;
    }

    public List<UserJson> getIncomeInvitations() {
        return incomeInvitations;
    }

    public void setIncomeInvitations(List<UserJson> incomeInvitations) {
        this.incomeInvitations = incomeInvitations;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserJson userJson)) return false;
        return Objects.equals(getId(), userJson.getId()) && Objects.equals(getUsername(), userJson.getUsername()) && Objects.equals(getFirstName(), userJson.getFirstName()) && Objects.equals(getPassword(), userJson.getPassword()) && Objects.equals(getSurname(), userJson.getSurname()) && getFriendState() == userJson.getFriendState() && Objects.equals(getFriends(), userJson.getFriends()) && Objects.equals(getOutcomeInvitations(), userJson.getOutcomeInvitations()) && Objects.equals(getIncomeInvitations(), userJson.getIncomeInvitations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getFirstName(), getPassword(), getSurname(), avatar);
    }

    @Override
    public String toString() {
        return "UserJson{" +
                "id=" + getId().toString() +
                ", username='" + getUsername() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", avatar='" + getAvatar() + '\'' +
                ", friendState=" + getFriendState() +
                ", friends=" + getFriends().iterator().next().getUsername() +
                ", outcomeInvitations=" + getOutcomeInvitations() +
                ", incomeInvitations=" +getIncomeInvitations() +
                '}';
    }
}
