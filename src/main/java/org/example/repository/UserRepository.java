package com.example.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todolist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}
