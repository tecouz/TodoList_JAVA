package com.example.todolist.api;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    // Liste des tâches
    @GetMapping
    public List<Task> list() {
        return taskRepo.findAll();
    }

    // Détail tâche
    @GetMapping("/{id}")
    public Task detail(@PathVariable Long id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    // Création tâche
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody Task task) {
        // Optionnel : vérifier que l'utilisateur associé existe
        if (task.getUser() != null && task.getUser().getId() != null) {
            boolean userExists = userRepo.existsById(task.getUser().getId());
            if (!userExists) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Associated user not found");
            }
        }
        return taskRepo.save(task);
    }

    // Mise à jour tâche
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        if (!taskRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        // Optionnel : vérifier que l'utilisateur associé existe
        if (task.getUser() != null && task.getUser().getId() != null) {
            boolean userExists = userRepo.existsById(task.getUser().getId());
            if (!userExists) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Associated user not found");
            }
        }
        task.setId(id);
        return taskRepo.save(task);
    }

    // Suppression tâche
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!taskRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        taskRepo.deleteById(id);
    }
}
