package com.example.task_management.task.dto;

import com.example.task_management.task.entity.Task;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskInput {

    private String title;
    private String description;
    private LocalDateTime deadLineDateTime;
    private int priority;
    private boolean finished;

    public Task toEntity(){
        return Task.builder()
                .title(this.title)
                .description(this.description)
                .deadLineDateTime(this.deadLineDateTime)
                .priority(this.priority)
                .finished(this.finished)
                .build();
    }
}
