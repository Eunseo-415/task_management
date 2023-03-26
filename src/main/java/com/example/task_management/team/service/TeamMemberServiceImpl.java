package com.example.task_management.team.service;

import com.example.task_management.member.entity.Member;
import com.example.task_management.team.dto.TeamMemberDto;
import com.example.task_management.team.entity.TeamMember;
import com.example.task_management.team.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    @Override
    public TeamMemberDto invitationAccept(Member member, String invitationCode) {
        TeamMember teamMember = teamMemberRepository.findByMember_MemberIdAndInvitationCode(member.getMemberId(), invitationCode)
                .orElseThrow(() -> new RuntimeException("No invitation sent"));

        if(teamMember.isInvitationAccepted()){
            throw new RuntimeException("Already accepted/invitation code not found");
        }
        teamMember.setInvitationAccepted(true);
        teamMemberRepository.save(teamMember);
        return new TeamMemberDto(teamMember);
    }
}
