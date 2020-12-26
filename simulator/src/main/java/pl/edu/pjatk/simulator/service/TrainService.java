package pl.edu.pjatk.simulator.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class TrainService extends CrudService<Train>{
    private final CompartmentRepository compartmentRepository;

    public TrainService(TrainRepository trainRepository, CompartmentRepository compartmentRepository) {
        super(trainRepository);
        this.compartmentRepository = compartmentRepository;

    }

    public void moveTimeForward() {
        getAll().forEach(Train::move);
        getAll().forEach(train -> repository.save(train));
    }

    @Override
    public Train createOrUpdate(Train updateEntity) {
        if (updateEntity.getId() == null) {
            var compartments = updateEntity.getCompartments();
            updateEntity.setCompartments(Collections.emptySet());
            Train insertedTrain = repository.save(updateEntity);

            compartments.forEach(compartment -> compartment.setTrain(insertedTrain));
            compartmentRepository.saveAll(compartments);

            return insertedTrain;
        }

        Optional<Train> trainInDb = repository.findById(updateEntity.getId());

        if (trainInDb.isPresent()) {
            var dbEntity = trainInDb.get();

            dbEntity.setCurrentStation(fallbackIfNull(updateEntity.getCurrentStation(), dbEntity.getCurrentStation()));
            dbEntity.setCurrentPauseTime(fallbackIfNull(updateEntity.getCurrentPauseTime(), dbEntity.getCurrentPauseTime()));
            dbEntity.setGoingToGdansk(fallbackIfNull(updateEntity.isGoingToGdansk(), dbEntity.isGoingToGdansk()));
            var insertedTrain = repository.save(dbEntity);

            Set<Compartment> compartments = updateEntity.getCompartments();
            compartments.forEach(compartment -> compartment.setTrain(dbEntity));
            compartmentRepository.saveAll(compartments);

            return insertedTrain;
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
