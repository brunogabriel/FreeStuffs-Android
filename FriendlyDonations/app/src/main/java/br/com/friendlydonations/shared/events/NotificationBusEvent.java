package br.com.friendlydonations.shared.events;

/**
 * Created by brunogabriel on 17/02/17.
 */

public class NotificationBusEvent {
    private int numberOfNotifications;

    public NotificationBusEvent(int numberOfNotifications) {
        this.numberOfNotifications = numberOfNotifications;
    }

    public int getNumberOfNotifications() {
        return numberOfNotifications;
    }

    public void setNumberOfNotifications(int numberOfNotifications) {
        this.numberOfNotifications = numberOfNotifications;
    }
}
