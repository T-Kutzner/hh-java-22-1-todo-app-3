package de.neuefische.todo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class TodoServiceTest {

    @Test
    void shouldAddNewTodo() {
        Todo todo1 = new Todo();
        todo1.setTask("Putzen");
        todo1.setStatus(TodoStatus.Open);

        TodoRepository repo = Mockito.mock(TodoRepository.class);
        TodoService todoService = new TodoService(repo);

        todoService.createTodo(todo1);

        verify(repo).save(todo1);
    }

    @Test
    void shouldRetrieveAllTodos() {
        Todo todo1 = new Todo();
        todo1.setTask("Putzen");
        todo1.setStatus(TodoStatus.Open);

        Todo todo2 = new Todo();
        todo2.setTask("Putzen");
        todo2.setStatus(TodoStatus.Open);

        List<Todo> todoList = List.of(todo1, todo2);
        TodoRepository repo = Mockito.mock(TodoRepository.class);
        Mockito.when(repo.findAll()).thenReturn(todoList);

        TodoService todoService = new TodoService(repo);

        Collection<Todo> actual = todoService.getTodos();

        assertThat(actual).isEqualTo(todoList);
    }

    @Test
    void shouldRetrieveOneTodo() {
        Todo todo1 = new Todo();
        todo1.setTask("Putzen");
        todo1.setStatus(TodoStatus.Open);

        TodoRepository repo = Mockito.mock(TodoRepository.class);
        Mockito.when(repo.findById(todo1.getId())).thenReturn(Optional.of(todo1));

        TodoService todoService = new TodoService(repo);

        Todo actual = todoService.getTodo(todo1.getId());

        assertThat(actual).isEqualTo(todo1);
    }


    @Test
    void shouldChangeTodo() {
        Todo todo1 = new Todo();
        todo1.setId("4711");
        todo1.setTask("Putzn");
        todo1.setStatus(TodoStatus.Open);

        Todo savedTodo = new Todo();
        savedTodo.setId("4711");
        savedTodo.setTask("Putzen");
        savedTodo.setStatus(TodoStatus.Done);

        TodoRepository repo = Mockito.mock(TodoRepository.class);
        Mockito.when(repo.findById("4711")).thenReturn(Optional.of(todo1));

        TodoService todoService = new TodoService(repo);

        todoService.changeTodo("4711", savedTodo);

        verify(repo).save(savedTodo);
    }
}
