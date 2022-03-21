package br.com.goldenraspberryawards.domain.service;

import br.com.goldenraspberryawards.domain.model.Movie;
import br.com.goldenraspberryawards.domain.model.Producer;
import br.com.goldenraspberryawards.domain.model.Studio;
import br.com.goldenraspberryawards.domain.repository.MovieRepository;
import br.com.goldenraspberryawards.domain.repository.ProducerRepository;
import br.com.goldenraspberryawards.domain.repository.StudioRepository;
import br.com.goldenraspberryawards.infrastructure.MovieCsvConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private static final String MSG_MOVIE_NOT_FOUND = "movie not found with the informed id: %d";
    private static final String MSG_MOVIE_CANNOT_BE_REMOVED = "movie cannot be removed, because its being used by another entity: %d";

    @Autowired
    MovieRepository repository;

    @Autowired
    ProducerRepository producerRepository;

    @Autowired
    StudioRepository studioRepository;

    @Autowired
    MovieCsvConverter movieCsvConverter;

    @PostConstruct()
    @Transactional
    public void init() {
        List<Movie> movieList = movieCsvConverter.getConvertedMovieList();
        movieList.forEach(movie -> {
            movie.getProducers().forEach(this::saveProducer);
            movie.getStudios().forEach(this::saveStudio);
            if (repository.findByTitle(movie.getTitle()).isEmpty()) {
                repository.save(movie);
            }
        });
    }

    private void saveStudio(Studio studio) {
        Optional<Studio> optStudio = studioRepository.findByName(studio.getName());
        if (optStudio.isEmpty()) {
            studioRepository.save(studio);
        } else {
            studio.setId(optStudio.get().getId());
        }
    }

    private void saveProducer(Producer producer) {
        Optional<Producer> optProducer = producerRepository.findByName(producer.getName());
        if (optProducer.isEmpty()) {
            producerRepository.save(producer);
        } else {
            producer.setId(optProducer.get().getId());
        }
    }

    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(MSG_MOVIE_NOT_FOUND, id));

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    String.format(MSG_MOVIE_CANNOT_BE_REMOVED, id));
        }
    }

    public Movie findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(MSG_MOVIE_NOT_FOUND, id)));
    }

}

