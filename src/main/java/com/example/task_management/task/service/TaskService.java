package com.example.task_management.task.service;

import com.example.task_management.task.entity.Task;
import com.example.task_management.task.dto.TaskInput;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task addTask(TaskInput input, String token);

    String deleteTask(String taskId, String token);

    Page<Task> getAllTasks(Pageable pageable, String token);

    Task getTaskById(String taskId, String token);

    Task updateTask(String taskId, String token, TaskInput taskInput);
}
