package com.example.task_management.team.dto;

import com.example.task_management.task.entity.Task;
import com.example.task_management.team.entity.Team;
import com.example.task_management.team.entity.TeamMember;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

public class TeamDto {

    @Data
    public static class TeamRequest {

        private String teamName;

        public Team toEntity(){
            return Team.builder()
                    .teamName(this.teamName)
                    .build();
        }

    }

    @Data
    public static class TeamResponse{
        private String teamId;
        private String teamName;
        private LocalDateTime createdDateTime;
        private Set<TeamMember> members;
        private Set<Task> tasks;

        public TeamResponse (Team team) {
            this.teamId = team.getTeamId();
            this.teamName = team.getTeamName();
            this.createdDateTime = team.getCreatedDateTime();
            this.members = team.getMembers();
            this.tasks = team.getTasks();
        }
    }



}
