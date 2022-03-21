package br.com.goldenraspberryawards.domain.service;

import br.com.goldenraspberryawards.domain.model.Studio;
import br.com.goldenraspberryawards.domain.repository.MovieRepository;
import br.com.goldenraspberryawards.domain.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudioService {

    private static final String MSG_STUDIO_NOT_FOUND = "studio not found with the informed id: %d";
    private static final String MSG_STUDIO_CANNOT_BE_REMOVED = "studio cannot be removed, because its being used by another entity: %d";

    @Autowired
    StudioRepository repository;

    public Studio save(Studio studio) {
        try {
            return repository.save(studio);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(MSG_STUDIO_NOT_FOUND, id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    String.format(MSG_STUDIO_CANNOT_BE_REMOVED, id));
        }
    }

    public Studio findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(MSG_STUDIO_NOT_FOUND, id)));
    }
}
