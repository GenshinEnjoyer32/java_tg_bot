package ru.dmitriev.lab.java_telg_bot.exceptions;

import lombok.Getter;

@Getter
public class SmehsNotFoundExceptions extends RuntimeException {

    private final Long id;

    public SmehsNotFoundExceptions(Long id) {
        super("Anecdots not found: " + id);
        this.id=id;
    }

    public Long getId() {
        return id;
    }
}
