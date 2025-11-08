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

    private final HandlerMapping resourceHandlerMapping;
    private UserService service;

    public UserController(UserService service, HandlerMapping resourceHandlerMapping){
        this.service = service;
        this.resourceHandlerMapping = resourceHandlerMapping;
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
    public ResponseEntity delete(){
        boolean active = service.delete();
        return ResponseEntity.ok(active);
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO dto){
        boolean changePassword = service.changePassword(dto);
        if(!changePassword){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(changePassword);
    }

}
