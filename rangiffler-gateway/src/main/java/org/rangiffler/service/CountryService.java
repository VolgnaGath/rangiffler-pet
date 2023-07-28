package org.rangiffler.service;

import java.util.List;
import java.util.stream.Collectors;

import com.google.protobuf.Empty;
import grpc.rangiffler.grpc.*;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.rangiffler.model.CountryJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CountryService {

  private static final Logger LOG = LoggerFactory.getLogger(CountryService.class);
  private static final Empty EMPTY = Empty.getDefaultInstance();

  @GrpcClient("grpcGeoClient")
  private RangifflerGeoServiceGrpc.RangifflerGeoServiceBlockingStub rangifflerGeoServiceBlockingStub;

  public List<CountryJson> getAllCountries() {
    try {
      return rangifflerGeoServiceBlockingStub.getAllCountries(EMPTY).getCountryList()
              .stream().map(CountryJson::fromGrpcMessage)
              .collect(Collectors.toList());
    } catch (StatusRuntimeException e) {
      LOG.error("### Error while calling gRPC server ", e);
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
    }
  }

  public CountryJson getCountryByCode(@Nonnull String code) {
    try {
      return CountryJson.fromGrpcMessage(rangifflerGeoServiceBlockingStub.getCountryByCode(GetCountryByCodeRequest.newBuilder()
                      .setCode(code).build())
              .getCountry());
    } catch (StatusRuntimeException e) {
      LOG.error("### Error while calling gRPC server ", e);
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
    }
  }
}
