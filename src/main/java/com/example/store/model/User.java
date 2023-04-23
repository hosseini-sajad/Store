package com.example.store.model;

import com.example.store.core.Gender;
import com.example.store.core.UserRole;
import com.example.store.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    private String name;
    private String email;
    private String username;
    private String password;
    private Boolean isActive;
    private Gender gender;
    private String phone;
    private Boolean isFirstLogin;
    private UserRole role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
}
