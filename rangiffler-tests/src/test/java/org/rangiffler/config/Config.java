package org.rangiffler.config;

import java.util.List;

public interface Config {

    static Config getConfig() {
            return LocalConfig.INSTANCE;
    }

    String getDBHost();

    String getDBLogin();

    String getDBPassword();

    String getGatewayUrl();

    int getDBPort();

    String getFrontUrl();

    String getAuthUrl();

    String getUserDataGrpcAddress();

    int getUserDataGrpcPort();

    String getPhotoGrpcAddress();

    int getPhotoGrpcPort();

    String getGeoGrpcAddress();

    int getGeoGrpcPort();
    String kafkaAddress();

    default List<String> kafkaTopics() {
        return List.of("users");
    }

}
