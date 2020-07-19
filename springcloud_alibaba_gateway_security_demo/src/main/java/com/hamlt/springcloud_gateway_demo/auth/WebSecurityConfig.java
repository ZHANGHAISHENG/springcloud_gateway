package com.hamlt.springcloud_gateway_demo.auth;

import com.hamlt.springcloud_gateway_demo.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
import org.springframework.security.web.server.context.ReactorContextWebFilter;
import org.springframework.security.web.server.savedrequest.ServerRequestCacheWebFilter;
import org.springframework.web.server.WebFilter;

import java.util.Iterator;

/**
 * @author Administrator
 * @date 2020-07-19 11:29
 * @TODO 不生效
 **/
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private TokenFilter tokenFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
       // http.oauth2ResourceServer().bearerTokenConverter(new MyAuthenticationConverter());
       // new AuthenticationWebFilter((ReactiveAuthenticationManager) authorizationManager);
        http.addFilterBefore(tokenFilter, SecurityWebFiltersOrder.AUTHORIZATION);
        http.formLogin().and().authorizeExchange()
            .anyExchange().access(authorizationManager)
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)//处理未授权
                .authenticationEntryPoint(authenticationEntryPoint)//处理未认证
            .and().csrf().disable();//必须支持跨域
        SecurityWebFilterChain chain = http.build();
        setConverter(chain); // 可以直接定义拦截器处理token
        return chain;
    }

    public void setConverter(SecurityWebFilterChain chain) {
        Iterator<WebFilter> weIterable = chain.getWebFilters().toIterable().iterator(); //最后一filter是AuthorizationWebFilter,执行权限校验
        while(weIterable.hasNext()) {
            WebFilter f = weIterable.next(); // AuthenticationWebFilter 拦截方式未生效还有待研究
            if(f instanceof AuthenticationWebFilter) { // 声明了 formLogin 才会添加AuthenticationWebFilter
                AuthenticationWebFilter webFilter = (AuthenticationWebFilter) f;
                webFilter.setServerAuthenticationConverter(new MyAuthenticationConverter());
            }
        }
    }


}

