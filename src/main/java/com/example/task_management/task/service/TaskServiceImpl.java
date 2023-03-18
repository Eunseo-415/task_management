package com.example.task_management.task.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.member.repository.MemberRepository;
import com.example.task_management.security.TokenProvider;
import com.example.task_management.task.entity.Task;
import com.example.task_management.task.dto.TaskInput;
import com.example.task_management.task.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Override
    public Task addTask(TaskInput input, String token) {
        Task task = input.toEntity();
        String memberId = tokenProvider.getUserEmail(token);
        Member member = this.memberRepository.findByMemberId(memberId)
                .orElseThrow(() ->new RuntimeException("Cannot find user: " + memberId));
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
    public String deleteTask(String taskId, String token) {
        String memberId = tokenProvider.getUserEmail(token);
        Task task = taskRepository.findByTaskIdAndMember_MemberId(taskId, memberId)
                        .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
        task.setDeletedDateTime(LocalDateTime.now());
        taskRepository.save(task);
        return task.getTitle();
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable, String token) {
        String memberId = tokenProvider.getUserEmail(token);
        return this.taskRepository.findAllByDeletedDateTimeIsNullAndMember_MemberId(pageable,memberId);
    }

    @Override
    public Task getTaskById(String taskId, String token) {
        String memberId = tokenProvider.getUserEmail(token);
        return this.taskRepository.findByTaskIdAndMember_MemberId(taskId, memberId)
                .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
    }

    @Override
    public Task updateTask(String taskId, String token, TaskInput taskInput) {
        String memberId = tokenProvider.getUserEmail(token);
        Task task = this.taskRepository.findByTaskIdAndMember_MemberId(taskId, memberId)
                .orElseThrow(() -> new RuntimeException("Cannot find task: " + taskId));
        task.setFinished(taskInput.isFinished());
        task.setTitle(taskInput.getTitle());
        task.setDescription(taskInput.getDescription());
        task.setDeadLineDateTime(taskInput.getDeadLineDateTime());
        task.setPriority(taskInput.getPriority());
        return this.taskRepository.save(task);
    }


}
