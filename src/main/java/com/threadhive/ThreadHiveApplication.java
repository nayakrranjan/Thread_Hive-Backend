package com.threadhive;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThreadHiveApplication {
	public static void main(String[] args) {
		// Get all .env file from this directory
		Dotenv env = Dotenv.configure().directory(".").ignoreIfMissing().load();

		// Load all the key, values from the .env as system variable
		env.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		// Start the application
		SpringApplication.run(ThreadHiveApplication.class, args);
	}

	public enum ROLES { ADMIN, USER; }
}
