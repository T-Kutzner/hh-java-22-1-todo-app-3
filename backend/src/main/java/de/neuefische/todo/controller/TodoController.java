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
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Collection<Todo> addTodo(@RequestBody Todo todo, Principal principal) {
        todoService.addTodo(todo, principal);
        return todoService.getTodos(principal);
    }

    @GetMapping
    public Collection<Todo> getTodos(Principal principal) {
        return todoService.getTodos(principal);
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable String id) {
        return todoService.getTodo(id);
    }

    @PutMapping("/{id}")
    public Collection<Todo> changeTodo(@PathVariable String id, @RequestBody Todo todo, Principal principal) {
        todoService.changeTodo(id, todo);
        return todoService.getTodos(principal);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id, Principal principal) {
        todoService.deleteTodo(id, principal);
    }

    @DeleteMapping()
    public Collection<Todo> deleteTodo(Principal principal) {
        todoService.deleteCheckedTodos();
        return todoService.getTodos(principal);
    }
}
