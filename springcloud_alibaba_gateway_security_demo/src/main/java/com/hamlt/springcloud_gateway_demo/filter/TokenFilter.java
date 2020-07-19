package com.hamlt.springcloud_gateway_demo.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * @date 2020-07-19 18:11
 **/
@Component
public class TokenFilter implements WebFilter {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //直接使用 tokenStore读取用户授权信息
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication("2059e14d-075c-48c6-8d39-3a8e5e16b228");
        oAuth2Authentication.setAuthenticated(true);
        Context context = ReactiveSecurityContextHolder.withAuthentication(oAuth2Authentication);
        return chain.filter(exchange).subscriberContext(x -> context);
    }
}
