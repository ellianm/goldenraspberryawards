package br.com.goldenraspberryawards.domain.repository;

import br.com.goldenraspberryawards.api.model.ProducerIntervalQuery;
import br.com.goldenraspberryawards.domain.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findByName(String name);

    @Query(
            value = "WITH Query as \n" +
                    "(\n" +
                    "   SELECT\n" +
                    "      Y.ID,\n" +
                    "      Y.NAME producer,\n" +
                    "      Y.firstYear previousWin,\n" +
                    "      Y.lastYear followingWin,\n" +
                    "      Y.yearsInterval\n" +
                    "   FROM\n" +
                    "      (\n" +
                    "         SELECT\n" +
                    "            X.ID,\n" +
                    "            X.NAME,\n" +
                    "            MIN(X.YEAR) OVER (PARTITION BY X.ID) firstYear,\n" +
                    "            MAX(X.YEAR) OVER (PARTITION BY X.ID) lastYear,\n" +
                    "            MAX(X.YEAR) OVER(PARTITION BY X.ID) - MIN(X.YEAR) OVER(PARTITION BY X.ID) yearsInterval \n" +
                    "         FROM\n" +
                    "            (\n" +
                    "               SELECT\n" +
                    "                  P.ID,\n" +
                    "                  P.NAME,\n" +
                    "                  M.YEAR,\n" +
                    "                  ROW_NUMBER() OVER (PARTITION BY P.ID) row_number \n" +
                    "               FROM\n" +
                    "                  PRODUCER P \n" +
                    "                  LEFT JOIN\n" +
                    "                     MOVIE_PRODUCERS MP \n" +
                    "                     ON P.ID = MP.PRODUCER_ID \n" +
                    "                  LEFT JOIN\n" +
                    "                     MOVIE M \n" +
                    "                     ON M.ID = MP.MOVIE_ID \n" +
                    "               WHERE\n" +
                    "                  M.WINNER = TRUE\n" +
                    "            )\n" +
                    "            X \t\t\t\t\n" +
                    "  WHERE X.ROW_NUMBER < 3\n" +
                    "         GROUP BY\n" +
                    "            X.ID,\n" +
                    "            X.NAME,\n" +
                    "            X.YEAR \n" +
                    "         ORDER BY\n" +
                    "            X.ID,\n" +
                    "            X.YEAR \n" +
                    "      )\n" +
                    "      Y \n" +
                    "   WHERE\n" +
                    "      Y.yearsInterval > 0\n" +
                    ")\n" +
                    "SELECT\n" +
                    "   MIN_VALUES.ID,\n" +
                    "   MIN_VALUES.producer,\n" +
                    "   MIN_VALUES.previousWin,\n" +
                    "   MIN_VALUES.followingWin,\n" +
                    "   MIN_VALUES.yearsInterval,\n" +
                    "   'N' MAX \n" +
                    "FROM\n" +
                    "   Query Y \n" +
                    "   LEFT JOIN\n" +
                    "      QUERY MIN_VALUES \n" +
                    "      ON MIN_VALUES.yearsInterval = \n" +
                    "      (\n" +
                    "         SELECT\n" +
                    "            MIN(yearsInterval ) \n" +
                    "         from\n" +
                    "            query\n" +
                    "      )\n" +
                    "GROUP BY\n" +
                    "   MIN_VALUES.ID,\n" +
                    "   MIN_VALUES.producer \n" +
                    "UNION ALL\n" +
                    "SELECT\n" +
                    "   MAX_VALUES.ID,\n" +
                    "   MAX_VALUES.producer,\n" +
                    "   MAX_VALUES.previousWin,\n" +
                    "   MAX_VALUES.followingWin,\n" +
                    "   MAX_VALUES.yearsInterval,\n" +
                    "   'S' MAX\n" +
                    "FROM\n" +
                    "   Query Y \n" +
                    "   LEFT JOIN\n" +
                    "      QUERY MAX_VALUES \n" +
                    "      ON MAX_VALUES.yearsInterval = \n" +
                    "      (\n" +
                    "         SELECT\n" +
                    "            MAX(yearsInterval) \n" +
                    "         from\n" +
                    "            query\n" +
                    "      )\n" +
                    "GROUP BY\n" +
                    "   Max_values.ID,\n" +
                    "   Max_values.producer",
            nativeQuery = true
    )
    List<ProducerIntervalQuery> findSlowerAndFasterWinner();
}
