package org.example.model.DTO.userDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserCreateDto {
    private String fullName;
    private String username;
    private String password;
    private String phone;
    private String email;
    private LocalDate birthDate;
}
