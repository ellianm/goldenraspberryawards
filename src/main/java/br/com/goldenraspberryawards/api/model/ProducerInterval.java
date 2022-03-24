package br.com.goldenraspberryawards.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProducerInterval {
    Long id;
    String producer;
    Integer previousWin;
    Integer followingWin;
    @JsonProperty("interval")
    Integer yearsInterval;
}
