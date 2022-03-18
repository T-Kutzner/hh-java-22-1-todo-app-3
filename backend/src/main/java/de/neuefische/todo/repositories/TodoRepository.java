package de.neuefische.todo.repositories;

import de.neuefische.todo.models.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

    List<Todo> findAllByUserId(String userId);
}