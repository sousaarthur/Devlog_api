package com.sousaarthur.blog.modules.user.service;

import com.sousaarthur.blog.modules.auth.repository.LoginRepository;
import com.sousaarthur.blog.modules.user.dto.UserDTO;
import com.sousaarthur.blog.modules.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticatedUser authUser;
    private final LoginRepository loginRepository;

    public UserService(UserRepository userRepository, AuthenticatedUser authUser, LoginRepository loginRepository) {
        this.userRepository = userRepository;
        this.authUser = authUser;
        this.loginRepository = loginRepository;
    }

    public UserDTO update(UserDTO dto) {
        try{
            var user = authUser.getUser();

            if(dto.name() != null){
                user.setName(dto.name());
            }
            if (dto.bio() != null) {
                user.setBio(dto.bio());
            }
            if (dto.avatar() != null) {
                user.setAvatar(dto.avatar());
            }
            if (dto.linkedin() != null) {
                user.setLinkedin(dto.linkedin());
            }
            if (dto.github() != null) {
                user.setGithub(dto.github());
            }

            userRepository.save(user);
            return UserDTO.toDTO(user);
        } catch (Exception e){
           throw new RuntimeException("Erro ao atualizar o usuário: " + e.getMessage());
        }
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
}
