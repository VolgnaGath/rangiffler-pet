package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import grpc.rangiffler.grpc.Country;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("code")
  private String code;

  @JsonProperty("name")
  private String name;

  public static CountryJson fromGrpcMessage(Country countryMessage) {
    return new CountryJson(UUID.fromString(countryMessage.getId()), countryMessage.getCode(), countryMessage.getName());
  }
}
