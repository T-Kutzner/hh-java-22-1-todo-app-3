package de.neuefische.todo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class UserDocument {

    @Id
    private String id;
    private String email;
    private String password;
    private UserRole role = UserRole.USER;
}