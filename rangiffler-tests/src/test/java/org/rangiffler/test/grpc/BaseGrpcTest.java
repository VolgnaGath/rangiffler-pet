package org.rangiffler.test.grpc;

import com.google.protobuf.Empty;
import grpc.rangiffler.grpc.RangifflerGeoServiceGrpc;
import grpc.rangiffler.grpc.RangifflerPhotoServiceGrpc;
import grpc.rangiffler.grpc.RangifflerUserServiceGrpc;
import org.rangiffler.config.Config;
import org.rangiffler.jupiter.annotation.GrpcTest;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.qameta.allure.grpc.AllureGrpc;
import org.rangiffler.utils.DataUtils;

@GrpcTest
public class BaseGrpcTest {

    protected static final Config CFG = Config.getConfig();

    protected static final Empty EMPTY = Empty.getDefaultInstance();
    private static Channel channelUserData;
    private static Channel channelPhoto;
    private static Channel channelGeo;

    static {
        channelUserData = ManagedChannelBuilder
                .forAddress(CFG.getUserDataGrpcAddress(), CFG.getUserDataGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();

        channelPhoto = ManagedChannelBuilder
                .forAddress(CFG.getPhotoGrpcAddress(), CFG.getPhotoGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();
        channelGeo = ManagedChannelBuilder
                .forAddress(CFG.getGeoGrpcAddress(), CFG.getGeoGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();
    }

    protected final RangifflerUserServiceGrpc.RangifflerUserServiceBlockingStub userDataStub
            = RangifflerUserServiceGrpc.newBlockingStub(channelUserData);
    protected final RangifflerGeoServiceGrpc.RangifflerGeoServiceBlockingStub geoStub
            = RangifflerGeoServiceGrpc.newBlockingStub(channelGeo);
    protected final RangifflerPhotoServiceGrpc.RangifflerPhotoServiceBlockingStub photoStub
            = RangifflerPhotoServiceGrpc.newBlockingStub(channelPhoto);

    public RangifflerUserServiceGrpc.RangifflerUserServiceBlockingStub getUserDataStub() {
        return userDataStub;
    }

    public RangifflerGeoServiceGrpc.RangifflerGeoServiceBlockingStub getGeoStub() {
        return geoStub;
    }

    public RangifflerPhotoServiceGrpc.RangifflerPhotoServiceBlockingStub getPhotoStub() {
        return photoStub;
    }
}
