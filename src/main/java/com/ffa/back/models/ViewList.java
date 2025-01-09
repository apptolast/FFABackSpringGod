package com.ffa.back.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "view_lists")
@IdClass(ViewListId.class)
@NoArgsConstructor
public class ViewList {

    // Getters y Setters
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "id")
    private Long listId;



}
