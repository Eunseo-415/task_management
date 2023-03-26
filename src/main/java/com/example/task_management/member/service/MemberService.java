package com.example.task_management.member.service;

import com.example.task_management.member.MemberDto;
import com.example.task_management.member.entity.Member;
import com.example.task_management.member.model.Auth;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    MemberDto register(Auth.Register register);
    MemberDto loginAuthentication(Auth.Login login);

}
