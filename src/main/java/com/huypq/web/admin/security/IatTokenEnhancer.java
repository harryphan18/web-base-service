package com.huypq.web.admin.security;

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
public class IatTokenEnhancer implements TokenEnhancer {
    private static final Logger logger = LoggerFactory.getLogger(IatTokenEnhancer.class);

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addClaims((DefaultOAuth2AccessToken) accessToken);
        return accessToken;
    }

    private void addClaims(DefaultOAuth2AccessToken accessToken) {
        logger.info("Iat accessToken " + accessToken);
        DefaultOAuth2AccessToken token = accessToken;
        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        if (additionalInformation.isEmpty()) {
            additionalInformation = new LinkedHashMap<String, Object>();
        }
        //add "iat" claim with current time in secs
        //this is used for an inactive session timeout
        additionalInformation.put("iat", new Integer((int)(System.currentTimeMillis()/1000L)));
        token.setAdditionalInformation(additionalInformation);
    }
}
