package com.italo.TaskBrother.models.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RedeemendRewardRecordDTO(@NotNull UUID userId, @NotNull UUID rewardId) {
}
