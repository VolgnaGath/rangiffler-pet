package org.rangiffler.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class UserJson {
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonProperty("username")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("friendState")
    private FriendState friendState;

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

    public FriendState getFriendState() {
        return friendState;
    }

    public void setFriendState(FriendState friendState) {
        this.friendState = friendState;
    }

    @Override
    public String toString() {
        return "UserJson{" +
                "username='" + getUsername() + '\'' +
                ", firstname='" + getFirstname() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", friendState=" + getFriendState() +
                '}';
    }
}


