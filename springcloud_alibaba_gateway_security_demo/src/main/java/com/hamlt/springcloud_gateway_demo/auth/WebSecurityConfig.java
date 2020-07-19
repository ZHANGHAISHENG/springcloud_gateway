package com.hamlt.springcloud_gateway_demo.auth;

import com.hamlt.springcloud_gateway_demo.auth.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;

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

    /*@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll() //option 请求默认放行
                .anyExchange().access(authorizationManager)
                .and()
                .httpBasic()
                .and()
                .formLogin() //启动页面表单登陆,spring security 内置了一个登陆页面/login
                .and().csrf().disable()//必须支持跨域
                .logout().disable();
        return http.build();
    }
*/
    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .formLogin().loginPage("login")
                .and()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/user/login").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}

