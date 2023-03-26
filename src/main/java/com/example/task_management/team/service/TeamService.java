package com.example.task_management.team.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.team.dto.TeamDto;

public interface TeamService {
    TeamDto.TeamResponse createTeam(Member member, TeamDto.TeamRequest teamInput);
    String sendInvitation(Member member, String mail, String teamId);
}
