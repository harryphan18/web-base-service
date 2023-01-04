package com.huypq.web.admin.config;

import com.huypq.web.admin.repository.UserRepository;
import com.huypq.web.admin.security.AppAuthenticationManager;
import com.huypq.web.admin.security.AuthoritiesConstants;
import com.huypq.web.admin.service.CustomTokenService;
import com.huypq.web.admin.service.UserService;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class UaaConfiguration extends AuthorizationServerConfigurerAdapter implements ApplicationContextAware {
    /**
     * Access tokens will not expire any earlier than this.
     */
    private static final int MIN_ACCESS_TOKEN_VALIDITY_SECS = 60;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @EnableResourceServer
    @EnableGlobalMethodSecurity(
            prePostEnabled = true,
            securedEnabled = false,
            jsr250Enabled = false)
    public static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        private final TokenStore tokenStore;

        private final JHipsterProperties jHipsterProperties;

        private final CorsFilter corsFilter;

        public ResourceServerConfiguration(TokenStore tokenStore, JHipsterProperties jHipsterProperties, CorsFilter corsFilter) {
            this.tokenStore = tokenStore;
            this.jHipsterProperties = jHipsterProperties;
            this.corsFilter = corsFilter;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                    .and()
                    .csrf()
                    .disable()
                    .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                    .headers()
                    .frameOptions()
                    .disable()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/api/account/reset-password/init").permitAll()
                    .antMatchers("/api/domain-management/list").permitAll()
                    .antMatchers("/api/account/reset-password/confirm").permitAll()
                    .antMatchers("/api/account/reset-password/finish").permitAll()
                    .antMatchers("/api/account/forgot-password/init").permitAll()
                    .antMatchers("/api/account/forgot-password/confirm").permitAll()
                    .antMatchers("/api/webtool/parent-tags").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/management/health").permitAll()
                    .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .antMatchers("/v2/api-docs/**").permitAll()
                    .antMatchers("/swagger-resources/configuration/ui").permitAll()
                    .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
                    .anyRequest().hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.ROOT_ADMIN);
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("jhipster-uaa").tokenStore(tokenStore);
        }
    }

    private final JHipsterProperties jHipsterProperties;
    private final UaaProperties uaaProperties;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Autowired
    private UserService userService;

    public UaaConfiguration(ApplicationContext applicationContext, JHipsterProperties jHipsterProperties, UaaProperties uaaProperties, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.applicationContext = applicationContext;
        this.jHipsterProperties = jHipsterProperties;
        this.uaaProperties = uaaProperties;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        int accessTokenValidity = uaaProperties.getWebClientConfiguration().getAccessTokenValidityInSeconds();
        accessTokenValidity = Math.max(accessTokenValidity, MIN_ACCESS_TOKEN_VALIDITY_SECS);
        int refreshTokenValidity = uaaProperties.getWebClientConfiguration().getRefreshTokenValidityInSecondsForRememberMe();
        refreshTokenValidity = Math.max(refreshTokenValidity, accessTokenValidity);
        /*
        For a better client design, this should be done by a ClientDetailsService (similar to UserDetailsService).
         */
        clients.inMemory()
                .withClient(uaaProperties.getWebClientConfiguration().getClientId())
                .secret(passwordEncoder.encode(uaaProperties.getWebClientConfiguration().getSecret()))
                .scopes("openid")
                .autoApprove(true)
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code")
                .accessTokenValiditySeconds(accessTokenValidity)
                .refreshTokenValiditySeconds(refreshTokenValidity)
                .and()
                .withClient(jHipsterProperties.getSecurity().getClientAuthorization().getClientId())
                .secret(passwordEncoder.encode(jHipsterProperties.getSecurity().getClientAuthorization().getClientSecret()))
                .scopes("web-app")
                .authorities("ROLE_ADMIN")
                .autoApprove(true)
                .authorizedGrantTypes("client_credentials")
                .accessTokenValiditySeconds((int) jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds())
                .refreshTokenValiditySeconds((int) jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //pick up all  TokenEnhancers incl. those defined in the application
        //this avoids changes to this class if an application wants to add its own to the chain
        Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(new ArrayList<>(tokenEnhancers));
        endpoints
                .authenticationManager(authenticationManager())
                .tokenStore(tokenStore())
                .tokenServices(tokenServices())
                .tokenEnhancer(tokenEnhancerChain)
                .reuseRefreshTokens(false);             //don't reuse or we will run into session inactivity timeouts
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(new ArrayList<>(tokenEnhancers));
        CustomTokenService tokenService = new CustomTokenService(tokenStore(), authenticationManager());
        tokenService.setTokenStore(tokenStore());
        tokenService.setSupportRefreshToken(true);
        tokenService.setTokenEnhancer(tokenEnhancerChain);
        return tokenService;
    }

//    @Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager;

    private AuthenticationManager authenticationManager() {
        return new AppAuthenticationManager(userRepository, passwordEncoder, userService);
    }

    /**
     * Apply the token converter (and enhancer) for token store.
     *
     * @return the {@link JwtTokenStore} managing the tokens.
     */
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * This bean generates an token enhancer, which manages the exchange between JWT access tokens and Authentication
     * in both directions.
     *
     * @return an access token converter configured with the authorization server's public/private keys.
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource(uaaProperties.getKeyStore().getName()), uaaProperties.getKeyStore().getPassword().toCharArray())
                .getKeyPair(uaaProperties.getKeyStore().getAlias());
        converter.setKeyPair(keyPair);
        converter.setAccessTokenConverter(new JwtConverter());
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
                "isAuthenticated()");
    }

    public static class JwtConverter extends DefaultAccessTokenConverter {

        @Override
        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            OAuth2Authentication auth = super.extractAuthentication(map);
            auth.setDetails(map);
            return auth;
        }
    }
}
