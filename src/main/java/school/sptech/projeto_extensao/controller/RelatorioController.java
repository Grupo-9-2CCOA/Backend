package school.sptech.projeto_extensao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.projeto_extensao.dto.*;
import school.sptech.projeto_extensao.service.RelatorioService;

import java.util.List;

@Tag(name = "Relatório", description = "Endpoints para geração de relatórios de vendas")
@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    public final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @Operation(
            summary = "Relatório geral de vendas",
            description = "Retorna métricas gerais do período informado: total de pedidos, cancelados, reagendados, clientes novos e fidelizados, e os 15 pedidos mais recentes"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = RelatorioDto.class))),
            @ApiResponse(responseCode = "400", description = "Datas do período inválidas ou ausentes", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> pegarRelatorio(
            @Parameter(description = "Período do relatório com data de início e fim", required = true)
            @ModelAttribute @Valid PeriodoFiltroDto periodo) {
        try {
            RelatorioDto relatorio = service.relatorioVendas(periodo);
            return ResponseEntity.status(HttpStatus.OK).body(relatorio);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Relatório de pedidos cancelados",
            description = "Retorna os 15 pedidos cancelados mais recentes dentro do período informado por data de início e fim"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos cancelados retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = PedidosCanceladosDto.class))),
            @ApiResponse(responseCode = "400", description = "Datas do período inválidas ou ausentes", content = @Content)
    })
    @GetMapping("/cancelados")
    public ResponseEntity<?> pegarRelatorioCancelados(
            @Parameter(description = "Período do relatório com data de início e fim", required = true)
            @ModelAttribute @Valid PeriodoFiltroDto periodo) {
        try {
            List<PedidosCanceladosDto> pedidosCanceladosList = service.relatorioCancelados(periodo);
            return ResponseEntity.status(HttpStatus.OK).body(pedidosCanceladosList);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Relatório de pedidos reagendados",
            description = "Retorna os 15 pedidos reagendados mais recentes dentro do período informado por data de início e fim"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos reagendados retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = PedidosReagendadosDto.class))),
            @ApiResponse(responseCode = "400", description = "Datas do período inválidas ou ausentes", content = @Content)
    })
    @GetMapping("/reagendados")
    public ResponseEntity<?> pegarRelatorioReagendados(
            @Parameter(description = "Período do relatório com data de início e fim", required = true)
            @ModelAttribute @Valid PeriodoFiltroDto periodo) {
        try {
            List<PedidosReagendadosDto> pedidosReagendadosList = service.relatorioReagendados(periodo);
            return ResponseEntity.status(HttpStatus.OK).body(pedidosReagendadosList);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Relatório de clientes",
            description = "Retorna os 15 clientes mais recentes que realizaram pelo menos um pedido dentro do período informado por data de início e fim"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ClienteDto.class))),
            @ApiResponse(responseCode = "400", description = "Datas do período inválidas ou ausentes", content = @Content)
    })
    @GetMapping("/clientes")
    public ResponseEntity<?> pegarRelatorioClientes(
            @Parameter(description = "Período do relatório com data de início e fim", required = true)
            @ModelAttribute @Valid PeriodoFiltroDto periodo) {
        try {
            List<ClienteDto> clientesList = service.relatorioClientes(periodo);
            return ResponseEntity.status(HttpStatus.OK).body(clientesList);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}