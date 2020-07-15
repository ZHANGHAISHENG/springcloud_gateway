package com.hamlt.springcloud_gateway_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * https://blog.csdn.net/zxl646801924/article/details/80764279
 *
 * 整合nacos
 * https://blog.csdn.net/cdy1996/article/details/87391609
 *
 * 负载ribbon：
 * https://www.cnblogs.com/ye-feng-yu/p/11106006.html
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudGatewayDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudGatewayDemoApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

		return builder.routes()
				.route(r -> r.readBody(String.class, requestBody -> { // 返回true才能读取body内容
									return true;
								}).uri("lb://spring-boot-test")).build();

	}

/*

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("path_route", r -> r.path("/get")
						.uri("http://httpbin.org"))
				.route("host_route", r -> r.host("*.myhost.org")
						.uri("http://httpbin.org"))
				.route("rewrite_route", r -> r.host("*.rewrite.org")
						.filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
						.uri("http://httpbin.org"))
				.route("hystrix_route", r -> r.host("*.hystrix.org")
						.filters(f -> f.hystrix(c -> c.setName("slowcmd")))
						.uri("http://httpbin.org"))
				.route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
						.filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
						.uri("http://httpbin.org"))
				.route("limit_route", r -> r
						.host("*.limited.org").and().path("/anything/**")
						.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
						.uri("http://httpbin.org"))
				.build();
	}
*/

}
