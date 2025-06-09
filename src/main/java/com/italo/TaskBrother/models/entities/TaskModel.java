package com.italo.TaskBrother.models.entities;


import com.italo.TaskBrother.models.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_TASKS")
public class TaskModel {
    private static final long serialVersion = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "task_ID")
    private UUID taskID;

    @ManyToOne
    @JoinColumn(name = "family_FK")
    private FamilyModel familyFK;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "score_value")
    private Integer scoreValue;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "create_by_parent")
    private UserModel createByParent;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private UserModel assignedTo;

}
