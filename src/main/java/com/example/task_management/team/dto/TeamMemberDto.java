package com.example.task_management.team.dto;

import com.example.task_management.member.entity.Member;
import com.example.task_management.team.entity.Team;
import com.example.task_management.team.entity.TeamMember;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamMemberDto {

    private Long teamMemberId;

    private Team team;

    private Member member;

    private boolean isAdmin;

    private String invitationCode;

    private boolean invitationAccepted;

    private LocalDateTime createdDateTime;

    public TeamMemberDto(TeamMember teamMember){
        this.teamMemberId = teamMember.getTeamMemberId();
        this.team = teamMember.getTeam();
        this.member = teamMember.getMember();
        this.isAdmin = teamMember.isAdmin();
        this.invitationCode = teamMember.getInvitationCode();
        this.invitationAccepted = teamMember.isInvitationAccepted();
        this.createdDateTime = teamMember.getCreatedDateTime();
    }

}
