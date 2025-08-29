/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import com.github.javafaker.Faker;
import java.util.Random;
import java.util.UUID;
import model.User;
import model.UserType;

/**
 *
 * @author Настя
 */
public class UserFactory {

    private static Faker faker = new Faker();
    private static Random random = new Random();

    public static User createUser() {
        UUID id = UUID.randomUUID();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        UserType userType = UserType.values()[random.nextInt(UserType.values().length)];
        return new User(id, name, email, userType);
    }
}
