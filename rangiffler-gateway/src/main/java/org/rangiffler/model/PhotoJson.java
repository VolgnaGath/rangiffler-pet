package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import grpc.rangiffler.grpc.Photo;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PhotoJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("country")
  private CountryJson countryJson;

  @JsonProperty("photo")
  private String photo;

  @JsonProperty("description")
  private String description;

  @JsonProperty("username")
  private String username;


  public static PhotoJson fromGrpcMessage(Photo photoMessage, CountryJson countryJson) {
    return new PhotoJson(UUID.fromString(photoMessage.getId()), countryJson,
            photoMessage.getImage(),
            photoMessage.getDescription(), photoMessage.getUsername());
  }
}
