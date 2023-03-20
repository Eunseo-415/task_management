package com.example.task_management.team;

import com.example.task_management.member.entity.Member;

public interface TeamService {
    Team createTeam(Member member, TeamInput teamInput);
    String sendInvitation(Member member, String mail, String teamId);
}
