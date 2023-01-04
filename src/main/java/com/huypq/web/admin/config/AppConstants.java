package com.huypq.web.admin.config;

public final class AppConstants {
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final Integer PERMISSION_TYPE_READ = 0;
    public static final Integer PERMISSION_TYPE_WRITE = 1;

    public static final String CLIENT_IP_KEY = "clientIp";
    public static final String CLIENT_DEVICE_KEY = "clientDevice";
}
