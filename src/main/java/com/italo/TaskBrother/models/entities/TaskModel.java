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
    @JoinColumn(name = "family_FK", nullable = true)
    private FamilyModel familyFK;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "score_value", nullable = false)
    private Integer scoreValue;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus = TaskStatus.AVAILABLE;

    @Column(name = "create_at")
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "create_by_parent")
    private UserModel createByParent;

    @Column(name = "rejection_reason" , nullable = true)
    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private UserModel assignedTo;

}
