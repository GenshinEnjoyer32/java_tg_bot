package ru.dmitriev.lab.java_telg_bot.service;

import ru.dmitriev.lab.java_telg_bot.model.User;
import ru.dmitriev.lab.java_telg_bot.model.UserAuthority;

import java.util.List;

public interface UserService {
    User registerNewUser(String username, String rawPassword);

    List<UserAuthority> getUserRoles(Long userId);

    void addRoleToUser(Long userId, UserAuthority role);

    void removeRoleFromUser(Long userId, UserAuthority role);

    void replaceUserRole(Long userId, UserAuthority oldRole, UserAuthority newRole);
}
