package com.italo.TaskBrother.services;

import com.italo.TaskBrother.models.dtos.TaskRecordDTO;
import com.italo.TaskBrother.models.entities.TaskModel;
import com.italo.TaskBrother.models.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity<TaskModel> saveTask(@RequestBody TaskRecordDTO taskRecordDTO){
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskRecordDTO, taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(taskModel));
    }

}
