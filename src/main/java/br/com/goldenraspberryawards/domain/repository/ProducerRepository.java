package br.com.goldenraspberryawards.domain.repository;

import br.com.goldenraspberryawards.api.model.ProducerInterval;
import br.com.goldenraspberryawards.domain.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findByName(String name);

    @Query(
            value = "SELECT * FROM\n" +
                    "(SELECT X.ID,\n" +
                    "       X.NAME,\n" +
                    "       MIN(X.YEAR) OVER (PARTITION BY X.ID) firstYear,\n" +
                    "       MAX(X.YEAR) OVER (PARTITION BY X.ID) lastYear,\n" +
                    "       MAX(X.YEAR) OVER(PARTITION BY X.ID) - MIN(X.YEAR) OVER(PARTITION BY X.ID) yearsInterval\n" +
                    "FROM\n" +
                    "  (SELECT P.ID,\n" +
                    "          P.NAME,\n" +
                    "          M.YEAR,\n" +
                    "          ROW_NUMBER() OVER (PARTITION BY P.ID) row_number\n" +
                    "   FROM PRODUCER P\n" +
                    "   LEFT JOIN MOVIE_PRODUCERS MP ON P.ID = MP.PRODUCER_ID\n" +
                    "   LEFT JOIN MOVIE M ON M.ID = MP.MOVIE_ID\n" +
                    "   WHERE M.WINNER = TRUE) X\n" +
                    "WHERE X.ROW_NUMBER < 3\n" +
                    "GROUP BY \n" +
                    "         X.ID,\n" +
                    "         X.NAME,\n" +
                    "         X.YEAR\n" +
                    "ORDER BY X.ID,\n" +
                    "         X.YEAR) Y\n" +
                    "WHERE Y.yearsInterval > 0 \n" +
                    "GROUP BY Y.ID,Y.NAME",
            nativeQuery = true
    )
    List<ProducerInterval> findSlowerAndFasterWinner();
}
