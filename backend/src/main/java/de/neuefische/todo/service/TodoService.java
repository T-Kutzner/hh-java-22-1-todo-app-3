package de.neuefische.todo.service;

import de.neuefische.todo.models.Todo;
import de.neuefische.todo.models.UserDocument;
import de.neuefische.todo.repositories.TodoRepository;
import de.neuefische.todo.TodoStatus;
import de.neuefische.todo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public void createTodo(Todo todo, String email) {
        Optional<UserDocument> userDocument = userRepository.findByEmail(email);
        todo.setUserId(userDocument.get().getId());
        todoRepository.save(todo);
    }

    public Collection<Todo> getTodos() {
        return todoRepository.findAll()
                .stream()
                .sorted()
                .toList();
    }

    public Todo getTodo(String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            return todo.get();
        }
        return new Todo();
    }

    public void deleteTodo(String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            todoRepository.delete(todo.get());
        }
    }

    public void changeTodo(String id, Todo changedTodo) {
        Optional<Todo> todo = todoRepository.findById(id);

        if(todo.isPresent()) {
            Todo todo1 = todo.get();
            todo1.setTask(changedTodo.getTask());
            todo1.setStatus(changedTodo.getStatus());
            todo1.setDescription(changedTodo.getDescription());

            todoRepository.save(todo1);
        }
    }

    public void deleteCheckedTodos() {
        todoRepository.findAll()
                .stream()
                .filter(todo -> todo.getStatus() == TodoStatus.Done)
                .toList()
                .forEach(todo -> todoRepository.delete(todo));
    }

    public List<Todo> findAllByUserId(String email) {
        Optional<UserDocument> document = userRepository.findByEmail(email);
        if(document.isPresent()) {
            return todoRepository.findAllByUserId(document.get().getEmail());
        }
        throw new IllegalArgumentException("User do not exist.");
    }
}
