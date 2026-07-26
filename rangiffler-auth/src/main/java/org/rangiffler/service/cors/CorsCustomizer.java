package org.rangiffler.service.cors;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CorsCustomizer {

    private final String rangifflerFrontUri;

    @Autowired
    public CorsCustomizer(@Value("${rangiffler-client.base-uri}") String rangifflerFrontUri) {
        this.rangifflerFrontUri = rangifflerFrontUri;
    }

    public void corsCustomizer(@Nonnull HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowCredentials(true);
                cc.setAllowedOrigins(List.of(rangifflerFrontUri));
                cc.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
                cc.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-XSRF-TOKEN"));
                cc.setExposedHeaders(List.of("X-XSRF-TOKEN"));
                return cc;
            };

            c.configurationSource(source);
        });
    }
}
