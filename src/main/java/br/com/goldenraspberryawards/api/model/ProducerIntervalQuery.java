package br.com.goldenraspberryawards.api.model;

public interface ProducerIntervalQuery {

    Long getId();
    String getProducer();
    Integer getPreviousWin();
    Integer getFollowingWin();
    Integer getYearsInterval();
    String getMax();
}
