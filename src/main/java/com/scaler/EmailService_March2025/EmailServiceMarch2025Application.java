package com.scaler.EmailService_March2025;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class EmailServiceMarch2025Application {

	public static void main(String[] args) {
		SpringApplication.run(EmailServiceMarch2025Application.class, args);
	}

}
