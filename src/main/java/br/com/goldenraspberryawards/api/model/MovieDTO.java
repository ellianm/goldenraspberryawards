package br.com.goldenraspberryawards.api.model;

import br.com.goldenraspberryawards.domain.model.Producer;
import br.com.goldenraspberryawards.domain.model.Studio;
import lombok.Data;

import java.util.Set;

@Data
public class MovieDTO {

    private String title;
    private int year;
    private Set<Studio> studios;
    private Set<Producer> producers;

}
