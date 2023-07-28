package org.rangiffler.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.test.grpc.BaseGrpcTest;

import java.util.Date;

public class GeneratePhotoExtension implements ParameterResolver, BeforeEachCallback {
    public static ExtensionContext.Namespace GENERATE_PHOTO_NAMESPACE = ExtensionContext.Namespace
            .create(GeneratePhotoExtension.class);
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Photo annotation = context.getRequiredTestMethod()
                .getAnnotation(Photo.class);
        if (annotation != null) {
            PhotoJson photo = new PhotoJson();
            photo.setPhoto(annotation.value());
            photo.setDescription(annotation.description());
            photo.setCountryCode(annotation.countryCode());
            context.getStore(GENERATE_PHOTO_NAMESPACE).put("photo", photo);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(PhotoJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(GENERATE_PHOTO_NAMESPACE).get("photo", PhotoJson.class);
    }
}
