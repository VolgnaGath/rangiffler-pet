package org.rangiffler.test.grpc;

import com.google.protobuf.Empty;
import grpc.rangiffler.grpc.*;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RangifflerGeoGrpcTest extends BaseGrpcTest{

    @AllureId("110")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис rangiffler-geo должен найти все страны")
    void getAllCountriesTest() {
        GetAllCountriesResponse allCountries = step("Get all cuntries", () ->
                geoStub.getAllCountries(Empty.getDefaultInstance())
        );
        final List<Country> countryList = allCountries.getCountryList();
        step("Check that response contains countries", () ->
                assertThat(countryList.size()).isGreaterThan(50)
        );
    }
    @AllureId("111")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис rangiffler-geo должен найти страну по коду")
    void getCountryByCodeTest() {
        GetCountryByCodeRequest getCountryByCodeRequest = GetCountryByCodeRequest.newBuilder().setCode("RU").build();
        GetCountryByCodeResponse getCountryByCodeResponse = geoStub.getCountryByCode(getCountryByCodeRequest);
        step("Check that response contains Russia", () ->
                assertThat(getCountryByCodeResponse.getCountry().getName()).isEqualTo("Russia")
        );
        step("Check that Russia contains id", () ->
                assertThat(getCountryByCodeResponse.getCountry().getId()).isNotNull()
        );
    }
    @AllureId("112")
    @Test
    @Tag("gRPC")
    @DisplayName("gRPC: Сервис rangiffler-geo должен найти страну по названию")
    void getCountryByNameTest() {
        GetCountryByNameRequest getCountryByNameRequest = GetCountryByNameRequest.newBuilder().setCountryName("Canada").build();
        GetCountryByNameResponse getCountryByNameResponse = geoStub.getCountryByName(getCountryByNameRequest);
        step("Check that response contains Russia", () ->
                assertThat(getCountryByNameResponse.getCountry().getName()).isEqualTo("Canada")
        );
        step("Check that Russia contains id", () ->
                assertThat(getCountryByNameResponse.getCountry().getId()).isNotNull()
        );
    }
}
