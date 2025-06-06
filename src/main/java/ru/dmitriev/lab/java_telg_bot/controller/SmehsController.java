package ru.dmitriev.lab.java_telg_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.dmitriev.lab.java_telg_bot.model.SmehSaveDTO;
import ru.dmitriev.lab.java_telg_bot.model.Smehs;
import ru.dmitriev.lab.java_telg_bot.service.SmehsService;
import ru.dmitriev.lab.java_telg_bot.service.SmehsServiceImpl;
import java.util.List;

@RequestMapping("/api/smehs")
@RequiredArgsConstructor
@RestController
public class SmehsController {

    private final SmehsService smehsService;

    @Operation(
            summary = "Добавить шутку",
            description = "Позволяет добавить новую шутку в базу данных. Возвращает созданный объект шутки.",
            tags = {"smehs", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Шутка успешно создана",
                    content = @Content(schema = @Schema(implementation = Smehs.class), mediaType = "application/json")),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Smehs> addSmehs(@RequestBody SmehSaveDTO smeh) {
        Smehs saved = smehsService.addSmehs(smeh);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(
            summary = "Получить все шутки",
            description = "Возвращает список всех шуток из базы. При указании параметра title — выполняет фильтрацию по заголовку.",
            tags = {"smehs", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список шуток успешно получен",
                    content = @Content(schema = @Schema(implementation = Smehs.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<Smehs>> getAllSmehs(
            @RequestParam(value = "title", required = false) String title) {
        List<Smehs> smehs = smehsService.getAllSmehs(title);
        return ResponseEntity.ok(smehs);
    }

    @Operation(
            summary = "Получить шутку по ID",
            description = "Возвращает шутку по её уникальному идентификатору. Если шутка не найдена — возвращает 404.",
            tags = {"smehs", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно получена",
                    content = @Content(schema = @Schema(implementation = Smehs.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<Smehs> getSmehsById(@PathVariable("id") Long id) {
        Smehs smeh = smehsService.getSmehsById(id);
        return ResponseEntity.ok(smeh);
    }

    @Operation(
            summary = "Редактировать шутку",
            description = "Позволяет изменить содержимое существующей шутки по её ID. При некорректных данных — 400, при отсутствии — 404.",
            tags = {"smehs", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно обновлена",
                    content = @Content(schema = @Schema(implementation = Smehs.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<Smehs> editSmehs(
            @PathVariable("id") Long id,
            @RequestBody SmehSaveDTO smeh) {
        Smehs editedSmeh = smehsService.editSmehs(id, smeh);
        return ResponseEntity.ok(editedSmeh);
    }

    @Operation(
            summary = "Удалить шутку",
            description = "Удаляет шутку по указанному ID. Возвращает подтверждение удаления.",
            tags = {"smehs", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно удалена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSmehs(@PathVariable("id") Long id) {
        smehsService.deleteSmehs(id);
        return ResponseEntity.ok("Шутка успешно удалена");
    }
}
