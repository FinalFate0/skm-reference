package pl.edu.pjatk.simulator.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pjatk.simulator.service.Identifiable;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails, Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String authorities;

    public User() {}

    public User(String username, String password, String authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.authorities.split(",")).map(String::trim).filter(authority -> !authorities.equals("")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    public String getAuthoritiesAsString() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(GrantedAuthority authority) {
        String trimmed = authority.getAuthority().trim();
        String current = this.authorities == null ? "" : (this.authorities + ",");
        this.authorities = current + trimmed;
    }

    public void removeAuthority(GrantedAuthority authority) {
        String trimmed = authority.getAuthority().trim();
        this.authorities = this.authorities.replace(trimmed, "").replace(",,", "").trim();
    }

    public Long getId() {
        return id;
    }
}
