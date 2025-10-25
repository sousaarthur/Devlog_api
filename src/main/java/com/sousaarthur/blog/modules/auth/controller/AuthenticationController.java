package com.sousaarthur.blog.modules.auth.controller;

import com.sousaarthur.blog.config.security.TokenService;
import com.sousaarthur.blog.modules.auth.dto.AuthenticationDTO;
import com.sousaarthur.blog.modules.auth.dto.LoginResponseDTO;
import com.sousaarthur.blog.modules.auth.dto.RegisterDTO;
import com.sousaarthur.blog.modules.auth.model.Login;
import com.sousaarthur.blog.modules.auth.repository.LoginRepository;
import com.sousaarthur.blog.modules.user.model.User;
import com.sousaarthur.blog.modules.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private LoginRepository repository;
    private TokenService tokenService;
    private UserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, LoginRepository repository, TokenService tokenService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Login) auth.getPrincipal());
        return  ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto){
        if(this.repository.findByLogin(dto.login()) != null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Login login = new Login(dto.login(), encryptedPassword, dto.role());
        User user = new User(dto.name(), login);

        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
