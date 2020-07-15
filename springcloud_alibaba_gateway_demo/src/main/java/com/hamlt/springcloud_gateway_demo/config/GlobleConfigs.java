package com.hamlt.springcloud_gateway_demo.config;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
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

/**
 * 获取body 参数
 * https://www.cnblogs.com/hyf-huangyongfei/p/12849406.html
 **/
@Configuration
public class GlobleConfigs {

    @Bean
    public GlobalFilter customFilter() {
        return new CustomGlobalFilter();
    }

    public class CustomGlobalFilter implements GlobalFilter, Ordered {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
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
                System.out.println(queryParams);
                return chain.filter(exchange);
            }
            ServerHttpResponseDecorator decoratedResponse = readResponse(exchange.getResponse());
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }

        private ServerHttpResponseDecorator readResponse(ServerHttpResponse originalResponse) {
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            return new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                        return super.writeWith(fluxBody.map(dataBuffer -> {
                            // probably should reuse buffers
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            //释放掉内存
                            DataBufferUtils.release(dataBuffer);
                            String s = new String(content, Charset.forName("UTF-8"));
                            System.out.println("response:" + s);
                            byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
                            return bufferFactory.wrap(uppedContent);
                        }));
                    }
                    // if body is not a flux. never got there.
                    return super.writeWith(body);
                }
            };
        }

        @Override
        public int getOrder() {
            return -1;
        }
    }

}
