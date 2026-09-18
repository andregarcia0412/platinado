package me.andregarcia0412.pipeline.modules.user.interfaces;

import me.andregarcia0412.pipeline.modules.user.entities.User;

import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    boolean existsById(Integer id);
    boolean existsByEmail(String email);
    void deleteById(Integer id);
}
