package com.example.task_management.team;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/team")
public class TeamController {

    public static final String TOKEN_PREFIX = "Bearer ";
    private final TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createTeam(@RequestHeader String authentication, @RequestBody TeamInput teamInput){
        var result = teamService.createTeam(authentication.substring(TOKEN_PREFIX.length()), teamInput);
        return ResponseEntity.ok(result.toString());
    }

    public ResponseEntity<?> sendInvitation(){
        return null;
    }
}
