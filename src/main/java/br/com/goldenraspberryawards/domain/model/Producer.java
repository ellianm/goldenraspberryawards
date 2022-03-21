package br.com.goldenraspberryawards.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Fetch(value = FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "producers")
    @JsonIgnore
    private List<Movie> movies;

    public Producer() {
    }
}
