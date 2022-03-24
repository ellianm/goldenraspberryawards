package br.com.goldenraspberryawards.domain.service;

import br.com.goldenraspberryawards.api.model.ProducerInterval;
import br.com.goldenraspberryawards.api.model.ProducerIntervalQuery;
import br.com.goldenraspberryawards.api.model.ProducerIntervalReturn;
import br.com.goldenraspberryawards.domain.model.Producer;
import br.com.goldenraspberryawards.domain.repository.ProducerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProducerService {

    private static final String MSG_PRODUCER_NOT_FOUND = "producer not found with the informed id: %d";
    private static final String MSG_PRODUCER_CANNOT_BE_REMOVED = "producer cannot be removed, because its being used by another entity: %d";

    @Autowired
    ProducerRepository repository;

    @Autowired
    ModelMapper modelMapper;


    public ProducerIntervalReturn fasterAndSlowerProducerPrize() {
        List<ProducerIntervalQuery> producerIntervalsQuery = repository.findSlowerAndFasterWinner();
        ProducerIntervalReturn producerIntervalsReturn = new ProducerIntervalReturn();
        producerIntervalsQuery.stream().filter(producerIntervalQuery -> producerIntervalQuery.getMax().equals("N"))
                .forEach(producerIntervalQuery -> {
                    ProducerInterval producerInterval = new ProducerInterval();
                    modelMapper.map(producerIntervalQuery, producerInterval);
                    producerIntervalsReturn.getMin().add(producerInterval);
                });
        producerIntervalsQuery.stream().filter(producerIntervalQuery -> producerIntervalQuery.getMax().equals("S"))
                .forEach(producerIntervalQuery -> {
                    ProducerInterval producerInterval = new ProducerInterval();
                    modelMapper.map(producerIntervalQuery, producerInterval);
                    producerIntervalsReturn.getMax().add(producerInterval);
                });
        return producerIntervalsReturn;
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(MSG_PRODUCER_NOT_FOUND, id));

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    String.format(MSG_PRODUCER_CANNOT_BE_REMOVED, id));
        }
    }

    public Producer findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(MSG_PRODUCER_NOT_FOUND, id)));
    }

}
