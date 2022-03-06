package de.neuefische.todo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void integrationTest() {
        // Erstes TodoItem erstellen
        Todo todo1 = new Todo("Tests schreiben");

        ResponseEntity<Todo[]> response1 = restTemplate.postForEntity("/todos", todo1, Todo[].class);

        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response1.getBody()).containsExactlyInAnyOrder(todo1);

        // Zweites TodoItem erstellen
        Todo todo2 = new Todo("TypeScript lernen");

        ResponseEntity<Todo[]> response2 = restTemplate.postForEntity("/todos", todo2, Todo[].class);

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response2.getBody()).containsExactlyInAnyOrder(todo1, todo2);

        // Erstes TodoItem löschen
        restTemplate.delete("/todos/" + todo1.getId());
        ResponseEntity<Todo[]> response3 = restTemplate.getForEntity("/todos" , Todo[].class);

        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response3.getBody()).containsExactlyInAnyOrder(todo2);

        // Zweites TodoItem ändern
        Todo todoWithChanges = new Todo();
        todoWithChanges.setId(todo2.getId());
        todoWithChanges.setTask("TypeScript verstehen");
        todoWithChanges.setDescription(todo2.getDescription());
        todoWithChanges.setStatus(todo2.getStatus());

        restTemplate.put("/todos/" + todo2.getId(), todoWithChanges);
        ResponseEntity<Todo[]> response4 = restTemplate.getForEntity("/todos" , Todo[].class);

        assertThat(response4.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response4.getBody()).containsExactlyInAnyOrder(todoWithChanges);
    }

}
