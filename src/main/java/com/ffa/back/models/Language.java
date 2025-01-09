package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "language")
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String language;

    @OneToMany(mappedBy = "language")
    private List<User> users = new ArrayList<>();


    public Language(@Pattern(regexp = "^[a-z]{2}$", message = "Language must be a 2-letter code") String language) {
        this.language = language;
    }
}
