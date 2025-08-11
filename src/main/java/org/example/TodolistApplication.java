package com.example.todolist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.todolist.model.User;
import com.example.todolist.model.Task;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.repository.TaskRepository;

@SpringBootApplication
public class TodolistApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }

    // Initialise des données au démarrage
    @Bean
    CommandLineRunner init(UserRepository userRepo, TaskRepository taskRepo) {
        return args -> {
            User alice = new User("alice", "alice@exemple.fr");
            User bob = new User("bob", "bob@exemple.fr");
            userRepo.save(alice);
            userRepo.save(bob);

            taskRepo.save(new Task("acheter des clopes", "2 paquets", false, alice));
            taskRepo.save(new Task("révision", "révision pour partiel", true, alice));
            taskRepo.save(new Task("monter le bureau", "a faire avant le week end", false, bob));
        };
    }
}
