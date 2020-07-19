package com.hamlt.springcloud_gateway_demo.filter;

import com.hamlt.springcloud_gateway_demo.utils.ReadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * @author Administrator
 * @date 2020-07-17 0:26
 **/

public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //  int i = 1/0;
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        // 设置头部信息
        Consumer<HttpHeaders> headersConsumer = x -> {
            x.add("userId", "1");
            x.add("userName", "zhs");
        };
        serverHttpRequest.mutate().headers(headersConsumer).build();
        exchange.getAttributes().put("val1", "val1");
        String method = serverHttpRequest.getMethodValue();
        if ("POST".equals(method)) {
            Object requestBody = exchange.getAttribute("cachedRequestBodyObject");
            System.out.println("request:" + requestBody);
        } else if ("GET".equals(method)) {
            MultiValueMap<String, String> queryParams = serverHttpRequest.getQueryParams();
            System.out.println("request:" + queryParams);
            return chain.filter(exchange);
        }
        ServerHttpResponseDecorator decoratedResponse = ReadUtils.readResponse(exchange.getResponse());
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}