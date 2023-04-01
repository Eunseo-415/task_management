package com.example.task_management.task.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.task.dto.TaskDto;
import com.example.task_management.task.entity.Task;
import com.example.task_management.task.repository.TaskRepository;
import com.example.task_management.team.entity.Team;
import com.example.task_management.team.entity.TeamMember;
import com.example.task_management.team.repository.TeamMemberRepository;
import com.example.task_management.team.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    @CacheEvict(key = "#member.email", value = "tasks")
    public TaskDto.TaskResponse addTask(TaskDto.TaskRequest input, Member member) {
        Task task = input.toEntity();
        task.setMember(member);
        this.taskRepository.save(task);
        log.info("Task added by user: " + member.getName());
        return new TaskDto.TaskResponse(task);
    }

    @Override
    @CacheEvict(key = "#member.email", value = "tasks")
    public String deleteTask(String taskId,  Member member) {
        Task task = taskRepository.findByTaskIdAndMember_MemberId(taskId, member.getMemberId())
                        .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
        task.setDeletedDateTime(LocalDateTime.now());
        taskRepository.save(task);
        return task.getTitle();
    }

    @Override
    @Cacheable(key = "#member.email", value = "tasks")
    public List<TaskDto.TaskResponse> getAllTasks(Member member) {
        return this.taskRepository.findAllByDeletedDateTimeIsNullAndMember_MemberId(member.getMemberId())
                .stream().map(TaskDto.TaskResponse::new).collect(Collectors.toList());
    }

    @Override
    public TaskDto.TaskResponse getTaskById(String taskId, Member member) {
        Task task = this.taskRepository.findByTaskIdAndMember_MemberId(taskId, member.getMemberId())
                .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
        return new TaskDto.TaskResponse(task);
    }

    @Override
    @CacheEvict(key = "#member.email", value = "tasks")
    public TaskDto.TaskResponse updateTask(String taskId, Member member, TaskDto.TaskRequest request) {
        Task task = this.taskRepository.findByTaskIdAndMember_MemberId(taskId, member.getMemberId())
                .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
        task.setFinished(request.isFinished());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadLineDateTime(request.getDeadLineDateTime());
        task.setPriority(request.getPriority());
        return new TaskDto.TaskResponse(this.taskRepository.save(task));
    }

    @Override
    @Cacheable(key = "#teamId", value = "tasks")
    public List<TaskDto.TaskResponse> getAllTeamTasks(Member member, String teamId) {
        if (!this.teamMemberRepository.existsByMember_MemberIdAndTeamTeamId(member.getMemberId(), teamId)){
            throw new RuntimeException("This member is not a team member");
        }
        return this.taskRepository.findAllByDeletedDateTimeIsNullAndTeamTeamId(teamId)
                .stream().map(TaskDto.TaskResponse::new).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(key = "#teamId", value = "tasks")
    public TaskDto.TaskResponse addTeamTask(TaskDto.TaskRequest request, Member member, String teamId) {
        if (!this.teamMemberRepository.existsByMember_MemberIdAndTeamTeamId(member.getMemberId(), teamId)){
            throw new RuntimeException("This member is not a team member");
        }
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Cannot find team: " + teamId));
        Task task = request.toEntity();
        task.setMember(member);
        task.setTeam(team);
        this.taskRepository.save(task);
        log.info("Task added by user: " + member.getName());
        return new TaskDto.TaskResponse(task);
    }


    //TODO
    @Override
    @CacheEvict(key = "#teamId", value = "tasks")
    public String deleteTeamTask(String teamId, String taskId, Member member) {
        if (!this.teamMemberRepository.existsByMember_MemberIdAndTeamTeamId(member.getMemberId(), teamId)){
            throw new RuntimeException("This member is not a team member");
        }
        Task task = taskRepository.findByTaskIdAndTeamTeamId(taskId, teamId)
                .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
        task.setDeletedDateTime(LocalDateTime.now());
        taskRepository.save(task);
        return task.getTitle();
    }

    @Override
    @CacheEvict(key = "#teamId", value = "tasks")
    public TaskDto.TaskResponse updateTeamTask(String teamId, String taskId, Member member, TaskDto.TaskRequest request ) {
        if (!this.teamMemberRepository.existsByMember_MemberIdAndTeamTeamId(member.getMemberId(), teamId)){
            throw new RuntimeException("This member is not a team member");
        }
        Task task = taskRepository.findByTaskIdAndTeamTeamId(taskId, teamId)
                .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
        task.setFinished(request.isFinished());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadLineDateTime(request.getDeadLineDateTime());
        task.setPriority(request.getPriority());
        return new TaskDto.TaskResponse(this.taskRepository.save(task));
    }
}
