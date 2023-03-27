package com.example.task_management.task.controller;


import com.example.task_management.aop.TeamLock;
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
    @GetMapping("/team/{teamId}")
    @PreAuthorize("hasRole('MEMBER')")
    @TeamLock
    public ResponseEntity<?> getAllTeamTask(@AuthenticationPrincipal Member member, @PathVariable String teamId){
        List<TaskDto.TaskResponse> tasks = this.taskService.getAllTeamTasks(member, teamId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/add/{teamId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addTeamTask(@RequestBody TaskDto.TaskRequest request, @AuthenticationPrincipal Member member,
                                         @PathVariable String teamId){
        TaskDto.TaskResponse result = this.taskService.addTeamTask(request, member, teamId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{teamId}/{taskId}")
    @PreAuthorize("hasRole('MEMBER')")
    @TeamLock
    public ResponseEntity<?> deleteTeamTask(@AuthenticationPrincipal Member member,
                                            @PathVariable String teamId, @PathVariable String taskId){
        String result = this.taskService.deleteTeamTask(teamId, taskId, member);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/update/{teamId}/{taskId}")
    @PreAuthorize("hasRole('MEMBER')")
    @TeamLock
    public ResponseEntity<?> updateTeamTask(@PathVariable String teamId, @PathVariable String taskId, @RequestBody TaskDto.TaskRequest request,
                                        @AuthenticationPrincipal Member member) throws InterruptedException {
        TaskDto.TaskResponse result = this.taskService.updateTeamTask(teamId,taskId, member, request);
        Thread.sleep(5000L);
        return ResponseEntity.ok(result);
    }
}
