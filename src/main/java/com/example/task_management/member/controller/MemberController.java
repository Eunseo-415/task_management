package com.example.task_management.member.controller;

import com.example.task_management.member.entity.Member;
import com.example.task_management.member.model.Auth;
import com.example.task_management.member.service.MemberService;
import com.example.task_management.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    @GetMapping("/logintest")
    public String test(){
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestBody Auth.Login request){
        Member member = this.memberService.loginAuthentication(request);
        String token = this.tokenProvider.generateToken(member.getMemberId(), member.getRoles());
        return token;
    }

    @PostMapping("/register")
    public Member register(@RequestBody Auth.Register request){
        return memberService.register(request);
    }
}
