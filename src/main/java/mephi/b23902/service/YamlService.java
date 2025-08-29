package mephi.b23902.service;

import com.github.javafaker.Faker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import model.User;
import model.UserType;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class YamlService {

    private final String CONFIG_DIR = "config";

    public void generateAndSaveUsers() {
        Faker faker = new Faker();
        Random random = new Random();
        List<Map<String, Object>> users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Map<String, Object> userData = new LinkedHashMap<>();
            userData.put("id", UUID.randomUUID().toString());
            userData.put("name", faker.name().fullName());
            userData.put("email", faker.internet().emailAddress());
            userData.put("userType", UserType.values()[random.nextInt(UserType.values().length)].toString());
            users.add(userData);
        }

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        
        try {
            Path configPath = Paths.get(CONFIG_DIR);
            if (!Files.exists(configPath)) {
                Files.createDirectories(configPath);
            }
        } catch (IOException e) {
            System.err.println("Не удалось создать директорию 'config': " + e.getMessage());
            return;
        }

        String timeStamp = new SimpleDateFormat("YYYY-MM-DD_HH-MM-SS").format(new Date());
        String fileName = CONFIG_DIR + File.separator + "users_data_" + timeStamp + ".yaml";

        try (FileWriter writer = new FileWriter(fileName)) {
            yaml.dump(users, writer);
            System.out.println("YAML-файл успешно сгенерирован: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи YAML-файла: " + e.getMessage());
        }
    }
    
    public List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();
        File configDir = new File(CONFIG_DIR);

        if (!configDir.exists() || !configDir.isDirectory()) {
            System.err.println("Директория 'config' не найдена.");
            return users;
        }

        File[] files = configDir.listFiles((dir, name) -> name.endsWith(".yaml"));
        if (files == null || files.length == 0) {
            System.err.println("YAML-файлы в директории 'config' не найдены.");
            return users;
        }

        File latestFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (files[i].lastModified() > latestFile.lastModified()) {
                latestFile = files[i];
            }
        }

        System.out.println("Загрузка пользователей из файла: " + latestFile.getName());
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(latestFile)) {
            List<Map<String, String>> data = yaml.load(inputStream);
            for (Map<String, String> userData : data) {
                User user = new User(
                    UUID.fromString(userData.get("id")),
                    userData.get("name"),
                    userData.get("email"),
                    UserType.valueOf(userData.get("userType"))
                );
                users.add(user);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при чтении или парсинге YAML-файла: " + e.getMessage());
        }
        return users;
    }
}