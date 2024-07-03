package com.rodtan.interview.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "content", length = 1500)
    private String content;

    @ManyToOne
    @JsonIgnore
    private User user;

    @JsonProperty("user_id")
    @Column(name = "user_id", updatable=false, insertable=false)
    private Integer userId;
}
