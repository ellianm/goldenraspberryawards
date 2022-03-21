package br.com.goldenraspberryawards.api.controller;

import br.com.goldenraspberryawards.GoldenRaspBerryAwardsApplication;
import br.com.goldenraspberryawards.domain.model.Movie;
import br.com.goldenraspberryawards.domain.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoldenRaspBerryAwardsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    @Transactional
    void listAll() {
        List<Movie> movieList = movieRepository.findAll();
        Assert.isTrue(movieList.get(0).getTitle().equals("Can't Stop the Music"));
        Assert.isTrue(movieList.get(0).getStudios().size() == 1);
        Assert.isTrue(movieList.get(0).getProducers().size() == 1);
    }

    @Test
    @Transactional
    void findById() {
        Movie movie = movieRepository.findById(22L).get();
        Assert.isTrue(movie.getTitle().equals("Hercules"));
        Assert.isTrue(movie.getYear() == 1983 );
        Assert.isTrue(!movie.isWinner());
        Assert.isTrue(movie.getStudios().size() == 3);
        Assert.isTrue(movie.getProducers().size() == 2);
    }
}