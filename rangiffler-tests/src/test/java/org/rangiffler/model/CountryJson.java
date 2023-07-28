package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class CountryJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("code")
  private String code;

  @JsonProperty("name")
  private String name;

  public CountryJson() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "CountryJson{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
