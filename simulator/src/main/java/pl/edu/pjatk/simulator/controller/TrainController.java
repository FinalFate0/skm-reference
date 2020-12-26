package pl.edu.pjatk.simulator.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.service.TrainService;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/trains")
public class TrainController extends CrudController<Train>{

    public TrainController(TrainService trainService) {
        super(trainService);
    }

    @Override
    public Function<Train, Map<String, Object>> transformToDTO() {
        return train -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", train.getId());
            payload.put("currentStation", train.getCurrentStation());
            payload.put("goingToGdansk", train.isGoingToGdansk());
            payload.put("currentPauseTime", train.getCurrentPauseTime());
            payload.put("compartmentIds", train.getCompartments().stream().map(Compartment::getId));

            return payload;
        };
    }
}
