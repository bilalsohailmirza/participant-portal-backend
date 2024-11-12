package com.campus.connect.participant.backend;

import com.campus.connect.participant.backend.model.User;
import com.campus.connect.participant.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository users) {
//		return args -> {
//			users.save(new User( UUID.randomUUID(), "user@gmail.com", "passowrd", "", "", "", "", "participant"));
//		};
//	}
//	SpringApplication.run(Application.class, args);
}