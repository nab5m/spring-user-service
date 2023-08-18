package com.nab5m.userservice.controller;

import com.nab5m.userservice.entity.User;
import com.nab5m.userservice.service.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Getter
    public static class SignUpRequestDTO {
        private User user;
    }
    @Builder
    @Getter
    public static class SignUpResponseDTO {
        private User user;
    }

    private static class DuplicateUserException extends RuntimeException {
        public DuplicateUserException(String message) {
            super(message);
        }
    }

    @PostMapping("/user")
    public SignUpResponseDTO signUp(@RequestBody SignUpRequestDTO signUpRequestDTO, Locale locale) {
        User newUser = signUpRequestDTO.getUser();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        try {
            userService.createUser(newUser);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DuplicateUserException(messageSource.getMessage("user.username.duplicate", new String[] { newUser.getUsername() }, locale));
        }

        return SignUpResponseDTO.builder().user(newUser).build();
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException duplicateUserException) {
        return ResponseEntity.badRequest().body(duplicateUserException.getLocalizedMessage());
    }
}
