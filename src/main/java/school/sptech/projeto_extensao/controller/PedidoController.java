package school.sptech.projeto_extensao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projeto_extensao.dto.pedido.PedidoRequestDto;
import school.sptech.projeto_extensao.dto.pedido.PedidoResponseDto;
import school.sptech.projeto_extensao.dto.pedido.PedidoStatusRequestDto;
import school.sptech.projeto_extensao.mapper.PedidoMapper;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.service.GoogleCalendarService;
import school.sptech.projeto_extensao.service.PedidoService;

import java.time.LocalDateTime;

@Tag(name = "Pedidos", description = "Gestão de pedidos do sistema")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    public final PedidoService service;
    public final GoogleCalendarService googleService;

    @Value("${app.google-calendar.enabled:false}")
    private boolean googleCalendarEnabled;

    public PedidoController(PedidoService service, GoogleCalendarService googleService) {
        this.service = service;
        this.googleService = googleService;
    }

    @Operation(
            summary = "Lista de Pedidos",
            description = "Retorna a lista de todos os pedidos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos com conteúdo",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "204", description = "Lista de pedidos vazia", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<PedidoResponseDto>> listar(
            @ParameterObject
            @PageableDefault(
                    size = 10,
                    page = 0,
                    direction = Sort.Direction.ASC,
                    sort = "dataPedido"
            ) Pageable pageable
    ) {
        Page<Pedido> pedidos = service.listar(pageable);
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(PedidoMapper.toDto(pedidos));
    }

    @Operation(
            summary = "Lista de Pedidos filtrada por Data de Início e Data de Fim",
            description = "Retorna a lista de pedidos filtrados entre uma data de início e uma data de fim"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos com conteúdo",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "204", description = "Lista de pedidos vazia", content = @Content),
            @ApiResponse(responseCode = "404", description = "Período Inválido", content = @Content)
    })
    @GetMapping("/listarData")
    public ResponseEntity<Page<PedidoResponseDto>> listarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @ParameterObject
            @PageableDefault(
                    size = 10,
                    page = 0,
                    direction = Sort.Direction.ASC,
                    sort = "dataPedido"
            ) Pageable pageable
    ) {
        if (dataInicio.isAfter(dataFim)) {
            return ResponseEntity.status(404).build();
        }

        Page<Pedido> pedidos = service.listarPorData(dataInicio, dataFim, pageable);
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(PedidoMapper.toDto(pedidos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> encontrarPorId(@PathVariable Integer id) {
        if (service.encontrarPorId(id) == null) {
            return ResponseEntity.status(404).build();
        }
        Pedido pedido = service.encontrarPorId(id);
        return ResponseEntity.status(200).body(PedidoMapper.toDto(pedido));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> cadastrar(@RequestBody @Valid PedidoRequestDto dto) {
        Pedido pedido = PedidoMapper.toEntity(dto);
        if (!validarPedido(pedido)) return ResponseEntity.status(400).build();

        var pedidoCadastrado = service.cadastrar(pedido);
        return ResponseEntity.status(201).body(PedidoMapper.toDto(pedidoCadastrado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid PedidoRequestDto dto) {
        Pedido pedidoExistente = service.encontrarPorId(id);
        if (pedidoExistente == null) {
            return ResponseEntity.status(404).build();
        }

        Pedido pedido = PedidoMapper.toEntity(id, dto);
        pedido.setIsAtivo(pedidoExistente.getIsAtivo());
        pedido.setEventoGoogleCalendarId(pedidoExistente.getEventoGoogleCalendarId());

        if (!validarPedido(pedido)) return ResponseEntity.status(400).build();

        var pedidoResultado = service.editar(pedido);
        return ResponseEntity.status(200).body(PedidoMapper.toDto(pedidoResultado));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDto> atualizarStatus(
            @PathVariable Integer id,
            @Valid @RequestBody PedidoStatusRequestDto dto) {
        Pedido pedido = service.atualizarStatus(id, dto.getPagamentoId(), dto.getEntregaId());
        return ResponseEntity.ok(PedidoMapper.toDto(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        int deletar = service.deletar(id);
        if (deletar > 0) return ResponseEntity.status(204).build();
        return ResponseEntity.status(404).build();
    }

    public Boolean validarPedido(Pedido pedido) {
        return pedido.getDataPedido() != null &&
                pedido.getCliente() != null &&
                pedido.getEndereco() != null &&
                pedido.getPagamento() != null &&
                pedido.getEntrega() != null &&
                pedido.getValor() != null &&
                pedido.getValor() > 0 &&
                pedido.getProduto() != null &&
                !pedido.getProduto().isBlank();
    }
}