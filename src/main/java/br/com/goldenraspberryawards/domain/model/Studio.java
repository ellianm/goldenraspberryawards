package br.com.goldenraspberryawards.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Fetch(value = FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "studios")
    @JsonIgnore
    private List<Movie> movies;

    public Studio() {
    }
}
