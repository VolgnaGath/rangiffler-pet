package org.rangiffler.service;

import com.google.protobuf.Empty;
import grpc.rangiffler.grpc.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.rangiffler.data.PhotoEntity;
import org.rangiffler.data.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@GrpcService
public class GrpcPhotoService extends RangifflerPhotoServiceGrpc.RangifflerPhotoServiceImplBase {

    private final PhotoRepository photoRepository;

    @Autowired
    public GrpcPhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public void addPhoto(AddPhotoRequest request, StreamObserver<AddPhotoResponse> responseObserver) {

        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setUsername(request.getPhoto().getUsername());
        photoEntity.setCountryCode(request.getPhoto().getCountry().getCode());
        photoEntity.setDescription(request.getPhoto().getDescription());
        photoEntity.setImage(request.getPhoto().getImage());
        PhotoEntity photo = photoRepository.save(photoEntity);
        AddPhotoResponse response = AddPhotoResponse.newBuilder()
                .setPhoto(Photo.newBuilder()
                        .setId(photo.getId().toString())
                        .setUsername(photo.getUsername())
                        .setDescription(photo.getDescription())
                        .setCountry(Country.newBuilder()
                                .setId(request.getPhoto().getCountry().getId())
                                .setName(request.getPhoto().getCountry().getName())
                                .setCode(photo.getCountryCode()).build())
                        .setImage(photo.getImage())
                        .build()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllUserPhotos(GetAllUserPhotosRequest request, StreamObserver<GetAllUserPhotosResponse> responseObserver) {
        List<PhotoEntity> photoEntityList = photoRepository.findAllByUsername(request.getUsername());
        GetAllUserPhotosResponse response = GetAllUserPhotosResponse.newBuilder()
                .addAllPhoto(photoEntityList.stream()
                        .map(e->(Photo.newBuilder()
                                    .setId(e.getId().toString())
                                    .setUsername(e.getUsername())
                                    .setDescription(e.getDescription())
                                    .setCountry(Country.newBuilder().setCode(e.getCountryCode()).build())
                                    .setImage(e.getImage())).build())
                        .toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void editUserPhoto(EditUserPhotoRequest request, StreamObserver<EditUserPhotoResponse> responseObserver) {
        Optional<PhotoEntity> photoById = photoRepository.findById(UUID.fromString(request.getPhoto().getId()));
        if (photoById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find photo by given id: " + request.getPhoto().getId());
        } else {
                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setId(photoById.get().getId());
                photoEntity.setUsername(photoById.get().getUsername());
                photoEntity.setDescription(request.getPhoto().getDescription());
                photoEntity.setCountryCode(request.getPhoto().getCountry().getCode());
                photoEntity.setImage(photoById.get().getImage());
                PhotoEntity updatedPhotoEntity = photoRepository.save(photoEntity);
                EditUserPhotoResponse response = EditUserPhotoResponse.newBuilder()
                        .setPhoto(Photo.newBuilder()
                                .setId(updatedPhotoEntity.getId().toString())
                                .setUsername(updatedPhotoEntity.getUsername())
                                .setCountry(Country.newBuilder()
                                        .setId(request.getPhoto().getCountry().getId())
                                        .setName(request.getPhoto().getCountry().getName())
                                        .setCode(updatedPhotoEntity.getCountryCode()).build())
                                .setDescription(updatedPhotoEntity.getDescription())
                                .setImage(updatedPhotoEntity.getImage())
                                .build()).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
        }
    }

    @Override
    public void getAllFriendsPhotos(GetAllFriendsPhotosRequest request, StreamObserver<GetAllFriendsPhotosResponse> responseObserver) {
        Channel userdataChannel = ManagedChannelBuilder
                .forAddress("localhost", 8092)
                .usePlaintext()
                .build();
        RangifflerUserServiceGrpc.RangifflerUserServiceBlockingStub userStub
                = RangifflerUserServiceGrpc.newBlockingStub(userdataChannel);
        UserFriendsRequest userFriendsRequest = UserFriendsRequest.newBuilder()
                .setUsername(request.getUser().getUsername())
                .setIncludePending(false)
                .build();
        UserFriendsResponse userFriendsResponse = userStub.friends(userFriendsRequest);
        List<User> friends = userFriendsResponse.getUserList();
        List<PhotoEntity> allPhotos = new ArrayList<>();
        for (User friend : friends) {
            List<PhotoEntity> friendsPhoto = photoRepository.findAllByUsername(friend.getUsername());
            allPhotos.addAll(friendsPhoto);
        }
        GetAllFriendsPhotosResponse response = GetAllFriendsPhotosResponse.newBuilder()
                .addAllPhoto(allPhotos.stream().map(e-> (Photo.newBuilder()
                                    .setId(e.getId().toString())
                                    .setUsername(e.getUsername())
                                    .setDescription(e.getDescription())
                                    .setCountry(Country.newBuilder().setCode(e.getCountryCode()).build())
                                    .setImage(e.getImage())
                                    .build()))
                        .toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deletePhoto(DeletePhotoRequest request, StreamObserver<Empty> responseObserver) {
        Optional<PhotoEntity> fileData = photoRepository.findById(UUID.fromString(request.getPhotoId()));
        if(fileData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find photo by given id: " + request.getPhotoId());
        } else {
            photoRepository.delete(fileData.get());
            Empty response = Empty.getDefaultInstance();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

}
