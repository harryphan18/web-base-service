package com.huypq.web.admin.service;

import com.huypq.web.admin.domain.AppUserDetail;
import com.huypq.web.admin.domain.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Deprecated
public class CustomTokenService extends DefaultTokenServices {
    private JwtTokenStore jwtTokenStore;
    AuthenticationManager authenticationManager;

    public CustomTokenService(JwtTokenStore jwtTokenStore, AuthenticationManager authenticationManager) {
        super();
        this.jwtTokenStore = jwtTokenStore;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return super.readAccessToken(accessToken);
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        OAuth2AccessToken token = super.createAccessToken(authentication);
        if (authentication.getPrincipal() instanceof AppUserDetail) {
            AppUserDetail user = (AppUserDetail) authentication.getPrincipal();
            token.getAdditionalInformation().put("user", user);
        } else if (authentication.getPrincipal() instanceof User) {
            User authUser = (User) authentication.getPrincipal();
            token.getAdditionalInformation().put("user", new AppUserDetail(authUser));
        }
        return token;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)
            throws AuthenticationException {
        if (refreshTokenValue == null) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
        }
        OAuth2RefreshToken refreshToken = jwtTokenStore.readRefreshToken(refreshTokenValue);
        OAuth2Authentication authentication = jwtTokenStore.readAuthenticationForRefreshToken(refreshToken);
        authentication.setDetails(tokenRequest.getRequestParameters());
        Authentication refreshUserAuthentication = this.authenticationManager.authenticate(authentication);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(authentication.getOAuth2Request(), refreshUserAuthentication);
        return super.createAccessToken(oAuth2Authentication);
    }
}
