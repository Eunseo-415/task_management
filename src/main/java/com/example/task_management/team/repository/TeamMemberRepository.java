package com.example.task_management.team.repository;

import com.example.task_management.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findByMember_MemberIdAndTeamTeamId(String memberId, String teamId);
    Optional<TeamMember> findByMember_MemberIdAndInvitationCode(String memberId, String invitationCode);
    boolean existsByMember_MemberIdAndTeamTeamId(String memberId, String teamId);
}
