package com.example.task_management.task.controller;


import com.example.task_management.member.entity.Member;
import com.example.task_management.task.dto.TaskDto;
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
        List<TaskDto.TaskResponse> tasks = this.taskService.getAllTasks(member);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/team/{teamId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllTeamTask(@AuthenticationPrincipal Member member, @PathVariable String teamId){
        List<TaskDto.TaskResponse> tasks = this.taskService.getAllTeamTasks(member, teamId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getTaskById(@PathVariable String taskId, @AuthenticationPrincipal Member member){
        TaskDto.TaskResponse result = this.taskService.getTaskById(taskId, member);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addTask(@RequestBody TaskDto.TaskRequest request, @AuthenticationPrincipal Member member){
        TaskDto.TaskResponse result = this.taskService.addTask(request, member);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add/{teamId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addTeamTask(@RequestBody TaskDto.TaskRequest request, @AuthenticationPrincipal Member member, @PathVariable String teamId){
        TaskDto.TaskResponse result = this.taskService.addTeamTask(request, member, teamId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping({"/{taskId}"})
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId, @AuthenticationPrincipal Member member){
        String result = this.taskService.deleteTask(taskId, member);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{taskId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> updateTask(@PathVariable String taskId, @RequestBody TaskDto.TaskRequest request,
                                        @AuthenticationPrincipal Member member){
        TaskDto.TaskResponse result = this.taskService.updateTask(taskId, member, request);
        return ResponseEntity.ok(result);
    }
}
