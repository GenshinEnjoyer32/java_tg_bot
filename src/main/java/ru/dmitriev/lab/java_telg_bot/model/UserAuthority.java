package ru.dmitriev.lab.java_telg_bot.model;



import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    USER,
    MODERATOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
