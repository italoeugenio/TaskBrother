package com.italo.TaskBrother.services;

import com.italo.TaskBrother.models.dtos.RewardRecordDTO;
import com.italo.TaskBrother.models.entities.RewardModel;
import com.italo.TaskBrother.models.repository.RewardRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RewardService {
    @Autowired
    private RewardRepository rewardRepository;

    public ResponseEntity<RewardModel> saveReward(RewardRecordDTO rewardRecordDTO){
        RewardModel rewardModel = new RewardModel();
        BeanUtils.copyProperties(rewardRecordDTO, rewardModel);
        return ResponseEntity.status(HttpStatus.OK).body(rewardRepository.save(rewardModel));
    }

    public ResponseEntity<List<RewardModel>> getAllReward(){
        return ResponseEntity.status(HttpStatus.OK).body(rewardRepository.findAll());
    }

    public ResponseEntity<RewardModel> getById(UUID id){
        Optional<RewardModel> rewardModelOptional = rewardRepository.findById(id);
        if(rewardModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(rewardModelOptional.get());
    }

    public ResponseEntity<RewardModel> updateReward(UUID id, RewardRecordDTO rewardRecordDTO){
        Optional<RewardModel> rewardModelOptional = rewardRepository.findById(id);
        if(rewardModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var rewardModel = rewardModelOptional.get();
        BeanUtils.copyProperties(rewardRecordDTO, rewardModel);
        return ResponseEntity.status(HttpStatus.OK).body(rewardRepository.save(rewardModel));
    }

    public ResponseEntity<RewardModel> deleteById(UUID id){
        Optional<RewardModel> optionalRewardModel = rewardRepository.findById(id);
        if(optionalRewardModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        rewardRepository.delete(optionalRewardModel.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
