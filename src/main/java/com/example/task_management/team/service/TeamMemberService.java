package com.example.task_management.team.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.team.dto.TeamMemberDto;

public interface TeamMemberService {
    TeamMemberDto invitationAccept(Member member, String invitationCode);
}
