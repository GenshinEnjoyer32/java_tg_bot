package ru.dmitriev.lab.java_telg_bot.repository;

import org.springframework.data.repository.CrudRepository;
import  ru.dmitriev.lab.java_telg_bot.model.Smehs;

public interface SmehsRepository extends CrudRepository<Smehs, Long> {
    List<Smehs> findByTitle(String title);

    Page<Smehs> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Smehs> findAll(Pageable pageable);
}
