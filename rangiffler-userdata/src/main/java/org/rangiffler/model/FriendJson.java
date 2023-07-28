package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class FriendJson {

    @JsonProperty("username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendJson that)) return false;
        return Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return "FriendJson{" +
                "username='" + getUsername() + '\'' +
                '}';
    }
}
