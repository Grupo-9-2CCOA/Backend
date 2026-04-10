package school.sptech.projeto_extensao.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.projeto_extensao.controller.enums.PeriodoFiltro;
import school.sptech.projeto_extensao.dto.ClienteDto;
import school.sptech.projeto_extensao.dto.PedidosCanceladosDto;
import school.sptech.projeto_extensao.dto.PedidosReagendadosDto;
import school.sptech.projeto_extensao.dto.RelatorioDto;
import school.sptech.projeto_extensao.service.RelatorioService;

import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    public final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> pegarRelatorio(@NotNull @RequestParam PeriodoFiltro periodo){
        try{
            RelatorioDto relatorio = service.relatorioVendas(periodo);
            return ResponseEntity.status(HttpStatus.OK).body(relatorio);
        }catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/cancelados")
    public ResponseEntity<?> pegarRelatorioCancelados(@NotNull @RequestParam PeriodoFiltro periodo){
        try{
            List<PedidosCanceladosDto> pedidosCanceladosList = service.relatorioCancelados(periodo);
            return ResponseEntity.status(HttpStatus.OK).body(pedidosCanceladosList);
        }catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/reagendados")
    public ResponseEntity<?> pegarRelatorioReagendados(@NotNull @RequestParam PeriodoFiltro periodo) {
        try {
            List<PedidosReagendadosDto> pedidosReagendadosList = service.relatorioReagendados(periodo);
            return ResponseEntity.status(HttpStatus.OK).body(pedidosReagendadosList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<?> pegarRelatorioClientes(@NotNull @RequestParam PeriodoFiltro periodo) {
        try {
            List<ClienteDto> clientesList = service.relatorioClientes(periodo);
            return ResponseEntity.status(HttpStatus.OK).body(clientesList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
