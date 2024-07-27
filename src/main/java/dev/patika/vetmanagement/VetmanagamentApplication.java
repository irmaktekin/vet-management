package dev.patika.vetmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories

public class VetmanagamentApplication {

	public static void main(String[] args) {
		SpringApplication.run(VetmanagamentApplication.class, args);
	}

}
