package com.ingryd.sms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @NotBlank
    @Length(min = 2, max = 30, message = "Name should be at least 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 30, message = "Name should be at least 2 characters")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Please enter a valid email address")
    @Column(unique = true, name = "email")
    private String email;

}
