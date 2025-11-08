package com.sousaarthur.blog.modules.user.service;

import com.sousaarthur.blog.exception.EventSizeException;
import com.sousaarthur.blog.modules.auth.repository.LoginRepository;
import com.sousaarthur.blog.modules.user.dto.ChangePasswordDTO;
import com.sousaarthur.blog.modules.user.dto.UserDTO;
import com.sousaarthur.blog.modules.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticatedUser authUser;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private MessageSource messageSource;

    public UserService(UserRepository userRepository, AuthenticatedUser authUser, LoginRepository loginRepository, PasswordEncoder passwordEncoder, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.authUser = authUser;
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    public UserDTO update(UserDTO dto) {
        var user = authUser.getUser();
        if(dto.name() != null){
            if(dto.name().length() > 20){
                throw new EventSizeException(
                        messageSource.getMessage("name.size.invalid", new Object[]{20}, getLocale())
                );
            }
            user.setName(dto.name());
        }
        if (dto.bio() != null) {
            user.setBio(dto.bio());
        }
        if (dto.avatar() != null) {
            user.setAvatar(dto.avatar());
        }
        if (dto.linkedin() != null) {
            if(!dto.linkedin().contains("https://www.linkedin.com/in/") && !dto.linkedin().isEmpty()){
                throw new EventSizeException("URL do LinkedIn inválido!");
            }
            user.setLinkedin(dto.linkedin());
        }
        if (dto.github() != null) {
            if(!dto.github().contains("https://github.com/") && !dto.github().isEmpty()){
                throw new EventSizeException("URL do Github inválido!");
            }
            user.setGithub(dto.github());
        }

        userRepository.save(user);
        return UserDTO.toDTO(user);
    }

    public UserDTO getCurrentUser() {
        try{
            var user = authUser.getUser();
            return UserDTO.toDTO(user);
        } catch (Exception e){
            throw new RuntimeException("Erro ao obter dados do usuário atual: " + e.getMessage());
        }
    }

    @Transactional
    public boolean delete(){
        try{
            var login = authUser.getLogin();
            login.setActive(false);
            loginRepository.save(login);
            return login.isEnabled();
        } catch (Exception e){
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    public boolean changePassword(ChangePasswordDTO dto){
        try {
            var user = authUser.getLogin();
            boolean equals = passwordEncoder.matches(dto.currentPassword(), user.getPassword());
            if(!equals) return false;
            String encryptedPassword = passwordEncoder.encode(dto.newPassword());
            user.setPassword(encryptedPassword);
            loginRepository.save(user);
            return true;
        } catch (Exception e){
            throw new RuntimeException("Erro ao alterar a senha" + e.getMessage());
        }
    }

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
