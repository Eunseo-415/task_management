package com.example.task_management.task.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.task.entity.Task;
import com.example.task_management.task.dto.TaskInput;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    Task addTask(TaskInput input, Member member);

    String deleteTask(String taskId,  Member member);

    List<Task> getAllTasks(Member member);

    Task getTaskById(String taskId, Member member);

    Task updateTask(String taskId, Member member, TaskInput taskInput);
}
