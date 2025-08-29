package observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Настя
 */
public class Publisher {

    private final List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers() {
        List<Subscriber> currentSubscribers = new ArrayList<>(subscribers);
        for (Subscriber subscriber : currentSubscribers) {
            subscriber.update();
        }
    }
}
