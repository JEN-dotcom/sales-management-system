package com.ingryd.sms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long id;

    @NotBlank
    @NotNull
    @Length(min = 2, max = 30, message = "Name should be at least 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @NotNull
    @Length(min = 2, max = 30, message = "Name should be at least 2 characters")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Please enter a valid email address")
    private String email;

}
