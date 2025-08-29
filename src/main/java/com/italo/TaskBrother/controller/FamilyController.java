package com.italo.TaskBrother.controller;

import com.italo.TaskBrother.models.dtos.FamilyRecordDTO;
import com.italo.TaskBrother.models.entities.FamilyModel;
import com.italo.TaskBrother.services.FamilyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @PostMapping("/post")
    public ResponseEntity<FamilyModel> saveFamily(@RequestBody @Valid FamilyRecordDTO familyRecordDTO) {
        return familyService.saveFamily(familyRecordDTO);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<FamilyModel>> getAll() {
        return familyService.getAllFamily();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FamilyModel> getById(@PathVariable("id") UUID id) {
        return familyService.getFamilyById(id);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Object> updateFamily(@PathVariable("id") UUID id, @RequestBody @Valid FamilyRecordDTO familyRecordDTO) {
        return familyService.updateFamily(id, familyRecordDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletById(@PathVariable("id") UUID id) {
        return familyService.deleteFamilyById(id);
    }
}

