package com.italo.TaskBrother.controller;

import com.italo.TaskBrother.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

//    @PostMapping("/post")
//    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserRecordDTO userRecordDTO) {
//        return userService.saveUser(userRecordDTO);
//    }
//
//    @GetMapping("/get/all")
//    public ResponseEntity<List<UserModel>> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/get/{email}")
//    public ResponseEntity<Optional<UserModel>> getByEmail(@PathVariable("email") @Valid String email) {
//        return userService.getByEmail(email);
//    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<Optional<UserModel>> getByID(@PathVariable("id") UUID id) {
//        return userService.getById(id);
//    }
//
//    @PutMapping("/put/{id}")
//        public ResponseEntity<Object> updateUser(@PathVariable("id") UUID id, @RequestBody UserRecordDTO userRecordDTO){
//            return userService.updateUser(id, userRecordDTO);
//    }

}
