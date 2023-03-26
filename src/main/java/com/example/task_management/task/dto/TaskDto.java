package com.example.task_management.task.dto;

import com.example.task_management.task.entity.Task;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

public class TaskDto {
    @Data
    public static class TaskRequest{
        private String title;
        private String description;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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


    @Data
    public static class TaskResponse{
        private String title;
        private String description;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime deadLineDateTime;
        private int priority;
        private boolean finished;
        private String member_email;

        public TaskResponse(Task task){
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.deadLineDateTime = task.getDeadLineDateTime();
            this.priority = task.getPriority();
            this.finished = task.isFinished();
            this.member_email = task.getMember().getEmail();
        }
    }
}
