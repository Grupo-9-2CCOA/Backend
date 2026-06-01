package school.sptech.projeto_extensao.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projeto_extensao.dto.EventoCalendarDto;
import school.sptech.projeto_extensao.service.GoogleCalendarService;

import java.util.Map;

@RestController
@RequestMapping("/calendario")
public class GoogleCalendarController {

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarEvento(@RequestBody @Valid EventoCalendarDto dto) {
        try {
            String linkEvento = googleCalendarService.criarEventoNaAgenda(dto);
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Agendamento efetuado com sucesso!",
                    "link", linkEvento
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "Falha interna ao realizar o agendamento no Google",
                    "detalhes", e.getMessage()
            ));
        }
    }
}
