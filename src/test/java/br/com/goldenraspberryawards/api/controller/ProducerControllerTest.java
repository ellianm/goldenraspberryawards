package br.com.goldenraspberryawards.api.controller;

import br.com.goldenraspberryawards.GoldenRaspBerryAwardsApplication;
import br.com.goldenraspberryawards.api.model.ProducerInterval;
import br.com.goldenraspberryawards.domain.model.Producer;
import br.com.goldenraspberryawards.domain.repository.ProducerRepository;
import br.com.goldenraspberryawards.domain.service.ProducerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoldenRaspBerryAwardsApplication.class)
class ProducerControllerTest {

    @Autowired
    ProducerRepository producerRepository;

    @Autowired
    ProducerService producerService;

    @Test
    void listAll() {
        List<Producer> list = producerRepository.findAll();
        Assert.isTrue(list.get(0).getName().equals("Allan Carr"));
        Assert.isTrue(list.get(1).getName().equals("Jerry Weintraub"));
        Assert.isTrue(list.get(358).getName().equals("Kevin King Templeton"));
    }

    @Test
    void findById() {
        Producer producer = producerRepository.findById(238L).get();
        Assert.isTrue(producer.getName().equals("Bob Ducsay"));
    }

    @Test
    void fasterAndSlowerProducerPrize() {
        List<ProducerInterval> producerIntervals = producerService.fasterAndSlowerProducerPrize();
        Assert.isTrue(producerIntervals.get(0).getName().equals("Joel Silver"));
        Assert.isTrue(producerIntervals.get(0).getYearsInterval() == 1);
        Assert.isTrue(producerIntervals.get(0).getFirstYear() == 1990);
        Assert.isTrue(producerIntervals.get(0).getLastYear() == 1991);

        Assert.isTrue(producerIntervals.get(1).getName().equals("Matthew Vaughn"));
        Assert.isTrue(producerIntervals.get(1).getYearsInterval() == 13);
        Assert.isTrue(producerIntervals.get(1).getFirstYear() == 2002);
        Assert.isTrue(producerIntervals.get(1).getLastYear() == 2015);
    }
}