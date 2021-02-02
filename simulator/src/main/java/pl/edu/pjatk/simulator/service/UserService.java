package pl.edu.pjatk.simulator.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.User;
import pl.edu.pjatk.simulator.repository.UserRepository;

import java.util.Optional;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class UserService extends CrudService<User> implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        repository.save( new User("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
        repository.save( new User("privilegeduser", passwordEncoder.encode("privilegeduser"), "ROLE_PRIVILEGED"));
        repository.save( new User("user", passwordEncoder.encode("user"), "ROLE_USER"));
    }

    @Override
    public User createOrUpdate(User updateEntity) {
        if (updateEntity.getId() == null) {
            if(updateEntity.getAuthoritiesAsString() == null) {
                GrantedAuthority defaultAuthority = () -> "ROLE_USER";
                updateEntity.addAuthority(defaultAuthority);
            }
            String encodedPassword = passwordEncoder.encode(updateEntity.getPassword());
            updateEntity.setPassword(encodedPassword);

            var insertedUser = repository.save(updateEntity);
            return insertedUser;
        }

        Optional<User> userInDB = repository.findById(updateEntity.getId());

        if (userInDB.isPresent()) {
            var dbEntity = userInDB.get();
            dbEntity.setUsername(fallbackIfNull(updateEntity.getUsername(), dbEntity.getUsername()));
            dbEntity.setAuthorities(fallbackIfNull(updateEntity.getAuthoritiesAsString(), dbEntity.getAuthoritiesAsString()));
            var insertedUser = repository.save(dbEntity);
            return insertedUser;
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return repository.findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny().orElse(null);
    }
}
