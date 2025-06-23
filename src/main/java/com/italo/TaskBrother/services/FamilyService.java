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

@Service
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    public ResponseEntity<FamilyModel> saveFamily(@RequestBody FamilyRecordDTO familyRecordDTO){
        var familyMdodel = new FamilyModel();
        BeanUtils.copyProperties(familyRecordDTO, familyMdodel);
        return ResponseEntity.status(HttpStatus.CREATED).body(familyRepository.save(familyMdodel));
    }

    public ResponseEntity<List<FamilyModel>> getAllFAmily(){
        return ResponseEntity.status(HttpStatus.OK).body(familyRepository.findAll());
    }


}
