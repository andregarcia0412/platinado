package me.andregarcia0412.platinado.shared.observer;

import me.andregarcia0412.platinado.modules.user.entities.User;

public interface IUserObserver {
    void onUserCreated(User user);
    void onUserDeleted(Integer id);
    void onUserUpdated(User user);
}
