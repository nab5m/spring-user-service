package com.nab5m.userservice.controller;

import com.nab5m.userservice.entity.User;
import com.nab5m.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Getter
    public static class SignUpRequestDTO {
        @Valid
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
    public SignUpResponseDTO signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO, Locale locale) {
        User newUser = signUpRequestDTO.getUser();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        try {
            userService.createUser(newUser);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DuplicateUserException(messageSource.getMessage("user.username.duplicate", new String[] { newUser.getUsername() }, locale));
        }

        return SignUpResponseDTO.builder().user(newUser).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException duplicateUserException) {
        return ResponseEntity.badRequest().body(duplicateUserException.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
