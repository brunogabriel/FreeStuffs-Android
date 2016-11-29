package br.com.friendlydonations.views.actions;

/**
 * Created by brunogabriel on 08/11/16.
 */

public interface BadgeAction {
    /**
     * Notifier badge that number of notifications increase/decrease
     * @param notifications quantity of notifications
     */
    void updateNotifications(Integer notifications);
}
