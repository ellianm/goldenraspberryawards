package br.com.goldenraspberryawards.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProducerIntervalReturn {

    List<ProducerInterval> min = new ArrayList<>();
    List<ProducerInterval> max = new ArrayList<>();

}
