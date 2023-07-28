package org.rangiffler.db;

import org.rangiffler.config.Config;
import org.apache.commons.lang3.StringUtils;

public enum ServiceDB {

    RANGIFFLER_AUTH("jdbc:postgresql://%s:%d/rangiffler-auth"),
    RANGIFFLER_PHOTO("jdbc:postgresql://%s:%d/rangiffler-photo"),
    RANGIFFLER_USERDATA("jdbc:postgresql://%s:%d/rangiffler-userdata"),
    RANGIFFLER_GEO("jdbc:postgresql://%s:%d/rangiffler-geo");

    private final String jdbcUrl;

    ServiceDB(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUrl() {
        return String.format(jdbcUrl,
                Config.getConfig().getDBHost(),
                Config.getConfig().getDBPort()
        );
    }

    public String p6SpyUrl() {
        String baseUrl = getJdbcUrl();
        return "jdbc:p6spy:" + StringUtils.substringAfter(baseUrl, "jdbc:");
    }
}