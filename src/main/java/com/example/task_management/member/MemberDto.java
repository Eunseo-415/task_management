package com.example.task_management.member;

import com.example.task_management.member.entity.Member;
import com.example.task_management.task.entity.Task;
import com.example.task_management.team.entity.TeamMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MemberDto{
    private String memberId;
    private Set<Task> tasks;
    private Set<TeamMember> teams;
    private String email;
    private String name;
    private String phone;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime registeredDateTime;
    private List<String> roles;

    public MemberDto(Member member){
        this.memberId = member.getMemberId();
        this.tasks = member.getTasks();
        this.teams = member.getTeams();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.registeredDateTime = member.getRegisteredDateTime();
        this.roles = member.getRoles();
    }
}
