package com.huypq.web.admin.security;

import com.huypq.web.admin.domain.AppUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PermissionTokenEnhancer implements TokenEnhancer {
    private static final Logger logger = LoggerFactory.getLogger(PermissionTokenEnhancer.class);

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addClaims((DefaultOAuth2AccessToken) accessToken, authentication);
        return accessToken;
    }

    private void addClaims(DefaultOAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        logger.info("Permission accessToken " + accessToken);
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        if (additionalInformation.isEmpty()) {
            additionalInformation = new LinkedHashMap<String, Object>();
        }
        if (authentication.getPrincipal() instanceof AppUserDetail) {
            AppUserDetail user = (AppUserDetail) authentication.getPrincipal();
            accessToken.getAdditionalInformation().put("userId", user.getId());
            accessToken.getAdditionalInformation().put("isRootOrAdmin", user.getIsRootOrAdmin());
//            accessToken.getAdditionalInformation().put("domainId", String.valueOf(user.getCurrentDomainId()));
//            if (!user.getIsRootOrAdmin()) {
//                accessToken.getAdditionalInformation().put("permissions", user.getPermissions());
//            }
        }
        accessToken.setAdditionalInformation(additionalInformation);
    }
}
