package com.italo.TaskBrother.controller;

import com.italo.TaskBrother.models.dtos.FamilyRecordDTO;
import com.italo.TaskBrother.models.entities.FamilyModel;
import com.italo.TaskBrother.services.FamilyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("family")
public class Familycontroller {

    @Autowired
    private FamilyService familyService;

    @PostMapping("/post")
    public ResponseEntity<FamilyModel> saveFamily( @RequestBody @Valid FamilyRecordDTO familyRecordDTO) {
        return familyService.saveFamily(familyRecordDTO);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<FamilyModel>> getAll(){
        return familyService.getAllFAmily();
    }
}

