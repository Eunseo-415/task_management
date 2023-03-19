package com.example.task_management.team;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Team {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String teamId;

    private String teamName;

    @CreatedDate
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;

    @OneToMany(mappedBy = "team")
    private Set<TeamMember> members;

}
