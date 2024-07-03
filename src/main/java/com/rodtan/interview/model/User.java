package com.rodtan.interview.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data object that represents a user:
 *
 * id - auto-generated id for a user
 * name - name of a user
 * email - user's email
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Valid
public class User {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "name", length = 20)
    @NotBlank
    @NotNull
    private String name;

    @Column(length = 30)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank
    @NotNull
    private String email;

}
