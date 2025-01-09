package com.ffa.back.models;

import jakarta.persistence.*;
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
@Table(name = "groups")
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "recommended_movie_id")
    private Movie recommendedMovie;

    @OneToMany(mappedBy = "group")
    private List<MovieUserGroup> movieUserGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<WatchList> watchLists = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<ViewList> viewLists = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<ContentStatus> contentStatuses = new ArrayList<>();

}
