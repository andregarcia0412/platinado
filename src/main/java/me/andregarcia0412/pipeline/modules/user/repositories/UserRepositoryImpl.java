package me.andregarcia0412.pipeline.modules.user.repositories;

import me.andregarcia0412.pipeline.modules.user.interfaces.IUserRepository;

public class UserRepositoryImpl implements IUserRepository {

    private final JpaUserRepository userRepository;

    public UserRepositoryImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
