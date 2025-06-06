package ru.dmitriev.lab.java_telg_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmitriev.lab.java_telg_bot.exceptions.SmehsNotFoundExceptions;
import ru.dmitriev.lab.java_telg_bot.model.SmehCall;
import ru.dmitriev.lab.java_telg_bot.model.Smehs;
import ru.dmitriev.lab.java_telg_bot.repository.SmehCallRepository;
import ru.dmitriev.lab.java_telg_bot.repository.SmehsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@RequiredArgsConstructor
@Service
public class SmehsServiceImpl implements SmehsService {

    private final SmehsRepository smehsRepository;

    private final SmehCallRepository smehCallRepository;

    public void logSmehCall(Long userId, Smehs smeh) {
        SmehCall call = new SmehCall(userId, LocalDateTime.now(), smeh);
        smehCallRepository.save(call);
    }

    public Page<Smehs> getSmehsPage(String title, Pageable pageable) {
        if (title != null && !title.isEmpty()) {
            return smehsRepository.findByTitleContainingIgnoreCase(title, pageable);
        }
        return smehsRepository.findAll(pageable);
    }

    public List<Smehs> getTopSmehs(int limit) {
        List<SmehCallRepository.SmehStats> stats = smehCallRepository.findTopSmeh();
        List<Long> smehIds = stats.stream()
                .map(SmehCallRepository.SmehStats::getSmehId)
                .limit(limit)
                .collect(Collectors.toList());
        return StreamSupport.stream(smehsRepository.findAllById(smehIds).spliterator(), false)
                .collect(Collectors.toList());

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
