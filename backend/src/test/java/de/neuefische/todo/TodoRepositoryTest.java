package de.neuefische.todo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoRepositoryTest {

    @Test
    void shouldRetrieveAddedTodo() {
        Todo todo = new Todo();
        todo.setTask("Putzen");
        todo.setStatus(TodoStatus.Open);

        TodoRepository repo = new TodoRepository();

        repo.save(todo);

        Todo actual = repo.findById(todo.getId());

        assertThat(actual).isEqualTo(todo);
    }

    @Test
    void shouldRetrieveMultipleTodos() {
        Todo todo1 = new Todo();
        todo1.setTask("Putzen");
        todo1.setStatus(TodoStatus.Open);

        Todo todo2 = new Todo();
        todo2.setTask("Einkaufen");
        todo2.setStatus(TodoStatus.Open);

        TodoRepository repo = new TodoRepository();

        repo.save(todo1);
        repo.save(todo2);

        Collection<Todo> actual = repo.findAll();

        Assertions.assertThat(actual).containsExactlyInAnyOrder(todo1, todo2);
    }

    @Test
    void shouldDeleteTodo() {
        Todo todo1 = new Todo();
        todo1.setTask("Putzen");
        todo1.setStatus(TodoStatus.Open);

        Todo todo2 = new Todo();
        todo2.setTask("Einkaufen");
        todo2.setStatus(TodoStatus.Open);

        Todo todo3 = new Todo();
        todo3.setTask("Gartenarbeit");
        todo3.setStatus(TodoStatus.Open);

        TodoRepository repo = new TodoRepository();
        repo.save(todo1);
        repo.save(todo2);
        repo.save(todo3);

        repo.delete(todo1.getId());
        Collection<Todo> actual = repo.findAll();

        Assertions.assertThat(actual).containsExactlyInAnyOrder(todo2, todo3);
    }

}
