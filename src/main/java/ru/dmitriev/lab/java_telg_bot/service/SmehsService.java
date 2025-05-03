package ru.dmitriev.lab.java_telg_bot.service;

import ru.dmitriev.lab.java_telg_bot.model.Smehs;
import java.util.List;

public interface SmehsService {
    public Smehs addSmehs(Smehs smeh);
    public List<Smehs> getAllSmehs(String title);


    public Smehs getSmehsById(Long id);

    public Void editSmehs(Long id, Smehs smeh);

    public Void deleteSmehs(Long id);
}
