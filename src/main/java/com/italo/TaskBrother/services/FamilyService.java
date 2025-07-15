package com.italo.TaskBrother.services;

import com.italo.TaskBrother.models.dtos.FamilyRecordDTO;
import com.italo.TaskBrother.models.entities.FamilyModel;
import com.italo.TaskBrother.models.repository.FamilyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    public ResponseEntity<FamilyModel>saveFamily(@RequestBody FamilyRecordDTO familyRecordDTO){
        var familyMdodel = new FamilyModel();
        BeanUtils.copyProperties(familyRecordDTO, familyMdodel);
        return ResponseEntity.status(HttpStatus.CREATED).body(familyRepository.save(familyMdodel));
    }

    public ResponseEntity<List<FamilyModel>> getAllFAmily(){
        return ResponseEntity.status(HttpStatus.OK).body(familyRepository.findAll());
    }

    public ResponseEntity<Object> getFamilyById(UUID id){
        Optional<FamilyModel> familyModel = familyRepository.findById(id);
        if(familyModel.isEmpty() || !(id instanceof UUID)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family don´t found by id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(familyModel);
    }
}
