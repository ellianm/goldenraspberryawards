package br.com.goldenraspberryawards.domain.repository;

import br.com.goldenraspberryawards.domain.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByName(String name);
    List<Studio> findStudiosByMoviesId(Long movieId);
}
