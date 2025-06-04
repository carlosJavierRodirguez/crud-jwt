package com.example.crud_jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class CrudJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudJwtApplication.class, args);
	}

}
