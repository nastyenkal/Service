package Strategy;

import model.User;

/**
 *
 * @author Настя
 */
public class SimpleDisplayStrategy implements DisplayStrategy{
    @Override
    public String format(User user) {
        return user.toString();
    }
}
