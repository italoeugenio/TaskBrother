package com.italo.TaskBrother.services;

import com.italo.TaskBrother.models.dtos.TaskRecordDTO;
import com.italo.TaskBrother.models.dtos.TaskRecordUpdateDTO;
import com.italo.TaskBrother.models.entities.TaskModel;
import com.italo.TaskBrother.models.enums.TaskStatus;
import com.italo.TaskBrother.models.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity<TaskModel> saveTask(@RequestBody TaskRecordDTO taskRecordDTO) {
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskRecordDTO, taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(taskModel));
    }


    public ResponseEntity<List<TaskModel>> getAllTask(TaskStatus status) {
        if (status != null) {
            return ResponseEntity.status(HttpStatus.OK).body(taskRepository.findByTaskStatus(status));
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.findAll());
    }

    public ResponseEntity<Object> updateTask(UUID id, @RequestBody TaskRecordUpdateDTO taskRecordUpdateDTO) {
        Optional<TaskModel> optionalTaskModel = taskRepository.findById(id);
        if (optionalTaskModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task don´t found");
        }
        var taskModel = optionalTaskModel.get();
        BeanUtils.copyProperties(taskRecordUpdateDTO, taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(taskModel));
    }

    public ResponseEntity<Object> deleteById(UUID id) {
        Optional<TaskModel> taskModelOptional = taskRepository.findById(id);
        if (taskModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tasks don´t found");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
