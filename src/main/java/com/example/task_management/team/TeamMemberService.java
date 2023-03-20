package com.example.task_management.team;

import com.example.task_management.member.entity.Member;

public interface TeamMemberService {
    TeamMember invitationAccept(Member member, String invitationCode);
}
