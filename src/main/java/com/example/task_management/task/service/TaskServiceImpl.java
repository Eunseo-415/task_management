package com.example.task_management.task.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.task.dto.TaskDto;
import com.example.task_management.task.entity.Task;
import com.example.task_management.task.repository.TaskRepository;
import com.example.task_management.team.entity.Team;
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

    //task 엔티티에 erd 진행시 delete date time 정보가 있으면 추후 유저의 행동분석에 도움이 된다고 하신걸 보면
    //디비에서 바로 삭제하는게 아니고 deleteDateTime 필드를 업데이트 후(필요하다면 이외 다른 필드들의 정보값 삭제),
    //만약 해당 필드에 정보가 들어가 있을 시에
    //삭제된 정보로 취급하는게 맞을지 아니면 그냥 바로 디비에서 삭제 해버리면 될지 잘 모르겠습니다
    //TODO: 딜릿(디비 삭제 혹은 딜릿 필드 업데이트)
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


}
