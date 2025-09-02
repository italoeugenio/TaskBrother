package com.italo.TaskBrother.services;

import com.italo.TaskBrother.models.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    public ResponseEntity<Object> saveUser(@RequestBody UserRecordDTO userRecordDTO) {
//        Optional<UserModel> optionalUserModel = userRepository.findByEmail(userRecordDTO.email());
//        if (!EmailValidator.checkIfEmailIsValid(userRecordDTO.email())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid Email");
//        }
//        if (optionalUserModel.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("This email already is registered");
//        }
//        var userModel = new UserModel();
//        BeanUtils.copyProperties(userRecordDTO, userModel);
//        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
//    }
//
//    public ResponseEntity<List<UserModel>> getAllUsers() {
//        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
//    }
//
//    public ResponseEntity<Optional<UserModel>> getByEmail(String email) {
//        Optional<UserModel> userModel = userRepository.findByEmail(email);
//        if (userModel.isPresent()) {
//            return ResponseEntity.status(HttpStatus.OK).body(userModel);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userModel);
//    }
//
//    public ResponseEntity<Optional<UserModel>> getById(UUID id) {
//        Optional<UserModel> userModel = userRepository.findById(id);
//        if (userModel.isPresent()) {
//            return ResponseEntity.status(HttpStatus.OK).body(userModel);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userModel);
//    }
//
//
//    public ResponseEntity<Object> updateUser(UUID id, @RequestBody UserRecordDTO userRecordDTO) {
//        Optional<UserModel> optionalUserModel = userRepository.findById(id);
//        if (optionalUserModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//        if (!EmailValidator.checkIfEmailIsValid(userRecordDTO.email())) return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        var userModel = optionalUserModel.get();
//        BeanUtils.copyProperties(userRecordDTO, userModel);
//        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
//    }
}
