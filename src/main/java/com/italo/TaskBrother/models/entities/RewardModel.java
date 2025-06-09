package com.italo.TaskBrother.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "TB_REWARD")
public class RewardModel {
    private static final long serialVersion = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reward_ID")
    private UUID rewardID;

    @Column(name = "title")
    private String Title;

    @Column(name = "description")
    private String Description;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private UserModel createByParentFK;

    @ManyToOne
    @JoinColumn(name = "family_FK")
    private FamilyModel familyFK;
}
