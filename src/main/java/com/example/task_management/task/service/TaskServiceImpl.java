package com.example.task_management.task.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.member.repository.MemberRepository;
import com.example.task_management.security.TokenProvider;
import com.example.task_management.task.dto.TaskInput;
import com.example.task_management.task.entity.Task;
import com.example.task_management.task.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Override
    @CacheEvict(key = "#member.email", value = "tasks")
    public Task addTask(TaskInput input, Member member) {
        Task task = input.toEntity();
        task.setMember(member);
        this.taskRepository.save(task);
        log.info("Task added by user: " + member.getName());
        return task;
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
    public List<Task> getAllTasks(Member member) {
        return this.taskRepository.findAllByDeletedDateTimeIsNullAndMember_MemberId(member.getMemberId());
    }

    @Override
    public Task getTaskById(String taskId, Member member) {
        return this.taskRepository.findByTaskIdAndMember_MemberId(taskId, member.getMemberId())
                .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
    }

    @Override
    @CacheEvict(key = "#member.email", value = "tasks")
    public Task updateTask(String taskId, Member member, TaskInput taskInput) {
        Task task = this.taskRepository.findByTaskIdAndMember_MemberId(taskId, member.getMemberId())
                .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
        task.setFinished(taskInput.isFinished());
        task.setTitle(taskInput.getTitle());
        task.setDescription(taskInput.getDescription());
        task.setDeadLineDateTime(taskInput.getDeadLineDateTime());
        task.setPriority(taskInput.getPriority());
        return this.taskRepository.save(task);
    }


}
