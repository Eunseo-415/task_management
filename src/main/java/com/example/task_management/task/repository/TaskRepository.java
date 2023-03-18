package com.example.task_management.task.repository;

import com.example.task_management.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    Page<Task> findAllByDeletedDateTimeIsNullAndMember_MemberId(Pageable pageable, String memberId);
    Optional<Task> findByTaskIdAndMember_MemberId(String taskId, String memberId);
}
