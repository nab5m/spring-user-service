package com.nab5m.userservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // 영문, 숫자, 한글, 특수문자로 4~20자
    @Pattern(regexp = "^[\\w가-힣\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-+<>@\\#$%&\\\\\\=\\(\\'\\\"]{4,20}$")
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    // 영문, 숫자, 특수문자 조합으로 8~50자
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-+<>@\\#$%&\\\\\\=\\(\\'\\\"])(?=.*[0-9]).{8,50}$")
    @Column(nullable = false, length = 100)
    private String password;

    @Email(regexp = "^[\\w._%+-]+@[\\w._-]+\\.[\\w]{2,}$")
    @Column(nullable = false, length = 320)
    @Size(max = 320)
    private String emailAddress;

    @Pattern(regexp = "^\\d{0,3}-?\\d{3,4}\\d{4}")
    @Column(nullable = false, length=25)
    private String phoneNumber;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateDateTime;

    private LocalDateTime deletionDateTime;

    @Builder
    private User(String username, String password, String emailAddress, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
