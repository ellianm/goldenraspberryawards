package br.com.goldenraspberryawards.domain.repository;

import br.com.goldenraspberryawards.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
}
