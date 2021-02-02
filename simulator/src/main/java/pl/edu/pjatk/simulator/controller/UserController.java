package pl.edu.pjatk.simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.edu.pjatk.simulator.model.User;
import pl.edu.pjatk.simulator.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;


@RestController
@RequestMapping("/users")
public class UserController extends CrudController<User> {

    public UserController(UserService userService) {
        super(userService);
    }

    public Function<User, Map<String, Object>> transformToDTO() {
        return user -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", user.getId());
            payload.put("username", user.getUsername());
            payload.put("authorities", user.getAuthoritiesAsString());

            return payload;
        };
    }
}
