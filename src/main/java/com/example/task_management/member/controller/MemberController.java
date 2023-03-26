package com.example.task_management.member.controller;

import com.example.task_management.member.MemberDto;
import com.example.task_management.member.model.Auth;
import com.example.task_management.member.service.MemberService;
import com.example.task_management.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    // 로그인 토큰 확인용 테스트용
    @GetMapping("/login-test")
    public String test(){
        return "index";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Auth.Login request){
        MemberDto member = this.memberService.loginAuthentication(request);
        String token = this.tokenProvider.generateToken(member.getMemberId(), member.getRoles());
        log.info("Token is generated for user: " + request.getEmail());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?>  register(@RequestBody Auth.Register request){
        log.info("User registered: " + request.getEmail());
        return ResponseEntity.ok(memberService.register(request));
    }
}
