package org.rangiffler.config;


import com.codeborne.selenide.Configuration;

public class LocalConfig implements Config {

    static final LocalConfig INSTANCE = new LocalConfig();

    static {
        Configuration.browser = "chrome";
        Configuration.browserVersion = "110.0";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.headless = false;
    }

    private LocalConfig() {
    }

    @Override
    public String getDBHost() {
        return "localhost";
    }

    @Override
    public String getDBLogin() {
        return "postgres";
    }

    @Override
    public String getDBPassword() {
        return "secret";
    }

    @Override
    public String getGatewayUrl() {
        return "http://127.0.0.1:8080";
    }

    @Override
    public int getDBPort() {
        return 5432;
    }

    @Override
    public String getFrontUrl() {
        return "http://127.0.0.1:3001";
    }

    @Override
    public String getAuthUrl() {
        return "http://127.0.0.1:9000";
    }

    @Override
    public String getUserDataGrpcAddress() {
        return "localhost";
    }

    @Override
    public int getUserDataGrpcPort() {
        return 8092;
    }

    @Override
    public String getPhotoGrpcAddress() {return "localhost";}

    @Override
    public int getPhotoGrpcPort() { return 8093;}
    @Override
    public String getGeoGrpcAddress() {return "localhost";}
    @Override
    public int getGeoGrpcPort() {return 8094;}
    @Override
    public String kafkaAddress() {
        return "localhost:9092";
    }

}
