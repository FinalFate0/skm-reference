package pl.edu.pjatk.simulator.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TrainRepositoryTest {

    @Autowired
    private TrainRepository trainRepository;

    @Test
    public void basicTest() {
        Train train = new Train();
        train.setId(1L);
        train.setCurrentStation(Station.GDANSK_OLIWA);
        train.setGoingToGdansk(false);
        train.setCurrentPauseTime(0);
        trainRepository.save(train);

        Train fromDb = trainRepository.getOne(1L);

        assertEquals(train.getId(), fromDb.getId());
    }
}
