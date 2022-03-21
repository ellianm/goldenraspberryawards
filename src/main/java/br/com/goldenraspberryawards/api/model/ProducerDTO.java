package br.com.goldenraspberryawards.api.model;

import br.com.goldenraspberryawards.domain.model.Movie;
import lombok.Data;

import java.util.Set;

@Data
public class ProducerDTO {

    private String name;
    private Set<Movie> movies;

}
