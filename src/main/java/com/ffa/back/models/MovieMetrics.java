package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor

@Entity
@Table(name = "movie_metrics",
        indexes = {
                @Index(name = "movie_metrics_pkey", columnList = "movie_id")
        })
@NoArgsConstructor
public class MovieMetrics {

    @Id
    private Long movieId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie;

    @Column(name = "watch_count")
    private Integer watchCount = 0;

    @Column(name = "to_watch_count")
    private Integer toWatchCount = 0;

    @Column(name = "group_count")
    private Integer groupCount = 0;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated = LocalDateTime.now();


}
