package com.italo.TaskBrother.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "TB_USERS")
public class UserModel {
    private static final long serialVersion = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_ID")
    private UUID userID;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "family_FK")
    private FamilyModel familyFK;

    @Column(unique = true , nullable = false)
    private String email;

    @Column(name = "wallet")
    private Integer wallet;

}
