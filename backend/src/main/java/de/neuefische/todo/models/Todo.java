package de.neuefische.todo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="todos")
@Data
@NoArgsConstructor
public class Todo implements Comparable<Todo> {

    @Id
    private String id;
    private String task = "";
    private String description = "";
    private TodoStatus status = TodoStatus.OPEN;
    private String userId;

    public Todo(String task) {
        this.task = task;
    }

    @Override
    public int compareTo(Todo todo) {
        if (status == todo.getStatus()) {
            return 0;
        } else if (status == TodoStatus.OPEN) {
            return -1;
        }
        return 1;
    }
}
