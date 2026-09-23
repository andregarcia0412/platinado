package me.andregarcia0412.platinado.shared.observer;

import me.andregarcia0412.platinado.modules.user.entities.User;

public class LogUserObserver implements IUserObserver {
    @Override
    public void onUserCreated(User user) {
        System.out.printf(
                "================== NEW USER CREATED ==================\n" +
                        "ID: %d\nUSERNAME: %s\n",
                user.getId(), user.getUsername()
        );
    }

    @Override
    public void onUserDeleted(Integer id) {
        System.out.printf(
                "================== USER DELETED ==================\nID: %d\n",
                id
        );
    }

    @Override
    public void onUserUpdated(User user) {
        System.out.printf(
                "================== USER UPDATED ==================\n" +
                        "ID: %d\nUSERNAME: %s\n",
                user.getId(), user.getUsername()
        );
    }
}
