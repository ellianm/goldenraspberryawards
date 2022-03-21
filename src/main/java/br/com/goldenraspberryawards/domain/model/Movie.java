package br.com.goldenraspberryawards.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;

    private String title;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="MovieStudios", joinColumns=
            {@JoinColumn(name="movie_id")}, inverseJoinColumns=
            {@JoinColumn(name="studio_id")})
    private Set<Studio> studios;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="MovieProducers", joinColumns=
            {@JoinColumn(name="movie_id")}, inverseJoinColumns=
            {@JoinColumn(name="producer_id")})
    private Set<Producer> producers;

    private boolean winner;

    public Movie() {

    }
}
