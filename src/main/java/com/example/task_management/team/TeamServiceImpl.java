package com.example.task_management.team;

import com.example.task_management.member.entity.Member;
import com.example.task_management.member.repository.MemberRepository;
import com.example.task_management.security.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    public Team createTeam(String token, TeamInput teamInput) {
        String memberId = tokenProvider.getUserEmail(token);
        Member member = this.memberRepository.findByMemberId(memberId)
                .orElseThrow(() ->new RuntimeException("Cannot find user: " + memberId));
        Team team = teamInput.toEntity();
        teamRepository.save(team);
        TeamMember teamMember = TeamMember.builder()
                .isAdmin(true)
                .team(team)
                .member(member)
                .build();
        teamMemberRepository.save(teamMember);
        log.info("User : " + member.getName() + " created team: " + teamInput.getTeamName());
        return team;
    }


}
