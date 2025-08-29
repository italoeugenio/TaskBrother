package com.italo.TaskBrother.models.repository;

import com.italo.TaskBrother.models.entities.RedeemendRewardsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RedeemendRewardsRepository extends JpaRepository<RedeemendRewardsModel , UUID> {
}
