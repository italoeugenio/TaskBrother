package com.italo.TaskBrother.controller;


import com.italo.TaskBrother.models.dtos.TaskRecordDTO;
import com.italo.TaskBrother.models.dtos.TaskRecordUpdateDTO;
import com.italo.TaskBrother.models.entities.TaskModel;
import com.italo.TaskBrother.models.enums.TaskStatus;
import com.italo.TaskBrother.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/post")
    public ResponseEntity<TaskModel> saveTask(@RequestBody @Valid TaskRecordDTO taskRecordDTO) {
        return taskService.saveTask(taskRecordDTO);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<TaskModel>> getAllTask(@RequestParam(required = false) TaskStatus status){
        return taskService.getAllTask(status);
    }

    @PutMapping("put/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable("id") UUID id, @Valid @RequestBody TaskRecordUpdateDTO taskRecordUpdateDTO){
        return taskService.updateTask(id, taskRecordUpdateDTO);
    }

}
