package pl.edu.pjatk.simulator.service;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TrainServiceTest {

    @Test
    public void basicTest() {
        TrainRepository trainRepository = Mockito.mock(TrainRepository.class);
        CompartmentRepository compartmentRepository = Mockito.mock(CompartmentRepository.class);
        CrudService<Train> trainService = new TrainService(trainRepository, compartmentRepository);

        Train train = new Train();
        train.setId(1L);
        train.setCurrentStation(Station.GDANSK_GLOWNY);
        train.setGoingToGdansk(false);
        train.setCurrentPauseTime(2);

        when(trainRepository.findById(anyLong())).thenReturn(Optional.of(train));
        Train byId = trainService.getById(1L);

        assertEquals(train.getCurrentStation(), byId.getCurrentStation());
    }
}
