package ru.dmitriev.lab.java_telg_bot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.dmitriev.lab.java_telg_bot.model.Smehs;
import java.util.List;

public interface SmehsService {
    public Smehs addSmehs(Smehs smeh);
    public List<Smehs> getAllSmehs(String title);

    List<Smehs> getTopSmehs(int limit);

    void logSmehCall(Long userId, Smehs smeh);

    Page<Smehs> getSmehsPage(String title, Pageable pageable);

    public Smehs getSmehsById(Long id);

    public Void editSmehs(Long id, Smehs smeh);

    public Void deleteSmehs(Long id);
}
