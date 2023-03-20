package com.example.task_management.team;

import com.example.task_management.member.entity.Member;
import com.example.task_management.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    public Team createTeam(Member member, TeamInput teamInput) {
        Team team = teamInput.toEntity();
        teamRepository.save(team);
        TeamMember teamMember = TeamMember.builder()
                .isAdmin(true)
                .team(team)
                .member(member)
                .build();
        teamMemberRepository.save(teamMember);
        log.info("User : " + member.getEmail() + " created team: " + teamInput.getTeamName());
        return team;
    }

    @Override
    public String sendInvitation(Member member, String mail, String teamId) {
        TeamMember sender = this.teamMemberRepository.findByMember_MemberIdAndTeamTeamId(member.getMemberId(), teamId)
                .orElseThrow(() -> new RuntimeException("Team member not found: " + member.getMemberId()));
        if (!sender.isAdmin()){
            throw new RuntimeException("Only team admin can invite");
        }
        Member sendTo = this.memberRepository.findByEmail(mail)
                .orElseThrow(() ->new RuntimeException("Mail owner is not user: " + mail));
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() ->new RuntimeException("Team is not found: " + teamId));
        String invitationCode = UUID.randomUUID().toString();
        TeamMember teamMember = TeamMember.builder()
                .isAdmin(false)
                .team(team)
                .member(sendTo)
                .invitationCode(invitationCode)
                .invitationAccepted(false)
                .build();
        teamMemberRepository.save(teamMember);

        return invitationCode;
    }


}
