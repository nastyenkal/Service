package factory;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import mephi.b23902.service.YamlService;
import model.User;
import model.UserType;

public class UserFactory {

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private final YamlService yamlService;

    public UserFactory(YamlService yamlService) {
        this.yamlService = yamlService;
    }

    public List<User> createUsers() {
        List<User> finalUserList = new ArrayList<>();

        List<User> usersFromYaml = yamlService.loadUsersFromFile();
        
        int usersToTakeFromYaml = Math.min(usersFromYaml.size(), 3);
        for (int i = 0; i < usersToTakeFromYaml; i++) {
            finalUserList.add(usersFromYaml.get(i));
        }

        int usersToGenerateWithFaker = 5 - usersToTakeFromYaml;
        for (int i = 0; i < usersToGenerateWithFaker; i++) {
            finalUserList.add(createSingleUserWithFaker());
        }

        return finalUserList;
    }

    private User createSingleUserWithFaker() {
        UUID id = UUID.randomUUID();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        UserType userType = UserType.values()[random.nextInt(UserType.values().length)];
        return new User(id, name, email, userType);
    }
}