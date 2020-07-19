package com.hamlt.springcloud_gateway_demo.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @date 2020-07-19 16:54
 **/
public class MyAuthenticationConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        //从header中获取登陆用户信息
        exchange.getRequest().getHeaders().getFirst("token");
       //添加用户信息到spring security之中。
       User user = new User("zhs", "123456", true, true, true, true,
               AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
       authenticationToken.setAuthenticated(true);
       return Mono.just(authenticationToken);
    }
}
