package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Role;
import com.grupo01.digitalbooking.domain.User;
import com.grupo01.digitalbooking.dto.NewUserDTO;
import com.grupo01.digitalbooking.dto.UserDTO;
import com.grupo01.digitalbooking.repository.RoleRepository;
import com.grupo01.digitalbooking.repository.UserRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createNewUser(NewUserDTO newUser) {
        validateInitialInformation(newUser);
        log.info("Checking for existing user information");
        Optional<User> existingEmailUser = userRepository.findByEmail(newUser.getEmail());
        if(existingEmailUser.isPresent()){
            log.error("Provided e-mail already in use.");
            throw new BadRequestException("Provided e-mail already in use.");
        }
        Util.validatePassword(newUser.getPassword());
        User entity = new User(newUser);
        entity.setRole(new Role(1L,"USER"));
        setDefaultUserInfo(entity);
        entity = userRepository.save(entity);
        log.info("New user created successfully");
        return new UserDTO(entity);
    }

    private void setDefaultUserInfo(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);
    }

    private void validateInitialInformation(NewUserDTO newUser) {
        if(newUser.getFirstName()==null || newUser.getFirstName().isBlank() ||
                newUser.getLastName()==null || newUser.getLastName().isBlank() ||
                newUser.getEmail()==null || newUser.getEmail().isBlank()||
                newUser.getPassword()==null || newUser.getPassword().isBlank()){
            throw new BadRequestException("First name, Last name, email or password is missing");
        }
    }
}
