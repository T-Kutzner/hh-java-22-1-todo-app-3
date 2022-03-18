package de.neuefische.todo.service;

import java.util.Optional;

import de.neuefische.todo.models.UserDocument;
import de.neuefische.todo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDocument createUser(UserDocument user) {
        return userRepository.save(user);
    }

    public Optional<UserDocument> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }
}
