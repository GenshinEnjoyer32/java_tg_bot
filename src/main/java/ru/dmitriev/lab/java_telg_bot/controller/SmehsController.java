package ru.dmitriev.lab.java_telg_bot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.dmitriev.lab.java_telg_bot.model.Smehs;
import ru.dmitriev.lab.java_telg_bot.service.SmehsService;
import ru.dmitriev.lab.java_telg_bot.service.SmehsServiceImpl;

import java.util.List;

@RequestMapping("/api/smehs")
@RestController
public class SmehsController {

    private final SmehsService smehsService;

    @Autowired
    public SmehsController(SmehsServiceImpl smehsService) {
        this.smehsService = smehsService;
    }

    @PostMapping
    public ResponseEntity<Smehs> addSmeh(@RequestBody  Smehs smeh){
        Smehs saved = smehsService.addSmehs(smeh);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);}

    @GetMapping
    public ResponseEntity<List<Smehs>> getAllSmehs(@RequestParam(value = "title", required = false) String title) {
        List<Smehs> smehs = smehsService.getAllSmehs(title);
        return ResponseEntity.ok(smehs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Smehs> getSmehsById(@PathVariable("id") Long id) {
        Smehs smeh = smehsService.getSmehsById(id);
        return ResponseEntity.ok(smeh);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editSmehs(@PathVariable("id") Long id, @RequestBody Smehs smeh) {
        smehsService.editSmehs(id, smeh);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSmehs(@PathVariable("id") Long id) {
        smehsService.deleteSmehs(id);
        return ResponseEntity.ok().build();
    }

}
