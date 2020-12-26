package pl.edu.pjatk.simulator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.service.CompartmentService;
import pl.edu.pjatk.simulator.service.CrudService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/compartments")
public class CompartmentController extends CrudController<Compartment> {

    public CompartmentController(CompartmentService compartmentService) {
        super(compartmentService);
    }

    @Override
    public Function<Compartment, Map<String, Object>> transformToDTO() {
        return compartment -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", compartment.getId());
            payload.put("capacity", compartment.getCapacity());
            payload.put("spaceUsed", compartment.getOccupants().size());
            payload.put("occupants", compartment.getOccupants());

            return payload;
        };
    }
}
