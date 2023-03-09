package com.example.task_management.member.model;

import com.example.task_management.member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class Auth {

    @Data
    public static class Login{
        private String email;
        private String password;
    }

    @Data
    public static class Register{
        private String email;
        private String name;
        private String password;
        private String phone;
        private List<String> roles;

        public Member toEntity(){
            return Member.builder()
                    .name(this.name)
                    .email(this.email)
                    .password(this.password)
                    .roles(this.roles)
                    .regDt(LocalDateTime.now())
                    .build();
        }
    }
}
