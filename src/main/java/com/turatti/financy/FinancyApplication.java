package com.turatti.financy;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class FinancyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancyApplication.class, args);
	}

}
