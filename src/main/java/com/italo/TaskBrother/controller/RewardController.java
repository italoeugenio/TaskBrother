package com.italo.TaskBrother.controller;

import com.italo.TaskBrother.models.dtos.RewardRecordDTO;
import com.italo.TaskBrother.models.entities.RewardModel;
import com.italo.TaskBrother.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("reward")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @PostMapping("/post")
    public ResponseEntity<RewardModel> saveReward(@RequestBody RewardRecordDTO rewardRecordDTO) {
        return rewardService.saveReward(rewardRecordDTO);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<RewardModel>> getAllReward() {
        return rewardService.getAllReward();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RewardModel> getById(@PathVariable("id") UUID id) {
        return rewardService.getById(id);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<RewardModel> updateReward(@PathVariable("id") UUID id, @RequestBody RewardRecordDTO rewardRecordDTO){
        return rewardService.updateReward(id, rewardRecordDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RewardModel> deleteById(@PathVariable("id") UUID id){
        return rewardService.deleteById(id);
    }
}
