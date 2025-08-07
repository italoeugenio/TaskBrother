package com.italo.TaskBrother.controller;


import com.italo.TaskBrother.models.dtos.TaskRecordDTO;
import com.italo.TaskBrother.models.entities.TaskModel;
import com.italo.TaskBrother.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/post")
    public ResponseEntity<TaskModel> saveTask(@RequestBody TaskRecordDTO taskRecordDTO) {
        return taskService.saveTask(taskRecordDTO);
    }

}
