package com.example.task_management.task.controller;


import com.example.task_management.member.entity.Member;
import com.example.task_management.task.dto.TaskInput;
import com.example.task_management.task.entity.Task;
import com.example.task_management.task.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllTask(@AuthenticationPrincipal Member member){
        List<Task> tasks = this.taskService.getAllTasks(member);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getTaskById(@PathVariable String taskId, @AuthenticationPrincipal Member member){
        var result = this.taskService.getTaskById(taskId, member);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addTask(@RequestBody TaskInput taskInput, @AuthenticationPrincipal Member member){
        var result = this.taskService.addTask(taskInput, member);
        return ResponseEntity.ok(result.toString());
    }

    @DeleteMapping({"/{taskId}"})
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId, @AuthenticationPrincipal Member member){
        String result = this.taskService.deleteTask(taskId, member);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{taskId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> updateTask(@PathVariable String taskId, @RequestBody TaskInput taskInput,
                                        @AuthenticationPrincipal Member member){
        var result = this.taskService.updateTask(taskId, member, taskInput );
        return ResponseEntity.ok(result);
    }
}
