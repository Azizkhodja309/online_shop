package org.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.entity.base.BaseEntity;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser extends BaseEntity {
    private String fullName;
    private String username;
    private String password;
    private Boolean isAdmin;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private Integer roleId;
    private Boolean isLocked;
}
