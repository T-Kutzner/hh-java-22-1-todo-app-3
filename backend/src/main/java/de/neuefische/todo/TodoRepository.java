package de.neuefische.todo;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TodoRepository {

    private Map<String, Todo> todos = new HashMap<>();

    public void save(Todo todo) {
        todos.put(todo.getId(), todo);
    }

    public Todo findById(String id) {
        return todos.get(id);
    }

    public Collection<Todo> findAll() {
        return todos.values();
    }

    public void delete(String id) {
        todos.remove(id);
    }
}
