package org.rangiffler.service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import grpc.rangiffler.grpc.*;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.rangiffler.model.CountryJson;
import org.rangiffler.model.PhotoJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PhotoService {
    private static final Logger LOG = LoggerFactory.getLogger(PhotoService.class);

    @GrpcClient("grpcPhotoClient")
    private RangifflerPhotoServiceGrpc.RangifflerPhotoServiceBlockingStub rangifflerPhotoServiceBlockingStub;

    @GrpcClient("grpcGeoClient")
    private RangifflerGeoServiceGrpc.RangifflerGeoServiceBlockingStub rangifflerGeoServiceBlockingStub;

    public PhotoJson addPhoto(@Nonnull PhotoJson photoJson) {
        try {
            Photo photo = rangifflerPhotoServiceBlockingStub
                    .addPhoto(AddPhotoRequest.newBuilder()
                            .setPhoto(Photo.newBuilder().setCountry(Country.newBuilder()
                                            .setId(photoJson.getCountryJson().getId().toString())
                                            .setName(photoJson.getCountryJson().getName())
                                            .setCode(photoJson.getCountryJson().getCode()).build())
                                    .setUsername(photoJson.getUsername())
                                    .setDescription(photoJson.getDescription())
                                    .setImage(photoJson.getPhoto()))
                            .build()).getPhoto();
            CountryJson countryJson = photoJson.getCountryJson();
            return  PhotoJson.fromGrpcMessage(photo,
                    countryJson);
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public List<PhotoJson> getAllUserPhotos(@Nonnull String username) {
        try {
            List<Photo> photoList = rangifflerPhotoServiceBlockingStub.getAllUserPhotos(GetAllUserPhotosRequest.newBuilder().setUsername(username).build()).getPhotoList();
            List<PhotoJson> photoJsonList = new ArrayList<>();
            for (Photo photo : photoList) {
                CountryJson country = CountryJson.fromGrpcMessage(rangifflerGeoServiceBlockingStub.getCountryByCode(GetCountryByCodeRequest.newBuilder()
                        .setCode(photo.getCountry().getCode()).build()).getCountry());
                photoJsonList.add(PhotoJson.fromGrpcMessage(photo, country));
            }
            return photoJsonList;
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public PhotoJson editPhoto(@Nonnull PhotoJson photoJson) {
        try {
            Photo photo = rangifflerPhotoServiceBlockingStub.editUserPhoto(EditUserPhotoRequest.newBuilder()
                    .setPhoto(Photo.newBuilder().setId(photoJson.getId().toString())
                            .setCountry(Country.newBuilder()
                                    .setId(photoJson.getCountryJson().getId().toString())
                                    .setName(photoJson.getCountryJson().getName())
                                    .setCode(photoJson.getCountryJson().getCode()).build())
                            .setUsername(photoJson.getUsername())
                            .setDescription(photoJson.getDescription())
                            .setImage(photoJson.getPhoto()))
                    .build()).getPhoto();
            CountryJson countryJson = photoJson.getCountryJson();
            return PhotoJson.fromGrpcMessage(photo, countryJson);
        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

  public List<PhotoJson> getAllFriendsPhotos(@Nonnull String username) {
      List<Photo> photoList = rangifflerPhotoServiceBlockingStub.getAllFriendsPhotos(GetAllFriendsPhotosRequest.newBuilder()
                .setUser(User.newBuilder().setUsername(username).build()).build()).getPhotoList();
      List<PhotoJson> photoJsonList = new ArrayList<>();
      for (Photo photo : photoList) {
          CountryJson country = CountryJson.fromGrpcMessage(rangifflerGeoServiceBlockingStub.getCountryByCode(GetCountryByCodeRequest.newBuilder()
                  .setCode(photo.getCountry().getCode()).build()).getCountry());
          photoJsonList.add(PhotoJson.fromGrpcMessage(photo, country));
      }
      return photoJsonList;
  }

    public void deletePhoto(@Nonnull String username, @Nonnull UUID photoId) {
        rangifflerPhotoServiceBlockingStub.deletePhoto(DeletePhotoRequest.newBuilder().setUsername(username).setPhotoId(photoId.toString()).build());
    }
}
