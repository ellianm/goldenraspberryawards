package br.com.goldenraspberryawards.infrastructure;


import br.com.goldenraspberryawards.domain.model.Movie;
import br.com.goldenraspberryawards.domain.model.Producer;
import br.com.goldenraspberryawards.domain.model.Studio;
import br.com.goldenraspberryawards.infrastructure.model.MovieCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@Component
public class MovieCsvConverter {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ModelMapper modelMapper;

    private List<Movie> movies = new ArrayList<>();

    public List<Movie> getConvertedMovieList() {
        Resource resource = resourceLoader.getResource("classpath:assets/import.csv");
        convertResourceToMovie(resource).forEach(movieCSV -> {
            Movie movie = new Movie();
            Set<Producer> producers = this.getProducers(movieCSV.getProducers());
            Set<Studio> studios = this.getStudios(movieCSV.getStudios());
            modelMapper.map(movieCSV, movie);
            movie.setProducers(producers);
            movie.setStudios(studios);
            movies.add(movie);
        });
        return movies;
    }

    private List<MovieCSV> convertResourceToMovie(Resource resource) {
        try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(MovieCSV.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build();
            List<MovieCSV> movieCSVList = csvToBean.parse();
            return movieCSVList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Set<Producer> getProducers(String producers) {
        Set<Producer> producerList = new HashSet<>();
        getListFromString(producers).forEach(name -> {
            Producer producer = new Producer();
            producer.setName(name);
            producerList.add(producer);
        });
        return producerList;
    }

    private List<String> getListFromString(String argument) {
        return new ArrayList<>(Arrays.asList(argument.trim().split(" and |, and |, ")));
    }

    private Set<Studio> getStudios(String studios) {
        Set<Studio> studioList = new HashSet<>();
        getListFromString(studios).forEach(name -> {
            Studio studio = new Studio();
            studio.setName(name);
            studioList.add(studio);
        });
        return studioList;
    }

}