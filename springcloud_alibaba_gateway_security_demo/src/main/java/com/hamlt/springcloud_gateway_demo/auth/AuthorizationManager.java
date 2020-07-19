package com.hamlt.springcloud_gateway_demo.auth;

/**
  * 鉴权管理器，用于判断是否有资源的访问权限
  * Created by macro on 2020/6/19.
  */
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.List;

@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //获取当前路径可访问角色列表
        List<String> authorities = Collections.singletonList("ROLE_USER");
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(true));
    }

}
