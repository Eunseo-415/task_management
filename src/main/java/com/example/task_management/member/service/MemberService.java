package com.example.task_management.member.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.member.model.Auth;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    Member register(Auth.Register register);
    Member loginAuthentication(Auth.Login login);

}
