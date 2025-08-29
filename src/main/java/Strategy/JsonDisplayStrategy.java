package Strategy;

import model.User;

/**
 *
 * @author Настя
 */
public class JsonDisplayStrategy implements DisplayStrategy{

    @Override
    public String format(User user) {
        return String.format("{\"id\": \"%s\", \"name\": \"%s\", \"email\": \"%s\", \"userType\": \"%s\"}",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserType());
    }
}
