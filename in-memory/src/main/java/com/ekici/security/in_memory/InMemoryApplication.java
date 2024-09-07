package com.ekici.security.in_memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InMemoryApplication {

	//InMemoryUserDetailsManager kuullandigimiz icin in-memory auth diyoruz

	public static void main(String[] args) {
		SpringApplication.run(InMemoryApplication.class, args);
	}

}
