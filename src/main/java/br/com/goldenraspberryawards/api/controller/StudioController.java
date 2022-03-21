package br.com.goldenraspberryawards.api.controller;

import br.com.goldenraspberryawards.api.model.StudioDTO;
import br.com.goldenraspberryawards.domain.model.Studio;
import br.com.goldenraspberryawards.domain.repository.StudioRepository;
import br.com.goldenraspberryawards.domain.service.StudioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/studio")
public class StudioController {

    @Autowired
    StudioRepository repository;

    @Autowired
    StudioService service;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping()
    public List<Studio> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Studio> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Studio create(@Valid @RequestBody StudioDTO studioRequest) {
        Studio studio = new Studio();
        modelMapper.map(studioRequest, studio);

        return service.save(studio);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Studio update(@PathVariable Long id, @Valid @RequestBody StudioDTO studioRequest) {
        Studio studio = service.findById(id);
        modelMapper.map(studioRequest, studio);
        return service.save(studio);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
