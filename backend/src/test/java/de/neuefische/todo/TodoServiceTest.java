package de.neuefische.todo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;

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
        Mockito.when(repo.findById(todo1.getId())).thenReturn(todo1);

        TodoService todoService = new TodoService(repo);

        Todo actual = todoService.getTodo(todo1.getId());

        assertThat(actual).isEqualTo(todo1);
    }

    @Test
    void shouldDeleteTodo() {
        String id = "4711";

        TodoRepository repo = Mockito.mock(TodoRepository.class);
        TodoService todoService = new TodoService(repo);

        todoService.deleteTodo(id);

        verify(repo).delete(id);
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
        Mockito.when(repo.findById("4711")).thenReturn(todo1);

        TodoService todoService = new TodoService(repo);

        todoService.changeTodo("4711", savedTodo);

        verify(repo).save(savedTodo);
    }

    @Test
    void shouldDeleteCheckedTodos() {
        Todo todo1 = new Todo();
        todo1.setTask("Todo 1");
        todo1.setStatus(TodoStatus.Open);

        Todo todo2 = new Todo();
        todo2.setTask("Todo 2");
        todo2.setStatus(TodoStatus.Done);

        Todo todo3 = new Todo();
        todo3.setTask("Todo 3");
        todo3.setStatus(TodoStatus.Open);

        Todo todo4 = new Todo();
        todo4.setTask("Todo 4");
        todo4.setStatus(TodoStatus.Done);

        List<Todo> todoList = List.of(todo1, todo2, todo3, todo4);
        TodoRepository repo = Mockito.mock(TodoRepository.class);
        Mockito.when(repo.findAll()).thenReturn(todoList);

        TodoService todoService = new TodoService(repo);

        todoService.deleteCheckedTodos();

        verify(repo).delete(todo2.getId());
        verify(repo).delete(todo4.getId());
    }

}
