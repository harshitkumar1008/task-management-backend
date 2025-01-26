package com.harshitkumar.task_management_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskManagementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementBackendApplication.class, args);
	}

}
