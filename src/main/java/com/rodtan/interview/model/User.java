package com.rodtan.interview.model;

import jakarta.persistence.*;

/**
 * Data object that represents a user:
 *
 * id - auto-generated id for a user
 * name - name of a user
 * email - user's email
 *
 */
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(length = 30)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    public User() {
        super();
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}
