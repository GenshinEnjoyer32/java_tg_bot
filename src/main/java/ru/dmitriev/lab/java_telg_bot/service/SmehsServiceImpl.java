package ru.dmitriev.lab.java_telg_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmitriev.lab.java_telg_bot.exceptions.SmehsNotFoundExceptions;
import ru.dmitriev.lab.java_telg_bot.model.Smehs;

import ru.dmitriev.lab.java_telg_bot.repository.SmehsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@RequiredArgsConstructor
@Service
public class SmehsServiceImpl implements SmehsService {

    private final SmehsRepository smehsRepository;

    @Autowired
    public SmehsServiceImpl(SmehsRepository smehsRepository) {
        this.smehsRepository = smehsRepository;
    }

    public Smehs addSmehs(Smehs smeh) {
        return smehsRepository.save(smeh);
    }

    public List<Smehs> getAllSmehs(String title) {
        if (title != null) {
            return StreamSupport.stream(smehsRepository.findAll().spliterator(), false)
                    .filter(smeh -> title.equals(smeh.getTitle()))
                    .collect(Collectors.toList());
        } else {
            return (List<Smehs>) smehsRepository.findAll();
        }
    }

    public Smehs getSmehsById(Long id) {
        Optional<Smehs> smehs = smehsRepository.findById(id);
        if (smehs.isPresent()){
            return smehs.get();
        }
        else {
            throw new SmehsNotFoundExceptions(id) ;
        }
    }

    public Void editSmehs(Long id, Smehs smeh) {
        if (!smehsRepository.existsById(id)) {
            throw new SmehsNotFoundExceptions(id);
        }
        smeh.setId(id);
        smehsRepository.save(smeh);
        return null;
    }

    public Void deleteSmehs(Long id) {
        if (!smehsRepository.existsById(id)) {
            throw new SmehsNotFoundExceptions(id);
        }
        smehsRepository.deleteById(id);
        return null;
    }
}
