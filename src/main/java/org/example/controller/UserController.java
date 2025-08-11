package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.todolist.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var user = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("user", user);
        return "users/detail";
    }
}
