package com.hamlt.springcloud_gateway_demo.config;

import com.hamlt.springcloud_gateway_demo.filter.CustomGlobalFilter;
import com.hamlt.springcloud_gateway_demo.utils.ReadUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 获取body 参数
 * https://www.cnblogs.com/hyf-huangyongfei/p/12849406.html
 **/
@Configuration
public class GlobleConfigs {
    @Bean
    public GlobalFilter customGlobalFilter() {
        return new CustomGlobalFilter();
    }

    @Bean
    public Predicate bodyPredicate(){
        return o -> true;
    }

}
