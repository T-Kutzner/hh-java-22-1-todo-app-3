package de.neuefische.todo.controller;

import de.neuefische.todo.models.Todo;
import de.neuefische.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api/todos")
//@CrossOrigin
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<Todo> createTodo(@RequestBody Todo todo, Principal principal) {
        String email = principal.getName();
        todoService.createTodo(todo, email);
        return todoService.getTodos();
    }

    @GetMapping
    public Collection<Todo> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable String id) {
        return todoService.getTodo(id);
    }

    @PutMapping("/{id}")
    public Collection<Todo> changeTodo(@PathVariable String id, @RequestBody Todo todo) {
        todoService.changeTodo(id, todo);
        return todoService.getTodos();
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
    }

    @DeleteMapping()
    public Collection<Todo> deleteTodo() {
        todoService.deleteCheckedTodos();
        return todoService.getTodos();
    }
}
