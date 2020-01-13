package com.cloudcomp.ccoms.configserver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = ConfigServerMicroserviceApplication.class , webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ConfigServerMicroserviceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
