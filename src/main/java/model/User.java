package model;

import java.util.UUID;

/**
 *
 * @author Настя
 */
public class User {
    private UUID id;
    private String name;
    private String email;
    private UserType userType;

    public User(UUID id, String name, String email, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "{" + name + "} | {" + email + "}";
    }
    
    
}
