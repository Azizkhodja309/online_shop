package org.example.model.DTO.userDTO;

import lombok.*;
import org.example.model.DTO.BaseDto;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDto implements BaseDto {
    private String id;
    private String fullName;
    private String username;
    private String password;
    private String phone;
    private Boolean isAdmin;
    private String email;
    private LocalDate birthDate;
}
