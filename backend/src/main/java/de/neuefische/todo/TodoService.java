package de.neuefische.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public void createTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public Collection<Todo> getTodos() {
        return todoRepository.findAll()
                .stream()
                .sorted()
                .toList();
    }

    public Todo getTodo(String id) {
        return todoRepository.findById(id) ;
    }

    public void deleteTodo(String id) {
        todoRepository.delete(id);
    }

    public void changeTodo(String id, Todo changedTodo) {
        Todo todo = todoRepository.findById(id);

        todo.setTask(changedTodo.getTask());
        todo.setStatus(changedTodo.getStatus());
        todo.setDescription(changedTodo.getDescription());

        todoRepository.save(todo);
    }

    public void deleteCheckedTodos() {
        todoRepository.findAll()
                .stream()
                .filter(todo -> todo.getStatus() == TodoStatus.Done)
                .toList()
                .forEach(todo -> todoRepository.delete(todo.getId()));
    }
}
