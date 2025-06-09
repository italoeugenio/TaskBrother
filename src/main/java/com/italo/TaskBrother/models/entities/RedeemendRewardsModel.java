package com.italo.TaskBrother.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "TB_REDEEMEND_REWARDS")
public class RedeemendRewardsModel {
    private static final long serialVersion = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "redeemend_rewards_ID")
    private UUID redeemendRewardsID;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "reward_FK")
    private RewardModel rewardFK;

    @ManyToOne
    @JoinColumn(name = "user_FK")
    private UserModel userFK;
}
