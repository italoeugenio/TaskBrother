package com.italo.TaskBrother.services;

import com.italo.TaskBrother.models.dtos.UserRecordDTO;
import com.italo.TaskBrother.models.entities.UserModel;
import com.italo.TaskBrother.models.repository.UserRepository;
import com.italo.TaskBrother.utils.EmailValidator;
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
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> saveUser(@RequestBody UserRecordDTO userRecordDTO){
        Optional<UserModel> optionalUserModel = userRepository.findByemail(userRecordDTO.email());
        if(!EmailValidator.checkIfEmailIsValid(userRecordDTO.email())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid Email");
        }
        if(optionalUserModel.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This email already is registered");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDTO, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    public ResponseEntity<Optional<UserModel>> getByEmail(String email){
        Optional<UserModel> userModel = userRepository.findByemail(email);
        if(userModel.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userModel);
    }

    public ResponseEntity<Optional<UserModel>> getById(UUID id){
        Optional<UserModel> userModel = userRepository.findById(id);
        if(userModel.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userModel);
    }
}
