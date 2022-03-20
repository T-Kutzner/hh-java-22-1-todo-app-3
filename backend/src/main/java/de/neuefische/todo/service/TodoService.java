package de.neuefische.todo.service;

import de.neuefische.todo.models.Todo;
import de.neuefische.todo.models.UserDocument;
import de.neuefische.todo.repositories.TodoRepository;
import de.neuefische.todo.models.TodoStatus;
import de.neuefische.todo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public void addTodo(Todo todo, Principal principal) {
        todo.setUserId(getUserId(principal));
        todoRepository.save(todo);
    }

    public Collection<Todo> getTodos(Principal principal) {
        return todoRepository.findAllByUserId(getUserId(principal));
    }

    public Todo getTodo(String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            return todo.get();
        }
        return new Todo();
    }

    public void deleteTodo(String id, Principal principal) {
            todoRepository.deleteTodoByIdAndUserId(id, getUserId(principal));
    }

    public void changeTodo(String id, Todo changedTodo) {
        Optional<Todo> todo = todoRepository.findById(id);

        if(todo.isPresent()) {
            Todo todo1 = todo.get();

            //todo1.setId(id);
            todo1.setTask(changedTodo.getTask());
            todo1.setDescription(changedTodo.getDescription());
            todo1.setStatus(changedTodo.getStatus());

            todoRepository.save(todo1);
        }
    }

    public void deleteCheckedTodos() {
        todoRepository.findAll()
                .stream()
                .filter(todo -> todo.getStatus() == TodoStatus.DONE)
                .toList()
                .forEach(todo -> todoRepository.delete(todo));
    }

    public List<Todo> findAllByUserId(String email) {
        Optional<UserDocument> document = userRepository.findByEmail(email);
        if(document.isPresent()) {
            return todoRepository.findAllByUserId(document.get().getId());
        }
        throw new IllegalArgumentException("User do not exist.");
    }

    private String getUserId(Principal principal) {
        return userRepository.findByEmail(principal.getName()).get().getId();
    }
}
