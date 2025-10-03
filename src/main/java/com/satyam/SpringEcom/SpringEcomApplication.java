package com.satyam.SpringEcom;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Log4j2
@SpringBootApplication
public class SpringEcomApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringEcomApplication.class, args);
        log.info("Spring-Ecom Started Successfully");
	}

}
