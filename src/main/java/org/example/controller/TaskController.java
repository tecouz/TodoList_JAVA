package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    // Liste des tâches
    @GetMapping
    public String list(Model model) {
        model.addAttribute("tasks", taskRepo.findAll());
        return "tasks/list";
    }

    // Détail d'une tâche
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var task = taskRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("task", task);
        return "tasks/detail";
    }

    // Formulaire création
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userRepo.findAll());
        return "tasks/form";
    }

    // Création
    @PostMapping
    public String save(@ModelAttribute Task task, @RequestParam Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        task.setUser(user);
        taskRepo.save(task);
        return "redirect:/tasks";
    }

    // Formulaire édition
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var task = taskRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("task", task);
        model.addAttribute("users", userRepo.findAll());
        return "tasks/form";
    }

    // Mise à jour
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Task task, @RequestParam Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        task.setId(id);
        task.setUser(user);
        taskRepo.save(task);
        return "redirect:/tasks";
    }

    // Suppression
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        taskRepo.deleteById(id);
        return "redirect:/tasks";
    }
}
