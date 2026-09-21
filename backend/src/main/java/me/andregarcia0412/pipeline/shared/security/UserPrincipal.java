package me.andregarcia0412.pipeline.shared.security;

import me.andregarcia0412.pipeline.modules.user.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record UserPrincipal(Integer id, String username, String passwordHash) implements UserDetails {

    public static UserPrincipal fromEntity(User user) {
        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
