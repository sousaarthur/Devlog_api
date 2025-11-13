package com.sousaarthur.blog.modules.user.controller;

import com.sousaarthur.blog.exception.EventSizeException;
import com.sousaarthur.blog.modules.user.dto.ChangePasswordDTO;
import com.sousaarthur.blog.modules.user.dto.UserDTO;
import com.sousaarthur.blog.modules.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

@RestController
@RequestMapping("api/user")
public class UserController {

    private UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto){
        UserDTO user = service.update(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser(){
        UserDTO user = service.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(){
        boolean active = service.delete();
        return ResponseEntity.ok(active);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordDTO dto){
        boolean changePassword = service.changePassword(dto);
        if(!changePassword){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(changePassword);
    }

}
