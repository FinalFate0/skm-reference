package pl.edu.pjatk.simulator.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;

import java.util.ArrayList;
import java.util.Optional;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class CompartmentService extends CrudService<Compartment> {
    public CompartmentService(CompartmentRepository repository) {
        super(repository);
    }

    @Override
    public Compartment createOrUpdate(Compartment updateEntity) {
        if (updateEntity.getId() == null) {
            return repository.save(updateEntity);
        }

        Optional<Compartment> compartmentInDb = repository.findById(updateEntity.getId());

        if (compartmentInDb.isPresent()) {
            var dbEntity = compartmentInDb.get();

            dbEntity.setTrain(fallbackIfNull(updateEntity.getTrain(), dbEntity.getTrain()));
            dbEntity.setCapacity(fallbackIfNull(updateEntity.getCapacity(), dbEntity.getCapacity()));

            return repository.save(dbEntity);
        } else {
            updateEntity = repository.save(updateEntity);
            return updateEntity;
        }
    }
}
