package pl.edu.pjatk.simulator.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CompartmentServiceTest {

    @Test
    public void basicTest() {
        CompartmentRepository compartmentRepository = Mockito.mock(CompartmentRepository.class);
        CrudService<Compartment> compartmentService = new CompartmentService(compartmentRepository);

        Compartment compartment = new Compartment();
        compartment.setId(1L);
        compartment.setCapacity(16);
        compartment.setTrain(new Train());

        when(compartmentRepository.findById(anyLong())).thenReturn(Optional.of(compartment));
        Compartment byId = compartmentService.getById(1L);

        assertEquals(compartment.getCapacity(), byId.getCapacity());
    }
}
