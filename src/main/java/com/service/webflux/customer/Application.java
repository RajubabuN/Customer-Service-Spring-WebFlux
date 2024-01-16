package com.service.webflux.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * The Class Application.
 */
@SpringBootApplication(scanBasePackages = "com.service.webflux.customer")
@EnableR2dbcRepositories
public class Application {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
