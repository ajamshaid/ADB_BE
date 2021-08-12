package com.infotech.adb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authManager;

    private  String publicKey ="-----BEGIN PUBLIC KEY-----\nabd\n-----END PUBLIC KEY-----\n";

//    @Bean
//    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }


    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
       converter.setSigningKey("adb");
        converter.setVerifierKey("adb");
        return converter;
    }
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("psw2$ilk")
                .secret(new BCryptPasswordEncoder().encode("@dbrP$w"))
                .authorizedGrantTypes("password", "client_credentials", "refresh_token")
                .scopes("read", "write", "trust")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.pathMapping("/oauth/token", "/saud/connect/token");
        endpoints.authenticationManager(authManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
//        endpoints.tokenStore(tokenStore())
//                .authenticationManager(authManager);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
     //   oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");

        oauthServer.allowFormAuthenticationForClients();
    }
}
