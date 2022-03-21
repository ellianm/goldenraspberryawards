package br.com.goldenraspberryawards.api.controller;

import br.com.goldenraspberryawards.api.model.MovieDTO;
import br.com.goldenraspberryawards.domain.model.Movie;
import br.com.goldenraspberryawards.domain.repository.MovieRepository;
import br.com.goldenraspberryawards.domain.repository.StudioRepository;
import br.com.goldenraspberryawards.domain.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    @Autowired
    MovieRepository repository;

    @Autowired
    MovieService service;

    @Autowired
    StudioRepository studioRepository;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping()
    public List<Movie> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@Valid @RequestBody MovieDTO movieRequest) {
        Movie movie = new Movie();
        modelMapper.map(movieRequest, movie);
        return service.save(movie);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie update(@PathVariable Long id, @Valid @RequestBody MovieDTO movieRequest) {
        Movie movie = service.findById(id);
        modelMapper.map(movieRequest, movie);
        return service.save(movie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
