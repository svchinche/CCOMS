package com.cloudcomp.ccoms.orgsvc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
