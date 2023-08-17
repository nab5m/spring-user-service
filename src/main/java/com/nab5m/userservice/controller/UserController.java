package com.nab5m.userservice.controller;

import com.nab5m.userservice.entity.User;
import com.nab5m.userservice.service.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Getter
    public static class SignUpRequestDTO {
        private User user;
    }
    @Builder
    @Getter
    public static class SignUpResponseDTO {
        private User user;
    }

    @PostMapping("/user")
    public SignUpResponseDTO signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        User newUser = signUpRequestDTO.getUser();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userService.createUser(newUser);

        return SignUpResponseDTO.builder().user(newUser).build();
    }
}
