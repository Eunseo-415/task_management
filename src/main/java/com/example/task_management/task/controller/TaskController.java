package com.example.task_management.task.controller;


import com.example.task_management.task.dto.TaskInput;
import com.example.task_management.task.entity.Task;
import com.example.task_management.task.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    public static final String TOKEN_PREFIX = "Bearer ";
    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllTask(final Pageable pageable, @RequestHeader String authentication){
        Page<Task> tasks = this.taskService.getAllTasks(pageable, authentication.substring(TOKEN_PREFIX.length()));
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getTaskById(@PathVariable String taskId, @RequestHeader String authentication){
        var result = this.taskService.getTaskById(taskId, authentication.substring(TOKEN_PREFIX.length()));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addTask(@RequestBody TaskInput taskInput, @RequestHeader String authentication){
        var result = this.taskService.addTask(taskInput, authentication.substring(TOKEN_PREFIX.length()));
        return ResponseEntity.ok(result.toString());
    }

    @DeleteMapping({"/{taskId}"})
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId, @RequestHeader String authentication){
        String result = this.taskService.deleteTask(taskId, authentication.substring(TOKEN_PREFIX.length()));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{taskId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> updateTask(@PathVariable String taskId, @RequestBody TaskInput taskInput,
                                        @RequestHeader String authentication){
        var result = this.taskService.updateTask(taskId,authentication.substring(TOKEN_PREFIX.length()), taskInput );
        return ResponseEntity.ok(result);
    }
}
