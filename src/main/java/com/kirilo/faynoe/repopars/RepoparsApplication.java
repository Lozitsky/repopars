package com.kirilo.faynoe.repopars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RepoparsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepoparsApplication.class, args);
	}
}
