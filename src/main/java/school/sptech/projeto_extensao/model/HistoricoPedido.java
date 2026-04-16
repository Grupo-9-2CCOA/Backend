package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Schema(description = "Representa o histórico de atualizações de um pedido")
@Entity
public class HistoricoPedido {
    @Schema(description = "ID do histórico de pedido", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @Schema(description = "ID da entrega", example = "1")
    private Pedido idPedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @Schema(description = "Cliente relacionado ao pedido", example = "4")
    private Cliente idCliente;

    @ManyToOne
    @JoinColumn(name = "status_entrega")
    @Schema(description = "Status da entrega na atualização", example = "2")
    private Entrega statusEntrega;

    @ManyToOne
    @JoinColumn(name = "status_pagamento")
    @Schema(description = "Status do pagamento na atualização", example = "3")
    private Pagamento statusPagamento;

    @Schema(description = "Data de criação da atualização do pedido", example = "2026-04-06")
    private LocalDateTime dataCriacao;

    public HistoricoPedido() {
    }

    public HistoricoPedido(Pedido idPedido, Cliente idCliente, Entrega statusEntrega, Pagamento statusPagamento, LocalDateTime dataCriacao) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.statusEntrega = statusEntrega;
        this.statusPagamento = statusPagamento;
        this.dataCriacao = dataCriacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Pedido idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Entrega getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(Entrega statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public Pagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(Pagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
