package br.com.goldenraspberryawards.api.controller;

import br.com.goldenraspberryawards.GoldenRaspBerryAwardsApplication;
import br.com.goldenraspberryawards.domain.model.Studio;
import br.com.goldenraspberryawards.domain.repository.StudioRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoldenRaspBerryAwardsApplication.class)
class StudioControllerTest {

    @Autowired
    StudioRepository studioRepository;


    @Test
    void listAll() {
        List<Studio> list = studioRepository.findAll();
        Assert.isTrue(list.get(0).getName().equals("Associated Film Distribution"));
        Assert.isTrue(list.get(15).getName().equals("Carolco Pictures"));
        Assert.isTrue(list.get(58).getName().equals("Saban Films"));
    }

    @Test
    void findById() {
        Studio studio = studioRepository.findById(41L).get();
        Assert.isTrue(studio.getName().equals("Hasbro"));
    }
}