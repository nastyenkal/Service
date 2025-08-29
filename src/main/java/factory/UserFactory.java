package factory;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import mephi.b23902.service.YamlService;
import model.DatabaseManager;
import model.User;
import model.UserType;

public class UserFactory {

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private final YamlService yamlService;
    private final DatabaseManager dbManager;
    private final int currentSessionId; 

    public UserFactory(YamlService yamlService, DatabaseManager dbManager, int sessionId) {
        this.yamlService = yamlService;
        this.dbManager = dbManager;
        this.currentSessionId = sessionId;
    }

    public List<User> createUsers() {
        List<User> finalUserList = new ArrayList<>();

        List<User> usersFromYaml = yamlService.loadUsersFromFile();
        
        int usersToTakeFromYaml = Math.min(usersFromYaml.size(), 3);
        for (int i = 0; i < usersToTakeFromYaml; i++) {
            User user = usersFromYaml.get(i);
            finalUserList.add(user);
            dbManager.addUser(user, currentSessionId, "YAML");
        }

        int usersToGenerateWithFaker = 5 - usersToTakeFromYaml;
        for (int i = 0; i < usersToGenerateWithFaker; i++) {
            User user = createSingleUserWithFaker();
            finalUserList.add(user);
            dbManager.addUser(user, currentSessionId, "FAKER");
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