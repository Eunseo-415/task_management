package com.example.task_management.member;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class MemberDto {
    private String memberId;
    private String email;
    private String name;
    private String password;
    private String phone;
    private LocalDateTime regDt;
    private LocalDateTime unregDt;
}
