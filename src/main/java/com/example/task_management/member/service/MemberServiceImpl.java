package com.example.task_management.member.service;

import com.example.task_management.member.MemberDto;
import com.example.task_management.member.entity.Member;
import com.example.task_management.member.model.Auth;
import com.example.task_management.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto register(Auth.Register member) {
        if(memberRepository.existsByEmail(member.getEmail())){
            throw new RuntimeException("이미 사용중인 이메일 입니다.");
        }
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));

        Member result = this.memberRepository.save(member.toEntity());
        return new MemberDto(result);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return this.memberRepository.findByMemberId(id)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find userID: " + id));
    }

    @Override
    public MemberDto loginAuthentication(Auth.Login member){
        Member user = this.memberRepository.findByEmail(member.getEmail())
                .orElseThrow(()-> new RuntimeException("Not registered email: " + member.getEmail()));
        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        return new MemberDto(user);
    }
}
