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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    public ResponseEntity<FamilyModel>saveFamily(@RequestBody FamilyRecordDTO familyRecordDTO){
        var familyModel = new FamilyModel();
        BeanUtils.copyProperties(familyRecordDTO, familyModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(familyRepository.save(familyModel));
    }

    public ResponseEntity<List<FamilyModel>> getAllFamily(){
        return ResponseEntity.status(HttpStatus.OK).body(familyRepository.findAll());
    }

    public ResponseEntity<FamilyModel> getFamilyById(UUID id){
        Optional<FamilyModel> familyModel = familyRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(familyModel.get());
    }

    public ResponseEntity<Object> updateFamily(UUID id, @RequestBody FamilyRecordDTO familyRecordDTO){
        Optional<FamilyModel> optionalFamilyModel = familyRepository.findById(id);
        if(optionalFamilyModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family don´t found");
        }
        var familyModel = optionalFamilyModel.get();
        BeanUtils.copyProperties(familyRecordDTO, familyModel);
        return ResponseEntity.status(HttpStatus.OK).body(familyRepository.save(familyModel));
    }

    public ResponseEntity<Object> deleteFamilyById(UUID id){
        Optional<FamilyModel> optionalFamilyModel = familyRepository.findById(id);
        if(optionalFamilyModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family don´t found");
        }
        familyRepository.delete(optionalFamilyModel.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
