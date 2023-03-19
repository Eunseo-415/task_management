package com.example.task_management.team;

import lombok.Data;

@Data
public class TeamInput {

    private String teamName;

    public Team toEntity(){
        return Team.builder()
                .teamName(this.teamName)
                .build();
    }

}
