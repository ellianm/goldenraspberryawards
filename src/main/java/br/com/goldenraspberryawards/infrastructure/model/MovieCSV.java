package br.com.goldenraspberryawards.infrastructure.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class MovieCSV {

    @CsvBindByPosition(position = 0)
    private String year;

    @CsvBindByPosition(position = 1)
    private String title;

    @CsvBindByPosition(position = 2)
    private String studios;

    @CsvBindByPosition(position = 3)
    private String producers;

    @CsvBindByPosition(position = 4)
    private String winner;
}
