package de.neuefische.todo.service;

import java.util.List;

import de.neuefische.todo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public MongoUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .map(userDocument -> new User(userDocument.getEmail(), userDocument.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + userDocument.getRole()))))
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}