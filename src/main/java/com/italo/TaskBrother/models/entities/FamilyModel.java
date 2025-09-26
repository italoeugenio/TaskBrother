package com.italo.TaskBrother.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "TB_FAMILIES")
public class FamilyModel {
    private static final long serialVersion = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "family_ID")
    private UUID familyID;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;
}