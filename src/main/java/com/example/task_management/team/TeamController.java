package com.example.task_management.team;

import com.example.task_management.MailComponents;
import com.example.task_management.member.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final TeamMemberService teamMemberService;
    private final MailComponents mailComponents;

    @PostMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createTeam(@AuthenticationPrincipal Member member, @RequestBody TeamInput teamInput){
        var result = teamService.createTeam(member, teamInput);
        return ResponseEntity.ok(result.toString());
    }

    @PostMapping("/{teamId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> sendInvitation(@AuthenticationPrincipal Member member, @PathVariable String teamId, @RequestBody String mail){
        String invitationCode = this.teamService.sendInvitation(member, mail, teamId);
        if (StringUtils.hasText(invitationCode)){
            mailComponents.sendMail(mail, "Team Invitation Code", invitationCode);
        }
        return ResponseEntity.ok(invitationCode);
    }

    @GetMapping("/team-auth/{invitationCode}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> invitationActivate(@AuthenticationPrincipal Member member, @PathVariable String invitationCode){
        return ResponseEntity.ok(this.teamMemberService.invitationAccept(member, invitationCode).toString());
    }
}
