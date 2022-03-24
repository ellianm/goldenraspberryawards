package br.com.goldenraspberryawards.api.controller;

import br.com.goldenraspberryawards.api.model.ProducerDTO;
import br.com.goldenraspberryawards.api.model.ProducerIntervalReturn;
import br.com.goldenraspberryawards.domain.model.Producer;
import br.com.goldenraspberryawards.domain.repository.ProducerRepository;
import br.com.goldenraspberryawards.domain.service.ProducerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping(value = "/producer")
public class ProducerController {

    @Autowired
    ProducerService service;

    @Autowired
    ProducerRepository repository;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping()
    public List<Producer> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Producer create(@Valid @RequestBody ProducerDTO producerRequest) {
        Producer producer = new Producer();
        modelMapper.map(producerRequest, producer);
        return service.save(producer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Producer update(@PathVariable Long id, @Valid @RequestBody ProducerDTO producerRequest) {
        Producer producer = service.findById(id);
        modelMapper.map(producerRequest, producer);
        return service.save(producer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/fasterAndSlower")
    public ProducerIntervalReturn fasterAndSlowerProducerPrize() {
        return this.service.fasterAndSlowerProducerPrize();
    }

}
