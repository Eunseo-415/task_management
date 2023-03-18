package com.example.task_management.task.repository;

import com.example.task_management.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findAllByDeletedDateTimeIsNullAndMember_MemberId(String memberId);
    Optional<Task> findByTaskIdAndMember_MemberId(String taskId, String memberId);
}
