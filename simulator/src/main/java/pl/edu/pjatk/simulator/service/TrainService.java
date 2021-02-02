package pl.edu.pjatk.simulator.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Station;
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

            updateEntity.setCurrentStation(Station.GDANSK_GLOWNY);

            var compartments = updateEntity.getCompartments();
            updateEntity.setCompartments(Collections.emptySet());

            Train insertedTrain = repository.save(updateEntity);
            if(!updateEntity.getCompartments().isEmpty()) {
                compartments.forEach(compartment -> compartment.setTrain(insertedTrain));
                compartmentRepository.saveAll(compartments);
            }

            return insertedTrain;
        }

        Optional<Train> trainInDb = repository.findById(updateEntity.getId());

        if (trainInDb.isPresent()) {
            var dbEntity = trainInDb.get();

            dbEntity.setCurrentPauseTime(fallbackIfNull(updateEntity.getCurrentPauseTime(), dbEntity.getCurrentPauseTime()));
            dbEntity.setGoingToGdansk(fallbackIfNull(updateEntity.isGoingToGdansk(), dbEntity.isGoingToGdansk()));

            if(updateEntity.getCompartments() != null) {
                Set<Compartment> compartments = updateEntity.getCompartments();
                compartments.forEach(compartment -> compartment.setTrain(dbEntity));
                compartmentRepository.saveAll(compartments);
            }

            var insertedTrain = repository.save(dbEntity);

            return insertedTrain;
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
