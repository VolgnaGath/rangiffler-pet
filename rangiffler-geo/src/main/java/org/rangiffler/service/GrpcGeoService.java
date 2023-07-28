package org.rangiffler.service;

import com.google.protobuf.Empty;
import grpc.rangiffler.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.rangiffler.data.CountryEntity;
import org.rangiffler.data.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@GrpcService
public class GrpcGeoService extends RangifflerGeoServiceGrpc.RangifflerGeoServiceImplBase {
    private final CountryRepository countryRepository;

    @Autowired
    public GrpcGeoService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void getAllCountries(Empty request, StreamObserver<GetAllCountriesResponse> responseObserver) {
        List<CountryEntity> all = countryRepository.findAll();
        GetAllCountriesResponse response = GetAllCountriesResponse.newBuilder()
                .addAllCountry(all.stream().map(e -> Country.newBuilder()
                                .setId(e.getId().toString())
                                .setCode(e.getCode())
                                .setName(e.getName())
                                .build())
                        .toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCountryByCode(GetCountryByCodeRequest request, StreamObserver<GetCountryByCodeResponse> responseObserver) {
        Optional<CountryEntity> countryEntity = Optional.ofNullable(countryRepository.findByCode(request.getCode()));
        if (countryEntity.isPresent()) {
            GetCountryByCodeResponse response = GetCountryByCodeResponse.newBuilder().setCountry(Country.newBuilder()
                    .setId(countryEntity.get().getId().toString())
                    .setCode(countryEntity.get().getCode())
                    .setName(countryEntity.get().getName())
                    .build()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find country by given code: " + request.getCode());
        }
    }

    @Override
    public void getCountryByName(GetCountryByNameRequest request, StreamObserver<GetCountryByNameResponse> responseObserver) {
        Optional<CountryEntity> countryEntity = Optional.ofNullable(countryRepository.findByName(request.getCountryName()));
        if (countryEntity.isPresent()) {
            GetCountryByNameResponse response = GetCountryByNameResponse.newBuilder().setCountry(Country.newBuilder()
                    .setId(countryEntity.get().getId().toString())
                    .setCode(countryEntity.get().getCode())
                    .setName(countryEntity.get().getName())
                    .build()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find country by given country name: " + request.getCountryName());
        }
    }

    @Override
    public void getCountry(GetCountryRequest request, StreamObserver<GetCountryResponse> responseObserver) {
        Optional<CountryEntity> countryEntity = countryRepository.findById(UUID.fromString(request.getCountry().getId()));
        if (countryEntity.isPresent()) {
            GetCountryResponse response = GetCountryResponse.newBuilder().setCountry(Country.newBuilder()
                    .setId(countryEntity.get().getId().toString())
                    .setCode(countryEntity.get().getCode())
                    .setName(countryEntity.get().getName())
                    .build()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find country by given id: " + request.getCountry().getId());
        }
    }
}
