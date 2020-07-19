package com.hamlt.springcloud_gateway_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
public class SpringCloudGatewaySecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewaySecurityDemoApplication.class, args);
	}

}
