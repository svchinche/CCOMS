package com.cloudcomp.ccoms.proxysvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ProxyServerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyServerMicroserviceApplication.class, args);
	}

}
