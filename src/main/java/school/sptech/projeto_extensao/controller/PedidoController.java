package school.sptech.projeto_extensao.controller;

import com.google.api.services.calendar.model.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projeto_extensao.mapper.PedidoMapper;
import school.sptech.projeto_extensao.dto.pedido.PedidoRequestDto;
import school.sptech.projeto_extensao.dto.pedido.PedidoResponseDto;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.service.GoogleCalendarService;
import school.sptech.projeto_extensao.service.PedidoService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    public final PedidoService service;

    public final GoogleCalendarService googleService;

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
    public ResponseEntity<List<PedidoResponseDto>> listar(){
        List<Pedido> pedidos = service.listar();
        if (pedidos.isEmpty()){
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
    public ResponseEntity<List<PedidoResponseDto>> listarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim){
        if (dataInicio.isAfter(dataFim)){
            return ResponseEntity.status(404).build();
        }
        List<Pedido> pedidos = service.listarPorData(dataInicio, dataFim);
        if (pedidos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(PedidoMapper.toDto(pedidos));
    }

    @Operation(
            summary = "Pedido retornado por ID",
            description = "Retorna um pedido através do próprio ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "ID Inválido, Pedido não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> encontrarPorId(@PathVariable Integer id){
        if (service.encontrarPorId(id) == null){
            return ResponseEntity.status(404).build();
        }
        Pedido pedido = service.encontrarPorId(id);
        return ResponseEntity.status(200).body(PedidoMapper.toDto(pedido));
    }

    @Operation(
            summary = "Cadastro de Pedido",
            description = "Cadastra um Pedido no banco de dados conforme Dto e retorna o pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "400", description = "Informações de Pedido inválidas", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDto> cadastrar(@RequestBody PedidoRequestDto dto) throws Exception {
        Pedido pedido = PedidoMapper.toEntity(dto);
        if (!validarPedido(pedido)) return ResponseEntity.status(400).build();
        Event eventoCriado = googleService.criarEventoNaAgenda(PedidoMapper.toGoogleApi(pedido));

        var pedidoCadastrado = service.cadastrar(pedido, eventoCriado);

        PedidoResponseDto pedidoFeito = PedidoMapper.toDto(pedidoCadastrado);
        pedidoFeito.setLinkEvento(eventoCriado.getHtmlLink());

        return ResponseEntity.status(201).body(pedidoFeito);
    }

    @Operation(
            summary = "Atualização de Pedido",
            description = "Atualiza um Pedido no banco de dados conforme Dto e retorna o pedido, além de cadastrar na tabela de Histórico de Pedido uma nova instância com as informações antigas do Pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "Informações de atualização do Pedido inválidas", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> atualizar(@PathVariable Integer id, @RequestBody PedidoRequestDto dto){
        Pedido pedido = PedidoMapper.toEntity(id, dto);
        if (pedido == null || !validarPedido(pedido)) return ResponseEntity.status(404).build();

        var pedidoResultado = service.editar(pedido);
        return ResponseEntity.status(200).body(PedidoMapper.toDto(pedidoResultado));
    }

    @Operation(
            summary = "Deleção de Pedido",
            description = "Realiza um Delete Lógico do Pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido deletado logicamente com sucesso",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "Pedido com id informado não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Pedido> deletar(@PathVariable Integer id){
        int deletar = service.deletar(id);
        if (deletar > 0) return ResponseEntity.status(204).build();
        if (deletar < 0) return ResponseEntity.status(404).build();

        return ResponseEntity.status(502).build();
    }

    @Operation(
            summary = "Validação de Pedido",
            description = "Realiza uma validação das informações recebidas no Dto do Pedido"
    )
    public Boolean validarPedido(Pedido pedido){
        return pedido.getDataPedido() != null &&
                pedido.getDataPedido().isAfter(LocalDateTime.now()) &&
                pedido.getCliente() != null &&
                pedido.getEndereco() != null &&
                pedido.getPagamento() != null &&
                pedido.getEntrega() != null &&
                pedido.getValor() != null &&
                pedido.getValor() > 0 &&
                pedido.getProduto() != null;
    }
}
