package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;


public class PhotoJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("country")
  private String countryCode;

  @JsonProperty("testdata")
  private String photo;

  @JsonProperty("description")
  private String description;

  @JsonProperty("username")
  private String username;

  public PhotoJson() {

  }
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
