package pl.edu.pjatk.simulator.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Train;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CompartmentRepositoryTest {

    @Autowired
    private CompartmentRepository compartmentRepository;

    @Test
    public void basicTest() {
        Compartment compartment = new Compartment();
        compartment.setId(1L);
        compartment.setCapacity(16);
        compartment.setTrain(new Train());

        compartmentRepository.save(compartment);

        Compartment fromDb = compartmentRepository.getOne(1L);

        assertEquals(compartment.getId(), fromDb.getId());
    }
}


