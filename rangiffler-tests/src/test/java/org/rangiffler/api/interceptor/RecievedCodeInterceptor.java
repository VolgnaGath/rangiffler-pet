package org.rangiffler.api.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.internal.annotations.EverythingIsNonNull;
import org.apache.commons.lang3.StringUtils;
import org.rangiffler.api.context.SessionContext;

import java.io.IOException;

public class RecievedCodeInterceptor implements Interceptor {

    @Override
    @EverythingIsNonNull
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String location = response.header("Location");
        if (location != null && location.contains("code=")) {
            SessionContext.getInstance().setCode(StringUtils.substringAfter(location, "code="));
        }
        return response;
    }
}