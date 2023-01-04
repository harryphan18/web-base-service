package com.huypq.web.admin.service.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class OauthException extends OAuth2Exception {
    private String entityName;
    private String errorKey;
    private String title;
    private Long loginFailedCount;

    public OauthException(String title, String entityName, String errorKey, Long loginFailedCount) {
        super(title);
        this.entityName = entityName;
        this.errorKey = errorKey;
        this.title = title;
        this.loginFailedCount = loginFailedCount;
    }
}
