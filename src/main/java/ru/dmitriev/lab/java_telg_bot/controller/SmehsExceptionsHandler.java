package ru.dmitriev.lab.java_telg_bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.dmitriev.lab.java_telg_bot.exceptions.ExceptionRespone;
import ru.dmitriev.lab.java_telg_bot.exceptions.SmehsNotFoundExceptions;

@ControllerAdvice
public class SmehsExceptionsHandler {

    @ExceptionHandler(SmehsNotFoundExceptions.class)
    public ResponseEntity<ExceptionRespone> handleSmehsNotFound (SmehsNotFoundExceptions exception) {

        System.out.println("Joke not found with ID: " + exception.getId());
        return ResponseEntity.notFound().build();
    }
}
