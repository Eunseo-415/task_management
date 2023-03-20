package com.example.task_management.team;

import com.example.task_management.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService{

    private final TeamMemberRepository teamMemberRepository;

    @Override
    public TeamMember invitationAccept(Member member, String invitationCode) {
        TeamMember teamMember = teamMemberRepository.findByMember_MemberIdAndInvitationCode(member.getMemberId(), invitationCode)
                .orElseThrow(() -> new RuntimeException("No invitation sent"));

        if(teamMember.isInvitationAccepted()){
            throw new RuntimeException("Already accepted/invitation code not found");
        }
        teamMember.setInvitationAccepted(true);
        teamMemberRepository.save(teamMember);
        return teamMember;
    }
}
