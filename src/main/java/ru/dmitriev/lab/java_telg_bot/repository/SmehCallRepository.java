package ru.dmitriev.lab.java_telg_bot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dmitriev.lab.java_telg_bot.model.SmehCall;

import java.util.List;

public interface SmehCallRepository extends JpaRepository<SmehCall, Long> {
    public interface SmehStats {
        Long getSmehId();
        Long getCount();

    }

    @Query("SELECT jc.smeh.id as smehId, COUNT(jc) as count FROM smeh_calls jc GROUP BY jc.smeh.id ORDER BY count DESC")
    List<SmehStats> findTopSmeh();
}
