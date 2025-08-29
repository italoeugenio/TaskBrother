package com.italo.TaskBrother.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_FAMILY")
public class FamilyModel {
    private static final long serialVersion = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "family_ID")
    private UUID familyID;

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @OneToMany(mappedBy = "familyFK")
    private List<UserModel> userModelList;
}
