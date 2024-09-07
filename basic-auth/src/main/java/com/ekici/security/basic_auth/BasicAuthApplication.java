package com.ekici.security.basic_auth;

import com.ekici.security.basic_auth.dto.CreateUserRequest;
import com.ekici.security.basic_auth.model.Role;
import com.ekici.security.basic_auth.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class BasicAuthApplication implements CommandLineRunner {

	private final UserService userService;

    public BasicAuthApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
		SpringApplication.run(BasicAuthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createDummyData();
	}

	private void createDummyData(){
		CreateUserRequest request1 =  new CreateUserRequest("furkan", "furkan", "pass", Set.of(Role.ROLE_USER));
		userService.createUser(request1);

		CreateUserRequest request2 =  new CreateUserRequest("busra", "busra", "pass", Set.of(Role.ROLE_ADMIN));
		userService.createUser(request2);
	}
}
