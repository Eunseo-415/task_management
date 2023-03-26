package com.example.task_management.task.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.task.dto.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto.TaskResponse addTask(TaskDto.TaskRequest input, Member member);

    String deleteTask(String taskId,  Member member);

    List<TaskDto.TaskResponse> getAllTasks(Member member);

    TaskDto.TaskResponse getTaskById(String taskId, Member member);

    TaskDto.TaskResponse updateTask(String taskId, Member member, TaskDto.TaskRequest taskRequest);

    List<TaskDto.TaskResponse> getAllTeamTasks(Member member, String teamID);

    TaskDto.TaskResponse addTeamTask(TaskDto.TaskRequest request, Member member, String teamId);
}
