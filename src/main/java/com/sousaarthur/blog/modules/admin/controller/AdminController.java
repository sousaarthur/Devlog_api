package com.sousaarthur.blog.modules.admin.controller;

import com.sousaarthur.blog.modules.admin.dto.UserIdDTO;
import com.sousaarthur.blog.modules.user.dto.UserDTO;
import com.sousaarthur.blog.modules.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public Page<UserDTO> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy
    ){
        return userService.listAllUsers(page, size, sortBy);
    }

    // Aplica tratamento de erro
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable int id){
        return userService.findById(id);
    }

    // Para fazer futuramente
    // Separar o endpoit em dois um para ativar usuário e outro para desativar
    @PatchMapping("/user")
    public boolean toggleUserStatus(@RequestBody UserIdDTO dto){
        return userService.toggleUserStatus(dto.id());
    }
}
