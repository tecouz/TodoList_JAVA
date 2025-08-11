package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.todolist.repository.TaskRepository;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tasks", taskRepo.findAll());
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var task = taskRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("task", task);
        return "tasks/detail";
    }
}
