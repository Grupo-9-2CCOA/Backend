package school.sptech.projeto_extensao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projeto_extensao.dto.PedidoMapper;
import school.sptech.projeto_extensao.dto.PedidoRequestDto;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.service.PedidoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    public final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar(){
        List<Pedido> pedidos = service.listar();
        if (pedidos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(pedidos);
    }

    @GetMapping("/listarPorData")
    public ResponseEntity<List<Pedido>> listarPorData(LocalDateTime dataInicio, LocalDateTime dataFim){
        List<Pedido> pedidos = service.listarPorData(dataInicio, dataFim);
        if (pedidos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> encontrarPorId(@PathVariable Integer id){
        Pedido pedido = service.encontrarPorId(id);
        return ResponseEntity.status(200).body(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> cadastrar(PedidoRequestDto dto){
        Pedido pedido = PedidoMapper.toEntity(dto);
        if (validarPedido(pedido)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(201).body(service.cadastrar(pedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Integer id, @RequestBody PedidoRequestDto dto){
        Pedido pedido = PedidoMapper.toEntity(id, dto);
        if (validarPedido(pedido)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(service.editar(pedido));
    }

    @PutMapping("/deletar/{id}")
    public ResponseEntity<Pedido> deletar(Integer id){
        int deletar = service.deletar(id);
        if (deletar > 0){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

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
